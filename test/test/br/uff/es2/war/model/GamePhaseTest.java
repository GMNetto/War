package test.br.uff.es2.war.model;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.GamePhase;

/**
 * Default structure of a game phase test
 * 
 * @author Arthur Pitzer
 */
abstract class GamePhaseTest {
    
    protected final GamePhase dependencies;
    protected final GamePhase phase;
    protected Game game;
    
    public GamePhaseTest() {
	phase = createTestedPhase();
	dependencies = createDependencies();
    }
    
    public void resetGame(){
	game = new StubGame();
	dependencies.execute(game);
    }
    
    protected abstract GamePhase createDependencies();
    
    protected abstract GamePhase createTestedPhase();

}
