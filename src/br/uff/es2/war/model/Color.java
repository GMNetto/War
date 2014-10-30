package br.uff.es2.war.model;

/**
 * Color of a player. Color is a unique identifier of player during a game.
 * @author Arthur Pitzer
 */
public class Color {
    
    private String name;
    
    public Color(String name){
	this.name = name;
    }
    
    public String getName() {
	return name;
    }
    
    @Override
    public int hashCode() {
	return name.hashCode() % 13;
    }
    
    @Override
    public boolean equals(Object obj) {
	try{
	    Color other = (Color) obj;
	    return other.name.equals(name);
	}catch(ClassCastException e){
	    return false;
	}
    }
    
    @Override
    public String toString() {
	return name;
    }
}
