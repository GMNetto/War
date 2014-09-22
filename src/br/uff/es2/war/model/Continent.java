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
    
    public Continent(String name) {
	super();
	this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
