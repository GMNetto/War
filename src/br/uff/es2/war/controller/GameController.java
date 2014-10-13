package br.uff.es2.war.controller;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.phases.GameMachine;
import br.uff.es2.war.model.phases.SetupPhase;
import br.uff.es2.war.network.JSONWarProtocol;
import br.uff.es2.war.network.Messenger;
import br.uff.es2.war.network.RemotePlayer;
import br.uff.es2.war.network.WarProtocol;

public class GameController implements Runnable {

    private final Messenger[] clients;
    private final WarProtocol protocol;
    private final GameMachine<Game> machine;

    public GameController(Messenger[] clients) {
	this.clients = clients;
	protocol = new JSONWarProtocol();
	Game game = new Game(loadPlayers(), loadWorld(), loadColors());
	machine = new GameMachine<Game>(game, new SetupPhase());
    }

    @Override
    public void run() {
	machine.run();
    }

    private Player[] loadPlayers() {
	Player[] players = new Player[clients.length];
	for (int i = 0; i < players.length; i++)
	    players[i] = new RemotePlayer(clients[i], protocol);
	return players;
    }

    private World loadWorld() {
	// TODO: load from database
	World world = new World("Test World");

	Continent continentA = new Continent("A", world);
	Continent continentB = new Continent("B", world);
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

    private Color[] loadColors() {
	//TODO: Load colors from database
	Color[] colors = new Color[clients.length];
	for(int i = 0; i < colors.length; i++)
	    colors[i] = new Color("Color " + i);
	return colors;
    }

}
