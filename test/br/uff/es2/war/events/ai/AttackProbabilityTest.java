/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.events.ai;

import br.uff.es2.war.ai.attack.probability.AttackProbability;
import br.uff.es2.war.ai.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.ai.attack.probability.ProbabilityTriple;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class to test if a probability and it complementation sums one.
 *
 * @author Victor Guimarães
 */
public class AttackProbabilityTest {

    private AttackProbabilityFactory factory;

    public AttackProbabilityTest() {
        factory = new AttackProbabilityFactory();
    }

    @Test
    public void ASSERT_PROBABILITY_TOTALS_ARROUND_1() {
        int n = 100;
        double total = 0;
        double threshold = Math.pow(10.0, -14);
        AttackProbability attackProbability;
        for (int i = 1; i < n + 2; i++) {
            for (int j = 1; j < n + 1; j++) {
                attackProbability = factory.getAttackProbability(i, j);
                System.out.println(attackProbability);
                total = (attackProbability.getAttackerWins() + attackProbability.getDefenderWins());
            }
        }
        System.out.println(threshold);
        Assert.assertTrue(Math.abs(1 - total) < threshold);
    }
}
