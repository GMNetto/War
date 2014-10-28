/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies.attack.allocation;

import br.uff.es2.war.ai.strategies.TerritoryValue;
import br.uff.es2.war.ai.strategies.WeightEquationTerritoryValue;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

/**
 *
 * @author Gustavo
 */
public class DefaultAllocationStrategy implements AllocationStrategy {

    private TerritoryValue function;
    private Player player;
    private Game game;
    private World world;

    public DefaultAllocationStrategy(TerritoryValue function, Player player, Game game) {
        this.function = function;
        this.player = player;
        this.game = game;
        this.world = game.getWorld();
    }

    @Override
    public void distributeSoldiers(int soldierQuantity, Set<Territory> territories) {
        Territory[] myTerritories = (Territory[]) world.getTerritoriesByOwner(player).toArray();
        Arrays.sort(myTerritories, (Territory t, Territory t1) -> {
            double tValue=function.getTerritoryValue(t);
            double t1Value=function.getTerritoryValue(t1);
            return Double.compare(tValue, t1Value)*(-1);
        });
        player.
    }

}
