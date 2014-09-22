package br.uff.es2.war.model;

import java.util.HashSet;
import java.util.Set;

/**
 * A territory is owned by a player and occupied by 
 * soldiers. Territories are nodes from a graph where
 * a edge between two territories mean that one is on
 * the borders of the other.
 * 
 * @author Arthur Pitzer
 */
public class Territory {
    
    private final String name;
    private final Set<Territory> borders;
    private Player owner;
    private int soldiers;
    
    public Territory(String name) {
	this.name = name;
	this.borders = new HashSet<>();
    }
    
    public String getName() {
        return name;
    }
    
    public Set<Territory> getBorders() {
        return borders;
    }
    
    public Player getOwner(){
	return owner;
    }
    
    public void setOwner(Player owner){
	this.owner = owner;
    }
    
    public int getSoldiers(){
	return soldiers;
    }
    
    public void setSoldiers(int soldiers){
	this.soldiers = soldiers;
    }
    
    public void addSoldiers(int soldiers){
	this.soldiers += soldiers;
    }
    
    public void removeSoldiers(int soldiers){
	this.soldiers -= soldiers;
    }
}    
