package br.uff.es2.war.model;

import java.util.HashSet;

@SuppressWarnings("serial")
public class Continent extends HashSet<Territory> {
    
    private final String world;
    private final String name;
    
    public Continent(String world, String name) {
	this.world = world;
	this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getWorld(){
	return world;
    }
}
