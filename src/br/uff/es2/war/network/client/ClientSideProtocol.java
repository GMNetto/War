package br.uff.es2.war.network.client;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.events.AddCardEvent;
import br.uff.es2.war.events.AnswerCombatEvent;
import br.uff.es2.war.events.BeginTurnEvent;
import br.uff.es2.war.events.ChooseColorEvent;
import br.uff.es2.war.events.DeclareCombatEvent;
import br.uff.es2.war.events.DistributeSoldiersEvent;
import br.uff.es2.war.events.ExchangeCardsEvent;
import br.uff.es2.war.events.MoveSoldiersEvents;
import br.uff.es2.war.events.SetGameEvent;
import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.Decoder;
import br.uff.es2.war.network.Encoder;
import br.uff.es2.war.network.ProtocolMessages;

public class ClientSideProtocol {
    
    private ProtocolMessages messages;
    private Encoder encoder;
    private Decoder decoder;
    
    public ClientSideProtocol(ProtocolMessages messages, Encoder encoder, Decoder decoder) {
	this.messages = messages;
	this.encoder = encoder;
	this.decoder = decoder;
    }
    
    public String chooseColor(Color color) {
	String colorCode = encoder.encode(color);
	return join(messages.chooseColor(), colorCode);
    }

    public String distributeSoldiers(Set<Territory> territories) {
	String territoriesCode = encoder.encode(territories);
	return join(messages.distributeSoldiers(), territoriesCode);
    }

    public String declareCombat(Combat combat) {
	String combatCode = encoder.encode(combat);
	return join(messages.declareCombat(), combatCode);
    }

    public String answerCombat(Combat combat) {
	String combatCode = encoder.encode(combat);
	return join(messages.answerCombat(), combatCode);
    }
    
    public String finishAttack() {
	return messages.finishAttack();
    }

    public String exchangeCards(List<Card> cards) {
	String cardsCode = encoder.encode(cards);
	return join(messages.exchangeCards(), cardsCode);
    }
    
    private String join(Object... objs) {
	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < objs.length; i++) {
	    builder.append(objs[i]);
	    if (i + 1 < objs.length)
		builder.append(messages.space());
	}
	return builder.toString();
    }

    public Object parseMessage(String message) {
	int spaceIndex = message.indexOf(messages.space());
	if(spaceIndex != -1){
	    String prefix = message.substring(0, spaceIndex);
	    String suffix = message.substring(spaceIndex + 1, message.length());
	    return parseMessage(prefix, suffix);
	}
	return parseMessage(message, "");
    }
    
    private Object parseMessage(String prefix, String suffix){
	if(prefix.equals(messages.chooseColor()))
	    return parseChooseColor(suffix);
	if(prefix.equals(messages.setGame()))
	    return parseSetGame(suffix);
	if(prefix.equals(messages.beginTurn()))
	    return parseBeginTurn(suffix);
	if(prefix.equals(messages.distributeSoldiers()))
	    return parseDistributeSoldiers(suffix);
	if(prefix.equals(messages.declareCombat()))
	    return parseDeclareCombat();
	if(prefix.equals(messages.answerCombat()))
	    return parseAnswerCombat(suffix);
	if(prefix.equals(messages.moveSoldiers()))
	    return parseMoveSoldiers();
	if(prefix.equals(messages.addCard()))
	    return parseAddCard(suffix);
	if(prefix.equals(messages.exchangeCards()))
	    return parseExchangeCard();
	return new Object();
    }

    private Object parseChooseColor(String suffix) {
	Color[] colors = decoder.decodeColors(suffix);
	return new ChooseColorEvent(colors);
    }
    
    private Object parseSetGame(String suffix) {
	Game game = decoder.decodeGame(suffix);
	return new SetGameEvent(game);
    }
    
    private Object parseBeginTurn(String suffix) {
	Player player = decoder.decodePlayer(suffix);
	return new BeginTurnEvent(player);
    }
    
    private Object parseDistributeSoldiers(String suffix){
	int spaceIndex = suffix.indexOf(messages.space());
	int quantity = Integer.parseInt(suffix.substring(0, spaceIndex));
	suffix = suffix.substring(spaceIndex + 1, suffix.length());
	Set<Territory> territories = decoder.decodeTerritories(suffix);
	return new DistributeSoldiersEvent(quantity, territories);
    }
    
    private Object parseDeclareCombat(){
	return new DeclareCombatEvent();
    }

    private Object parseAnswerCombat(String suffix) {
	Combat combat = decoder.decodeCombat(suffix);
	return new AnswerCombatEvent(combat);
    }

    private Object parseMoveSoldiers() {
	return new MoveSoldiersEvents();
    }

    private Object parseAddCard(String suffix) {
	Card card = decoder.decodeCard(suffix);
	return new AddCardEvent(card);
    }

    private Object parseExchangeCard() {
	return new ExchangeCardsEvent();
    }
}
