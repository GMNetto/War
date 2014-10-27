/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies.attack.allocation;

import br.uff.es2.war.ai.TerritoryValueComparator;
import br.uff.es2.war.ai.strategies.TerritoryValue;
import br.uff.es2.war.model.Territory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class allocates soldiers on {@link Territory}s weighted ramdon based on
 * the {@link Territory} value calculated by multipling the values from the
 * given {@link TerritoryValue}s.
 *
 * @author Victor Guimarães
 */
public class WeightedRandomAllocation implements AllocationStrategy {

    private final TerritoryValue[] territoryValues;
    
    /**
     * Variable to get random number.
     */
    private Random random;

    /**
     * Constructor with the {@link TerritoryValue}s to calculate the value of a
     * {@link Territory}.
     *
     * @param values the {@link TerritoryValue}s
     */
    public WeightedRandomAllocation(TerritoryValue... values) {
        TerritoryValueComparator territoryValueComparator = new TerritoryValueComparator(values);
        territoryValues = new TerritoryValue[values.length + 1];
        
        territoryValues[0] = territoryValueComparator;
        for (int i = 1; i < territoryValues.length; i++) {
            territoryValues[i] = values[i - 1];
        }
        
        this.random = new Random();
    }

    /**
     * Constructor with the {@link TerritoryValue}s to calculate the value of a
     * {@link Territory} and a seed for the random generator.
     *
     * @param seed the seed
     * @param values the {@link TerritoryValue}s
     */
    public WeightedRandomAllocation(long seed, TerritoryValue values) {
        this(values);
        this.random = new Random(seed);
    }

    @Override
    public void distributeSoldiers(int soldierQuantity, Set<Territory> territories) {
        for (int i = 0; i < soldierQuantity; i++) {
            allocSoldierWeightedRandom(territories, 0);
        }
    }

    /**
     * Allocates a soldier on a {@link Territory} based on its value.
     *
     * @param territories a {@link Set} of possible territories.
     */
    private void allocSoldierWeightedRandom(Set<Territory> territories, int territoryValueIndex) {
        List<Territory> valuesTerritories = new ArrayList<>(territories);
        Collections.sort(valuesTerritories, territoryValues[territoryValueIndex]);
        double[] weights = getWeights(valuesTerritories, territoryValues[territoryValueIndex]);
        double r = random.nextDouble() * weights[weights.length - 1];

        for (int i = 0; i < weights.length; i++) {
            if (r < weights[i]) {
                valuesTerritories.get(i).addSoldiers(1);
                return;
            }
        }
        
        if (territoryValueIndex < territoryValues.length - 1) {
            allocSoldierWeightedRandom(territories, territoryValueIndex + 1);
        } else {
            valuesTerritories.get(random.nextInt(valuesTerritories.size())).addSoldiers(1);
        }
    }

    /**
     * Generate an array with the accumulated weights of the given {@link List}
     * of {@link Territory}s.
     *
     * @param territories the {@link List} of {@link Territory}s
     * @return the array
     */
    private double[] getWeights(List<Territory> territories, TerritoryValue territoryValue) {
        double[] weights = new double[territories.size()];
        for (int i = 0; i < territories.size(); i++) {
            weights[i] = territoryValue.getTerritoryValue(territories.get(i));
            for (int j = 0; j < i; j++) {
                weights[i] += weights[j];
            }
        }
        return weights;
    }

}
