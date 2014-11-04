/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
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
     * The owner of the {@link Objective}.
     */
    private Player owner;

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
        
        return hasAchievedSecondaryObjective();
    }

    @Override
    public boolean isNeeded(Territory territory) {
        for (ParcialObjetive parcialObjetive : mandatoryObjectives) {
            if (parcialObjetive.isNeeded(territory))
                return true;
        }

        return false;
    }

    private boolean hasAchievedSecondaryObjective() {
        boolean completedSet;
        if (secondaryObjective != null && !secondaryObjective.values().isEmpty()) {
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

        } else {
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

    @Override
    public boolean isPossible() {
        for (ParcialObjetive parcialObjetive : mandatoryObjectives) {
            if (!parcialObjetive.isPossible()) {
                return false;
            }
        }
        
        return true;
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

    /**
     * Setter for the owner of the {@link Objective}.
     *
     * @param owner the owner of the {@link Objective}
     */
    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
        for (ParcialObjetive parcialObjetive : mandatoryObjectives) {
            parcialObjetive.setOwner(owner);
        }

        if (secondaryObjective != null && !secondaryObjective.values().isEmpty()) {
            for (Set<ParcialObjetive> set : secondaryObjective.values()) {
                for (ParcialObjetive parcialObjetive : set) {
                    parcialObjetive.setOwner(owner);
                }
            }
        }

        if (alternativeObjective != null) {
            alternativeObjective.setOwner(owner);
        }
    }

    /**
     * Getter for the owner of the {@link Objective}.
     *
     * @return the owner of the {@link Objective}
     */
    public Player getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return (alternative ? alternativeObjective.toString() : description);
    }

}