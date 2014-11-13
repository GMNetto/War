package br.uff.es2.war.network;

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

class JSONEncoder implements Encoder{

    @Override
    public String encode(Color[] colors) {
	JSONArray array = new JSONArray();
	for(Color color : colors)
	    array.put(new JSONObject(encode(color)));
	return array.toString();
    }

    @Override
    public String encode(Game game) {
	JSONObject json = new JSONObject();
	json.put("players", encode(game.getPlayers()));
	json.put("world", encode(game.getWorld()));
	return json.toString();
    }
    
    private JSONArray encode(Player[] players){
	JSONArray array = new JSONArray();
	for (Player player : players) 
	    array.put(new JSONObject(encode(player)));
	return array;
    }
    
    private JSONObject encode(World world){
	JSONObject json = new JSONObject();
	json.put("name", world.getName());
	json.put("continents", encodeContinents(world));
	return json;
    }
    
    private JSONArray encodeContinents(Set<Continent> continents){
	JSONArray array = new JSONArray();
	for (Continent continent : continents)
	    array.put(encodeContinent(continent));
	return array;
    }
    
    private JSONObject encodeContinent(Continent continent){
	JSONObject json = new JSONObject();
	json.put("name", continent.getName());
	json.put("totalityBonus", continent.getTotalityBonus());
	json.put("territories", new JSONArray(encode(continent)));
	return json;
    }
     

    @Override
    public String encode(Player current) {
	JSONObject json = new JSONObject();
	json.put("color", new JSONObject(encode(current.getColor())));
        json.put("objective", current.getObjective().toString());
	return json.toString();
    }

    @Override
    public String encode(Set<Territory> territories) {
	JSONArray array = new JSONArray();
	for (Territory territory : territories)
	    array.put(encode(territory));
	return array.toString();
    }
    
    private JSONObject encode(Territory territory){
	JSONObject obj = new JSONObject();
	obj.put("name", territory.getName());
	obj.put("x", territory.getX());
	obj.put("y", territory.getY());
	if(territory.getContinent() != null)
	    obj.put("continent", territory.getContinent().getName());
	obj.put("soldiers", territory.getSoldiers());
	if(territory.getOwner() != null)
	    obj.put("owner", new JSONObject(encode(territory.getOwner())));
	return obj;
    }

    @Override
    public String encode(Combat combat) {
	JSONObject json = new JSONObject();
	json.put("attackingTerritory", encode(combat.getAttackingTerritory()));
	json.put("defendingTerritory", encode(combat.getAttackingTerritory()));
	json.put("attackingSoldiers", combat.getAttackingSoldiers());
	json.put("defendingSoldiers", combat.getDefendingSoldiers());
	return json.toString();
    }

    @Override
    public String encode(Card card) {
	JSONObject json = new JSONObject();
	json.put("figure", card.getFigure());
	json.put("territory", encode(card.getTerritory()));
	return json.toString();
    }

    @Override
    public String encode(Color color) {
	JSONObject obj = new JSONObject();
	obj.put("name", color.getName());
	return obj.toString();
    }

    @Override
    public String encode(List<Card> cards) {
	JSONArray array = new JSONArray();
	for (Card card : cards) 
	    array.put(new JSONObject(encode(card)));
	return array.toString();
    }
}
