/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies;

import br.uff.es2.war.ai.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to estimate, statistically, the probability of a {@link Player} wins or
 * lose a {@link Territory}. If the {@link Territory} is owned by another
 * {@link Player} it calculates the probability of you winning the
 * {@link Territory} by attacking it with your strongest {@link Territory} on
 * the border of the attacked {@link Territory}. If the {@link Territory} is
 * already owned by the {@link Player}, it calculates the probability of losing
 * the {@link Territory} by being attacked for the sum of attackable soldiers on
 * the borders of the {@link Territory}, in this case, is considered the
 * {@link Player} with the biggest number of soldiers around you.
 *
 * @author Victor Guimarães
 */
public class WinLoseTerritoryValue implements TerritoryValue {

    /**
     * The game where this strategy will be used.
     */
    private final Game game;

    /**
     * The player whose this strategy belongs.
     */
    private final Player player;

    /**
     * A factory to calculate and store the probabilities.
     */
    private final AttackProbabilityFactory factory;

    /**
     * Constructor with all needed parameters.
     *
     * @param game the {@link Game} to calculate the probabilities
     * @param player the {@link Player} to refer the probabilities
     * @param factory the {@link AttackProbabilityFactory} of probabilities
     */
    public WinLoseTerritoryValue(Game game, Player player, AttackProbabilityFactory factory) {
        this.game = game;
        this.player = player;
        this.factory = factory;
    }

    /**
     * Get the probability of an attacker wins the combat against a defender.
     *
     * @param attack the attacker number of soldiers
     * @param defense the defender number of soldiers
     * @return the probability
     */
    private double getAttackerWins(int attack, int defense) {
        return factory.getAttackProbability(attack, defense).getAttackerWins();
    }

    /**
     * Calculates the probability of winning or losing a given
     * {@link Territory}.
     *
     * @param territory the {@link Territory}
     * @return the probability [0..1]
     */
    @Override
    public double getTerritoryValue(Territory territory) {
        if (territory.getOwner().equals(player)) {
            return getProbability(territory, null);
        } else {
            return getProbability(territory, player);
        }
    }

    /**
     * Method to calculates the probability of a given {@link Territory} be
     * conquered by an specific attacker {@link Player}. In case the attacker be
     * null, it calculates the probability for any {@link Player}.
     *
     * @param territory the {@link Territory}
     * @param attackOwner the attacker
     * @return the probability
     */
    private double getProbability(Territory territory, Player attackOwner) {
        double probability = 0;
        //Direct attack
        probability = getWinLoseDirectAttack(territory);
        if (probability != 0)
            return probability;

        //First level attack
        probability += getLoseUndirectAttack(territory, attackOwner);
        if (probability != 0)
            return probability;

        //Second level attack
        double proAux = 0, aux = 0;
        int soldiers = 0;
        int soldierAux = 0;
        for (Territory t : territory.getBorders()) {
            aux = getLoseUndirectAttack(t, attackOwner);
            if (aux > proAux) {
                proAux = aux;
                for (Territory t1 : t.getBorders()) {
                    if (attackOwner == null)
                        soldierAux = Math.max(soldierAux, getMaxSoldierFromBorderNotOwnedByPlayer(t1, player));
                    else
                        soldierAux = Math.max(soldierAux, getMaxSoldiersOwnedByPlayerOnBorder(t1, attackOwner));
                }
                soldiers = Math.min(2, Math.max(soldierAux - 2, 0));
            }
        }

        probability += getAttackerWins(soldiers, territory.getSoldiers()) * proAux;

        return probability;
    }

    /**
     * Get the probability of winning or losing an attack from a border's
     * {@link Territory}.
     *
     * @param territory the {@link Territory}
     * @return the probability
     */
    private double getWinLoseDirectAttack(Territory territory) {
        int soldiers;
        if (territory.getOwner().equals(player)) {
            soldiers = getMaxSumAttackableSoldiersFromBorders(territory);
        } else {
            soldiers = getMaxSoldiersOwnedByPlayerOnBorder(territory, player);
        }
        return getAttackerWins(soldiers, territory.getSoldiers());
    }

