/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import com.sun.corba.se.spi.oa.OADefault;
import java.util.Map;
import java.util.Set;

/**
 * This class describes an Objective of the game.
 *
 * @see Objective
 * @see ConquerContinent
 * @see ConquerExtraContinent
 * @see ConquerTerritory
 * @see DestroyPlayer
 *
 * @author Victor Guimarães
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
     * Another {@link Objective} to be achieved in case the main objective
     * becomes impossible, the {@link Player} must achieve the
     * {@link #alternativeObjective} instead.
     */
    private FullObjective alternativeObjective;

    /**
     * Defines if the {@link #alternativeObjective} is the one which must be
     * achieved now.
     */
    private boolean alternative = false;

    /**
     * A description of the {@link Objective}.
     */
    private String description;

    /**
     * Constructor with all needed parameters for a {@link Objective} that does
     * not have secondary {@link Objective}.
     *
     * @param description a description of the {@link Objective}
     * @param mandatoryObjectives a {@link Set} of mandatory objectives. To win
     * the game, the {@link Player} must achieve each {@link ParcialObjective}
     * from this {@link Set}.
     */
    public FullObjective(String description, Set<ParcialObjetive> mandatoryObjectives) {
        this.description = description;
        this.mandatoryObjectives = mandatoryObjectives;
    }

    /**
     * Constructor with all needed parameters.
     *
     * @param description a description of the {@link Objective}
     * @param mandatoryObjectives a {@link Set} of mandatory objectives. To win
     * the game, the {@link Player} must achieve each {@link ParcialObjective}
     * from this {@link Set}.
     * @param alternativeObjective another {@link Objective} to be achieved in
     * case the main objective becomes impossible, the {@link Player} must
     * achieve the {@link #alternativeObjective} instead.
     */
    public FullObjective(String description, Set<ParcialObjetive> mandatoryObjectives, FullObjective alternativeObjective) {
        this.description = description;
        this.mandatoryObjectives = mandatoryObjectives;
        this.alternativeObjective = alternativeObjective;
    }

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

    /**
     * Getter for a {@link Set} of mandatory objectives. To win the game, the
     * {@link Player} must achieve each {@link ParcialObjective} from this
     * {@link Set}.
     *
     * @return a {@link Set} of mandatory objectives. To win the game, the
     * {@link Player} must achieve each {@link ParcialObjective} from this
     * {@link Set}
     */
    public Set<ParcialObjetive> getMandatoryObjectives() {
        return (alternative ? alternativeObjective.getMandatoryObjectives() : mandatoryObjectives);
    }

    /**
     * Getter for a {@link Map} of {@link Set} of {@link ParcialObjective}. To
     * win the game, the {@link Player} must achieve each
     * {@link ParcialObjective} from, at least, a {@link Set}.
     *
     * @return a {@link Map} of {@link Set} of {@link ParcialObjective}. To win
     * the game, the {@link Player} must achieve each {@link ParcialObjective}
     * from, at least, a {@link Set}.
     */
    public Map<Integer, Set<ParcialObjetive>> getSecondaryObjective() {
        return (alternative ? alternativeObjective.getSecondaryObjective() : secondaryObjective);
    }

    /**
     * Setter for another {@link Objective} to be achieved in case the main
     * objective becomes impossible, the {@link Player} must achieve the
     * {@link #alternativeObjective} instead.
     *
     * @param alternativeObjective another {@link Objective} to be achieved in
     * case the main objective becomes impossible, the {@link Player} must
     * achieve the {@link #alternativeObjective} instead
     */
    void setAlternativeObjective(FullObjective alternativeObjective) {
        this.alternativeObjective = alternativeObjective;
    }

    /**
     * Getter for another {@link Objective} to be achieved in case the main
     * objective becomes impossible, the {@link Player} must achieve the
     * {@link #alternativeObjective} instead.
     *
     * @return another {@link Objective} to be achieved in case the main
     * objective becomes impossible, the {@link Player} must achieve the
     * {@link #alternativeObjective} instead
     */
    public FullObjective getAlternativeObjective() {
        return (alternative ? alternativeObjective.getAlternativeObjective() : alternativeObjective);
    }

    /**
     * Getter for a description of the {@link Objective}.
     *
     * @return a description of the {@link Objective}.
     */
    public String getDescription() {
        return (alternative ? alternativeObjective.toString() : description);
    }

    @Override
    public String toString() {
        return (alternative ? alternativeObjective.toString() : description);
    }

}
