package br.uff.es2.war.view.states;

import java.util.ArrayList;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.network.client.ClientSidePlayer;
import br.uff.es2.war.view.GameController;

public class ExchangeCardsState implements ViewState {

    @Override
    public void execute(GameController controller) {
	System.out.println("ExchangeCardsState");
	ClientSidePlayer player = controller.getGameController2().getPlayer();
	player.exchangeCards(new ArrayList<Card>());
    }

}
