package test.br.uff.es2.war.model;

import static org.junit.Assert.*;

import org.junit.Test;

import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

public class SetupPhaseTest extends GameTestContext {

    @Test
    public void PLAYER_CHOOSES_COLOR() throws Exception {
	game.readColors();
	for (Player player : players)
	    assertNotNull(player.getColor());
    }

    @Test
    public void TERRITORIES_HAVE_ONE_SOLDIER_AND_A_OWNER() throws Exception {
	world.distributeTerritories(players);
	for (Territory territory : world.getTerritories()) {
	    assertNotNull(territory.getOwner());
	    assertTrue(territory.getSoldiers() > 0);
	}
    }

}
