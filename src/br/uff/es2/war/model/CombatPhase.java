package br.uff.es2.war.model;

import br.uff.gamemachine.GameState;

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
	if(combat == null)
	    return new SoldierMovementPhase();
	Player defender = combat.getDefendingTerritory().getOwner();
	defender.answerCombat(combat);
	judge.resolve(combat);
	if(game.isOver())
	    return new GameOver();
	return this;
    }
}
