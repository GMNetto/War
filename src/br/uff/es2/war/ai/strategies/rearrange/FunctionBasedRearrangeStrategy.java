/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies.rearrange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import br.uff.es2.war.ai.strategies.TerritoryValue;
import br.uff.es2.war.ai.strategies.TerritoryValueComparator;
import br.uff.es2.war.ai.strategies.rearrange.thresholdfunction.ThresholdFunction;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
//import java.util.function.Predicate;

/**
 *This class tries to distribute the soldiers according a function(f(x,y)) where x is the position of the territory in 
 * a sorted set of those belonged to that player. And f(x,y) is the number of soldires. Also there is a factor called y.
 * @author Gustavo
 */
public class FunctionBasedRearrangeStrategy implements RearrangeStrategy {

    private Player player;
    private Game game;
    private TerritoryValueComparator territoryValueComparator;
    private ThresholdFunction tFunction;
    private Map<Territory, Integer> destinyAndQtd;

    public FunctionBasedRearrangeStrategy(ThresholdFunction tFunction, Player player, Game game, TerritoryValue... values) {
        this.tFunction = tFunction;
        this.player = player;
        this.game = game;
        this.territoryValueComparator = new TerritoryValueComparator(values);
        this.destinyAndQtd = new HashMap<>();

    }

    @Override
    public void moveSoldiers() {
        List<Territory> territories = new ArrayList(game.getWorld().getTerritoriesByOwner(player));
        Collections.sort(territories, territoryValueComparator);
        destinyAndQtd.clear();
        for (Territory territory : territories) {
            destinyAndQtd.put(territory, territory.getSoldiers());
            int localThreshold = tFunction.value(territories.indexOf(territory), territoryValueComparator.getTerritoryValue(territory));
            List<Territory> neighboors = new ArrayList(territory.getBorders());
            ListIterator<Territory> iteratorNeighboors = neighboors.listIterator();
            while (iteratorNeighboors.hasNext()) {
                Territory candidate = iteratorNeighboors.next();
                if (territoryValueComparator.getTerritoryValue(candidate) > territoryValueComparator.getTerritoryValue(territory) || !game.getWorld().getTerritoriesByOwner(player).contains(candidate) || candidate.getSoldiers() < 2) {
                    iteratorNeighboors.remove();
                }
            }
            exchange(territory, neighboors, localThreshold);
        }
    }

    private void exchange(Territory destiny, List<Territory> neighboors, int localThreshold) {
        Collections.sort(neighboors, territoryValueComparator);
        Collections.reverse(neighboors);
        for (Territory origin : neighboors) {
            while (destiny.getSoldiers() < localThreshold && origin.getSoldiers()>1) {
                if (destinyAndQtd.containsKey(origin) && destinyAndQtd.get(origin) > 0) {
                    destinyAndQtd.put(origin, origin.getSoldiers() - 1);
                }
                destiny.addSoldiers(1);
                origin.addSoldiers(-1);
            }
        }
    }

}
