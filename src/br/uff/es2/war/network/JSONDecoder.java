package br.uff.es2.war.network;

//import br.uff.es2.war.model.objective.ClientObjective;
import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.PlayerData;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.ClientObjective;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

class JSONDecoder implements Decoder {

    @Override
    public Game decodeGame(String code) {
	JSONObject json = new JSONObject(code);
	Player[] players = decodePlayers(json.getJSONArray("players"));
	World world = decodeWorld(json.getJSONObject("world"));
	return new Game(players, world);
    }

    private Player[] decodePlayers(JSONArray json) {
	Player[] players = new Player[json.length()];
	for (int i = 0; i < players.length; i++)
	    players[i] = decodePlayer(json.getJSONObject(i));
	return players;
    }

    private World decodeWorld(JSONObject json) {
	World world = new World(json.getString("name"));
	Set<Continent> continents = decodeContinents(
		json.getJSONArray("continents"), world);
	world.addAll(continents);
	return world;
    }

    private Set<Continent> decodeContinents(JSONArray array, World world) {
	Set<Continent> continents = new HashSet<>();
	for (int i = 0; i < array.length(); i++)
	    continents.add(decodeContinent(array.getJSONObject(i), world));
	return continents;
    }

    private Continent decodeContinent(JSONObject json, World world) {
	String name = json.getString("name");
	int totalityBonus = json.getInt("totalityBonus");
	Continent continent = new Continent(name, world, totalityBonus);
	continent.addAll(decodeTerritories(json.getJSONArray("territories"),
		continent));
	return continent;
    }

    @Override
    public Player decodePlayer(String code) {
	return decodePlayer(new JSONObject(code));
    }

    private Player decodePlayer(JSONObject json) {
	Player player = new PlayerData();
	Color color = decodeColor(json.getJSONObject("color"));
	player.setColor(color);
	if (json.keySet().contains("objective")) {
	    ClientObjective clientObjective = new ClientObjective(
		    json.getString("objective"));
	    player.setObjective(clientObjective);
	}
	return player;
    }

    @Override
    public Color[] decodeColors(String code) {
	JSONArray array = new JSONArray(code);
	Color[] colors = new Color[array.length()];
	for (int i = 0; i < colors.length; i++)
	    colors[i] = decodeColor(array.getJSONObject(i));
	return colors;
    }

    @Override
    public Color decodeColor(String code) {
	return decodeColor(new JSONObject(code));
    }

    private Color decodeColor(JSONObject json) {
	return new Color(json.getString("name"));
    }

    @Override
    public Set<Territory> decodeTerritories(String code) {
	return decodeTerritories(new JSONArray(code), null);
    }

    private Set<Territory> decodeTerritories(JSONArray array,
	    Continent continent) {
	Set<Territory> territories = new HashSet<>();
	for (int i = 0; i < array.length(); i++) {
	    Territory territory = decodeTerritory(array.getJSONObject(i),
		    continent);
	    territories.add(territory);
	}
	return territories;
    }

    private Territory decodeTerritory(JSONObject json, Continent continent) {
	String name = json.getString("name");
	Territory territory = new Territory(name, continent);
	int soldiers = json.getInt("soldiers");
	Player owner = decodePlayer(json.getJSONObject("owner"));
	territory.setSoldiers(soldiers);
	territory.setOwner(owner);
	territory.setX(json.getInt("x"));
	territory.setY(json.getInt("y"));
	return territory;
    }

    @Override
    public Combat decodeCombat(String code) {
	JSONObject json = new JSONObject(code);
	Territory attacker = decodeTerritory(
		json.getJSONObject("attackingTerritory"), null);
	Territory defender = decodeTerritory(
		json.getJSONObject("defendingTerritory"), null);
	int soldiers = json.getInt("attackingSoldiers");
	Combat combat = new Combat(attacker, defender, soldiers);
	combat.setDefendingSoldiers(json.getInt("defendingSoldiers"));
	return combat;
    }

    @Override
    public List<Card> decodeCards(String code) {
	JSONArray array = new JSONArray(code);
	List<Card> cards = new ArrayList<Card>(array.length());
	for (int i = 0; i < array.length(); i++) {
	    Card card = decodeCard(array.getJSONObject(i));
	    cards.add(card);
	}
	return cards;
    }

    @Override
    public Card decodeCard(String code) {
	return decodeCard(new JSONObject(code));
    }

    private Card decodeCard(JSONObject json) {
	int figure = json.getInt("figure");
	Territory territory = decodeTerritory(json.getJSONObject("territory"),
		null);
	return new Card(figure, territory);
    }

}
