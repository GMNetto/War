package br.uff.es2.war.view.states;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.view.GameController;

/**
 * State where the player is attack by another player
 * @author Arthur Pitzer
 */
public class DefenseState implements ViewState {
    
    @Override
    public void execute(GameController controller) {
	throw new NotImplementedException();
    }

}
