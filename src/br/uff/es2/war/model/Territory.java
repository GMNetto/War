package br.uff.es2.war.model;

import br.uff.es2.war.entity.Ocupacao;
import br.uff.es2.war.entity.Territorio;
import java.util.HashSet;
import java.util.Observer;
import java.util.Set;
import javafx.beans.InvalidationListener;
import java.util.Observable;

/**
 * A territory is owned by a player and occupied by 
 * soldiers. Territories are nodes from a graph where
 * a edge between two territories mean that one is on
 * the borders of the other.
 * 
 * @author Arthur Pitzer
 */
public class Territory extends Observable {
    
    private final String name;
    private final Continent continent;
    private final Set<Territory> borders;
    private Player owner;
    private int soldiers;
    
    public Territory(String name, Continent continent) {
	this.name = name;
        this.continent = continent;
	this.borders = new HashSet<>();
    }
    
    
    
    public String getName() {
        return name;
    }
    
    public Continent getContinent() {
        return continent;
    }
    
    public Set<Territory> getBorders() {
        return borders;
    }
    
    public void addBorder(Territory border) {
        borders.add(border);
    }
    
    public Player getOwner(){
	return owner;
    }
    
    public void setOwner(Player owner){
	this.owner = owner;
        super.notifyObservers(this.getOwner());
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

    @Override
    public String toString() {
        return "Territory Name:\t" + name;
    }
     
   
}
