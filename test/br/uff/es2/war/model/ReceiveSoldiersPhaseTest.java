package br.uff.es2.war.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.ReceiveSoldiersPhase;
import br.uff.es2.war.model.SetupPhase;
import br.uff.es2.war.model.TurnChangePhase;
import br.uff.gamemachine.GameState;
import java.util.Set;

/**
 * @author Arthur Pitzer
 */
public class ReceiveSoldiersPhaseTest extends GamePhaseTest {
    
    @Override
    protected GameState<Game> createDependencies() {
	
	return new GameState<Game>() {
	    @Override
	    public GameState<Game> execute(Game context) {
		new SetupPhase().execute(game);
		new TurnChangePhase().execute(game);
		return new ReceiveSoldiersPhase().execute(game);
	    }
	};
    }
    
    @Override
    protected GameState<Game> createTestedPhase() {
	return new ReceiveSoldiersPhase();
    }
    
    @Before
    @Override
    public void resetGame() {
	super.resetGame();
    }
    
    
    private int extraArmyFromContinents(World world, Set<Territory> myTerritories) {
        int extraQTD = 0;
        for (Continent continent : world) {
            if (myTerritories.containsAll(continent)) {
                extraQTD += continent.getTotalityBonus();
            }
        }
        return extraQTD;
    }
    
    
    @Test
    public void PLAYER_RECEIVES_RIGHT_QUANTITY_OF_SOLDIERS() throws Exception {
	phase.execute(game);
	StubPlayer stub = (StubPlayer)game.getCurrentPlayer();
	int territoryQuantity = game.getWorld().getTerritoriesByOwner(stub).size()/2 + extraArmyFromContinents(game.getWorld(), game.getWorld().getTerritoriesByOwner(stub));
	assertEquals(territoryQuantity, stub.getSoldierPool());
    }
}
