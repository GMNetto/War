package br.uff.es2.war.model;

import br.uff.es2.war.model.objective.Objective;

public interface Player {

    void setWorld(World world);

    void setAllPlayers(Player[] players);

    void setObjective(Objective objective);
    
    Color getColor();
    
    void setColor(Color color);

    Color chooseColor(Color[] colors);

    void beginTurn(Player current);

    Territory[] distributeSoldiers(int soldierQuantity);

    Combat declareCombat();

    void answerCombat(Combat combat);

    void setCombatResult(CombatResult result);

}
