/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.events.ai;

import br.uff.es2.war.ai.BasicBot;
import br.uff.es2.war.ai.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.ai.strategies.OffensiveTerritoryValue;
import br.uff.es2.war.ai.strategies.WeightEquationTerritoryValue;
import br.uff.es2.war.ai.strategies.WinLoseTerritoryValue;
import br.uff.es2.war.ai.strategies.attack.allocation.WeightedRandomAllocationStrategy;
import br.uff.es2.war.ai.strategies.cardchange.GreedyChangeCardStrategy;
import br.uff.es2.war.controller.GameLoader;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.DumbPlayer;
import br.uff.es2.war.model.objective.Objective;
import br.uff.es2.war.model.objective.ObjectiveComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.management.InvalidAttributeValueException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;

/**
 *
 * @author Gustavo
 */
public class GreedyChangeCardStrategyTest {

    private World world;
    private Game game;
    private SortedSet<Objective> objectives;
    private Player[] players;
    private Color[] colors;
    private double[] w;
    WeightEquationTerritoryValue[] weightEquationTerritoryValues;

    public GreedyChangeCardStrategyTest() throws NonexistentEntityException, InvalidAttributeValueException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("WarESIIPU");
        GameLoader gl = new GameLoader(0, factory);
        world = gl.getWorld();
        objectives = new TreeSet<>(new ObjectiveComparator());
        objectives.addAll(gl.getObjectives());
        players = new Player[3];
        colors = new Color[gl.getColors().size()];

        int i = 0;
        for (Color color : gl.getColors()) {
            colors[i] = color;
            i++;
        }

        for (i = 0; i < players.length; i++) {
            players[i] = new BasicBot(null, null);
            players[i].setColor(colors[i]);
        }
        game = new Game(players, world, colors, gl.getCards());

        List<Objective> obj = new ArrayList<>(objectives);
        Collections.shuffle(obj);
        Random r = new Random();
        for (i = 0; i < players.length; i++) {
            players[i].setObjective(obj.get(r.nextInt(obj.size())));
            obj.remove(players[i].getObjective());
            players[i].setGame(game);
        }

        game.distributeTerritories();
        AttackProbabilityFactory afp = new AttackProbabilityFactory();

        WinLoseTerritoryValue winLoseTerritoryValue;
        WeightEquationTerritoryValue weightEquationTerritoryValue;
        WeightedRandomAllocationStrategy weightedRandomAllocation;
        OffensiveTerritoryValue offensiveTerritoryValue;

        for (Player player : players) {
            winLoseTerritoryValue = new WinLoseTerritoryValue(game, player, afp);
            weightEquationTerritoryValue = new WeightEquationTerritoryValue(game, player, 0.9, 0.1, 0.15, 0.1, 0.1, 0.3);
            offensiveTerritoryValue = new OffensiveTerritoryValue(winLoseTerritoryValue);
            weightedRandomAllocation = new WeightedRandomAllocationStrategy(offensiveTerritoryValue, weightEquationTerritoryValue, winLoseTerritoryValue);
            ((BasicBot) player).setAllocationInstruction(weightedRandomAllocation);
            ((BasicBot) player).setChangeCardStrategy(new GreedyChangeCardStrategy(0, player, game, weightEquationTerritoryValue));
        }

        for (int j = 0; j < players.length; j++) {
            for (int k = 0; k < (j % 2 == 0 ? 4 : 5); k++) {
                players[j].addCard(game.drawCard());
            }
        }

    }

    @Test
    public void TESTCARDS() {
        for (Player player : players) {
            System.out.println("Player " + player.getColor() + " cards: ");
            for (Card card : player.getCards()) {
                if (card.getFigure() != 0) {
                    System.out.print(card.getTerritory().getName() + " " + card.getFigure() + "-");
                } else {
                    System.out.print("Curinga " + card.getFigure() + "-");
                }
            }
            System.out.println();
            System.out.println("Serão trocados: ");
            List<Card> resp = player.exchangeCards();
            for (Card card : resp) {
                if (card.getFigure() != 0) {
                    System.out.print(card.getTerritory().getName() + " " + card.getFigure() + "-");
                } else {
                    System.out.print("Curinga " + card.getFigure() + "-");
                }
            }
            System.out.println();
        }
    }

}
