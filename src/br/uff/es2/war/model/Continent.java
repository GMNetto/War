package br.uff.es2.war.model;

import java.util.HashSet;

/**
 * Continents are named sets of territories.
 * @see Territory
 * @author Arthur Pitzer
 */
@SuppressWarnings("serial")
public class Continent extends HashSet<Territory> {
    
    private final String name;
    private final World world;
    private final int totalityBonus;
    
    public Continent(String name, World world, int totalityBonus) {
	super();
	this.name = name;
        this.world = world;
        this.totalityBonus=totalityBonus;
    }

    public int getTotalityBonus() {
        return totalityBonus;
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
