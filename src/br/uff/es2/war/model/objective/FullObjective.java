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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
