package br.uff.es2.war.network.json;

import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.ServerSideWarProtocol;

public class ServerSideJSONWarProtocol implements ServerSideWarProtocol {
    
    private final JSONEncoder encoder;
    private JSONDecoder decoder;
    
    public ServerSideJSONWarProtocol(){
	encoder = new JSONEncoder();
    }

    @Override
    public String setGame(Game game) {
	decoder = new JSONDecoder(game);
	return join(JSONProtocolMessages.SET_GAME, encoder.encode(game));
    }

    @Override
    public String chooseColor(Color[] colors) {
	return join(JSONProtocolMessages.CHOOSE_COLOR, new JSONArray(colors));
    }

    @Override
    public Color chooseColor(String message) {
	String choice = message.substring(JSONProtocolMessages.CHOOSE_COLOR.length());
	JSONObject obj = new JSONObject(choice);
	return new Color(obj.getString("name"));
    }

    @Override
    public String beginTurn(Player current) {
	return join(JSONProtocolMessages.BEGIN_TURN, new JSONObject(current));
    }

    @Override
    public String distributeSoldiers(int soldierQuantity,
	    Set<Territory> territories) {
	JSONArray territoriesJSON = encoder.encode(territories);
	return join(JSONProtocolMessages.DISTRIBUTE_SOLDIERS, soldierQuantity, territoriesJSON);
    }

    @Override
    public void distributeSoldiers(String receive, int soldierQuantity,
	    Set<Territory> territories) {
	receive = receive.substring(JSONProtocolMessages.DISTRIBUTE_SOLDIERS.length());
	decoder.updateTerritories(territories, receive);
    }

    @Override
    public String declareCombat() {
	return JSONProtocolMessages.DECLARE_COMBAT;
    }

    @Override
    public Combat declareCombat(String receive) {
	if(receive.indexOf(JSONProtocolMessages.FINISH_ATTACK) != -1)
	    return null;
	receive = receive.substring(JSONProtocolMessages.DECLARE_COMBAT.length());
	return decoder.decodeCombat(receive);
    }

    @Override
    public String answerCombat(Combat combat) {
	return join(JSONProtocolMessages.ANSWER_COMBAT, encoder.encode(combat));
    }

    @Override
    public void answerCombat(String receive, Combat combat) {
	receive = receive.substring(JSONProtocolMessages.ANSWER_COMBAT.length() + 1).trim();
	Integer soldiers = Integer.parseInt(receive);
	combat.setDefendingSoldiers(soldiers);
    }

    @Override
    public String moveSoldiers() {
	return JSONProtocolMessages.MOVE_SOLDIERS;
    }

    @Override
    public void moveSoldiers(String receive, Set<Territory> territories) {
	receive = receive.substring(JSONProtocolMessages.MOVE_SOLDIERS.length());
	decoder.updateTerritories(territories, receive);
    }

    @Override
    public String addCard(Card card) {
	return join(JSONProtocolMessages.ADD_CARD, new JSONObject(card));
    }

    @Override
    public String exchangeCards() {
	return JSONProtocolMessages.EXCHANGE_CARDS;
    }

    @Override
    public List<Card> exchangeCards(String receive) {
	receive = receive.substring(JSONProtocolMessages.EXCHANGE_CARDS.length());
	return decoder.decodeCards(receive);
    }

    private String join(Object... objs) {
	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < objs.length; i++) {
	    builder.append(objs[i]);
	    if (i + 1 < objs.length)
		builder.append(JSONProtocolMessages.SPACE);
	}
	return builder.toString();
    }

}
