package br.uff.es2.war.model.phases;

import br.uff.es2.war.events.TerritoryConquestEvent;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.CombatJudge;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.phases.GameState;

/**
 * During each turn, after receive new soldiers and before moving his soldiers,
 * a player can attack other players. The current player can declare how many
 * combats he wants to.
 * 
 * @see CombatJudge
 * @author Arthur Pitzer
 */
public class CombatPhase implements GameState<Game> {

    private final CombatJudge judge;

    public CombatPhase() {
	judge = new CombatJudge();
    }

    @Override
    public GameState<Game> execute(Game game) {
	Player attacker = game.getCurrentPlayer();
	Combat combat = attacker.declareCombat();
	if (combat == null)
	    return new SoldierMovementPhase();
	Territory defendingTerritory = combat.getDefendingTerritory();
	Player defender = defendingTerritory.getOwner();
	defender.answerCombat(combat);
	judge.resolve(combat);
	if (defendingTerritory.getOwner().equals(attacker))
	    game.getEvents().publish(
		    new TerritoryConquestEvent(defender, attacker,
			    defendingTerritory));
	if (game.isOver())
	    return new GameOver();
	return this;
    }
}
