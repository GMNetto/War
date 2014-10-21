package br.uff.es2.war.network;

import java.util.LinkedList;
import java.util.List;
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
    
    private static final String CHOOSE_COLOR = "CHOOSE_COLOR";
    private static final String BEGIN_TURN = "BEGIN_TURN";
    private static final String DISTRIBUTE_SOLDIERS = "DISTRIBUTE_SOLDIERS";
    private static final String ADD_CARD = "ADD_CARD";
    private static final String DISCARD = "DISCARD";
    private static final String EXCHANGE_CARDS = "EXCHANGE_CARDS";
    private final World world;
    
    public JSONWarProtocol(World world) {
	this.world = world;
    }

    @Override
    public String chooseColor(Color[] colors) {
	JSONArray array = new JSONArray(colors);
	return CHOOSE_COLOR + " " + array;
    }

    @Override
    public Color chooseColor(String message) {
	String choice = message.substring(CHOOSE_COLOR.length());
	JSONObject obj = new JSONObject(choice);
	return new Color(obj.getString("name"));
    }

    @Override
    public String beginTurn(Player current) {
	return BEGIN_TURN + " " + new JSONObject(current);
    }

    @Override
    public String distributeSoldiers(int soldierQuantity,
	    Set<Territory> territories) {
	Territory[] array = new Territory[territories.size()];
	territories.toArray(array);
	JSONArray territoriesJSON = new JSONArray(array);
	return DISTRIBUTE_SOLDIERS + " " + soldierQuantity + territoriesJSON;
    }

    @Override
    public void distributeSoldiers(String receive, int soldierQuantity,
	    Set<Territory> territories) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public String declareCombat() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Combat declareCombat(String receive) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String answerCombat() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object answerCombat(String receive, Combat combat) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String moveSoldiers() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void moveSoldiers(String receive, Set<Territory> territoriesByOwner) {
	// TODO Auto-generated method stub
	
    }
    
    @Override
    public String addCard(Card card) {
	return ADD_CARD + " " + new JSONObject(card);
    }
    
    @Override
    public String discard() {
	return DISCARD;
    }
    
    @Override
    public Card discard(String receive) {
	JSONObject cardJSON = new JSONObject(receive);
	JSONObject territoryJSON = cardJSON.getJSONObject("territory");
	Territory territory = world.getTerritoryByName(territoryJSON.getString("name"));
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
	    Territory territory = world.getTerritoryByName(cardItem.getString("name"));
	    cards.add(new Card(cardItem.getInt("figure"), territory));
	}
	return cards;
    }
}
