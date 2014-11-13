package br.uff.es2.war.view.states;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.phases.ReceiveSoldiersPhase;
import static br.uff.es2.war.model.phases.ReceiveSoldiersPhase.checkExchange;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.view.GameController;
import br.uff.es2.war.view.GameController2;
import br.uff.es2.war.view.PopUpController;
import java.util.ArrayList;
import java.util.List;

public class ExchangeCardsState implements ViewState {

    @Override
    public void execute(GameController controller) {
        PopUpController pc = controller.getGameController2().getPopUpController();
        List<Card> cartas = pc.getCartasTroca();

        if (ReceiveSoldiersPhase.checkExchange(pc.getCartas())) {
            // é possível fazer trocas
            pc.atualizaCartas();
            pc.mostraCartas();
            if (pc.getCartas().size() == 5) {
                //obrigatório trocar
                pc.bloqueiaParaTroca();
            } else {
                //Pode realizar trocas
                pc.trocarCartas();
            }

        }

    }

}
