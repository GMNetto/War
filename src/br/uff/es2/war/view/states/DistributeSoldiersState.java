package br.uff.es2.war.view.states;

import br.uff.es2.war.view.GameController;
import br.uff.es2.war.view.GameController2;
import br.uff.es2.war.view.widget.AllocTerritoryStrategy;

/**
 * State where the player distribute their soldiers on the map
 * @author Arthur Pitzer
 */
public class DistributeSoldiersState implements ViewState {

    @Override
    public void execute(GameController controller) {
	GameController2 controller2 = controller.getGameController2();
	controller2.setAcaoTerr(new AllocTerritoryStrategy(controller2));
    }
}
