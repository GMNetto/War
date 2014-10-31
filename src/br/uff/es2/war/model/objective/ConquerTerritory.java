/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import java.util.Set;

/**
 * This class represents a partial objective of conquer a specific number of
 * territories and have, on each, a minimal number of soldiers.
 * 
 * @author Victor Guimarães
 */
public class ConquerTerritory extends ParcialObjetive {

    /**
     * The number of territories needed.
     */
    private final int numberOfTerritories;

    /**
     * The minimal number of soldiers needed on each {@link Territory}.
     */
    private final int minimalOfSoldiersInEach;

    /**
     * Constructor with all needed parameters.
     * 
     * @param world
     *            the specific {@link World} of the {@link Objective}
     * @param numberOfTerritories
     *            the number of territories needed
     * @param minimalOfSoldiersInEach
     *            the minimal number of soldiers needed on each
     *            {@link Territory}
     */
    public ConquerTerritory(World world, int numberOfTerritories,
	    int minimalOfSoldiersInEach) {
	super(world);
	this.numberOfTerritories = numberOfTerritories;
	this.minimalOfSoldiersInEach = minimalOfSoldiersInEach;
    }

    @Override
    public boolean isNeeded(Territory territory) {
        if (territory.getOwner().equals(owner)) {
            return territory.getSoldiers() >= numberOfTerritories;
        } else {
            return world.getTerritoriesByOwner(owner).size() < numberOfTerritories;
        }
    }

    @Override
    public boolean wasAchieved() {
	Set<Territory> territories = world.getTerritoriesByOwner(owner);
	if (territories.size() < numberOfTerritories)
	    return false;

	int count = 0;
	for (Territory territory : territories) {
	    if (territory.getSoldiers() >= minimalOfSoldiersInEach) {
		count++;
	    }

	    if (count == numberOfTerritories)
		return true;
	}

	return false;
    }

    /**
     * Getter for the number of territories needed.
     * 
     * @return the number of territories needed
     */
    public int getNumberOfTerritories() {
	return numberOfTerritories;
    }

    /**
     * Getter for the minimal number of soldiers needed on each
     * {@link Territory}.
     * 
     * @return the minimal number of soldiers needed on each {@link Territory}
     */
    public int getMinimalOfSoldiersInEach() {
	return minimalOfSoldiersInEach;
    }

}
