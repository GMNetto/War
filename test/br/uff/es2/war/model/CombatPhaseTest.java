package br.uff.es2.war.model;

import org.junit.Before;

import br.uff.gamemachine.GameState;

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
    protected GameState<Game> createDependencies() {
	return new GameState<Game>(){
	    @Override
	    public GameState<Game> execute(Game game) {
		new SetupPhase().execute(game);
		new TurnChangePhase().execute(game);
		return new ReceiveSoldiersPhase().execute(game);
	    }
	};
    }
    
    @Override
    protected GameState<Game> createTestedPhase() {
	return new CombatPhase();
    }
    
    @Before
    @Override
    public void resetGame() {
	super.resetGame();
    }
}