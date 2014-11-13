package br.uff.es2.war.view.states;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.view.GameController;

/**
 * State where the game is finished and the winner is displayed
 * @author Arthur Pitzer
 */
public class GameOverState implements ViewState {
    
    @Override
    public void execute(GameController controller) {
	throw new NotImplementedException();
    }
}
