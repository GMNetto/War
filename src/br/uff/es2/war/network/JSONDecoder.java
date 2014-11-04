package br.uff.es2.war.network;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

/**
 * Decode complex objects encode by JSONEncoder
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

    public Combat decodeCombat(String sufix) {
	JSONObject obj = new JSONObject(sufix);
	JSONObject attackingObj = obj.getJSONObject("attackingTerritory");
	JSONObject defendingObj = obj.getJSONObject("defendingTerritory");
	int soldiers = obj.getInt("attackingSoldiers");
	Territory attacking = game.getWorld().getTerritoryByName(attackingObj.getString("name"));
	Territory defending = game.getWorld().getTerritoryByName(defendingObj.getString("name"));
	return new Combat(attacking, defending, soldiers);
    }
}
