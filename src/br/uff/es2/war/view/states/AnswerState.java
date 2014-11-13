package br.uff.es2.war.view.states;

import br.uff.es2.war.model.Combat;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.view.GameController;
import br.uff.es2.war.view.GameController2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * State where the player is attack by another player
 *
 * @author Arthur Pitzer
 */
public class AnswerState extends ViewState {

    private GameController controller;
    private GameController2 controller2;
    private Combat resultCombat;

    private Button btn_continua;

    @Override
    protected void innerExecute(final GameController controller) {
        this.controller = controller;
        controller2 = controller.getGameController2();
        controller2.setTextFase(
                resultCombat.getAttackingTerritory().getName() + " atacou " + resultCombat.getDefendingTerritory(),
                "Selecione o territorio onde deseja alocar", "", "");
        btn_continua.setVisible(true);
        this.btn_continua.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                controller.getGameController2().getPlayer().answerCombat(resultCombat);
                btn_continua.setVisible(false);
            }
        });

    }

    public void setCombat(Combat combat) {
        this.resultCombat = combat;
    }

    public void setBtn(Button btn) {
        btn_continua = btn;
    }

}
