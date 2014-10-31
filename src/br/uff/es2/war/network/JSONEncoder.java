package br.uff.es2.war.network;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
    
    JSONArray encode(Collection<Territory> territories) {
	JSONArray array = new JSONArray();
	Iterator<Territory> iterator = territories.iterator();
	for (int i = 0; i < territories.size(); i++)
	    array = array.put(i, encode(iterator.next()));
	return array;
    }

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
	json.put("territories", encode(world.getTerritories()));
	return json;
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
}
