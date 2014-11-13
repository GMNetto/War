package br.uff.es2.war.view.states;

import br.uff.es2.war.view.GameController;
import br.uff.es2.war.view.GameController2;
import br.uff.es2.war.view.widget.WaitTerritoryStrategy;

/**
 * State where the client is waiting for his turn.
 * @author Arthur Pitzer
 */
public class WaitingState implements ViewState {
    
    @Override
    public void execute(GameController controller) {
	GameController2 controller2 = controller.getGameController2();
	controller2.setAcaoTerr(new WaitTerritoryStrategy(controller2));
    }
}
