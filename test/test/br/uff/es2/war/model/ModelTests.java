package test.br.uff.es2.war.model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

public class ModelTests {

    private final Set<Continent> world;
    
    public ModelTests() {
	world = new HashSet<Continent>();
	
	Continent continentA = new Continent("test world", "A");
	Continent continentB = new Continent("test world", "B");
	Territory territoryA1 = new Territory("A 1");
	Territory territoryA2 = new Territory("A 2");
	Territory territoryB1 = new Territory("B 1");
	Territory territoryB2 = new Territory("B 2");
	
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
    }
    
    @Test
    public void SETTING_UP_A_NEW_GAME() {
	Player[] players = new Player[2];
	for(int i = 0; i < players.length; i++)
	    players[i] = new StubPlayer();
	Game game = new Game(players, world);
	game.setupPhase();
	
	for(Player player : players)
	    assertNotNull(player.getColor());
	for(Continent continent : world){
	    for(Territory territory : continent){
		assertNotNull(territory.getOwner());
		assertTrue(territory.getSoldiers() > 0);
	    }
	}
    }
}
