/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import br.uff.es2.war.controller.GameLoader;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
public class ObjectiveTest {

    private World world;
    private Game game;
    private SortedSet<Objective> objectives;
    private Player[] players;
    private Set<Color> colors;

    public ObjectiveTest() throws NonexistentEntityException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("WarESIIPU");
        GameLoader wc = new GameLoader(0, factory);
        world = wc.getWorld();
        objectives = new TreeSet<>(new ObjectiveComparator());
        objectives.addAll(wc.getObjectives());
        players = new Player[6];
        colors = wc.getColors();

        for (int i = 0; i < players.length; i++) {
            players[i] = new DumbPlayer(Color.values()[i], i);
        }
        
        game = new Game(players, world);
        world.distributeTerritories(players, game);
    }

    @Test
    public void COUNT_COLORS() {
        assertEquals(6, colors.size());
    }

    @Test
    public void COUNT_OBJECTIVES() {
        assertEquals(14, objectives.size());
    }

    @Test
    public void COUNT_TERRITORIES_FOR_EACH_PLAYER() {
        for (Player player : players) {
            System.out.println(player.getColor());
            assertTrue(world.getTerritoriesByOwner(player).size() >= (world.size() / players.length));
            for (Territory territory : world.getTerritoriesByOwner(player)) {
                System.out.println(territory.getName() + ": " + territory.getSoldiers());
                assertEquals(territory.getSoldiers(), 1);
            }
            System.out.println("");
        }
    }

    /**
     * Conquistar 18 TERRITÓRIOS e ocupar cada um deles com pelo menos dois
     * exércitos.
     */
    @Test
    public void ASSERT_OBJECTIVE_18_TERRITORIES_2_SOLDIERS() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[0]);

        for (Territory territory : world.getTerritoriesByOwner(player)) {
            territory.addSoldiers(1);
        }

        int i = 1;
        Territory t;
        while (world.getTerritoriesByOwner(player).size() < 18) {
            assertFalse(player.getObjective().wasAchieved());
            if (i % players.length != 0) {
                t = world.getTerritoriesByOwner(players[i % players.length]).iterator().next();
                t.setOwner(player);
                t.setSoldiers(2);
            }
            i++;
        }

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Conquistar 24 TERRITÓRIOS à sua escolha.
     */
    @Test
    public void ASSERT_OBJECTIVE_24_TERRITORIES() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[1]);

        for (Territory territory : world.getTerritoriesByOwner(player)) {
            territory.addSoldiers(1);
        }

        int i = 1;
        Territory t;
        while (world.getTerritoriesByOwner(player).size() < 24) {
            assertFalse(player.getObjective().wasAchieved());
            if (i % players.length != 0) {
                t = world.getTerritoriesByOwner(players[i % players.length]).iterator().next();
                t.setOwner(player);
                t.setSoldiers(1);
            }
            i++;
        }

        assertTrue(player.getObjective().wasAchieved());
    }

    private void conquerContinent(Player player, String continentName) {
        for (Territory territory : world.getContinentByName(continentName)) {
            //assertFalse(player.getObjective().wasAchieved());

            territory.setOwner(player);
            territory.setSoldiers(1);
        }
    }

    /**
     * Conquistar na totalidade a AMÉRICA DO NORTE e a OCEANIA.
     */
    @Test
    public void ASSERT_OBJECTIVE_CONQUER_NORTH_AMERICA_AND_OCEANIA() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[2]);

        for (Territory territory : world.getTerritoriesByOwner(player)) {
            territory.addSoldiers(1);
        }

        conquerContinent(player, "América do Norte");

        conquerContinent(player, "Oceania");

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Conquistar na totalidade a AMÉRICA DO NORTE e a ÁFRICA.
     */
    @Test
    public void ASSERT_OBJECTIVE_CONQUER_NORTH_AMERICA_AND_AFRICA() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[3]);

        conquerContinent(player, "América do Norte");

        conquerContinent(player, "África");

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Conquistar na totalidade a EUROPA, a AMÉRICA DO SUL e mais um continente
     * à sua escolha.
     */
    @Test
    public void ASSERT_OBJECTIVE_CONQUER_EUROPE_SOUTH_AMERICA_AND_ONE_MORE() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[4]);

        conquerContinent(player, "Europa");

        conquerContinent(player, "América do Sul");

        String options[] = {"África", "América do Norte", "Ásia", "Oceania"};

        for (int i = 1; i < options.length + 1; i++) {
            conquerContinent(player, options[i % options.length]);
            conquerContinent(players[i], options[i - 1]);
            assertTrue(player.getObjective().wasAchieved());
        }
    }

    /**
     * Conquistar na totalidade a EUROPA, a OCEANIA e mais um continente à sua
     * escolha.
     */
    @Test
    public void ASSERT_OBJECTIVE_CONQUER_EUROPE_OCEANIA_AND_ONE_MORE() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[5]);

        conquerContinent(player, "Europa");

        conquerContinent(player, "Oceania");

        String options[] = {"África", "América do Norte", "América do Sul", "Ásia"};
        for (int i = 1; i < options.length + 1; i++) {
            conquerContinent(player, options[i % options.length]);
            conquerContinent(players[i], options[i - 1]);
            assertTrue(player.getObjective().wasAchieved());
        }

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Conquistar na totalidade a ÁSIA e a AMÉRICA DO SUL.
     */
    @Test
    public void ASSERT_OBJECTIVE_CONQUER_ASIA_AND_SOUTH_AMERICA() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[6]);

        conquerContinent(player, "Ásia");

        conquerContinent(player, "América do Sul");

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Conquistar na totalidade a ÁSIA e a ÁFRICA.
     */
    @Test
    public void ASSERT_OBJECTIVE_CONQUER_ASIA_AND_AFRICA() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[7]);

        conquerContinent(player, "Ásia");

        conquerContinent(player, "África");

        assertTrue(player.getObjective().wasAchieved());
    }

    /*
     DumbPlayer:	0	Color:	Preto
     DumbPlayer:	1	Color:	Azul
     DumbPlayer:	2	Color:	Verde
     DumbPlayer:	3	Color:	Vermelho
     DumbPlayer:	4	Color:	Branco
     DumbPlayer:	5	Color:	Amarelo
     */
    private void destroyPlayer(Player source, Player target) {
        for (Territory territory : world.getTerritoriesByOwner(target)) {
            assertFalse(source.getObjective().wasAchieved());
            territory.setOwner(source);
        }
    }

    /**
     * Destruir totalmente os EXÉRCITOS AMARELOS se é vocé quem possui os
     * exércitos amarelos ou se o jogador que os possui for eliminado por um
     * outro jogador, o seu objetivo passa a ser automaticamente conquistar 24
     * TERRITÓRIOS.
     */
    @Test
    public void ASSERT_OBJECTIVE_DESTROY_YELLOW() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[8]);

        destroyPlayer(player, players[5]);

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Destruir totalmente os EXÉRCITOS AZUIS se é vocé quem possui os exércitos
     * amarelos ou se o jogador que os possui for eliminado por um outro
     * jogador, o seu objetivo passa a ser automaticamente conquistar 24
     * TERRITÓRIOS.
     */
    @Test
    public void ASSERT_OBJECTIVE_DESTROY_BLUE() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[9]);

        destroyPlayer(player, players[1]);

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Destruir totalmente os EXÉRCITOS BRANCOS se é vocé quem possui os
     * exércitos amarelos ou se o jogador que os possui for eliminado por um
     * outro jogador, o seu objetivo passa a ser automaticamente conquistar 24
     * TERRITÓRIOS.
     */
    @Test
    public void ASSERT_OBJECTIVE_DESTROY_WHITE() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[10]);

        destroyPlayer(player, players[4]);

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Destruir totalmente os EXÉRCITOS PRETOS se é vocé quem possui os
     * exércitos amarelos ou se o jogador que os possui for eliminado por um
     * outro jogador, o seu objetivo passa a ser automaticamente conquistar 24
     * TERRITÓRIOS.
     */
    @Test
    public void ASSERT_OBJECTIVE_DESTROY_BLACK() {
        Player player = players[1];
        player.setObjective((Objective) objectives.toArray()[11]);

        destroyPlayer(player, players[0]);

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Destruir totalmente os EXÉRCITOS VERDES se é vocé quem possui os
     * exércitos amarelos ou se o jogador que os possui for eliminado por um
     * outro jogador, o seu objetivo passa a ser automaticamente conquistar 24
     * TERRITÓRIOS.
     */
    @Test
    public void ASSERT_OBJECTIVE_DESTROY_GREE() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[12]);

        destroyPlayer(player, players[2]);

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Destruir totalmente os EXÉRCITOS VERMELHOS se é vocé quem possui os
     * exércitos amarelos ou se o jogador que os possui for eliminado por um
     * outro jogador, o seu objetivo passa a ser automaticamente conquistar 24
     * TERRITÓRIOS.
     */
    @Test
    public void ASSERT_OBJECTIVE_DESTROY_RED() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[13]);

        destroyPlayer(player, players[3]);

        assertTrue(player.getObjective().wasAchieved());
    }

    /**
     * Destruir totalmente os EXÉRCITOS PRETOS se é vocé quem possui os
     * exércitos amarelos ou se o jogador que os possui for eliminado por um
     * outro jogador, o seu objetivo passa a ser automaticamente conquistar 24
     * TERRITÓRIOS.
     */
    @Test
    public void ASSERT_OBJECTIVE_DESTROY_BLACK_ALTERNATIVE() {
        Player player = players[0];
        player.setObjective((Objective) objectives.toArray()[11]);
        ((FullObjective) player.getObjective()).switchToAlternativeObjective();

        int i = 1;
        Territory t;
        while (world.getTerritoriesByOwner(player).size() < 24) {
            assertFalse(player.getObjective().wasAchieved());
            if (i % players.length != 0) {
                t = world.getTerritoriesByOwner(players[i % players.length]).iterator().next();
                t.setOwner(player);
                t.setSoldiers(1);
            }
            i++;
        }

        assertTrue(player.getObjective().wasAchieved());
    }
}
