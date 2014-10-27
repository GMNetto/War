/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.events.ai;

import br.uff.es2.war.ai.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.ai.strategies.WinLoseTerritoryValue;
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
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.Assert.*;
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
    public void LOSE_AUTRALIA_BY_DIRECT_ATTACKING() {
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
            assertTrue(0.0 <= aux && aux <= 1.0);
            prob = aux;
        }
    }

    @Test
    public void LOSE_AUTRALIA_BY_FIRST_LEVEL_ATTACKING() {
        System.out.println("\n\nLOSE_AUTRALIA_BY_FIRST_LEVEL_ATTACK\n\n");
        //double threshold = Math.pow(10.0, -14);

        Territory attacked = world.getTerritoryByName("Austrália");
        double prob = wltv.getTerritoryValue(attacked);
        System.out.println(prob);
        System.out.println("");
        assertTrue(prob == 0);

        Territory attacker = world.getTerritoryByName("Índia");
        Territory middle = world.getTerritoryByName("Sumatra");
        attacker.setOwner(players[1]);

        double aux;
        attacker.addSoldiers(1);
        for (int i = 0; i < 10; i++) {
            attacker.addSoldiers(1);
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + attacker.getName() + "\tSoldiers: " + attacker.getSoldiers()
                    + "\tMiddle: " + middle.getName() + "\tSoldiers: " + middle.getSoldiers()
                    + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            assertTrue(aux > prob);
            assertTrue(0.0 <= aux && aux <= 1.0);
            prob = aux;
        }

        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0)
                attacked.addSoldiers(1);
            else
                middle.addSoldiers(1);

            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + attacker.getName() + "\tSoldiers: " + attacker.getSoldiers()
                    + "\tMiddle: " + middle.getName() + "\tSoldiers: " + middle.getSoldiers()
                    + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            assertTrue(aux < prob);
            assertTrue(0.0 <= aux && aux <= 1.0);
            prob = aux;
        }
    }

    @Test
    public void LOSE_AUTRALIA_BY_SECOND_LEVEL_ATTACKING() {
        System.out.println("\n\nLOSE_AUTRALIA_BY_SECOND_LEVEL_ATTACK\n\n");

        Territory attacked = world.getTerritoryByName("Austrália");
        double prob = wltv.getTerritoryValue(attacked);
        System.out.println(prob);
        System.out.println("");
        assertTrue(prob == 0);

        Territory attacker = world.getTerritoryByName("Aral");
        attacker.setOwner(players[1]);

        Territory middle1 = world.getTerritoryByName("Índia");
        Territory middle2 = world.getTerritoryByName("Sumatra");
        
        double aux;
        prob = -1;
        attacker.addSoldiers(1);
        for (int i = 0; i < 10; i++) {
            attacker.addSoldiers(1);
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + attacker.getName() + "\tSoldiers: " + attacker.getSoldiers()
                    + "\tMiddle: " + middle1.getName() + "\tSoldiers: " + middle1.getSoldiers()
                    + "\tMiddle: " + middle2.getName() + "\tSoldiers: " + middle2.getSoldiers()
                    + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            assertTrue(aux > prob);
            assertTrue(0.0 <= aux && aux <= 1.0);
            prob = aux;
        }

        for (int j = 2; j < 7; j++) {
            System.out.println("\n");
            middle1.setSoldiers(j);
            System.out.println(j + " soldiers on " + middle1.getName());
            attacker.setSoldiers(2);
            prob = -1;
            for (int i = 0; i < 10; i++) {
                attacker.addSoldiers(1);
                aux = wltv.getTerritoryValue(attacked);
                System.out.println("Attacker: " + attacker.getName() + "\tSoldiers: " + attacker.getSoldiers()
                        + "\tMiddle: " + middle1.getName() + "\tSoldiers: " + middle1.getSoldiers()
                        + "\tMiddle: " + middle2.getName() + "\tSoldiers: " + middle2.getSoldiers()
                        + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
                assertTrue(aux > prob);
                assertTrue(0.0 <= aux && aux <= 1.0);
                prob = aux;
            }
        }

        middle1.setSoldiers(1);
        for (int j = 2; j < 7; j++) {
            System.out.println("\n");
            middle2.setSoldiers(j);
            System.out.println(j + " soldiers on " + middle2.getName());
            attacker.setSoldiers(2);
            prob = -1;
            for (int i = 0; i < 10; i++) {
                attacker.addSoldiers(1);
                aux = wltv.getTerritoryValue(attacked);
                System.out.println("Attacker: " + attacker.getName() + "\tSoldiers: " + attacker.getSoldiers()
                        + "\tMiddle: " + middle1.getName() + "\tSoldiers: " + middle1.getSoldiers()
                        + "\tMiddle: " + middle2.getName() + "\tSoldiers: " + middle2.getSoldiers()
                        + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
                assertTrue(aux > prob);
                assertTrue(0.0 <= aux && aux <= 1.0);
                prob = aux;
            }
        }

    }

    @Test
    public void WIN_AUTRALIA_BY_DIRECT_ATTACKING() {
        System.out.println("\n\nWIN_AUTRALIA_BY_DIRECT_ATTACKING\n\n");
        Territory attacked = world.getTerritoryByName("Austrália");
        Territory attacker = world.getTerritoryByName("Sumatra");
        attacked.setOwner(players[1]);

        double aux, prob = -1;
        for (int i = 0; i < 8; i++) {
            attacker.addSoldiers(1);
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + attacker.getName() + "\tSoldiers: " + attacker.getSoldiers() + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            assertTrue(aux > prob);
            assertTrue(0.0 <= aux && aux <= 1.0);
            prob = aux;
        }
    }

    @Test
    public void WIN_AUTRALIA_BY_FIRST_LEVEL_ATTACKING() {
        System.out.println("\n\nWIN_AUTRALIA_BY_FIRST_LEVEL_ATTACKING\n\n");
        Territory attacked = world.getTerritoryByName("Austrália");
        Territory middle = world.getTerritoryByName("Sumatra");
        Territory attacker = world.getTerritoryByName("Índia");
        attacked.setOwner(players[1]);
        middle.setOwner(players[3]);

        double aux, prob = -1;
        for (int i = 0; i < 8; i++) {
            attacker.addSoldiers(1);
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + attacker.getName() + "\tSoldiers: " + attacker.getSoldiers()
                    + "\tMiddle: " + middle.getName() + "\tSoldiers: " + middle.getSoldiers()
                    + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            assertTrue(aux > prob);
            assertTrue(0.0 <= aux && aux <= 1.0);
            prob = aux;
        }

        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) {
                middle.addSoldiers(1);
            } else {
                attacked.addSoldiers(1);
            }
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + attacker.getName() + "\tSoldiers: " + attacker.getSoldiers()
                    + "\tMiddle: " + middle.getName() + "\tSoldiers: " + middle.getSoldiers()
                    + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            assertTrue(aux < prob);
            assertTrue(0.0 <= aux && aux <= 1.0);
            prob = aux;
        }
    }

    @Test
    public void WIN_AUTRALIA_BY_SECOND_LEVEL_ATTACKING() {
        System.out.println("\n\nWIN_AUTRALIA_BY_SECOND_LEVEL_ATTACKING\n\n");
        Territory attacker = world.getTerritoryByName("Aral");
        Territory middle1 = world.getTerritoryByName("Índia");
        Territory middle2 = world.getTerritoryByName("Sumatra");
        Territory attacked = world.getTerritoryByName("Austrália");

        attacked.setOwner(players[1]);
        middle1.setOwner(players[3]);
        middle2.setOwner(players[4]);

        double aux, prob = -1;
        for (int i = 0; i < 8; i++) {
            attacker.addSoldiers(1);
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + attacker.getName() + "\tSoldiers: " + attacker.getSoldiers()
                    + "\tMiddle: " + middle1.getName() + "\tSoldiers: " + middle1.getSoldiers()
                    + "\tMiddle: " + middle2.getName() + "\tSoldiers: " + middle2.getSoldiers()
                    + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            assertTrue((i < 2 && aux == 0) || (aux > prob));
            assertTrue(0.0 <= aux && aux <= 1.0);
            prob = aux;
        }

        for (int i = 0; i < 12; i++) {
            if (i % 3 == 0) {
                middle1.addSoldiers(1);
            } else {
                if (i % 2 == 0) {
                    middle2.addSoldiers(1);
                } else {
                    attacked.addSoldiers(1);
                }
            }
            aux = wltv.getTerritoryValue(attacked);
            System.out.println("Attacker: " + attacker.getName() + "\tSoldiers: " + attacker.getSoldiers()
                    + "\tMiddle: " + middle1.getName() + "\tSoldiers: " + middle1.getSoldiers()
                    + "\tMiddle: " + middle2.getName() + "\tSoldiers: " + middle2.getSoldiers()
                    + "\tAttacked: " + attacked.getName() + "\tSoldiers: " + attacked.getSoldiers() + "\tProbability: " + aux);
            assertTrue(aux < prob);
            assertTrue(0.0 <= aux && aux <= 1.0);
            prob = aux;
        }
    }

}
