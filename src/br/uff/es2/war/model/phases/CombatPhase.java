package br.uff.es2.war.model.phases;

import br.uff.es2.war.events.TerritoryConquestEvent;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.CombatJudge;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

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
    private final SoldierMovementPhase soldierMovement;

    public CombatPhase() {
	judge = new CombatJudge();
        soldierMovement = new SoldierMovementPhase();
    }

    @Override
    public GameState<Game> execute(Game game) {
	Player attacker = game.getCurrentPlayer();
	Combat combat = attacker.declareCombat();
	if (combat == null)
	    return soldierMovement;
	Territory defendingTerritory = combat.getDefendingTerritory();
	Player defender = defendingTerritory.getOwner();
	judge.resolve(combat);
	attacker.answerCombat(combat);
	defender.answerCombat(combat);
        game.getEvents().subscribe(TerritoryConquestEvent.class, soldierMovement);
	if (defendingTerritory.getOwner().equals(attacker))
	    game.getEvents().publish(
		    new TerritoryConquestEvent(defender, attacker,
			    defendingTerritory));
	if (game.isOver())
	    return new GameOver();
	return this;
    }
}
