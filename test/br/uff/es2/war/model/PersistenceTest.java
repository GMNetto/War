/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.model;

import br.uff.es2.war.controller.GameLoader;
import br.uff.es2.war.controller.GamePersister;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Jogador;
import br.uff.es2.war.entity.Partida;
import br.uff.es2.war.model.objective.DumbPlayer;
import br.uff.es2.war.model.objective.Objective;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Victor Guimarães
 */
public class PersistenceTest {

    private GameLoader gameLoader;
    private GamePersister gamePersister;
    private Game game;
    private Player[] players;
    private EntityManagerFactory factory;
    private Map<Player, Integer> iDPlayers;
    
    public PersistenceTest() throws NonexistentEntityException {
        factory = Persistence.createEntityManagerFactory("WarESIIPU");
        this.gameLoader = new GameLoader(0, factory);
        
        EntityManager manager = factory.createEntityManager();
        @SuppressWarnings("JPQLValidation")
        Query query = manager.createQuery("select max(codJogador) from Jogador as Integer");
        int startCode = ((int) query.getResultList().get(0) + 1);
        manager.close();
        
        players = new Player[6];
        iDPlayers = new HashMap<>();
        
        for (int i = 0; i < players.length; i++) {
            players[i] = new DumbPlayer(Color.values()[i], i);
            players[i].setGame(game);
            iDPlayers.put(players[i], startCode);
            startCode++;
        }
        
        List<Objective> objectives = new ArrayList<>(gameLoader.getObjectives());
        
        Random random = new Random();
        int r;
        for (Player player : players) {
            r = random.nextInt(objectives.size());
            player.setObjective(objectives.get(r));
            objectives.remove(r);
        }
        
        this.game = new Game(players, gameLoader.getWorld());
        this.gamePersister = new GamePersister(gameLoader.getiDOfTerritory(), iDPlayers, gameLoader.getiDObjectives(), gameLoader.getiDOfColor(), game, factory);
    }
    
    @Test
    public void ASSERT_UNIQUE_OBJECTIVE_FOR_PLAYER() {
        Set<Objective> objs = new HashSet<>();
        for (Player player : players) {
            assertFalse(objs.contains(player.getObjective()));
            assertNotNull(player.getObjective());
            objs.add(player.getObjective());
        }
    }
    
    @Test
    public void TEST_PERSIST_PLAYERS() {
        EntityManager manager = factory.createEntityManager();
        
        Jogador jog;
        int code;
        manager.getTransaction().begin();
        for (Player player : players) {
            code = iDPlayers.get(player);
            jog = new Jogador(code, "LoginTest" + code, "SenhaTest" + code, "EmailTest" + code);
            manager.merge(jog);
        }
        manager.getTransaction().commit();
        manager.close();
    }
    
    @Test
    public void TEST_PERSIST_GAME() throws Exception {
        int code = gamePersister.addPartida();
        gamePersister.addJogam();
        World w = gameLoader.getWorld();
        w.distributeTerritories(players, game);
        
        for (Continent continent : w) {
            for (Territory territory : continent) {
                gamePersister.addOcupacao(territory);
            }
        }
        
        gamePersister.persist();
        
        EntityManager manager = factory.createEntityManager();
        Partida partida = manager.find(Partida.class, code);
        
        assertEquals(gamePersister.getPartida(), partida);
        
        Query query = manager.createQuery("select j from Jogador as j inner join Jogam as jm on j.codJogador = "
                + "jm.codJogador inner join Partida as p on jm.codPartida = p.codPartida "
                + "where p.codPartida = " + code);
        
        List<Jogador> jogs = query.getResultList();
        
        assertEquals(players.length, jogs.size());
        
        for (Jogador jogador : jogs) {
            assertTrue(iDPlayers.values().contains(jogador.getCodJogador()));
        }
    }
}
