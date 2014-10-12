package br.uff.es2.war.model;

import java.util.HashSet;

/**
 * Continents are named sets of territories.
 * @see Territory
 * @author Arthur Pitzer
 */
public class Continent extends HashSet<Territory> {

    private static final long serialVersionUID = -939594860172028714L;
    private final String name;
    private final World world;
    
    public Continent(String name, World world) {
	super();
	this.name = name;
        this.world = world;
    }
    
    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public String toString() {
        return "Continent Name:\t" + name;
    }
    
}