    /**
     * Get the probability of losing a {@link Territory} by being attacked from
     * a border of a border's {@link Territory}.
     *
     * @param territory the {@link Territory}
     * @param attackOwner the owner of attack
     * @return the probability of losing the {@link Territory}
     */
    private double getLoseUndirectAttack(Territory territory, Player attackOwner) {
        double probAux = 0, aux = 0;
        int soldier = 0, soldierAux = 0;
        for (Territory t : territory.getBorders()) {
            if (attackOwner != null && t.getOwner().equals(attackOwner))
                continue;

            if (attackOwner == null)
                soldierAux = getMaxSoldierFromBorderNotOwnedByPlayer(t, territory.getOwner());
            else
                soldierAux = getMaxSoldiersOwnedByPlayerOnBorder(t, attackOwner);

            aux = getAttackerWins(soldierAux, t.getSoldiers());
            if (aux > probAux) {
                soldier = Math.min(3, soldierAux - 1);
                probAux = aux;
            }
        }

        return getAttackerWins(soldier, territory.getSoldiers()) * probAux;
    }

    /**
     * Get the sum of attackable soldiers on the border of a given
     * {@link Territory}. The soldiers must belong to the same owner.
     *
     * @param territory the {@link Territory}
     * @return the sum of soldiers
     */
    private int getMaxSumAttackableSoldiersFromBorders(Territory territory) {
        Player owner = null;
        int max = 0, aux;
        for (Player p : game.getPlayers()) {
            if (p.equals(territory.getOwner()))
                continue;

            aux = getMaxSoldiersOwnedByPlayerOnBorder(territory, p);
            if (aux > max) {
                owner = p;
                max = aux;
            }
        }

        return max - getBordersByOwner(territory, owner).size() + 1;
    }

    /**
     * Get the max number of soldier not owned by a {@link Player} (neither by
     * the {@link Territory}'s owner) on the border of a given
     * {@link Territory}.
     *
     * @param territory the {@link Territory}
     * @param p the {@link Player}
     * @return the max number of soldier not owned by a {@link Player} (neither
     * by the {@link Territory}'s owner) on the border of a given
     * {@link Territory}
     */
    private int getMaxSoldierFromBorderNotOwnedByPlayer(Territory territory, Player p) {
        int aux = 0;
        for (Territory t : territory.getBorders()) {
            if (!t.getOwner().equals(p) && !t.getOwner().equals(territory.getOwner())) {
                aux = Math.max(aux, t.getSoldiers());
            }
        }

        return aux;
    }

    /**
     * Get the max number of soldier owned by a {@link Player} on the border of
     * a given {@link Territory}.
     *
     * @param territory the {@link Territory}
     * @param p the {@link Player}
     * @return the max number of soldier owned by a {@link Player} on the border
     * of a given {@link Territory}
     */
    private int getMaxSoldiersOwnedByPlayerOnBorder(Territory territory, Player p) {
        int max = 0;

        for (Territory t : getBordersByOwner(territory, p)) {
            max = Math.max(max, t.getSoldiers());
        }

        return max;
    }

    /**
     * Gets a {@link Set} with all the {@link Territory}is on the border of the
     * given {@link Territory} owned by the given {@link Player}.
     *
     * @param territory the given {@link Territory}
     * @param p the given {@link Player}
     * @return a {@link Set} with all the {@link Territory}is on the border
     * owned by the given the given {@link Player}
     */
    private Set<Territory> getBordersByOwner(Territory territory, Player p) {
        Set<Territory> borders = new HashSet<>();
        for (Territory t : territory.getBorders()) {
            if (t.getOwner().equals(p))
                borders.add(t);
        }
        return borders;
    }

    /**
     * Getter for the {@link Game}.
     *
     * @return the {@link Game}
     */
    public Game getGame() {
        return game;
    }

    /**
     * Getter for the {@link Player}.
     *
     * @return the {@link Player}
     */
    public Player getPlayer() {
        return player;
    }

}
