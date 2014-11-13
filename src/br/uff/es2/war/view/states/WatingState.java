package br.uff.es2.war.view.states;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.view.GameController;

/**
 * State where the client is waiting for his turn.
 * @author Arthur Pitzer
 */
public class WatingState implements ViewState {
    
    @Override
    public void execute(GameController controller) {
	throw new NotImplementedException();
    }
}