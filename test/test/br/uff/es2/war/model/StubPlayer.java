package test.br.uff.es2.war.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Objective;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.WorldMap;

class StubPlayer implements Player {

    private WorldMap world;
    private Color color;
    private int soldierPool;

    @Override
    public void setWorld(WorldMap world) {
	this.world = world;
    }

    @Override
    public void setAllPlayers(Player[] players) {
    }

    @Override
    public void setObjective(Objective objective) {
    }

    @Override
    public void setColor(Color color) {
	this.color = color;
    }

    @Override
    public Color chooseColor(Color[] colors) {
	int index = (int)(Math.random() * (colors.length - 1));
	return colors[index];
    }
    
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void beginTurn(Player current) {
    }

    @Override
    public Map<String, Integer> distributeSoldiers(int soldierQuantity) {
	this.soldierPool = soldierQuantity;
	Set<Territory> territories = world.getTerritoriesByOwner(this);
	Map<String, Integer> distribution = new HashMap<>();
	if(territories.isEmpty())
	    return distribution;
	String territory = territories.iterator().next().getName();
	distribution.put(territory, soldierQuantity);
	return distribution;
    }

    public int soldierPool() {
	return soldierPool;
    }
}
