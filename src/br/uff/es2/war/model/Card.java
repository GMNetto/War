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

    private String getFigureString() {
        switch (figure) {
            case 1:
                return "Triangle";
            case 2:
                return "Circle";
            case 3:
                return "Square";
            default:
                return "All";
        }
    }

    @Override
    public String toString() {
        return "Card: " + (territory != null ? territory.getName() : "Joker") + " Figure: " + getFigureString();
    }
}
