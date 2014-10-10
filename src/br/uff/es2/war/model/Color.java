package br.uff.es2.war.model;

/**
 * Color of a player. Color is a unique identifier of player during a game.
 * @author Arthur Pitzer
 */
import javafx.scene.paint.Paint;

public enum Color {
    
    /*
    BLACK("Black", java.awt.Color.BLACK),
    BLUE("Blue", java.awt.Color.BLUE),
    GREEN("Green", java.awt.Color.GREEN),
    RED("Red", java.awt.Color.RED),
    WHITE("White", java.awt.Color.WHITE),
    YELLOW("Yellow", java.awt.Color.YELLOW);
    */
    
    Preto("Preto", Paint.valueOf("BLACK")),
    Azul("Azul", Paint.valueOf("AQUA")),
    Verde("Verde", Paint.valueOf("GREEN")),
    Vermelho("Vermelho", Paint.valueOf("RED")),
    Branco("Branco", Paint.valueOf("WHITE")),
    Amarelo("Amarelo", Paint.valueOf("YELLOW"));
    
    final String name;
    final Paint color;
    
    private Color(String name, Paint color){
	this.name = name;
	this.color = color;
    }
}
