package br.uff.es2.war.network.json;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

/**
 * Encode complex objects avoiding recursive structures and unnecessary attributes
 * 
 * @author Arthur Pitzer
 */
class JSONEncoder {

    JSONObject encode(Territory territory) {
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
    
    JSONObject encode(Game game) {
	JSONObject json = new JSONObject();
	json.put("world", encode(game.getWorld()));
	json.put("turn", game.getNumberOfTurns());
	json.put("players", encode(game.getPlayers()));
	return json;
    }
    
    JSONObject encode(World world){
	JSONObject json = new JSONObject();
	json.put("name", world.getName());
	json.put("continents", encode(new HashSet<Continent>(world)));
	return json;
    }
    
    JSONArray encode(Set<Continent> continents){
	JSONArray array = new JSONArray();
	for(Continent continent : continents)
	    array.put(encode(continent));
	return array;
    }
    
    JSONArray encode(Continent continent) {
	JSONArray array = new JSONArray();
	Iterator<Territory> iterator = continent.iterator();
	for (int i = 0; i < continent.size(); i++)
	    array = array.put(i, encode(iterator.next()));
	return array;
    }
     
    JSONArray encode(Player[] players){
	JSONArray json = new JSONArray();
	for (int i = 0; i < players.length; i++)
	    json.put(i, encode(players[i]));
	return json;
    }
    
    JSONObject encode(Player player){
	JSONObject json = new JSONObject();
	json.put("color", player.getColor().getName());
	return json;
    }

    JSONObject encode(Combat combat) {
	JSONObject json = new JSONObject();
	json.put("attacking", encode(combat.getAttackingTerritory()));
	json.put("defending", encode(combat.getDefendingTerritory()));
	json.put("soldiers", combat.getAttackingSoldiers());
	return json;
    }

    JSONObject encode(Color color) {
	return new JSONObject(color);
    }

    JSONArray encode(List<Card> cards) {
	JSONArray array = new JSONArray();
	for(Card card : cards)
	    array.put(encode(card));
	return array;
    }
    
    JSONObject encode(Card card){
	JSONObject json = new JSONObject();
	json.put("figure", card.getFigure());
	json.put("territory", encode(card.getTerritory()));
	return json;
    }
}
