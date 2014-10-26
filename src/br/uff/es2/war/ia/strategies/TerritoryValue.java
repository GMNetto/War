/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ia.strategies;

import br.uff.es2.war.model.Territory;

/**
 * Interface that defines a way to measure the value of a given
 * {@link Territory}.
 *
 * @author Victor Guimarães
 */
public interface TerritoryValue {

    /**
     * Method to obtain the value of a given {@link Territory}. The possible
     * values of this method will depends of the class which implements it.
     *
     * @param territory the {@link Territory}
     * @return the importance
     */
    public double getTerritoryValue(Territory territory);
}
