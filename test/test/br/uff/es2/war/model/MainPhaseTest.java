package test.br.uff.es2.war.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MainPhaseTest extends GameTestContext{
    
    public MainPhaseTest() {
	game.setupPhase();
    }
    
    @Test
    public void PLAYER_RECEIVES_RIGHT_QUANTITY_OF_SOLDIERS() throws Exception {
	StubPlayer player = (StubPlayer) players[0];
	int territoryQuantity = world.getTerritoriesByOwner(player).size();
	game.addSoldiers(player);
	assertEquals(territoryQuantity, player.soldierPool());
    }

}
