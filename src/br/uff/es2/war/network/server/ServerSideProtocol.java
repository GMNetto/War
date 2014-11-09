package br.uff.es2.war.network.server;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.Decoder;
import br.uff.es2.war.network.Encoder;
import br.uff.es2.war.network.ProtocolMessages;

/**
 * Formats messages sent by the server to the clients, and 
 * synchronizes the server model with the messages sent by the clients.
 * 
 * @author Arthur Pitzer
 */
public class ServerSideProtocol {
    
    private ProtocolMessages messages;
    private Encoder encoder;
    private Decoder decoder;
    
    public ServerSideProtocol(ProtocolMessages messages, Encoder encoder, Decoder decoder) {
	this.messages = messages;
	this.encoder = encoder;
	this.decoder = decoder;
    }
    
    public String chooseColor(Color[] colors){
	String colorsCode = encoder.encode(colors);
	return join(messages.chooseColor(), colorsCode);
    }
    
    public Color chooseColor(String code){
	code = code.substring(messages.chooseColor().length());
	return decoder.decodeColor(code);
    }
    
    public String setGame(Game game){
	String gameCode = encoder.encode(game);
	return join(messages.setGame(), gameCode);
    }
    
    public String beginTurn(Player current){
	String playerCode = encoder.encode(current);
	return join(messages.beginTurn(), playerCode);
    }

    public String distributeSoldiers(int soldierQuantity, Set<Territory> territories){
	String territoriesCode = encoder.encode(territories);
	return join(messages.distributeSoldiers(), soldierQuantity, territoriesCode);
    } 

    public void distributeSoldiers(String code, int soldierQuantity,
	    Set<Territory> territories){
	code = code.substring(messages.distributeSoldiers().length());
	Set<Territory> receivedTerritories = decoder.decodeTerritories(code);
	updateTerritories(territories, receivedTerritories);
    }

    public String declareCombat(){
	return messages.declareCombat();
    }

    Combat declareCombat(String code){
	code = code.substring(messages.declareCombat().length());
	return decoder.decodeCombat(code);
    }

    String answerCombat(Combat combat){
	String combatCode = encoder.encode(combat);
	return join(messages.answerCombat(), combatCode);
    }

    void answerCombat(String code, Combat combat){
	code = code.substring(messages.answerCombat().length());
	Combat receivedCombat = decoder.decodeCombat(code);
	combat.setDefendingSoldiers(receivedCombat.getDefendingSoldiers());
    }

    String moveSoldiers(){
	return messages.moveSoldiers();
    }

    void moveSoldiers(String code, Set<Territory> territories){
	code = code.substring(messages.moveSoldiers().length());
	Set<Territory> receivedTerritories = decoder.decodeTerritories(code);
	updateTerritories(territories, receivedTerritories);
    }

    String addCard(Card card){
	String cardCode = encoder.encode(card);
	return join(messages.addCard(), cardCode);
    }

    String exchangeCards(){
	return messages.exchangeCards();
    }

    List<Card> exchangeCards(String code){
	code = code.substring(messages.exchangeCards().length());
	return decoder.decodeCards(code);
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
    
    private void updateTerritories(Set<Territory> a, Set<Territory> b){
	a.retainAll(b);
	for(Territory itemA : a){
	    Territory lastB = null;
	    for(Territory itemB : b){
		if(itemA.equals(itemB)){
		    lastB = itemB;
		    itemA.setSoldiers(itemB.getSoldiers());
		    break;
		}
	    }
	    b.remove(lastB);
	}
    }

    public Combat finishAttack() {
	return null;
    }
}
