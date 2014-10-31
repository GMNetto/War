/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies;

import br.uff.es2.war.model.Territory;

/**
 * This class combines a set of {@link TerritoryValue}s heuristics by
 * multiplying the values each other.
 *
 * @author Victor Guimarães
 */
public class TerritoryValueComparator extends TerritoryValue {

    /**
     * An array with all the {@link TerritoryValue}s.
     */
    private final TerritoryValue[] values;

    /**
     * Constructor with the {@link TerritoryValue}s.
     *
     * @param values the {@link TerritoryValue}s
     */
    public TerritoryValueComparator(TerritoryValue... values) {
        this.values = values;
    }

    @Override
    public double getTerritoryValue(Territory territory) {
        double value = 1;
        for (TerritoryValue territoryValue : values) {
            value *= territoryValue.getTerritoryValue(territory);
        }

        return value;
    }

    /**
     * Getter for the {@link TerritoryValue}s.
     *
     * @return the {@link TerritoryValue}s
     */
    public TerritoryValue[] getValues() {
        return values;
    }

}
