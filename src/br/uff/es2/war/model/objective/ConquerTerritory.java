/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import java.util.Set;

/**
 *
 * @author Victor
 */
public class ConquerTerritory extends ParcialObjetive {

    private int numberOfTerritories;
    private int minimalOfSoldiersInEach;

    public ConquerTerritory(World world, Player owner, int numberOfTerritories, int minimalOfSoldiersInEach) {
        super(world, owner);
        this.numberOfTerritories = numberOfTerritories;
        this.minimalOfSoldiersInEach = minimalOfSoldiersInEach;
    }

    @Override
    public boolean isNeeded(Territory territory) {
        return world.getTerritoriesByOwner(owner).size() < numberOfTerritories;
    }

    @Override
    public boolean wasAchieved() {
        Set<Territory> territories = world.getTerritoriesByOwner(owner);
        if (territories.size() < minimalOfSoldiersInEach)
            return false;

        int count = numberOfTerritories;
        for (Territory territory : territories) {
            if (territory.getSoldiers() >= minimalOfSoldiersInEach) {
                count--;
            }

            if (count == 0)
                return true;
        }
        
        return false;
    }

}
