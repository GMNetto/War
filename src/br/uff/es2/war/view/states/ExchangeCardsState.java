package br.uff.es2.war.view.states;

import java.util.ArrayList;
import java.util.List;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.phases.ReceiveSoldiersPhase;
import br.uff.es2.war.view.GameController;
import br.uff.es2.war.view.PopUpController;

public class ExchangeCardsState extends ViewState {

    @Override
    protected void innerExecute(GameController controller) {
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
        }else{
            controller.getGameController2().getPlayer().exchangeCards(new ArrayList<Card>());
        }
    }
}
