package br.uff.es2.war.view.states;

import br.uff.es2.war.model.Combat;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.view.GameController;
import br.uff.es2.war.view.GameController2;

/**
 * State where the player is attack by another player
 * @author Arthur Pitzer
 */
public class AnswerState extends ViewState {
    private GameController controller;
    private GameController2 controller2;
    private Combat resultCombat;
    
    @Override
    protected void innerExecute(GameController controller) {
        this.controller = controller;
	controller2 = controller.getGameController2();
        controller2.setTextFase(
                resultCombat.getAttackingTerritory().getName() + " atacou " + resultCombat.getDefendingTerritory(),
		"Selecione o territorio onde deseja alocar", "", "");
        controller.getGameController2().getPlayer().answerCombat(resultCombat);
    }

    public void setCombat(Combat combat){
        this.resultCombat=combat;
    }

}
