/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies;

import java.util.Comparator;

import br.uff.es2.war.model.Territory;

/**
 * Interface that defines a way to measure the value of a given
 * {@link Territory}.
 *
 * @author Victor Guimarães
 */
public abstract class TerritoryValue implements Comparator<Territory> {

    /**
     * Method to obtain the value of a given {@link Territory}. The possible
     * values of this method will depends of the class which implements it.
     *
     * @param territory the {@link Territory}
     * @return the importance
     */
    public abstract double getTerritoryValue(Territory territory);

    /**
     * Compares its two arguments for order.
     *
     * @param o1 first {@link Territory}
     * @param o2 second {@link Territory}
     * @return a negative integer, zero, or a positive integer as the first
     * argument is greater than, equal to, or less than the second.
     * @throws NullPointerException if an argument is null and this comparator
     * does not permit null arguments
     * @throws ClassCastException if the arguments' types prevent them from
     * being compared by this comparator.
     */
    @Override
    public int compare(Territory o1, Territory o2) {
        return Double.compare(getTerritoryValue(o2), getTerritoryValue(o1));
    }

}
