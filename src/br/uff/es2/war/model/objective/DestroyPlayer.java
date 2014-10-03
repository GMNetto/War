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
 *
 * @author Victor
 */
public class DestroyPlayer extends ParcialObjetive {

    private Player player;

    public DestroyPlayer(Player player, World world, Player owner) {
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
    
}
