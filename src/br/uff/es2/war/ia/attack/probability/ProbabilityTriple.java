/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ia.attack.probability;

import br.uff.es2.war.util.Triple;
import java.util.Objects;

/**
 * Class to represent a node from a Attack Probability Tree.
 *
 * @author Victor Guimarães
 */
public class ProbabilityTriple extends Triple<Integer, Integer, Double> {

    /**
     * Constructor with all needed parameters.
     *
     * @param attackerSoldiers the number of attacking soldiers
     * @param defenderSoldiers the number of defending soldiers
     * @param probability the probability of attacker's success
     */
    public ProbabilityTriple(int attackerSoldiers, int defenderSoldiers, double probability) {
        super(attackerSoldiers, defenderSoldiers, probability);
    }

    /**
     * Getter for the number of attacking soldiers.
     *
     * @return the number of attacking soldiers
     */
    public int getAttackerSoldiers() {
        return getFirst();
    }

    /**
     * Getter for the number of defending soldiers.
     *
     * @return the number of defending soldiers
     */
    public int getDefenderSoldiers() {
        return getSecond();
    }

    /**
     * Getter for the probability of attacker's success.
     *
     * @return the probability of attacker's success
     */
    public double getProbability() {
        return getThird();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
        if (!Objects.equals(this.getFirst(), other.getFirst()))
            return false;
        if (!Objects.equals(this.getSecond(), other.getSecond()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.getFirst());
        hash = 83 * hash + Objects.hashCode(this.getSecond());
        return hash;
    }
}
