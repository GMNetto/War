/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ia.strategies;

import br.uff.es2.war.ia.attack.probability.AttackProbability;
import br.uff.es2.war.ia.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.ia.attack.probability.ProbabilityTriple;
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

    public WinLoseTerritoryValue(Game game, Player player, AttackProbabilityFactory factory) {
        this.game = game;
        this.player = player;
        this.factory = factory;
    }

    @Override
    public double getTerritoryValue(Territory territory) {
        if (!territory.getOwner().equals(player)) {
            return factory.getAttackProbability(new ProbabilityTriple(getMaxSoldiersFromPlayersBorders(territory, player), territory.getSoldiers())).getAttackerWins();
        } else {
            return factory.getAttackProbability(new ProbabilityTriple(getMaxSumAttackableSoldiersFromBorders(territory), territory.getSoldiers())).getAttackerWins();
        }
    }

    private int getMaxSumAttackableSoldiersFromBorders(Territory territory) {
        Set<Player> owners = new HashSet<>();
        Player owner = null;
        int max = 0, aux;
        for (Player p : owners) {
            aux = getMaxSoldiersFromPlayersBorders(territory, p);
            if (aux > max) {
                owner = p;
                max = aux;
            }
        }

        return max - getBordersByOwner(territory, owner).size();
    }

    private int getMaxSoldiersFromPlayersBorders(Territory territory, Player p) {
        int max = 0;

        for (Territory t : getBordersByOwner(territory, p)) {
            max = Math.max(max, t.getSoldiers());
        }

        return max;
    }

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
