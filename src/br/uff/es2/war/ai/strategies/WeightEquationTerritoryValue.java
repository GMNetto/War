package br.uff.es2.war.ai.strategies;

import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.objective.Objective;
import java.util.HashSet;
import java.util.Set;
import javax.management.InvalidAttributeValueException;

/**
 * Class to stimate how important is a {@link territory} for a {@link Player}.
 * To do so, this class uses a equa
 *
 * @author Victor Guimarães
 */
public class WeightEquationTerritoryValue extends TerritoryValue {

    /**
     * The game where this strategy will be used.
     */
    private final Game game;

    /**
     * The player whose this strategy belongs.
     */
    private final Player player;

    /**
     * A weight to be applied on a value that says if the {@link Territory}
     * belongs to the objective.
     */
    private final double belongsToObjectiveWeight;

    /**
     * A weight to be applied on a value that represents the number of borders a
     * {@link Territory} has.
     */
    private final double numberOfBordersWeight;

    /**
     * A weight to be applied on a value that represents the relative number of
     * borders a {@link Territory} has on the same {@link Continent}.
     */
    private final double relativeBordersOnSameContinentWeight;

    /**
     * A weight to be applied on a value that represents the number of borders
     * on another {@link Continent} a {@link Territory} has.
     */
    private final double numberOfBordersOutsideTheContinentWeight;

    /**
     * A weight to be applied on a value that represents the number of allied
     * borders.
     */
    private final double numberOfAlliedBordersWeight;

    /**
     * A weight to be applied on a value that represents the number of
     * {@link Territory}is on the the same {@link Continent}.
     */
    private final double numberOfTerritoriesOnSameContinentWeight;

    /**
     * An array to make sure that all possible results will be [0, 1].
     */
    private final double[] balanceWeight;

    /**
     * Constructor with the needed parameters.
     *
     * @param game the game where this strategy will be used
     * @param player the player whose this strategy belongs
     * @param w0 a weight to be applied on a value that says if the
     * {@link Territory} belongs to the objective
     * @param w1 a weight to be applied on a value that represents the number of
     * borders a {@link Territory} has
     * @param w2 a weight to be applied on a value that represents the relative
     * number of borders a {@link Territory} has on the same {@link Continent}
     * @param w3 a weight to be applied on a value that represents the number of
     * borders on another {@link Continent} a {@link Territory} has
     * @param w4 a weight to be applied on a value that represents the number of
     * allied borders
     * @param w5 a weight to be applied on a value that represents the number of
     * {@link Territory}is on the the same {@link Continent}.
     * @throws javax.management.InvalidAttributeValueException in case a weight
     * not be between [1, 0]
     */
    public WeightEquationTerritoryValue(final Game game, final Player player, double w0, double w1, double w2, double w3, double w4, double w5) throws InvalidAttributeValueException {
        this.game = game;
        this.player = player;
        this.belongsToObjectiveWeight = ensureInterval(w0);
        this.numberOfBordersWeight = ensureInterval(w1);
        this.relativeBordersOnSameContinentWeight = ensureInterval(w2);
        this.numberOfBordersOutsideTheContinentWeight = ensureInterval(w3);
        this.numberOfAlliedBordersWeight = ensureInterval(w4);
        this.numberOfTerritoriesOnSameContinentWeight = ensureInterval(w5);
        this.balanceWeight = new double[6];
        loadBalanceWeightArray();
    }

    /**
     * Method to ensure that the weight will be [0, 1].
     *
     * @param w the weight
     * @return the weight
     * @throws javax.management.InvalidAttributeValueException in case a weight
     * not be between [1, 0]
     */
    private double ensureInterval(double w) throws InvalidAttributeValueException {
        if (w < 0.0 || w > 1.0)
            throw new InvalidAttributeValueException();
        else
            return w;
    }

    /**
     * Constructor with the needed parameters. Notes that this constructor must
     * recieve an array with, at least, the same number of elements as the
     * weights. If it has more, only the necessaries will be considered, in
     * order.
     *
     * @see #WeightEquationStrategy(br.uff.es2.war.model.Game,
     * br.uff.es2.war.model.Player, double, double, double, double, double,
     * double)
     * @param game the game where this strategy will be used
     * @param player the player whose this strategy belongs
     * @param w an array with all the weights.
     */
    public WeightEquationTerritoryValue(final Game game, final Player player, double... w) throws InvalidAttributeValueException {
        this(game, player, w[0], w[1], w[2], w[3], w[4], w[5]);
    }

    /**
     * Load the balance weight to make sure that the values of the weight be [0,
     * 1].
     */
    private void loadBalanceWeightArray() {
        balanceWeight[0] = 1;

        balanceWeight[2] = 1;
        Set<Continent> others;
        int sameContinent;
        for (Territory territory : game.getWorld().getTerritories()) {
            sameContinent = 0;
            balanceWeight[1] = Math.max(balanceWeight[1], territory.getBorders().size());
            others = new HashSet<>();
            for (Territory t : territory.getBorders()) {
                if (t.getContinent().equals(territory.getContinent())) {
                    sameContinent++;
                } else {
                    others.add(t.getContinent());
                }
            }
            balanceWeight[3] = Math.max(balanceWeight[3], others.size());
            balanceWeight[5] = Math.max(balanceWeight[5], sameContinent);
        }

        balanceWeight[4] = balanceWeight[1];
        balanceWeight[2] = 1;
    }

