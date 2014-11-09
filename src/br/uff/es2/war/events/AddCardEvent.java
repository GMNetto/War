package br.uff.es2.war.events;

import br.uff.es2.war.model.Card;

public class AddCardEvent {
    
    private final Card card;
    
    public AddCardEvent(Card card){
	this.card = card;
    }
    
    public Card getCard() {
	return card;
    }
}
