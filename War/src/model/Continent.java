package br.uff.es2.war.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Continent {
    
    private final String name;
    private final Set<Territory> territories;
    
    public Continent(String name) {
	this.name = name;
	territories = new HashSet<>();
    }
    
    public String getName() {
        return name;
    }

    public Set<Territory> getTerritories() {
        return Collections.unmodifiableSet(territories);
    }
    
    public void add(Territory territory){
	if(territory.getContinent() != null)
	    territory.getContinent().territories.remove(territory);
	territory.setContinent(this);
	territories.add(territory);
    }
}
