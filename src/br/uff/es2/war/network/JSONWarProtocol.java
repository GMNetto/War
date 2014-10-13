package br.uff.es2.war.network;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

public class JSONWarProtocol implements WarProtocol {
    
    private static final String CHOOSE_COLOR = "CHOOSE_COLOR";
    private static final String BEGIN_TURN = "BEGIN_TURN";
    
    @Override
    public String chooseColor(Color[] colors) {
	JSONArray array = new JSONArray(colors);
	return CHOOSE_COLOR + " " + array;
    }

    @Override
    public Color chooseColor(String message) {
	String choice = message.substring(CHOOSE_COLOR.length());
	JSONObject obj = new JSONObject(choice);
	return new Color(obj.getString("name"));
    }

    @Override
    public String beginTurn(Player current) {
	return BEGIN_TURN + " " + new JSONObject(current);
    }

    @Override
    public String distributeSoldiers(int soldierQuantity,
	    Set<Territory> territories) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void distributeSoldiers(String receive, int soldierQuantity,
	    Set<Territory> territories) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public String declareCombat() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Combat declareCombat(String receive) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String answerCombat() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object answerCombat(String receive, Combat combat) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String moveSoldiers() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void moveSoldiers(String receive, Set<Territory> territoriesByOwner) {
	// TODO Auto-generated method stub
	
    }

}
