package br.uff.es2.war.model;

import java.util.HashSet;

@SuppressWarnings("serial")
public class Continent extends HashSet<Territory> {
    
    private final String name;
    
    public Continent(String name) {
	this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
