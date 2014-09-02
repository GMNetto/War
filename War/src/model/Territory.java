package model;

import java.util.HashSet;
import java.util.Set;

public class Territory {
    
    private final String name;
    private final Set<Territory> borders;
    private Continent continent;
    
    public Territory(String name) {
	this.name = name;
	this.borders = new HashSet<>();
    }
    
    public String getName() {
        return name;
    }
    
    public Continent getContinent() {
        return continent;
    }
    
    void setContinent(Continent continent){
	this.continent = continent;
    }

    public Set<Territory> getBorders() {
        return borders;
    }
}
