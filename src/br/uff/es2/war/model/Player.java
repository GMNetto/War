package br.uff.es2.war.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.objective.Objective;

/**
 * Player is aware of the Game state and perform actions as 
 * declare combat and move soldiers in order to achieve his 
 * objective.
 * 
 * @author Arthur Pitzer
 */

public interface Player {
    
    void setGame(Game game);

    Objective getObjective();

    void setObjective(Objective objective);
    
    Color chooseColor(Color[] colors);
    
    Color getColor();
    
    void setColor(Color color);

    void beginTurn(Player current);

    void distributeSoldiers(int soldierQuantity, Set<Territory> territories);

    Combat declareCombat();

    void answerCombat(Combat combat);

    void moveSoldiers();

    void addCard(Card drawCard);

    Collection<Card> getCards();

    Card discard();

    List<Card> exchangeCards();

}
