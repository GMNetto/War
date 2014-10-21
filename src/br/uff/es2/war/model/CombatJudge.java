package br.uff.es2.war.model;

import java.util.Arrays;
import java.util.Random;

/**
 * Resolves combats and apply the results on the world.
 * 
 * @see Combat
 * @author Arthur Pitzer
 */
public class CombatJudge {
    
    private final Random dice;
    
    public CombatJudge() {
	dice = new Random();
    }
    
    public void resolve(Combat combat){
	int[] attack = combatValues(combat.getAttackingSoldiers());
	int[] defense = combatValues(combat.getDefendingSoldiers());
	int[] scores = calculateScores(attack, defense);
	Territory attackingTerritory = combat.getAttackingTerritory();
	Territory defendingTerritory = combat.getDefendingTerritory();
	applyScores(scores, attackingTerritory, defendingTerritory, combat);
	updateOwnership(attackingTerritory, defendingTerritory, combat.getAttackingSoldiers() - scores[1]);
    }
    
    protected int[] combatValues(int soldiers){
	int[] values = rollDiceNTimes(soldiers);
	Arrays.sort(values);
	return values;
    }
    
    private int[] rollDiceNTimes(int n){
	int[] result = new int[n];
	for(int i = 0; i < n; i++)
	    result[i] = rollDice();
	return result;
    }
    
    private int rollDice(){
	return dice.nextInt(6) + 1;
    }
    
    /**
     * @return First index: soldiers lost by defender<br/>Second index: soldiers lost by attacker
     */
    private int[] calculateScores(int[] attackValues, int[] defenseValues){
	int length = Math.max(attackValues.length, defenseValues.length);
	int[] scores = new int[2];
	for(int i = 0; i < length; i++){
	    int attack = getIthGreatest(i, attackValues);
	    int defense = getIthGreatest(i, defenseValues);
	    if(attack > defense)
		scores[0]++;
	    else
		scores[1]++;
	}
	return scores;
    }
    
    private int getIthGreatest(int i, int[] values){
	i = Math.min(values.length - 1, i);
	return values[i];
    }
    
    private void applyScores(int[] scores, Territory attackingTerritory, Territory defendingTerritory, Combat combat){
	defendingTerritory.removeSoldiers(scores[0]);
	attackingTerritory.removeSoldiers(scores[1]);
    }

    private void updateOwnership(Territory attackingTerritory, Territory defendingTerritory, int survivingSoldiers) {
	if(defendingTerritory.getSoldiers() <= 0){
	    defendingTerritory.setSoldiers(1);
	    attackingTerritory.removeSoldiers(survivingSoldiers - 1);
	    defendingTerritory.setOwner(attackingTerritory.getOwner());
	}
    }
}
