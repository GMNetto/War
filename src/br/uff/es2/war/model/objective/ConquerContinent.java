/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

/**
 * This class represents a partial objective of conquer a specific
 * {@link Continent}.
 *
 * @author Victor Guimarães
 */
public class ConquerContinent extends ParcialObjetive {

    /**
     * The continent to be conquered.
     */
    private Continent continent;

    /**
     * Constructor with all needed parameters.
     *
     * @param world the specific {@link World} of the {@link Objective}
     * @param continent the continent to be conquered
     */
    public ConquerContinent(World world, Continent continent) {
        super(world);
        this.continent = continent;
    }

    @Override
    public boolean isNeeded(Territory territory) {
        return territory.getContinent().equals(continent);
    }

    @Override
    public boolean wasAchieved() {
        for (Territory territory : continent) {
            if (!territory.getOwner().equals(owner)) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Getter for the {@link Continent}.
     * 
     * @return the {@link Continent}
     */
    public Continent getContinent() {
        return continent;
    }

}
