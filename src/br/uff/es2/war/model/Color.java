package br.uff.es2.war.model;

/**
 * Color of a player. Color is a unique identifier of player during a game.
 * @author Arthur Pitzer
 */
public enum Color {
    
    BLACK("Black", java.awt.Color.BLACK),
    BLUE("Blue", java.awt.Color.BLUE),
    GREEN("Green", java.awt.Color.GREEN),
    RED("Red", java.awt.Color.RED),
    WHITE("White", java.awt.Color.WHITE);
    
    final String name;
    final java.awt.Color awtColor;
    
    private Color(String name, java.awt.Color awtColor){
	this.name = name;
	this.awtColor = awtColor;
    }
}
