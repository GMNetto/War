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
import br.uff.es2.war.ai.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.ai.strategies.OffensiveTerritoryValue;
import br.uff.es2.war.ai.strategies.WeightEquationTerritoryValue;
import br.uff.es2.war.ai.strategies.WinLoseTerritoryValue;
import br.uff.es2.war.ai.strategies.attack.BestEffortAttackStrategy;
import br.uff.es2.war.ai.strategies.attack.allocation.WeightedRandomAllocationStrategy;
import br.uff.es2.war.ai.strategies.cardchange.GreedyChangeCardStrategy;
import br.uff.es2.war.ai.strategies.rearrange.FunctionBasedRearrangeStrategy;
import br.uff.es2.war.ai.strategies.rearrange.thresholdfunction.LinearThresholdFunction;
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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.InvalidAttributeValueException;

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
	Player[] players = new Player[WarServer.PLAYER_PER_GAME];
	int i,j;
	for (i = 0; i < clients.length; i++)
	    players[i] = new ServerSidePlayer(clients[i], protocol);
        j=i;
	for (; i < WarServer.PLAYER_PER_GAME; i++) 
	    players[i] = new BasicBot();

	// Load from database
//	Game game = loadFromDatabase(players); // The
//	/*
//	 * GameLoader class needs a world ID that I defined as 1 but we still
//	 * have to find a way to do it. //Also, the name of the persistence.xml
//	 * file will have to be in a file or will we keep it in hardcode?
//	 */
//	persister = new GamePersister(loader.getiDOfTerritory(),
//		getIDPlayers(players), loader.getiDObjectives(),
//		loader.getiDOfColor(), game,
//		Persistence.createEntityManagerFactory("WarESIIPU"));

	// Load Stubs
	WinLoseTerritoryValue winLoseTerritoryValue;
        WeightEquationTerritoryValue weightEquationTerritoryValue = null;
        WeightedRandomAllocationStrategy weightedRandomAllocation;
        OffensiveTerritoryValue offensiveTerritoryValue;
        FunctionBasedRearrangeStrategy functionBasedRearrangeStrategy;
        AttackProbabilityFactory afp = new AttackProbabilityFactory();
        Random r = new Random();
        Game game = createStubGame(players);
        for (; j < WarServer.PLAYER_PER_GAME; j++) {
            winLoseTerritoryValue = new WinLoseTerritoryValue(game, players[j], afp);
            try {
                weightEquationTerritoryValue = new WeightEquationTerritoryValue(game, players[j], 0.9, 0.1, 0.15, 0.1, 0.1, 0.3);
            } catch (InvalidAttributeValueException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            offensiveTerritoryValue = new OffensiveTerritoryValue(winLoseTerritoryValue);
            weightedRandomAllocation = new WeightedRandomAllocationStrategy(offensiveTerritoryValue, weightEquationTerritoryValue, winLoseTerritoryValue);
            functionBasedRearrangeStrategy = new FunctionBasedRearrangeStrategy(new LinearThresholdFunction(), players[j], game, winLoseTerritoryValue, weightEquationTerritoryValue);

            ((BasicBot)players[j]).setAllocationInstruction(weightedRandomAllocation);
            try {
                ((BasicBot)players[j]).setAttackStrategy(new BestEffortAttackStrategy(players[j], game, winLoseTerritoryValue, weightEquationTerritoryValue, (r.nextInt(10) + 1) / 10.0));
            } catch (InvalidAttributeValueException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((BasicBot)players[j]).setRelocationStrategy(functionBasedRearrangeStrategy);
            ((BasicBot)players[j]).setChangeCardStrategy(new GreedyChangeCardStrategy(0, players[j], game, weightEquationTerritoryValue));
        }
         
	machine = new GameMachine<Game>(game, new SetupPhase());
    }

    private Game loadFromDatabase(Player[] players) throws NonexistentEntityException {
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

	Continent continent1 = new Continent("América do Norte", world, 5);
	Continent continent2 = new Continent("América do Sul", world, 2);
        Continent continent3 = new Continent("Europa", world, 5);
        Continent continent4 = new Continent("África", world, 3);
        Continent continent5 = new Continent("Ásia", world, 7);
        Continent continent6 = new Continent("Oceania", world, 2);
        
	Territory territory1 = new Territory("Alaska", continent1);
        territory1.setX(143);territory1.setY(76);
        Territory territory2 = new Territory("Mackenzie", continent1);
        territory2.setX(205);territory2.setY(100);
        Territory territory3 = new Territory("Groelândia", continent1);
        territory3.setX(460);territory3.setY(85);
        Territory territory4 = new Territory("Labrador", continent1);
        territory4.setX(316);territory4.setY(200);
        Territory territory5 = new Territory("Ottawa", continent1);
        territory5.setX(250);territory5.setY(160);
        Territory territory6 = new Territory("Vancover", continent1);
        territory6.setX(175);territory6.setY(150);
        Territory territory7 = new Territory("California", continent1);
        territory7.setX(120);territory7.setY(230);
        Territory territory8 = new Territory("Nova York", continent1);
        territory8.setX(240);territory8.setY(250);
        Territory territory9 = new Territory("México", continent1);
        territory9.setX(140);territory9.setY(350);
        
        Territory territory10 = new Territory("Venezuela", continent2);
        territory10.setX(235);territory10.setY(420);
        Territory territory11 = new Territory("Peru", continent2);
        territory11.setX(245);territory11.setY(530);
        Territory territory12 = new Territory("Brasil", continent2);
        territory12.setX(350);territory12.setY(525);
        Territory territory13 = new Territory("Argentina", continent2);
        territory13.setX(280);territory13.setY(640);
        
        Territory territory14 = new Territory("Islândia", continent3);
        territory14.setX(535);territory14.setY(145);
        Territory territory15 = new Territory("Inglaterra", continent3);
        territory15.setX(515);territory15.setY(195);
        Territory territory16 = new Territory("França", continent3);
        territory16.setX(600);territory16.setY(255);
        Territory territory17 = new Territory("Polônia", continent3);
        territory17.setX(650);territory17.setY(210);
        Territory territory18 = new Territory("Moscou", continent3);
        territory18.setX(735);territory18.setY(180);
        Territory territory19 = new Territory("Alemanha", continent3);
        territory19.setX(620);territory19.setY(210);
        Territory territory21 = new Territory("Suécia", continent3);
        territory21.setX(625);territory21.setY(110);
        
        Territory territory22 = new Territory("Argélia", continent4);
        territory22.setX(565);territory22.setY(375);
        Territory territory23 = new Territory("Egito", continent4);
        territory23.setX(670);territory23.setY(350);
        Territory territory24 = new Territory("Sudão", continent4);
        territory24.setX(700);territory24.setY(425);
        Territory territory25 = new Territory("Congo", continent4);
        territory25.setX(660);territory25.setY(520);
        Territory territory26 = new Territory("África do Sul", continent4);
        territory26.setX(670);territory26.setY(620);
        Territory territory27 = new Territory("Madagascar", continent4);
        territory27.setX(780);territory27.setY(570);
        
        Territory territory28 = new Territory("Dudinka", continent5);
        territory28.setX(870);territory28.setY(90);
        Territory territory29 = new Territory("Omsk", continent5);
        territory29.setX(830);territory29.setY(150);
        Territory territory30 = new Territory("Aral", continent5);
        territory30.setX(845);territory30.setY(250);
        Territory territory31 = new Territory("Oriente Médio", continent5);
        territory31.setX(765);territory31.setY(340);
        Territory territory32 = new Territory("Sibéria", continent5);
        territory32.setX(965);territory32.setY(115);
        Territory territory33 = new Territory("Tchita", continent5);
        territory33.setX(955);territory33.setY(175);
        Territory territory34 = new Territory("Mongólia", continent5);
        territory34.setX(1000);territory34.setY(240);
        Territory territory35 = new Territory("China", continent5);
        territory35.setX(1040);territory35.setY(295);
        Territory territory36 = new Territory("India", continent5);
        territory36.setX(920);territory36.setY(350);
        Territory territory37 = new Territory("Vietnã", continent5);
        territory37.setX(1045);territory37.setY(395);
        Territory territory38 = new Territory("Vladvostok", continent5);
        territory38.setX(1095);territory38.setY(115);
        Territory territory39 = new Territory("Japão", continent5);
        territory39.setX(1220);territory39.setY(240);
        
        Territory territory40 = new Territory("Sumatra", continent6);
        territory40.setX(1010);territory40.setY(530);
        Territory territory41 = new Territory("Borneo", continent6);
        territory41.setX(1080);territory41.setY(510);
        Territory territory42 = new Territory("Nova Guiné", continent6);
        territory42.setX(1195);territory42.setY(570);
        Territory territory43 = new Territory("Austrália", continent6);
        territory43.setX(1085);territory43.setY(670);
        
        
        continent1.add(territory1);
        continent1.add(territory2);
        continent1.add(territory3);
        continent1.add(territory4);
        continent1.add(territory5);
        continent1.add(territory6);
        continent1.add(territory7);
        continent1.add(territory8);
        continent1.add(territory9);
        
	continent2.add(territory10);
        continent2.add(territory11);
        continent2.add(territory12);
        continent2.add(territory13);
        
	continent3.add(territory14);
        continent3.add(territory15);
        continent3.add(territory16);
        continent3.add(territory17);
        continent3.add(territory18);
        continent3.add(territory19);
        continent3.add(territory21);
        
	continent4.add(territory22);
        continent4.add(territory23);
        continent4.add(territory24);
        continent4.add(territory25);
        continent4.add(territory26);
        continent4.add(territory27);
        
        continent5.add(territory28);
        continent5.add(territory29);
        continent5.add(territory30);
        continent5.add(territory31);
        continent5.add(territory32);
        continent5.add(territory33);
        continent5.add(territory34);
        continent5.add(territory35);
        continent5.add(territory36);
        continent5.add(territory37);
        continent5.add(territory38);
        continent5.add(territory39);
        
        continent6.add(territory40);
        continent6.add(territory41);
        continent6.add(territory42);
        continent6.add(territory43);

        territory1.getBorders().add(territory2);
        territory1.getBorders().add(territory6);
        territory1.getBorders().add(territory38);
        
        territory2.getBorders().add(territory2);
        territory2.getBorders().add(territory6);
        territory2.getBorders().add(territory3);
        territory2.getBorders().add(territory5);
        
        territory3.getBorders().add(territory2);
        territory3.getBorders().add(territory4);
        territory3.getBorders().add(territory14);
        
        territory4.getBorders().add(territory3);
        territory4.getBorders().add(territory5);
        territory4.getBorders().add(territory8);
        
        territory5.getBorders().add(territory2);
        territory5.getBorders().add(territory4);
        territory5.getBorders().add(territory6);
        territory5.getBorders().add(territory7);
        territory5.getBorders().add(territory8);
        
        territory6.getBorders().add(territory1);
        territory6.getBorders().add(territory2);
        territory6.getBorders().add(territory5);
        territory6.getBorders().add(territory7);
        
        
        territory7.getBorders().add(territory6);
        territory7.getBorders().add(territory5);
        territory7.getBorders().add(territory8);
        territory7.getBorders().add(territory9);
        
        territory8.getBorders().add(territory4);
        territory8.getBorders().add(territory5);
        territory8.getBorders().add(territory7);
        territory8.getBorders().add(territory9);
        
        territory9.getBorders().add(territory7);
        territory9.getBorders().add(territory8);
        territory9.getBorders().add(territory10);
        
        territory10.getBorders().add(territory9);
        territory10.getBorders().add(territory11);
        territory10.getBorders().add(territory12);
        
        territory11.getBorders().add(territory10);
        territory11.getBorders().add(territory12);
        territory11.getBorders().add(territory13);
        
        territory12.getBorders().add(territory10);
        territory12.getBorders().add(territory11);
        territory12.getBorders().add(territory13);
        territory12.getBorders().add(territory22);
        
        territory13.getBorders().add(territory11);
        territory13.getBorders().add(territory12);
        
        territory14.getBorders().add(territory2);
        territory14.getBorders().add(territory15);
        territory14.getBorders().add(territory21);
        
        territory15.getBorders().add(territory14);
        territory15.getBorders().add(territory21);
        territory15.getBorders().add(territory16);
        territory15.getBorders().add(territory19);
        
        territory16.getBorders().add(territory15);
        territory16.getBorders().add(territory19);
        territory16.getBorders().add(territory17);
        territory16.getBorders().add(territory22);
        
        territory17.getBorders().add(territory19);
        territory17.getBorders().add(territory16);
        territory17.getBorders().add(territory18);
        territory17.getBorders().add(territory23);
        territory17.getBorders().add(territory31);
        
        territory18.getBorders().add(territory17);
        territory18.getBorders().add(territory31);
        territory18.getBorders().add(territory21);
        territory18.getBorders().add(territory29);
        territory18.getBorders().add(territory30);
        
      
	world.add(continent1);
	world.add(continent2);
        world.add(continent3);
        world.add(continent4);
        world.add(continent5);
        world.add(continent6);
	return world;

    }

    private Color[] createColors() {
	// TODO: Load colors from database
	Color[] colors = new Color[]{
		new Color("Branco"),
		new Color("Preto"),
		new Color("Vermelho"),
		new Color("Amarelo"),
		new Color("Azul"),
		new Color("Verde")
	};
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
