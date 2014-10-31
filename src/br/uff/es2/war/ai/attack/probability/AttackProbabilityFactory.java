/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.attack.probability;

import br.uff.es2.war.util.TreeNode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Factory to build {@link AttackProbability}s.
 *
 * @see AttackProbability
 * @author Victor Guimarães
 */
public class AttackProbabilityFactory {

    /**
     * Map to keep a cache of the probabilities already calculated.
     */
    private Map<ProbabilityTriple, AttackProbability> probabilities;

    /**
     * Represents the biggest key on the {@link Map} of probabilities.
     */
    private int biggestKey = 1;
    
    /**
     * Default constructor.
     */
    public AttackProbabilityFactory() {
        probabilities = new HashMap<>();
    }

    /**
     * A table with attack probabilities. In this case, the probabilities of
     * attacking with 3 soldiers against a number of defenders (row number + 1)
     * and the probability of killing a number of soldiers (column number).
     */
    public static final double[][] TABLE_PROBABILITY_3_ATTACKERS = {
        {(441.0 / 1296.0), (855.0 / 1296.0)},
        {(2275.0 / 7776.0), (2611.0 / 7776.0), (2890.0 / 7776.0)},
        {(17871.0 / 46656.0), (12348.0 / 46656.0), (10017.0 / 46656.0), (6420.0 / 46656.0)},};

    /**
     * A table with attack probabilities. In this case, the probabilities of
     * attacking with 2 soldiers against a number of defenders (row number + 1)
     * and the probability of killing a number of soldiers (column number).
     */
    public static final double[][] TABLE_PROBABILITY_2_ATTACKERS = {
        {(91.0 / 216.0), (125.0 / 216.0)},
        {(581.0 / 1296.0), (420.0 / 1296.0), (295.0 / 1296.0)},
        {(4816.0 / 7776.0), (1981.0 / 7776.0), (979.0 / 7776.0)},};

    /**
     * A table with attack probabilities. In this case, the probabilities of
     * attacking with a soldier against a number of defenders (row number + 1)
     * and the probability of killing a number of soldiers (column number).
     */
    public static final double[][] TABLE_PROBABILITY_1_ATTACKER = {
        {(21.0 / 36.0), (15.0 / 36.0)},
        {(161.0 / 216.0), (55.0 / 216.0)},
        {(1071.0 / 1296.0), (225.0 / 1296.0)},};

    /**
     * Getter for an {@link AttackProbability} which contains the probabilities
     * of win or lose an specific attack.
     *
     * @param attackSoldiers the attacker's initial number of soldiers
     * @param defenderSoldiers the defender's initial number of soldiers
     * @return an {@link AttackProbability} which contains the probabilities of
     * win or lose an specific attack
     */
    public AttackProbability getAttackProbability(int attackSoldiers, int defenderSoldiers) {
        int aux = Math.max(attackSoldiers, defenderSoldiers);
        
        if (aux > biggestKey) {
            for (int i = 1; i < aux + 2; i++) {
                for (int j = 1; j < aux + 1; j++) {
                    getAttackProbability(new ProbabilityTriple(i, j));
                }
            }
            biggestKey = aux;
        }
        
        return getAttackProbability(new ProbabilityTriple(attackSoldiers, defenderSoldiers));
    }
    
    /**
     * Getter for an {@link AttackProbability} which contains the probabilities
     * of win or lose an specific attack.
     *
     * @param probability the attack's initial state
     * @return an {@link AttackProbability} which contains the probabilities of
     * win or lose an specific attack
     */
    private AttackProbability getAttackProbability(ProbabilityTriple probability) {
        AttackProbability attackProbability = probabilities.get(probability);
        if (attackProbability == null) {
            if (probability.getAttackerSoldiers() == 1) {
                attackProbability = new AttackProbability(probability.getAttackerSoldiers(), probability.getDefenderSoldiers());
                attackProbability.setAttackerWins(0);
                attackProbability.setDefenderWins(1);
            } else {
                TreeNode<ProbabilityTriple> root = new TreeNode<>();
                root.setValue(probability);
                attackProbability = getAttackProbability(root);
                probabilities.put(probability, attackProbability);
            }
        }

        return attackProbability;
    }

    /**
     * Getter for an {@link AttackProbability} which contains the probabilities
     * of win or lose an specific attack. This method uses a tree of
     * possibilities to calculate the probabilities of win or lose.
     *
     * @param root the attack's initial state as a tree
     * @return an {@link AttackProbability} which contains the probabilities of
     * win or lose an specific attack
     */
    private AttackProbability getAttackProbability(TreeNode<ProbabilityTriple> root) {
        Set<TreeNode<ProbabilityTriple>> leaves = new LinkedHashSet<>();
        buildProbabilityTree(root, leaves);
        AttackProbability attackProbability = new AttackProbability(root.getValue().getAttackerSoldiers(), root.getValue().getDefenderSoldiers());
        double aWin = 0.0, dWin = 0.0;
        for (TreeNode<ProbabilityTriple> leaf : leaves) {
            if (leaf.getValue().getDefenderSoldiers() == 0) {
                aWin += leaf.getValue().getProbability();
                continue;
            } else if (leaf.getValue().getAttackerSoldiers() == 0) {
                dWin += leaf.getValue().getProbability();
                continue;
            }
        }

        attackProbability.setAttackerWins(aWin);
        attackProbability.setDefenderWins(dWin);
        return attackProbability;
    }

