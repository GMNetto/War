package br.uff.es2.war.model;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

/**
 * @author Arthur Pitzer
 */
public class MockGame extends Game {

    public MockGame() {
	super(createPlayers(), createWorld(), createColors());
    }

    private static Player[] createPlayers() {
	Player[] players = new Player[4];
	for (int i = 0; i < players.length; i++)
	    players[i] = new MockPlayer();
	return players;
    }

    private static World createWorld() {
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
    
    private static Color[] createColors(){
	return new Color[]{
		new Color("Blue"),
		new Color("White"),
		new Color("Red"),
		new Color("Green"),
	};
    }
}
