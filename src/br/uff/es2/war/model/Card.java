package br.uff.es2.war.model;

public class Card {

    private final int figure;
    private final Territory territory;

    public Card(int figure, Territory territory) {
	this.figure = figure;
	this.territory = territory;
    }

    public int getFigure() {
	return figure;
    }

    public Territory getTerritory() {
	return territory;
    }
}
