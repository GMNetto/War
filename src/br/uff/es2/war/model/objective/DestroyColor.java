/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

/**
 * This class represents a partial objective of destroying a {@link Color}.
 *
 * @author Victor Guimarães
 */
public class DestroyColor extends ParcialObjetive {

    /**
     * The {@link Color} to be destroyed.
     */
    private final Color color;

    /**
     * Constructor with all needed parameters.
     *
     * @param world the specific {@link World} of the {@link Objective}
     * @param color the {@link Color} to be destroyed
     */
    public DestroyColor(World world, final Color color) {
        super(world);
        this.color = color;
    }

    @Override
    public boolean isNeeded(Territory territory) {
        if (territory.getOwner().getColor().equals(color)) {
            return true;
//        } else {
//            for (Territory t : territory.getBorders()) {
//                if (t.getOwner().getColor().equals(color))
//                    return true;
//            }
        }
        return false;
    }

    @Override
    public boolean wasAchieved() {
        return world.getTerritoriesByColor(color).isEmpty();
    }

    /**
     * Getter for the {@link Color} to be destroyed.
     *
     * @return the {@link Color} to be destroyed
     */
    public Color getPlayer() {
        return color;
    }

    @Override
    public boolean isPossible() {
        return !owner.getColor().equals(color);
    }
    
}
