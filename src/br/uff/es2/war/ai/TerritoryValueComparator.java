/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai;

import br.uff.es2.war.ai.strategies.TerritoryValue;
import br.uff.es2.war.model.Territory;
import java.util.Comparator;

/**
 *
 * @author Victor Guimarães
 */
public class TerritoryValueComparator implements TerritoryValue, Comparator<Territory> {

    private final TerritoryValue[] values;

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

    public TerritoryValue[] getValues() {
        return values;
    }

    @Override
    public int compare(Territory o1, Territory o2) {
        return Double.compare(getTerritoryValue(o2), getTerritoryValue(o1));
    }

}
