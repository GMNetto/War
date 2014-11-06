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
import br.uff.es2.war.model.PlayerData;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

/**
 * Decode complex objects encode by JSONEncoder and map then to real instances in the memory.
 * 
 * @see JSONEncoder
 * @author Arthur Pitzer
 */
class JSONDecoder {
    
    private Game game;
    
    public JSONDecoder() {
    }
    
    JSONDecoder(Game game) {
	this.game = game;
    }
    
    void setGame(Game game) {
	this.game = game;
    }
    
    public Game decodeGame(String suffix) {
	JSONObject json = new JSONObject(suffix);
	Player[] players = decodePlayers(json.getJSONArray("players"));
	World world = decodeWorld(json.getJSONObject("world"));
	Color[] colors = decodeColors(json.getJSONArray("colors"));
	List<Card> cards = decodeCards(json.getJSONArray("cards"));
	return new Game(players, world, colors, cards);
    }
    
    Player[] decodePlayers(JSONArray array){
	Player[] players = new Player[array.length()];
	for(int i = 0; i < players.length; i++)
	    players[i] = decodePlayer(array.getJSONObject(i));
	return players;
    }
    
    Player decodePlayer(JSONObject json){
	Player player = new PlayerData();
	Color color = decodeColor(json.getJSONObject("color"));
	player.setColor(color);
	return player;
    }

    Player decodePlayer(String sufix) {
	JSONObject json = new JSONObject(sufix);
	String color = json.getString("color");
	return game.playerByColor(new Color(color));
    }
    
    World decodeWorld(JSONObject obj){
	String name = obj.getString("name");
	World world = new World(name);
	Set<Continent> continents = decodeContinents(obj.getJSONArray("continents"), world);
	world.addAll(continents);
	return world;
    }
    
    Set<Continent> decodeContinents(JSONArray array, World world){
	Set<Continent> continents = new HashSet<>();
	for(int i = 0; i < array.length(); i++)
	    continents.add(decodeContinent(array.getJSONObject(i), world));
	return continents;
    }
    
    Continent decodeContinent(JSONObject json, World world){
	String name = json.getString("name");
	int totalityBonus = json.getInt("totalityBonus");
	return new Continent(name, world, totalityBonus);
    }

    Color[] decodeColors(String suffix) {
	return decodeColors(new JSONArray(suffix));
    }
    
    Color[] decodeColors(JSONArray array) {
	Color[] colors = new Color[array.length()];
	for (int i = 0; i < array.length(); i++) {
	    JSONObject obj = array.getJSONObject(i);
	    colors[i] = decodeColor(obj);
	}
	return colors;
    }

    Color decodeColor(JSONObject json){
	return new Color(json.getString("name"));
    }
    
    Set<Territory> decodeTerritories(String suffix) {
	return decodeTerritories(new JSONArray(suffix));
    }
    
    Set<Territory> decodeTerritories(JSONArray array){
	Set<Territory> territories = new HashSet<>();
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
	return decodeCards(new JSONArray(receive));
    }
    
    public List<Card> decodeCards(JSONArray array){
	List<Card> cards = new LinkedList<>();
	for (int i = 0; i < array.length(); i++) {
	    JSONObject cardItem = array.getJSONObject(i);
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
