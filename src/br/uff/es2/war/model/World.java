package br.uff.es2.war.model;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class World extends HashSet<Continent>   {
    
    private final String name;
    
    public World(String name) {
	this.name = name;
    }
    
    public String getName(){
	return name;
    }
    
    public Set<Territory> getTerritories(){
	Set<Territory> territories = new HashSet<>();
	for(Continent continent : this)
	    territories.addAll(continent);
	return territories;
    }

    public Set<Territory> getTerritoriesByOwner(Player current) {
	Set<Territory> owned = new HashSet<>();
	for(Territory territory : getTerritories())
	    if(territory.getOwner().equals(current))
		owned.add(territory);
	return owned;
    }
    
    public Territory getTerritoryByName(String name) {
	for(Territory territory : getTerritories())
	    if(territory.getName().equals(name))
		return territory;
	return null;
    }
    
    public void distributeTerritories(Player[] players){
	int index = 0;
        for(Territory territory : getTerritories()){
	    territory.setOwner(players[index % players.length]);
	    territory.setSoldiers(1);
            index++;
	}
    }

    @Override
    public String toString() {
        return "World Name:\t" + name;
    }
    
}
