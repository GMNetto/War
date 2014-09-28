package br.uff.es2.war.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import br.uff.es2.war.model.CombatJudge;
import br.uff.es2.war.model.CombatPhase;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.GamePhase;
import br.uff.es2.war.model.ReceiveSoldiersPhase;
import br.uff.es2.war.model.SetupPhase;
import br.uff.es2.war.model.TurnChangePhase;

/**
 * Tests to do:
 * 
 * DEFENDER_WINS_IN_CASE_OF_WITHDRAW
 * DEFENDER_WINS_X_ATTACKERS_Y_DEFENDERS
 * ATTACKER_WINS_X_ATTACKERS_Y_DEFENDERS
 * 
 * Find a way to manipulate the combat results:
 * 	-extending CombatJudge
 * 
 * @author Arthur Pitzer
 *
 */
public class CombatPhaseTest extends GamePhaseTest {
    
    @Override
    protected GamePhase createDependencies() {
	return new GamePhase(){
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
	return new CombatPhase();
    }
    
    @Before
    @Override
    public void resetGame() {
	super.resetGame();
    }
}