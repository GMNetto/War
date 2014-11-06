package br.uff.es2.war.network.json;

import java.util.HashSet;
import java.util.LinkedList;
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

/**
 * Decode complex objects encode by JSONEncoder and map then to real instances in the memory.
 * 
 * @see JSONEncoder
 * @author Arthur Pitzer
 */
class JSONDecoder {
    
    private final Game game;
    
    JSONDecoder(Game game) {
	this.game = game;
    }

    Player decodePlayer(String sufix) {
	JSONObject json = new JSONObject(sufix);
	String color = json.getString("color");
	return game.playerByColor(new Color(color));
    }

    Color[] decodeColors(String sufix) {
	JSONArray array = new JSONArray(sufix);
	Color[] colors = new Color[array.length()];
	for (int i = 0; i < array.length(); i++) {
	    JSONObject obj = array.getJSONObject(i);
	    colors[i] = new Color(obj.getString("name"));
	}
	return colors;
    }

    Set<Territory> decodeTerritories(String sufix) {
	Set<Territory> territories = new HashSet<>();
	JSONArray array = new JSONArray(sufix);
	for(int i = 0; i < array.length(); i++){
	    JSONObject obj = array.getJSONObject(i);
	    territories.add(decodeTerritory(obj));
	}
	return territories;
    }
    
    Territory decodeTerritory(JSONObject obj){
	return game.getWorld().getTerritoryByName(obj.getString("name"));
    }

    Combat decodeCombat(String sufix) {
	JSONObject obj = new JSONObject(sufix);
	JSONObject attackingObj = obj.getJSONObject("attackingTerritory");
	JSONObject defendingObj = obj.getJSONObject("defendingTerritory");
	int soldiers = obj.getInt("attackingSoldiers");
	Territory attacking = game.getWorld().getTerritoryByName(attackingObj.getString("name"));
	Territory defending = game.getWorld().getTerritoryByName(defendingObj.getString("name"));
	return new Combat(attacking, defending, soldiers);
    }

    public List<Card> decodeCards(String receive) {
	JSONArray cardsJSON = new JSONArray(receive);
	List<Card> cards = new LinkedList<>();
	for (int i = 0; i < cardsJSON.length(); i++) {
	    JSONObject cardItem = cardsJSON.getJSONObject(i);
	    Territory territory = game.getWorld().getTerritoryByName(cardItem
		    .getString("name"));
	    cards.add(new Card(cardItem.getInt("figure"), territory));
	}
	return cards;
    }

    public void updateTerritories(Set<Territory> territories, String json) {
	JSONArray territoriesJSON = new JSONArray(json);
	for (int i = 0; i < territoriesJSON.length(); i++) {
	    JSONObject territoryJSON = territoriesJSON.getJSONObject(i);
	    String name = territoryJSON.getString("name");
	    Territory territory = game.getWorld().getTerritoryByName(name);
	    territory.setSoldiers(territoryJSON.getInt("soldiers"));
	}
    }
}