    /**
     * Method that evaluates how important a {@link Territory} is for a player.
     * Based on a set of weight defined on the constructor and some criterias
     * from the game.
     *
     * @param territory the {@link Territory}
     * @return a value representing how important is the {@link Territory}
     */
    @Override
    public double getTerritoryValue(Territory territory) {
        double result = 0.0;

        result += belongsToObjectiveWeight * (getObjective().isNeeded(territory) ? 1.0 : 0.0) / balanceWeight[0];
        result += numberOfBordersWeight * (double) territory.getBorders().size() / balanceWeight[1];

        int sameContinent = 0;
        int alliedBorder = 0;
        Set<Continent> others = new HashSet<>();
        for (Territory t : territory.getBorders()) {
            if (t.getContinent().equals(territory.getContinent())) {
                sameContinent++;
            } else {
                others.add(t.getContinent());
            }
            if (t.getOwner().equals(player)) {
                alliedBorder++;
            }
        }

        result += relativeBordersOnSameContinentWeight * ((double) sameContinent / (double) territory.getContinent().size()) / balanceWeight[2];
        result += numberOfBordersOutsideTheContinentWeight * ((double) others.size()) / balanceWeight[3];
        result += numberOfAlliedBordersWeight * ((double) alliedBorder) / balanceWeight[4];

        int ownedOnSameContinent = 0;
        
        for (Territory t : territory.getContinent()) {
            if (t.getOwner().equals(player)) {
                ownedOnSameContinent++;
            }
        }
        
        result += numberOfTerritoriesOnSameContinentWeight * ((double) ownedOnSameContinent) / balanceWeight[5];

        return result / 6.0;
    }

    /**
     * Getter for the {@link Player}'s {@link Objective}.
     *
     * @return the {@link Player}'s {@link Objective}.
     */
    private Objective getObjective() {
        return player.getObjective();
    }

    /**
     * Getter for a weight to be applied on a value that says if the
     * {@link Territory} belongs to the objective.
     *
     * @return a weight to be applied on a value that says if the
     * {@link Territory} belongs to the objective
     */
    public double getBelongsToObjectiveWeight() {
        return belongsToObjectiveWeight;
    }

    /**
     * Getter for a weight to be applied on a value that represents the number
     * of borders a {@link Territory} has.
     *
     * @return a weight to be applied on a value that represents the number of
     * borders a {@link Territory} has
     */
    public double getNumberOfBordersWeight() {
        return numberOfBordersWeight;
    }

    /**
     * Getter for a weight to be applied on a value that represents the relative
     * number of borders a {@link Territory} has on the same {@link Continent}.
     *
     * @return a weight to be applied on a value that represents the relative
     * number of borders a {@link Territory} has on the same {@link Continent}
     */
    public double getRelativeBordersOnSameContinentWeight() {
        return relativeBordersOnSameContinentWeight;
    }

    /**
     * Getter for a weight to be applied on a value that represents the number
     * of borders on another {@link Continent} a {@link Territory} has.
     *
     * @return a weight to be applied on a value that represents the number of
     * borders on another {@link Continent} a {@link Territory} has
     */
    public double getNumberOfBordersOutsideTheContinentWeight() {
        return numberOfBordersOutsideTheContinentWeight;
    }

    /**
     * Getter for a weight to be applied on a value that represents the number
     * of allied borders.
     *
     * @return a weight to be applied on a value that represents the number of
     * allied borders
     */
    public double getNumberOfAlliedBordersWeight() {
        return numberOfAlliedBordersWeight;
    }

    /**
     * Getter for a weight to be applied on a value that represents the number
     * of {@link Territory}is on the the same {@link Continent}.
     *
     * @return a weight to be applied on a value that represents the number of
     * {@link Territory}is on the the same {@link Continent}
     */
    public double getNumberOfTerritoriesOnSameContinentWeight() {
        return numberOfTerritoriesOnSameContinentWeight;
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

    /**
     * Getter to obtain all the weights as an array. The weights are on the same
     * order of the constructor.
     *
     * @see #WeightEquationStrategy(br.uff.es2.war.model.Game,
     * br.uff.es2.war.model.Player, double, double, double, double, double,
     * double)
     * @return all the weights as an array.
     */
    public double[] getWeights() {
        double[] result = new double[4];

        result[0] = belongsToObjectiveWeight;
        result[1] = numberOfBordersWeight;
        result[2] = relativeBordersOnSameContinentWeight;
        result[3] = numberOfBordersOutsideTheContinentWeight;
        result[4] = numberOfAlliedBordersWeight;
        result[5] = numberOfTerritoriesOnSameContinentWeight;

        return result;
    }

}
