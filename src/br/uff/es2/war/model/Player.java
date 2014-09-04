package br.uff.es2.war.model;

import java.util.Map;

public interface Player {

    void setWorld(WorldMap world);

    void setAllPlayers(Player[] players);

    void setObjective(Objective objective);
    
    Color getColor();
    
    void setColor(Color color);

    Color chooseColor(Color[] colors);

    void beginTurn(Player current);

    Map<String, Integer> distributeSoldiers(int soldierQuantity);

}
