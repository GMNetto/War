package br.uff.es2.war.model;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

/**
 * A territory is owned by a player and occupied by soldiers. Territories are
 * nodes from a graph where a edge between two territories mean that one is on
 * the borders of the other.
 * 
 * @author Arthur Pitzer
 */
public class Territory extends Observable {

    private final String name;
    private final Continent continent;
    private final Set<Territory> borders;
    private Player owner;
    private int x;
    private int y;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addBorder(Territory border) {
	borders.add(border);
    }

    public Player getOwner() {
	return owner;
    }

    public void setOwner(Player owner) {
	this.owner = owner;
	super.notifyObservers(this.getOwner());
    }

    public int getSoldiers() {
	return soldiers;
    }

    public void setSoldiers(int soldiers) {
	this.soldiers = soldiers;
    }

    public void addSoldiers(int soldiers) {
	this.soldiers += soldiers;
    }

    public void removeSoldiers(int soldiers) {
	this.soldiers -= soldiers;
    }

    @Override
    public String toString() {
	return "Territory Name:\t" + name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Territory other = (Territory) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }
}
