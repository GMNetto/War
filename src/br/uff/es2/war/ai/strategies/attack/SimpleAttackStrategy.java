/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies.attack;

import br.uff.es2.war.ai.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.ai.strategies.WeightEquationTerritoryValue;
import br.uff.es2.war.ai.strategies.WinLoseTerritoryValue;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Victor Guimarães
 */
public class SimpleAttackStrategy implements AttackStrategy {

    private final WeightEquationTerritoryValue weightEquation;
    private final WinLoseTerritoryValue winLose;
    private final Game game;
    private final Player player;

    private Map<Territory, Double> territoryImportance;

    public SimpleAttackStrategy(WeightEquationTerritoryValue weightEquation, WinLoseTerritoryValue winLose, Game game, Player player) {
        this.weightEquation = weightEquation;
        this.winLose = winLose;
        this.game = game;
        this.player = player;
    }

    @Override
    public Combat declareCombat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadAllEnemiesTerritories() {
        Set<Territory> territories;
        for (Player enemy : game.getPlayers()) {
            if (enemy.equals(player))
                continue;

            /*
            for (Object object : game) {
                
            }
            */
        }
    }

    private boolean putTerritory() {
        return false;
    }

}
