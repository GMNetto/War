package br.uff.es2.war.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Persistence;

import br.uff.es2.war.ai.BasicBot;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.Objective;
import br.uff.es2.war.model.phases.GameMachine;
import br.uff.es2.war.model.phases.SetupPhase;
import br.uff.es2.war.network.Messenger;
import br.uff.es2.war.network.ProtocolFactory;
import br.uff.es2.war.network.server.ServerSidePlayer;
import br.uff.es2.war.network.server.ServerSideProtocol;
import br.uff.es2.war.network.server.WarServer;

public class GameController implements Runnable {

    private final Messenger[] clients;
    private final ServerSideProtocol protocol;
    private final GameMachine<Game> machine;
    private GameLoader loader;
    private GamePersister persister;
    private static int idPlayerGenerator = 0;

    public GameController(Messenger[] clients)
	    throws NonexistentEntityException {
	this.clients = clients;
	protocol = ProtocolFactory.defaultJSONServerSideProtocol();
	Player[] players = new Player[clients.length];
	int i, j;
	for (i = 0; i < players.length; i++)
	    players[i] = new ServerSidePlayer(clients[i], protocol);
	for (; i < WarServer.PLAYER_PER_GAME; i++) {
	    players[i] = new BasicBot();
	}

	// Load from database
	Game game = loadFromDatabase(players); // The
	/*
	 * GameLoader class needs a world ID that I defined as 1 but we still
	 * have to find a way to do it. //Also, the name of the persistence.xml
	 * file will have to be in a file or will we keep it in hardcode?
	 */
	persister = new GamePersister(loader.getiDOfTerritory(),
		getIDPlayers(players), loader.getiDObjectives(),
		loader.getiDOfColor(), game,
		Persistence.createEntityManagerFactory("WarESIIPU"));

	// Load Stubs
	// Game game = createStubGame(players);

	machine = new GameMachine<Game>(game, new SetupPhase());
    }

    private Game loadFromDatabase(Player[] players) {
	loader = new GameLoader(0,
		Persistence.createEntityManagerFactory("WarESIIPU"));

	Color[] colors = new Color[loader.getColors().size()];
	colors = loader.getColors().toArray(colors);
	return new Game(players, loader.getWorld(), colors, loader.getCards(),
		loader.getObjectives());
    }

    private Game createStubGame(Player[] players) {
	World world = loadWorld();
	return new Game(players, world, createColors(),
		createCards(world.getTerritories()),
		createObjectives(players.length));
    }

    private Set<Objective> createObjectives(int length) {
	Set<Objective> objectives = new HashSet<Objective>();
	for (int i = 0; i < length; i++) {
	    objectives.add(new Objective() {

		@Override
		public boolean wasAchieved() {
		    return false;
		}

		@Override
		public void switchToAlternativeObjective() {
		}

		@Override
		public void setOwner(Player owner) {
		}

		@Override
		public boolean isPossible() {
		    return false;
		}

		@Override
		public boolean isNeeded(Territory territory) {
		    return false;
		}
	    });
	}
	return objectives;
    }

    @Override
    public void run() {
	machine.run();
    }

    private Map<Player, Integer> getIDPlayers(Player[] players) {
	Map<Player, Integer> idPlayers = new HashMap<>();
	for (Player player : players) {
	    idPlayers.put(player, idPlayerGenerator++);
	}
	return idPlayers;
    }

    private World loadWorld() {
	// TODO: load from database
	World world = new World("Test World");

	Continent continentA = new Continent("A", world, 2);
	Continent continentB = new Continent("B", world, 3);
	Territory territoryA1 = new Territory("A 1", continentA);
	Territory territoryA2 = new Territory("A 2", continentA);
	Territory territoryB1 = new Territory("B 1", continentB);
	Territory territoryB2 = new Territory("B 2", continentB);

	continentA.add(territoryA1);
	continentA.add(territoryA2);
	continentB.add(territoryB1);
	continentB.add(territoryB2);

	territoryA1.getBorders().add(territoryA2);
	territoryA2.getBorders().add(territoryA1);
	territoryA2.getBorders().add(territoryB1);
	territoryB1.getBorders().add(territoryA2);
	territoryB1.getBorders().add(territoryB2);
	territoryB2.getBorders().add(territoryB1);
	territoryB2.getBorders().add(territoryA1);
	territoryA1.getBorders().add(territoryB2);

	world.add(continentA);
	world.add(continentB);
	return world;

    }

    private Color[] createColors() {
	// TODO: Load colors from database
	Color[] colors = new Color[clients.length];
	for (int i = 0; i < colors.length; i++)
	    colors[i] = new Color("Color " + i);
	return colors;
    }

    private List<Card> createCards(Collection<Territory> territories) {
	List<Card> cards = new LinkedList<Card>();
	cards.add(new Card(4, null));
	cards.add(new Card(4, null));
	int figure = 0;
	for (Territory territory : territories) {
	    cards.add(new Card(figure, territory));
	    figure = figure == 2 ? 0 : figure + 1;
	}
	return cards;
    }
}
