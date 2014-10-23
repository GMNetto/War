/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ia.attack.probability;

/**
 * Class that calculates the probability of success or failed on a attack.
 *
 * @see AttackProbabilityFactory
 * @author Victor Guimarães
 */
public class AttackProbability {

    /**
     * Number of attacking soldiers. You should pass the number of soldiers on
     * the source of attack territory, including the soldier that must keep the
     * territory.
     */
    private int attackerSoldiers;

    /**
     * Number of defending soldiers.
     */
    private int defenderSoldiers;

    /**
     * Probability of the attacker wins the combat.
     */
    private double attackerWins;

    /**
     * Probability of the defender wins the combat.
     */
    private double defenderWins;

    /**
     * Constructor with the initial territories configuration.
     *
     * @param attackerSoldiers the number of attacking soldiers. You should pass
     * the number of soldiers on the source of attack territory, including the
     * soldier that must keep the territory
     * @param defenderSoldiers the number of defending soldiers
     */
    public AttackProbability(int attackerSoldiers, int defenderSoldiers) {
        this.attackerSoldiers = attackerSoldiers;
        this.defenderSoldiers = defenderSoldiers;
    }

    /**
     * Getter for the probability of the attacker wins the combat.
     *
     * @return the probability of the attacker wins the combat
     */
    public double getAttackerWins() {
        return attackerWins;
    }

    /**
     * Getter for the probability of the defender wins the combat.
     *
     * @return the probability of the defender wins the combat
     */
    public double getDefenderWins() {
        return defenderWins;
    }

    /**
     * Getter for the number of attacking soldiers. This number includes the
     * soldier who keeps the territory.
     *
     * @return the number of attacking soldiers
     */
    public int getAttackerSoldiers() {
        return attackerSoldiers;
    }

    /**
     * Getter for the number of defending soldiers.
     *
     * @return the number of defending soldiers
     */
    public int getDefenderSoldiers() {
        return defenderSoldiers;
    }

    /**
     * Setter for the probability of the attacker wins the combat. This method
     * should be used for the {@link AttackProbabilityFactory}.
     *
     * @param attackerWins the probability of the attacker wins the combat
     */
    void setAttackerWins(double attackerWins) {
        this.attackerWins = attackerWins;
    }

    /**
     * Setter for the probability of the defender wins the combat. This method
     * should be used for the {@link AttackProbabilityFactory}.
     *
     * @param defenderWins the probability of the defender wins the combat
     */
    void setDefenderWins(double defenderWins) {
        this.defenderWins = defenderWins;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.attackerSoldiers;
        hash = 67 * hash + this.defenderSoldiers;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final AttackProbability other = (AttackProbability) obj;
        if (this.attackerSoldiers != other.attackerSoldiers)
            return false;
        if (this.defenderSoldiers != other.defenderSoldiers)
            return false;
        return true;
    }

}
