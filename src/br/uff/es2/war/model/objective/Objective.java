package br.uff.es2.war.model.objective;

import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

/**
 * Interface to represent an Objective.
 * 
 * @author Arthur
 * @author Victor Guimarães
 */
public interface Objective {

    /**
     * Checks if the objective was achieved.
     * 
     * @return true if it were achieved, false otherwise
     */
    public boolean wasAchieved();

    /**
     * Test if the given territory is needed to complete the objective or not.
     * 
     * @param territory
     *            the {@link Territory}
     * @return true if it is, false otherwise
     */
    public boolean isNeeded(Territory territory);
    
    public void setOwner(Player owner);

}
