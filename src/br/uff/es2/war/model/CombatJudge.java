package br.uff.es2.war.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Resolves combats and apply the results on the world.
 *
 * @see Combat
 * @author Arthur Pitzer
 */
public class CombatJudge {

    private final Random dice;
    private final InverseIntegerComparator inverseIntegerComparator;
    
    
    public CombatJudge() {
        dice = new Random();
        inverseIntegerComparator = new InverseIntegerComparator();
    }

    public void resolve(Combat combat) {
        Integer[] attack = combatValues(combat.getAttackingSoldiers());
        Integer[] defense = combatValues(combat.getDefendingSoldiers());
        int[] scores = calculateScores(attack, defense);
        Territory attackingTerritory = combat.getAttackingTerritory();
        Territory defendingTerritory = combat.getDefendingTerritory();
        applyScores(scores, attackingTerritory, defendingTerritory);
        updateOwnership(attackingTerritory, defendingTerritory, combat.getAttackingSoldiers() - scores[1]);
    }

    protected Integer[] combatValues(int soldiers) {
        Integer[] values = rollDiceNTimes(soldiers);
        Arrays.sort(values, inverseIntegerComparator);
        return values;
    }

    private Integer[] rollDiceNTimes(int n) {
        Integer[] result = new Integer[n];
        for (int i = 0; i < n; i++) {
            result[i] = rollDice();
        }
        return result;
    }

    private int rollDice() {
        return dice.nextInt(6) + 1;
    }

    /**
     * @return First index: soldiers lost by defender<br/>
     * Second index: soldiers lost by attacker
     */
    private int[] calculateScores(Integer[] attackValues, Integer[] defenseValues) {
        int length = Math.min(attackValues.length, defenseValues.length);
        int[] scores = new int[2];
        for (int i = 0; i < length; i++) {
            if (attackValues[i] > defenseValues[i])
                scores[0]++;
            else
                scores[1]++;
        }
        }
        return scores;
    }

    private int getIthGreatest(int i, int[] values) {
        //Again you are supposing the ascending sort.
        i = Math.min(values.length - 1, i);
        return values[values.length-1-i];
    }
}

    private void applyScores(int[] scores, Territory attackingTerritory,
            Territory defendingTerritory, Combat combat) {
        defendingTerritory.removeSoldiers(scores[0]);
        attackingTerritory.removeSoldiers(scores[1]);
    }
    
    private void updateOwnership(Territory attackingTerritory,
            Territory defendingTerritory, int survivingSoldiers) {
        if (defendingTerritory.getSoldiers() <= 0) {
            defendingTerritory.setSoldiers(1);
            //Why are you doing it, the player should decide how many soldiers he would like to move?
            //However none of us have thought about it, so maybe we will maintain it.
            attackingTerritory.removeSoldiers(survivingSoldiers - 1);
            defendingTerritory.setOwner(attackingTerritory.getOwner());
}
    }
}
