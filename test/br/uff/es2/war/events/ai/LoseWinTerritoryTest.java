/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.events.ai;

import br.uff.es2.war.ai.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.ai.attack.probability.ProbabilityTriple;
import br.uff.es2.war.ai.strategies.WinLoseTerritoryValue;
import br.uff.es2.war.controller.GameLoader;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.DumbPlayer;
import br.uff.es2.war.model.objective.Objective;
import br.uff.es2.war.model.objective.ObjectiveComparator;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the probabilities of wining or losing a {@link Territory}.
 *
 * @author Victor Guimarães
 */
public class LoseWinTerritoryTest {

    private World world;
    private Game game;
    private SortedSet<Objective> objectives;
    private Player[] players;
    private Color[] colors;
    private WinLoseTerritoryValue wltv;

    public LoseWinTerritoryTest() throws NonexistentEntityException {
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
            players[i] = new DumbPlayer(colors[i], i);
        }

        game = new Game(players, world, colors, gl.getCards());
        game.distributeTerritories();
        AttackProbabilityFactory afp = new AttackProbabilityFactory();
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 10; k++) {
                afp.getAttackProbability(j, k);
            }
        }
        wltv = new WinLoseTerritoryValue(game, players[0], afp);

        for (Territory territory : world.getContinentByName("Oceania")) {
            territory.setOwner(players[0]);
            territory.setSoldiers(1);
        }
        for (Territory territory : world.getContinentByName("Ásia")) {
            territory.setOwner(players[0]);
            territory.setSoldiers(1);
        }
        for (Territory territory : world.getContinentByName("Europa")) {
            territory.setOwner(players[1]);
            territory.setSoldiers(7);
        }
        for (Territory territory : world.getContinentByName("África")) {
            territory.setOwner(players[2]);
            territory.setSoldiers(5);
        }
    }

    @Test
    public void LOSE_AUTRALIA_BY_DIRECT_ATTACK() {
        System.out.println("\n\nLOSE_AUTRALIA_BY_DIRECT_ATTACK\n\n");
        double threshold = Math.pow(10.0, -14);

        Territory territory = world.getTerritoryByName("Sumatra");
        territory.setOwner(players[1]);
        territory.setSoldiers(4);

        Territory attacked = world.getTerritoryByName("Austrália");
        double prob = wltv.getTerritoryValue(attacked);
        System.out.println(prob);

        world.getTerritoryByName("Borneo").setSoldiers(4);
        assertEquals(prob, wltv.getTerritoryValue(attacked), threshold);

        world.getTerritoryByName("Nova Guiné").setSoldiers(7);
        assertEquals(prob, wltv.getTerritoryValue(attacked), threshold);

        double aux;
        for (int i = 0; i < 4; i++) {
            attacked.addSoldiers(1);
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + territory.getName() + "\tSoldiers: " + territory.getSoldiers() + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            assertTrue(aux < prob);
            prob = aux;
        }
    }

    @Test
    public void LOSE_AUTRALIA_BY_FIRST_LEVEL_ATTACK() {
        System.out.println("\n\nLOSE_AUTRALIA_BY_FIRST_LEVEL_ATTACK\n\n");
        //double threshold = Math.pow(10.0, -14);

        Territory attacked = world.getTerritoryByName("Austrália");
        double prob = wltv.getTerritoryValue(attacked);
        System.out.println(prob);
        System.out.println("");
        assertTrue(prob == 0);

        Territory territory = world.getTerritoryByName("Índia");
        territory.setOwner(players[1]);

        double aux;
        territory.addSoldiers(1);
        for (int i = 0; i < 10; i++) {
            territory.addSoldiers(1);
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + territory.getName() + "\tSoldiers: " + territory.getSoldiers() + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            assertTrue(aux > prob);
            prob = aux;
        }

        for (int i = 0; i < 3; i++) {
            attacked.addSoldiers(1);
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + territory.getName() + "\tSoldiers: " + territory.getSoldiers() + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            //assertTrue(aux > prob);
            //prob = aux;
        }
    }

    @Test
    public void LOSE_AUTRALIA_BY_SECOND_LEVEL_ATTACK() {
        System.out.println("\n\nLOSE_AUTRALIA_BY_SECOND_LEVEL_ATTACK\n\n");

        Territory attacked = world.getTerritoryByName("Austrália");
        double prob = wltv.getTerritoryValue(attacked);
        System.out.println(prob);
        System.out.println("");
        assertTrue(prob == 0);

        Territory territory = world.getTerritoryByName("Aral");
        territory.setOwner(players[1]);

        double aux;
        territory.addSoldiers(1);
        for (int i = 0; i < 10; i++) {
            territory.addSoldiers(1);
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + territory.getName() + "\tSoldiers: " + territory.getSoldiers() + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            //assertTrue(aux > prob);
            prob = aux;
        }

        String ter = "Índia";
        for (int j = 2; j < 7; j++) {
            System.out.println("\n");
            ter = "Índia";
            world.getTerritoryByName(ter).setSoldiers(j);
            System.out.println(j + " soldiers on " + ter);
            territory.setSoldiers(2);
            for (int i = 0; i < 10; i++) {
                territory.addSoldiers(1);
                aux = wltv.getTerritoryValue(attacked);
                System.out.println("Attacker: " + territory.getName() + "\tSoldiers: " + territory.getSoldiers() + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
                //assertTrue(aux > prob);
                prob = aux;
            }
        }
        
        world.getTerritoryByName(ter).setSoldiers(1);
        for (int j = 2; j < 7; j++) {
            System.out.println("\n");
            ter = "Sumatra";
            world.getTerritoryByName(ter).setSoldiers(j);
            System.out.println(j + " soldiers on " + ter);
            territory.setSoldiers(2);
            for (int i = 0; i < 10; i++) {
                territory.addSoldiers(1);
                aux = wltv.getTerritoryValue(attacked);
                System.out.println("Attacker: " + territory.getName() + "\tSoldiers: " + territory.getSoldiers() + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
                //assertTrue(aux > prob);
                prob = aux;
            }
        }

    }

}
