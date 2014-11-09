package br.uff.es2.war.network.client;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.PlayerData;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.network.Decoder;

/**
 * Decoder designed to create a initial instance of Game. 
 * Used by the client to initialize its Game from a server JSON.
 * 
 * @author Arthur Pitzer
 */
class GameInitializerJSONDecoder implements Decoder {

    @Override
    public Game decodeGame(String code) {
	JSONObject json = new JSONObject(code);
	Player[] players = decodePlayers(json.getJSONArray("players"));
	World world = decodeWorld(json.getJSONObject("world"));
	return new Game(players, world);
    }

    private Player[] decodePlayers(JSONArray array) {
	Player[] players = new Player[array.length()];
	for (int i = 0; i < players.length; i++)
	    players[i] = decodePlayer(array.getJSONObject(i));
	return players;
    }

    @Override
    public Player decodePlayer(String suffix) {
	return decodePlayer(new JSONObject(suffix));
    }

    private Player decodePlayer(JSONObject json) {
	Player player = new PlayerData();
	Color color = decodeColor(json.getJSONObject("color"));
	player.setColor(color);
	return player;
    }

    @Override
    public Color[] decodeColors(String suffix) {
	return decodeColors(new JSONArray(suffix));
    }

    private Color[] decodeColors(JSONArray array) {
	Color[] colors = new Color[array.length()];
	for (int i = 0; i < array.length(); i++)
	    colors[i] = decodeColor(array.getJSONObject(i));
	return colors;
    }

    private Color decodeColor(JSONObject json) {
	return new Color(json.getString("name"));
    }

    private World decodeWorld(JSONObject json) {
	World world = new World(json.getString("name"));
	Set<Continent> continents = decodeContinents(
		json.getJSONArray("continents"), world);
	world.addAll(continents);
	return world;
    }

    private Set<Continent> decodeContinents(JSONArray array, World world) {
	Set<Continent> continents = new HashSet<Continent>();
	for (int i = 0; i < array.length(); i++)
	    continents.add(decodeContinent(array.getJSONObject(i), world));
	return continents;
    }

    private Continent decodeContinent(JSONObject json, World world) {
	String name = json.getString("name");
	int bonus = json.getInt("totalityBonus");
	Continent continent = new Continent(name, world, bonus);
	Set<Territory> territories = decodeTerritories(
		json.getJSONArray("territories"), continent);
	continent.addAll(territories);
	return continent;
    }

    @Override
    public Set<Territory> decodeTerritories(String suffix) {
	return decodeTerritories(new JSONArray(suffix), null);
    }

    private Set<Territory> decodeTerritories(JSONArray array,
	    Continent continent) {
	Set<Territory> territories = new HashSet<>();
	for (int i = 0; i < array.length(); i++) {
	    JSONObject obj = array.getJSONObject(i);
	    territories.add(decodeTerritory(obj, continent));
	}
	return territories;
    }

    private Territory decodeTerritory(JSONObject json, Continent continent) {
	String name = json.getString("name");
	int soldiers = json.getInt("soldiers");
	Territory territory = new Territory(name, continent);
	territory.setSoldiers(soldiers);
	return territory;
    }

    @Override
    public Combat decodeCombat(String suffix) {
	throw new NotImplementedException();
    }
}
