/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.ai.strategies.rearrange;

import br.uff.es2.war.ai.strategies.TerritoryValue;
import br.uff.es2.war.ai.strategies.TerritoryValueComparator;
import br.uff.es2.war.ai.strategies.rearrange.thresholdfunction.ThresholdFunction;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
//import java.util.function.Predicate;

/**
 *
 * @author Gustavo
 */
public class FunctionBasedRearrangeStrategy implements RearrangeStrategy {
    
    private Player player;
    private Game game;
    private TerritoryValueComparator territoryValueComparator;
    private ThresholdFunction tFunction;
    private Map<Territory, Integer> destinyAndQtd;
    
    public FunctionBasedRearrangeStrategy(ThresholdFunction tFunction, Player player, Game game, TerritoryValue ... values){
        this.tFunction=tFunction;
        this.player=player;
        this.game=game;
        this.territoryValueComparator = new TerritoryValueComparator(values);
        this.destinyAndQtd=new HashMap<>();
        
    }

    @Override
    public void moveSoldiers() {
        List<Territory> territories=new ArrayList(game.getWorld().getTerritoriesByOwner(player));
        Collections.sort(territories, territoryValueComparator);
        for(Territory territory:territories){
            int localThreshold=tFunction.value(territories.indexOf(territory),territoryValueComparator.getTerritoryValue(territory));
            List<Territory> neighboors=new ArrayList(territory.getBorders());
            ListIterator<Territory> iteratorNeighboors=neighboors.listIterator();
            while(iteratorNeighboors.hasNext()){
                Territory candidate=iteratorNeighboors.next();
                if(territoryValueComparator.getTerritoryValue(candidate)>territoryValueComparator.getTerritoryValue(territory)||!game.getWorld().getTerritoriesByOwner(player).contains(candidate)){
                    iteratorNeighboors.remove();
                }
            }
           exchange(territory,neighboors,localThreshold);
        }
    }

    private void exchange(Territory destiny, List<Territory> neighboors,int localThreshold) {
        Collections.sort(neighboors, territoryValueComparator);
        Collections.reverse(neighboors);
        for(Territory origin:neighboors){
            while(destiny.getSoldiers()<localThreshold && origin.getSoldiers()>1){
                destiny.addSoldiers(1);
                origin.addSoldiers(-1);
            }
        }
    }
    
    
}
