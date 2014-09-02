package br.uff.es2.war.model;

import java.util.Set;

public interface Player {

    void set(Set<Continent> map);

    void set(Player[] players);

    void set(Objective randomObjective);
    
    Color getColor();
    
    void set(Color color);

    Color chooseColor(Color[] colors);
    
}
