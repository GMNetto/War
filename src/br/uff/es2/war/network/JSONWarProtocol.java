package br.uff.es2.war.network;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

public class JSONWarProtocol implements WarProtocol {

    private static final String SPACE = " ";
    private static final String CHOOSE_COLOR = "CHOOSE_COLOR";
    private static final String BEGIN_TURN = "BEGIN_TURN";
    private static final String DISTRIBUTE_SOLDIERS = "DISTRIBUTE_SOLDIERS";
    private static final String ADD_CARD = "ADD_CARD";
    private static final String DISCARD = "DISCARD";
    private static final String EXCHANGE_CARDS = "EXCHANGE_CARDS";
    private static final String DECLARE_COMBAT = "DECLARE_COMBAT";
    private static final String ANSWER_COMBAT = "ANSWER_COMBAT";
    private static final String MOVE_SOLDIERS = "MOVE_SOLDIERS";
    private static final String FINISH_ATTACK = "FINISH_ATTACK";
    private final World world;

    public JSONWarProtocol(World world) {
	this.world = world;
    }

    @Override
    public String chooseColor(Color[] colors) {
	return join(CHOOSE_COLOR, new JSONArray(colors));
    }

    @Override
    public Color chooseColor(String message) {
	String choice = message.substring(CHOOSE_COLOR.length());
	JSONObject obj = new JSONObject(choice);
	return new Color(obj.getString("name"));
    }

    @Override
    public String beginTurn(Player current) {
	return join(BEGIN_TURN, new JSONObject(current));
    }

    @Override
    public String distributeSoldiers(int soldierQuantity,
	    Set<Territory> territories) {
	JSONArray territoriesJSON = encodeTerritories(territories);
	return join(DISTRIBUTE_SOLDIERS, soldierQuantity, territoriesJSON);
    }

    private JSONArray encodeTerritories(Collection<Territory> territories) {
	JSONArray array = new JSONArray();
	Iterator<Territory> iterator = territories.iterator();
	for (int i = 0; i < territories.size(); i++)
	    array = array.put(i, encodeTerritory(iterator.next()));
	return array;
    }

    private JSONObject encodeTerritory(Territory territory) {
	JSONObject obj = new JSONObject();
	obj.put("name", territory.getName());
	obj.put("soldiers", territory.getSoldiers());
	obj.put("continent", territory.getContinent().getName());
	obj.put("owner", territory.getOwner().getColor().getName());
	List<String> borders = new LinkedList<>();
	for (Territory border : territory.getBorders())
	    borders.add(border.getName());
	obj.put("borders", borders);
	return obj;
    }

    @Override
    public void distributeSoldiers(String receive, int soldierQuantity,
	    Set<Territory> territories) {
	receive = receive.substring(DISTRIBUTE_SOLDIERS.length());
	updateTerritoriesFromJSON(territories, receive);
    }

    @Override
    public String declareCombat() {
	return DECLARE_COMBAT;
    }

    @Override
    public Combat declareCombat(String receive) {
	if(receive.indexOf(FINISH_ATTACK) != -1)
	    return null;
	receive = receive.substring(DECLARE_COMBAT.length());
	JSONObject json = new JSONObject(receive);
	Territory attacking = world.getTerritoryByName(json.getString("attacking"));
	Territory defending = world.getTerritoryByName(json.getString("defending"));
	int soldiers = json.getInt("soldiers");
	return new Combat(attacking, defending, soldiers);
    }

    @Override
    public String answerCombat(Combat combat) {
	JSONObject json = new JSONObject();
	json.put("attacking", encodeTerritory(combat.getAttackingTerritory()));
	json.put("defending", encodeTerritory(combat.getDefendingTerritory()));
	json.put("soldiers", combat.getAttackingSoldiers());
	return join(ANSWER_COMBAT, json);
    }

    @Override
    public void answerCombat(String receive, Combat combat) {
	receive = receive.substring(ANSWER_COMBAT.length() + 1).trim();
	Integer soldiers = Integer.parseInt(receive);
	combat.setDefendingSoldiers(soldiers);
    }

    @Override
    public String moveSoldiers() {
	return MOVE_SOLDIERS;
    }

    @Override
    public void moveSoldiers(String receive, Set<Territory> territories) {
	receive = receive.substring(MOVE_SOLDIERS.length());
	updateTerritoriesFromJSON(territories, receive);
    }

    @Override
    public String addCard(Card card) {
	return join(ADD_CARD, new JSONObject(card));
    }

    @Override
    public String discard() {
	return DISCARD;
    }

    @Override
    public Card discard(String receive) {
	JSONObject cardJSON = new JSONObject(receive);
	JSONObject territoryJSON = cardJSON.getJSONObject("territory");
	Territory territory = world.getTerritoryByName(territoryJSON
		.getString("name"));
	return new Card(cardJSON.getInt("figure"), territory);
    }

    @Override
    public String exchangeCards() {
	return EXCHANGE_CARDS;
    }

    @Override
    public List<Card> exchangeCards(String receive) {
	receive = receive.substring(EXCHANGE_CARDS.length());
	JSONArray cardsJSON = new JSONArray(receive);
	List<Card> cards = new LinkedList<>();
	for (int i = 0; i < cardsJSON.length(); i++) {
	    JSONObject cardItem = cardsJSON.getJSONObject(i);
	    Territory territory = world.getTerritoryByName(cardItem
		    .getString("name"));
	    cards.add(new Card(cardItem.getInt("figure"), territory));
	}
	return cards;
    }

    private void updateTerritoriesFromJSON(Set<Territory> territories,
	    String json) {
	JSONArray territoriesJSON = new JSONArray(json);
	for (int i = 0; i < territoriesJSON.length(); i++) {
	    JSONObject territoryJSON = territoriesJSON.getJSONObject(i);
	    String name = territoryJSON.getString("name");
	    Territory territory = world.getTerritoryByName(name);
	    territory.setSoldiers(territoryJSON.getInt("soldiers"));
	}
    }

    private String join(Object... objs) {
	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < objs.length; i++) {
	    builder.append(objs[i]);
	    if (i + 1 < objs.length)
		builder.append(SPACE);
	}
	return builder.toString();
    }
}
