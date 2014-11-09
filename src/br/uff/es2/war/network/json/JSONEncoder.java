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
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

/**
 * Encode complex objects avoiding recursive structures and unnecessary
 * attributes
 * 
 * @author Arthur Pitzer
 */
public class JSONEncoder {
    
    public JSONObject encode(Game game) {
	JSONObject json = new JSONObject();
	json.put("world", encode(game.getWorld()));
	json.put("turn", game.getNumberOfTurns());
	json.put("players", encode(game.getPlayers()));
	return json;
    }
    
    public JSONObject encode(World world) {
	JSONObject json = new JSONObject();
	json.put("name", world.getName());
	json.put("continents", encode(new HashSet<Continent>(world)));
	return json;
    }
    
    public JSONArray encode(Set<Continent> continents) {
	JSONArray array = new JSONArray();
	for (Continent continent : continents)
	    array.put(encode(continent));
	return array;
    }
    
    public JSONObject encode(Continent continent) {
	JSONObject json = new JSONObject();
	json.put("name", continent.getName());
	json.put("totalityBonus", continent.getTotalityBonus());
	json.put("territories", encodeTerritories(continent));
	return json;
    }
    
    public JSONArray encodeTerritories(Set<Territory> territories) {
	JSONArray array = new JSONArray();
	for (Territory territory : territories)
	    array.put(encode(territory));
	return array;
    }

    public JSONObject encode(Territory territory) {
	JSONObject obj = new JSONObject();
	obj.put("name", territory.getName());
	obj.put("soldiers", territory.getSoldiers());
	obj.put("continent", territory.getContinent().getName());
	if(territory.getOwner() != null)
	    obj.put("owner", territory.getOwner().getColor().getName());
	List<String> borders = new LinkedList<>();
	for (Territory border : territory.getBorders())
	    borders.add(border.getName());
	obj.put("borders", borders);
	return obj;
    }

    public JSONArray encode(Player[] players) {
	JSONArray json = new JSONArray();
	for (int i = 0; i < players.length; i++)
	    json.put(i, encode(players[i]));
	return json;
    }

    public JSONObject encode(Player player) {
	JSONObject json = new JSONObject();
	json.put("color", encode(player.getColor()));
	return json;
    }
    
    public JSONObject encode(Color color) {
	return new JSONObject(color);
    }
    
    public JSONArray encode(List<Card> cards) {
	JSONArray array = new JSONArray();
	for (Card card : cards)
	    array.put(encode(card));
	return array;
    }

    public JSONObject encode(Card card) {
	JSONObject json = new JSONObject();
	json.put("figure", card.getFigure());
	json.put("territory", encode(card.getTerritory()));
	return json;
    }

    public JSONObject encode(Combat combat) {
	JSONObject json = new JSONObject();
	json.put("attacking", encode(combat.getAttackingTerritory()));
	json.put("defending", encode(combat.getDefendingTerritory()));
	json.put("attackingSoldiers", combat.getAttackingSoldiers());
	json.put("defendingSoldiers", combat.getDefendingSoldiers());
	return json;
    }
    
}
