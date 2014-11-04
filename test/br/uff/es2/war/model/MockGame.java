package br.uff.es2.war.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.Objective;

/**
 * @author Arthur Pitzer
 */
public class MockGame extends Game {

    private static final int QTD_PLAYERS = 4;
    private static World WORLD;

    public MockGame() {
	super(createPlayers(), createWorld(), createColors(), createCards(), createObjectives());
    }
    
    public MockGame(Player[] player, World world, Color[] colors, List<Card> cards){
	super(player, world, colors, cards);
    }
    
    public MockGame(Player[] player, World world, Color[] colors, List<Card> cards, Set<Objective> objectives){
	super(player, world, colors, cards, objectives);
    }

    public static Player[] createPlayers() {
	Player[] players = new Player[QTD_PLAYERS];
	for (int i = 0; i < players.length; i++)
	    players[i] = new MockPlayer();
	return players;
    }
    
    public static Player[] createPlayers(int numberOfPlayers) {
	Player[] players = new Player[numberOfPlayers];
	for (int i = 0; i < players.length; i++)
	    players[i] = new MockPlayer();
	return players;
    }

    public static World createWorld() {
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
	WORLD = world;
	return world;
    }

    public static Color[] createColors() {
	return new Color[] { new Color("Blue"), new Color("White"),
		new Color("Red"), new Color("Green"), };
    }

    public static List<Card> createCards() {
	List<Card> cards = new LinkedList<Card>();
	cards.add(new Card(4, null));
	cards.add(new Card(4, null));
	int figure = 0;
	for (Territory territory : WORLD.getTerritories()) {
	    cards.add(new Card(figure, territory));
	    figure = figure == 2 ? 0 : figure + 1;
	}
	return cards;
    }
    
    public static Set<Objective> createObjectives(){
	Set<Objective> objectives = new HashSet<>();
	for (int i = 0; i < QTD_PLAYERS; i++)
	    objectives.add(new MockObjective());
	return objectives;
    }
    
    private static class MockObjective implements Objective{

        @Override
        public boolean wasAchieved() {
            return false;
        }

        @Override
        public boolean isNeeded(Territory territory) {
            return false;
        }

        @Override
        public void setOwner(Player owner) {
        }
    }
}