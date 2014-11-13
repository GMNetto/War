package br.uff.es2.war.view.states;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.view.GameController;

/**
 * State where the player make attacks.
 * @author Arthur Pitzer
 */
public class AttackState implements ViewState {

    @Override
    public void execute(GameController controller) {
	throw new NotImplementedException();
    }
}
