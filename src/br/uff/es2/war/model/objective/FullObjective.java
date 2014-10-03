/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Victor
 */
public class FullObjective implements Objective {

    /**
     * A {@link Set} of mandatory objectives. To win the game, the
     * {@link Player} must achieve each {@link ParcialObjective} from this
     * {@link Set}.
     */
    private Set<ParcialObjetive> mandatoryObjectives;

    /**
     * A {@link Map} of {@link Set} of {@link ParcialObjective}. To win the
     * game, the {@link Player} must achieve each {@link ParcialObjective} from,
     * at least, a {@link Set}.
     */
    private Map<Integer, Set<ParcialObjetive>> secondaryObjective;

    /**
     * In case the main objective becomes impossible, the {@link Player} must
     * achieve the {@link #alternativeObjective} instead.
     */
    private FullObjective alternativeObjective;

    /**
     * Defines if the {@link #alternativeObjective} is the one which must be
     * achieved now.
     */
    private boolean alternative;

    @Override
    public boolean wasAchieved() {
        if (alternative)
            return alternativeObjective.wasAchieved();

        for (ParcialObjetive parcialObjetive : mandatoryObjectives) {
            if (!parcialObjetive.wasAchieved())
                return false;
        }

        boolean completedSet;
        for (Set<ParcialObjetive> parcialObjetives : secondaryObjective.values()) {
            completedSet = true;
            for (ParcialObjetive parcialObjetive : parcialObjetives) {
                if (!parcialObjetive.wasAchieved()) {
                    completedSet = false;
                    break;
                }
            }
            if (completedSet)
                return true;
        }

        return false;
    }

    /**
     * Switches to the alternative {@link Objective}.
     */
    public void switchToAlternativeObjective() {
        if (alternative) {
            alternativeObjective.switchToAlternativeObjective();
        } else {
            alternative = true;
        }
    }

}
