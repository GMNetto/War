package br.uff.es2.war.network.client;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.PlayerData;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.network.Decoder;

class ReferenceMappingJSONDecoder implements Decoder{
    
    private final Game game;
    
    public ReferenceMappingJSONDecoder(Game game) {
	this.game = game;
    }

    @Override
    public Game decodeGame(String suffix) {
	throw new NotImplementedException();
    }

    @Override
    public Player decodePlayer(String suffix) {
	JSONObject json = new JSONObject(suffix);
	Color color = decodeColor(json.getJSONObject("color"));
	return game.getPlayerByColor(color);
    }
    
    @Override
    public Color[] decodeColors(String suffix) {
	JSONArray array = new JSONArray(suffix);
	Color[] colors = new Color[array.length()];
	for (int i = 0; i < array.length(); i++)
	    colors[i] = decodeColor(array.getJSONObject(i));
	return colors;
    }
    
    private Color decodeColor(JSONObject json) {
	return new Color(json.getString("name"));
    }

    @Override
    public Set<Territory> decodeTerritories(String suffix) {
	JSONArray array = new JSONArray(suffix);
	Set<Territory> territories = new HashSet<>();
	for (int i = 0; i < array.length(); i++) 
	    territories.add(decodeTerritory(array.getJSONObject(i)));
	return territories; 
    }
    
    private Territory decodeTerritory(JSONObject json){
	String name = json.getString("name");
	return game.getWorld().getTerritoryByName(name);
    }

    @Override
    public Combat decodeCombat(String suffix) {
	JSONObject json = new JSONObject(suffix);
	Territory attacking = decodeTerritory(json.getJSONObject("attacking"));
	Territory defending = decodeTerritory(json.getJSONObject("defending"));
	int attackingSoldiers = json.getInt("attackingSoldiers");
	int defendingSoldiers = json.getInt("defendingSoldiers");
	Combat combat = new Combat(attacking, defending, attackingSoldiers);
	combat.setDefendingSoldiers(defendingSoldiers);
	return combat;
    }
}
