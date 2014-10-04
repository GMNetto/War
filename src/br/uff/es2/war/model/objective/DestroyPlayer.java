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
 * This class represents a partial objective of destroying a {@link Player}.
 *
 * @author Victor Guimarães
 */
public class DestroyPlayer extends ParcialObjetive {

    /**
     * The {@link Player} to be destroyed.
     */
    private final Player player;

    /**
     * Constructor with all needed parameters.
     *
     * @param world the specific {@link World} of the {@link Objective}
     * @param owner the owner of the {@link Objective}
     * @param player the {@link Player} to be destroyed
     */
    public DestroyPlayer(World world, Player owner, final Player player) {
        super(world, owner);
        this.player = player;
    }

    @Override
    public boolean isNeeded(Territory territory) {
        return territory.getOwner().equals(player);
    }

    @Override
    public boolean wasAchieved() {
        return world.getTerritoriesByOwner(player).isEmpty();
    }

    /**
     * Getter for the {@link Player} to be destroyed.
     *
     * @return the {@link Player} to be destroyed
     */
    public Player getPlayer() {
        return player;
    }
    
}
