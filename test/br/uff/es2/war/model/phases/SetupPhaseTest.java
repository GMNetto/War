package br.uff.es2.war.model.phases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

/**
 * @author Arthur Pitzer
 */
public class SetupPhaseTest extends GamePhaseTest {

    @Override
    protected GameState<Game> createDependencies() {
	return new EmptyPhase();
    }

    @Override
    protected GameState<Game> createTestedPhase() {
	return new SetupPhase();
    }

    @Before
    @Override
    public void resetGame() {
	super.resetGame();
    }

    @Test
    public void PLAYER_CHOOSES_COLOR() throws Exception {
	phase.execute(game);
	for (Player player : game.getPlayers())
	    assertNotNull(player.getColor());
    }

    @Test
    public void ALL_TERRITORIES_HAVE_AT_LEAST_ONE_SOLDIER_AND_A_OWNER()
	    throws Exception {
	phase.execute(game);
	for (Territory territory : game.getWorld().getTerritories()) {
	    assertNotNull(territory.getOwner());
	    assertTrue(territory.getSoldiers() > 0);
	}
    }
}
