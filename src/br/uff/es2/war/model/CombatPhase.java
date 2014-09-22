package br.uff.es2.war.model;

/**
 * During each turn, after receive new soldiers and before moving his soldiers,
 * a player can attack other players. The current player can declare how many
 * combats he wants to.
 * 
 * @see CombatJudge
 * @author Arthur Pitzer
 */
public class CombatPhase implements GamePhase {
    
    private final CombatJudge judge;
    
    public CombatPhase() {
	judge = new CombatJudge();
    }
    
    @Override
    public void execute(Game game) {
	Player attacker = game.getCurrentPlayer();
	Combat combat = attacker.declareCombat();
	while(combat != null){
	    Player defender = combat.getDefendingTerritory().getOwner();
	    defender.answerCombat(combat);
	    judge.resolve(combat);
	}
    }
}
