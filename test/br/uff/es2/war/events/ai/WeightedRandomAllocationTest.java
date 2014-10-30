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
import br.uff.es2.war.controller.GameLoader;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
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
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Victor Guimarães
 */
public class WeightedRandomAllocationTest {

    private World world;
    private Game game;
    private SortedSet<Objective> objectives;
    private Player[] players;
    private Color[] colors;

    public WeightedRandomAllocationTest() throws NonexistentEntityException, InvalidAttributeValueException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("WarESIIPU");
        GameLoader gl = new GameLoader(0, factory);
        world = gl.getWorld();
        objectives = new TreeSet<>(new ObjectiveComparator());
        objectives.addAll(gl.getObjectives());
        players = new Player[6];
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
        }
        
        
        
    }

    @Test
    public void TEST_ALLOCATIONS() {

        for (int i = 0; i < 3; i++) {
            allocRound();
        }

        for (Territory t : world.getTerritories()) {
            System.out.println("Name: " + t.getName() + "\t\t\tSoldiers: " + t.getSoldiers() + "\t\t\tOwner: " + t.getOwner().getColor().getName());
        }
    }

    private void allocRound() {
        int previous;
        int post;
        for (Player player : players) {
            previous = 0;
            System.out.println("Color: " + player.getColor().getName());
            System.out.println("Objective: " + player.getObjective());
            System.out.println("");

            for (Territory t : world.getTerritoriesByOwner(player)) {
                System.out.println("Name: " + t.getName() + "\t\t\tSoldiers: " + t.getSoldiers());
                previous += t.getSoldiers();
            }

            System.out.println("\n\n");
            player.distributeSoldiers(3, world.getTerritoriesByOwner(player));

            post = 0;
            for (Territory t : world.getTerritoriesByOwner(player)) {
                System.out.println("Name: " + t.getName() + "\t\t\tSoldiers: " + t.getSoldiers());
                post += t.getSoldiers();
            }
            assertEquals(previous + 3, post);
            System.out.println("\n\n");
        }
    }

}
