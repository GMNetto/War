package br.uff.es2.war.network;

import java.util.Set;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

public interface WarProtocol {
    
    String chooseColor(Color[] colors);

    Color chooseColor(String receive);

    String beginTurn(Player current);

    String distributeSoldiers(int soldierQuantity, Set<Territory> territories);
    
    void distributeSoldiers(String receive, int soldierQuantity,
	    Set<Territory> territories);

    String declareCombat();

    Combat declareCombat(String receive);

    String answerCombat();

    Object answerCombat(String receive, Combat combat);

    String moveSoldiers();

    void moveSoldiers(String receive, Set<Territory> territoriesByOwner);

}
