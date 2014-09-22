package test.br.uff.es2.war.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.GamePhase;
import br.uff.es2.war.model.ReceiveSoldiersPhase;
import br.uff.es2.war.model.SetupPhase;
import br.uff.es2.war.model.TurnChangePhase;

/**
 * @author Arthur Pitzer
 */
public class ReceiveSoldiersPhaseTest extends GamePhaseTest {
    
    @Override
    protected GamePhase createDependencies() {
	return new GamePhase() {
	    @Override
	    public void execute(Game game) {
		new SetupPhase().execute(game);
		new TurnChangePhase().execute(game);
		new ReceiveSoldiersPhase().execute(game);
	    }
	};
    }
    
    @Override
    protected GamePhase createTestedPhase() {
	return new ReceiveSoldiersPhase();
    }
    
    @Before
    @Override
    public void resetGame() {
	super.resetGame();
    }
    
    @Test
    public void PLAYER_RECEIVES_RIGHT_QUANTITY_OF_SOLDIERS() throws Exception {
	phase.execute(game);
	StubPlayer stub = (StubPlayer)game.getCurrentPlayer();
	int territoryQuantity = game.getWorld().getTerritoriesByOwner(stub).size();
	assertEquals(territoryQuantity, stub.getSoldierPool());
    }
}
