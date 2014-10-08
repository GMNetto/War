/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

/**
 * This class represents a part of a objective. This part is a specific part
 * which compose the main objective, such as conquer a single continent or
 * destroy a player.
 *
 * @author Victor Guimarães
 */
public abstract class ParcialObjetive implements Objective {

    /**
     * The specific {@link World} of the {@link Objective}.
     */
    protected final World world;

    /**
     * The owner of the {@link Objective}.
     */
    protected Player owner;

    /**
     * Constructor with all needed parameters.
     *
     * @param world the specific {@link World} of the {@link Objective}
     */
    public ParcialObjetive(World world) {
        this.world = world;
    }

    /**
     * Test if the given territory is needed to complete the objective or not.
     *
     * @param territory the {@link Territory}
     * @return true if it is, false otherwise
     */
    public abstract boolean isNeeded(Territory territory);

    /**
     * Getter for the specific {@link World} of the {@link Objective}.
     *
     * @return the specific {@link World} of the {@link Objective}
     */
    public World getWorld() {
        return world;
    }

    /**
     * Setter for the owner of the {@link Objective}.
     *
     * @param owner the owner of the {@link Objective}
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Getter for the owner of the {@link Objective}.
     *
     * @return the owner of the {@link Objective}
     */
    public Player getOwner() {
        return owner;
    }

}
