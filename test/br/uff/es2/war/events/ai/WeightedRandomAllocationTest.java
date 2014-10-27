/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.events.ai;

import br.uff.es2.war.ai.BasicBot;
import br.uff.es2.war.ai.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.ai.strategies.WeightEquationTerritoryValue;
import br.uff.es2.war.ai.strategies.WinLoseTerritoryValue;
import br.uff.es2.war.ai.strategies.attack.allocation.WeightedRandomAllocation;
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
    private WinLoseTerritoryValue winLoseTerritoryValue;
    private WeightEquationTerritoryValue weightEquationTerritoryValue;
    private WeightedRandomAllocation weightedRandomAllocation;

    public WeightedRandomAllocationTest() throws NonexistentEntityException {
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

        //game = new Game(players, world, colors, gl.getCards());
        

        for (i = 1; i < players.length; i++) {
            players[i] = new DumbPlayer(colors[i], i);
        }
        
        game = new Game(players, world, colors, gl.getCards());
        players[0] = new BasicBot(null, game);
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
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 10; k++) {
                afp.getAttackProbability(j, k);
            }
        }
        winLoseTerritoryValue = new WinLoseTerritoryValue(game, players[0], afp);
        weightEquationTerritoryValue = new WeightEquationTerritoryValue(game, players[0], 2, 0.5, 0.5, 0.5, 0.5, 0.5);
        weightedRandomAllocation = new WeightedRandomAllocation(weightEquationTerritoryValue, winLoseTerritoryValue);
        ((BasicBot) players[0]).setAllocationInstruction(weightedRandomAllocation);
    }

    @Test
    public void TEST_ALLOCATIONS() {
        Player p = players[0];
        int previous = 0;
        System.out.println(p.getObjective());
        if (p.getObjective().toString().startsWith("Destruir"))
            System.out.println("breakpoint");
        System.out.println("");
        for (Territory territory : world.getTerritoriesByOwner(players[0])) {
            System.out.println("Name: " + territory.getName() + "\t\t\tSoldiers: " + territory.getSoldiers());
            previous += territory.getSoldiers();
        }
        System.out.println("\n\n");
        p.distributeSoldiers(4, world.getTerritoriesByOwner(players[0]));

        int post = 0;
        for (Territory territory : world.getTerritoriesByOwner(players[0])) {
            System.out.println("Name: " + territory.getName() + "\t\t\tSoldiers: " + territory.getSoldiers());
            post += territory.getSoldiers();
        }

        assertEquals(previous + 4, post);
    }

}