    /**
     * Method to build the tree of probabilities.
     *
     * @param root the tree's root
     * @param leaves a {@link Set} with the tree's leaves
     */
    private void buildProbabilityTree(TreeNode<ProbabilityTriple> root, Set<TreeNode<ProbabilityTriple>> leaves) {
        Set<TreeNode<ProbabilityTriple>> sons = getProbabilitiesSons(root);
        if (sons != null) {
            for (TreeNode<ProbabilityTriple> son : sons) {
                root.addSon(son);
                buildProbabilityTree(son, leaves);
            }
        } else {
            leaves.add(root);
        }
    }

    /**
     * Method to get sons of a specific node of the tree. This method also
     * contains some initial probabilities already loaded as O(1) getter.
     *
     * @param node the node to load the sons
     * @return a {@link Set} of sons of the given node. Note that this method
     * does not add the sons on the node, you must do this by yourself
     */
    private Set<TreeNode<ProbabilityTriple>> getProbabilitiesSons(TreeNode<ProbabilityTriple> node) {
        int attacker = node.getValue().getAttackerSoldiers() - (node.getFather() == null ? 1 : 0);
        int defender = node.getValue().getDefenderSoldiers();
        double prevProb = node.getValue().getProbability();

        if (attacker < 1 || defender < 1)
            return null;

        Set<TreeNode<ProbabilityTriple>> sons = new HashSet<>();
        TreeNode<ProbabilityTriple> son;

        //Dynamic Programming part
        AttackProbability ap = probabilities.get(node.getValue());
        if (ap != null) {
            sons.add(new TreeNode<>(node, new ProbabilityTriple(1, 0, ap.getAttackerWins() * prevProb)));
            sons.add(new TreeNode<>(node, new ProbabilityTriple(0, 1, ap.getDefenderWins() * prevProb)));
            return sons;
        }

        if (attacker > 2) {
            if (defender > 2) {
                son = new TreeNode<>(node, new ProbabilityTriple(attacker, defender - 3, (6420.0 / 46656.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 1, defender - 2, (10017.0 / 46656.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 2, defender - 1, (12348.0 / 46656.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 3, defender, (17871.0 / 46656.0) * prevProb));
                sons.add(son);
            } else if (defender == 2) {
                son = new TreeNode<>(node, new ProbabilityTriple(attacker, defender - 2, (2890.0 / 7776.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 1, defender - 1, (2611.0 / 7776.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 2, defender, (2275.0 / 7776.0) * prevProb));
                sons.add(son);
            } else if (defender == 1) {
                son = new TreeNode<>(node, new ProbabilityTriple(attacker, defender - 1, (855.0 / 1296.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 1, defender, (441.0 / 1296.0) * prevProb));
                sons.add(son);
            }
        } else if (attacker == 2) {
            if (defender > 2) {
                son = new TreeNode<>(node, new ProbabilityTriple(attacker, defender - 2, (979.0 / 7776.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 1, defender - 1, (1981.0 / 7776.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 2, defender, (4816.0 / 7776.0) * prevProb));
                sons.add(son);
            } else if (defender == 2) {
                son = new TreeNode<>(node, new ProbabilityTriple(attacker, defender - 2, (295.0 / 1296.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 1, defender - 1, (420.0 / 1296.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 2, defender, (581.0 / 1296.0) * prevProb));
                sons.add(son);
            } else if (defender == 1) {
                son = new TreeNode<>(node, new ProbabilityTriple(attacker, defender - 1, (125.0 / 216.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 1, defender, (91.0 / 216.0) * prevProb));
                sons.add(son);
            }
        } else if (attacker == 1) {
            if (defender > 2) {
                son = new TreeNode<>(node, new ProbabilityTriple(attacker, defender - 1, (225.0 / 1296.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 1, defender, (1071.0 / 1296.0) * prevProb));
                sons.add(son);
            } else if (defender == 2) {
                son = new TreeNode<>(node, new ProbabilityTriple(attacker, defender - 1, (55.0 / 216.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 1, defender, (161.0 / 216.0) * prevProb));
                sons.add(son);
            } else if (defender == 1) {
                son = new TreeNode<>(node, new ProbabilityTriple(attacker, defender - 1, (15.0 / 36.0) * prevProb));
                sons.add(son);

                son = new TreeNode<>(node, new ProbabilityTriple(attacker - 1, defender, (21.0 / 36.0) * prevProb));
                sons.add(son);
            }
        }

        return sons;
    }

}
