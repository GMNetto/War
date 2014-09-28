package br.uff.es2.war.model;

import br.uff.es2.war.model.Game;
import br.uff.gamemachine.GameState;

/**
 * Default structure of a game phase test
 * 
 * @author Arthur Pitzer
 */
abstract class GamePhaseTest {
    
    protected final GameState<Game> dependencies;
    protected final GameState<Game> phase;
    protected Game game;
    
    public GamePhaseTest() {
	phase = createTestedPhase();
	dependencies = createDependencies();
    }
    
    public void resetGame(){
	game = new StubGame();
	dependencies.execute(game);
    }
    
    protected abstract GameState<Game> createDependencies();
    
    protected abstract GameState<Game> createTestedPhase();

}
