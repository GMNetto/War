package br.uff.es2.war.model.phases;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;

/**
 * @author Arthur Pitzer
 */
public class TurnChangePhaseTest extends GamePhaseTest{
    
    @Override
    protected GameState<Game> createDependencies() {
	return new GameState<Game>() {
	    @Override
	    public GameState<Game> execute(Game game) {
		new SetupPhase().execute(game);
		return new TurnChangePhase().execute(game);
	    }
	};
    }
    
    @Override
    protected GameState<Game> createTestedPhase() {
	return new TurnChangePhase();
    }
    
    @Before
    @Override
    public void resetGame() {
	super.resetGame();
    }
    
    @Test
    public void AFTER_TURN_CHANGE_CURRENT_PLAYER_IS_NOT_NULL(){
	phase.execute(game);
	assertNotNull(game.getCurrentPlayer());
    }
    
    @Test
    public void AFTER_TURN_CHANGE_PREVIOUS_PLAYER_IS_NOT_CURRENT_PLAYER(){
	phase.execute(game);
	Player previous = game.getCurrentPlayer();
	phase.execute(game);
	assertFalse(previous == game.getCurrentPlayer());
    }
}