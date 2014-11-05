/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies.attack;

import br.uff.es2.war.ai.strategies.TerritoryValue;
import br.uff.es2.war.ai.strategies.TerritoryValueComparator;
import br.uff.es2.war.ai.strategies.WinLoseTerritoryValue;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.management.InvalidAttributeValueException;
import org.omg.CORBA.DynAnyPackage.Invalid;

/**
 * This class is used to get declarations of attacks from BOTs. It declares
 * attack always it believes it can win. Attacking on order of importance of the
 * attacked territory. It believes that can win by calculating the probability
 * of victory compared against a threshold, setted on the constructor. The
 * threshold decreases each turn without attacking, until it becomes,
 * approximately, half the original. When a attack happens, the threshold
 * returns to original value.
 *
 * @author Victor Guimarães
 */
public class BestEffortAttackStrategy implements AttackStrategy {

    /**
     * The {@link Player} interested on the combat declaration.
     */
    private final Player player;

    /**
     * The game context.
     */
    private final Game game;

    /**
     * A {@link WinLoseTerritoryValue} to get the probabilities of a combat.
     */
    private final WinLoseTerritoryValue winLoseTerritoryValue;

    /**
     * A {@link TerritoryValueComparator} to get the importance of a
     * {@link Territory}.
     */
    private final TerritoryValue territoryValue;

    /**
     * A threshold to measure the acceptable risk of a combat.
     */
    private final double threshold;

    /**
     * A threshold to redefine the risk.
     */
    private double optimisticThreshold;

    /**
     * A counter to count the turns since the last combat.
     */
    private int turnsSinceLastAttack;

    /**
     * The constructor with all needed parameters.
     *
     * @param player the {@link Player} interested on the combat declaration.
     * @param game the game context.
     * @param winLoseTerritoryValue a {@link WinLoseTerritoryValue} to get the
     * probabilities of a combat.
     * @param territoryValue a {@link TerritoryValueComparator} to get the
     * importance of a {@link Territory}.
     * @param threshold a threshold to measure the acceptable risk of a combat
     * must be [1, 0].
     * @throws javax.management.InvalidAttributeValueException in case a weight
     * not be between [1, 0]
     */
    public BestEffortAttackStrategy(Player player, Game game, WinLoseTerritoryValue winLoseTerritoryValue, TerritoryValue territoryValue, double threshold) throws InvalidAttributeValueException {
        this.player = player;
        this.game = game;
        this.winLoseTerritoryValue = winLoseTerritoryValue;
        this.territoryValue = territoryValue;
        this.threshold = threshold;

        if (threshold < 0.0 || threshold > 1.0)
            throw new InvalidAttributeValueException();

        this.optimisticThreshold = threshold;
        this.turnsSinceLastAttack = 0;
    }

    @Override
    public Combat declareCombat() {
        Territory attacker = null;
        optimisticThreshold = threshold * Math.pow(0.9, turnsSinceLastAttack);

        List<Territory> list = getAllEnemiesOnBorders();
        for (Territory territory : list) {
            if (winLoseTerritoryValue.getTerritoryValue(territory) >= optimisticThreshold) {
                turnsSinceLastAttack = 0;
                attacker = getBestAttackerFor(territory);
                if (attacker != null)
                    return new Combat(attacker, territory, Math.min(attacker.getSoldiers() - 1, 3));
            }
        }

        turnsSinceLastAttack++;
        turnsSinceLastAttack = Math.min(turnsSinceLastAttack, 7);

        return null;
    }

    /**
     * Loads the enemies {@link Territory}s sorted by the importance for the
     * {@link Player}.
     *
     * @return a {@link List} of the enemies {@link Territory}s
     */
    @Deprecated
    private List<Territory> loadAllEnemiesTerritories() {
        List<Territory> territories = new LinkedList<>();

        for (Player enemy : game.getPlayers()) {
            if (enemy.equals(player))
                continue;

            territories.addAll(game.getWorld().getTerritoriesByOwner(enemy));
        }

        Collections.sort(territories, territoryValue);

        return territories;
    }
    
    private List<Territory> getAllEnemiesOnBorders() {
        Set<Territory> territories = new HashSet<>();
        
        for (Territory territory : game.getWorld().getTerritoriesByOwner(player)) {
            for (Territory t : territory.getBorders()) {
                if (!t.getOwner().equals(player)) {
                    territories.add(t);
                }
            }
        }
        
        List<Territory> ter = new ArrayList<>(territories);
        
        Collections.sort(ter, territoryValue);
        
        return ter;
    }

    /**
     * Gets the best {@link Territory} to attack. It try to get first the
     * {@link Territory} with bigger number of soldiers, consequently better
     * probability of winning. If both have the same number of soldiers, gets
     * the less important for the player.
     *
     * @param territory the attacked
     * @return the best attacker
     */
    private Territory getBestAttackerFor(Territory territory) {
        Territory attacker = null;

        for (Territory t : territory.getBorders()) {
            if (!t.getOwner().equals(player))
                continue;

            if (attacker == null) {
                attacker = t;
            } else {
                attacker = getBestAttacker(attacker, t);
            }
        }

        return (attacker != null && attacker.getSoldiers() > 1 ? attacker : null);
    }

    /**
     * Gets the best {@link Territory} to attack. It try to get first the
     * {@link Territory} with bigger number of soldiers, consequently better
     * probability of winning. If both have the same number of soldiers, gets
     * the less important for the player.
     *
     * @param a the option a
     * @param b the option b
     * @return the best option
     */
    private Territory getBestAttacker(Territory a, Territory b) {
        int value;
        if (a.getSoldiers() > b.getSoldiers()) {
            return a;
        } else if (a.getSoldiers() < b.getSoldiers()) {
            return b;
        } else {
            value = territoryValue.compare(a, b);
            if (value == 0) {
                return a;
            } else if (value < 0) {
                return b;
            }
            return a;
        }
    }

    public double getThreshold() {
        return threshold;
    }

    public double getOptimisticThreshold() {
        return optimisticThreshold;
    }
    
}
