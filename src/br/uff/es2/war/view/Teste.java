/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.controller.GameLoader;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Objetivo;
import br.uff.es2.war.entity.Partida;
import br.uff.es2.war.entity.Territorio;
import br.uff.es2.war.ai.BasicBot;
import br.uff.es2.war.ai.attack.probability.AttackProbability;
import br.uff.es2.war.ai.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.ai.attack.probability.ProbabilityTriple;
import br.uff.es2.war.ai.strategies.WeightEquationTerritoryValue;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.Objective;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.eclipse.persistence.exceptions.JAXBException;

/**
 *
 * @author Victor
 */
public class Teste {

    private EntityManagerFactory factory;
    private World world;

    public Teste(EntityManagerFactory emf) {
        this.factory = emf;
    }

    public void loadWorld() throws NonexistentEntityException {
        GameLoader wc;

        wc = new GameLoader(0, factory);

        this.world = wc.getWorld();

        System.out.println(world + " " + world.size() + " Continent(s)");
        System.out.println();
        for (Continent continent : world) {
            System.out.println(continent + " " + continent.size() + " Territory(ies)");
            for (Territory territory : continent) {
                System.out.println(territory);
                for (Territory border : territory.getBorders()) {
                    System.out.println("---->\t" + border.getName());
                }
                System.out.println("");
            }
            System.out.println("");
        }

    }

    public void loadObjective() {
        EntityManager manager = factory.createEntityManager();
        Mundo mundo = manager.find(Mundo.class, 0);

        for (Objetivo objetivo : mundo.getObjetivoCollection()) {
            System.out.println(objetivo.getCodObjetivo());
            System.out.println(objetivo.getDescricao());

            if (objetivo.getObjconqcont() != null) {
                System.out.println("Conquista Continente, extras = " + objetivo.getObjconqcont().getContinentesExtras());
            }

            if (!objetivo.getObjderjogadorCollection1().isEmpty()) {
                System.out.println("É opcional de: " + objetivo.getObjderjogadorCollection1().size() + " objetivo(s).");
            }

            if (!objetivo.getObjderjogadorCollection().isEmpty()) {
                System.out.println("Derrota Jogador: " + objetivo.getObjderjogadorCollection().size());
            }

            if (objetivo.getObjterritorio() != null) {
                System.out.println("Objetivo de Território");
            }

            System.out.println("");
        }
    }

    private List<Player> shufflePlayers(final Collection<Player> players) {
        List<Player> p = new ArrayList<>(players.size());
        for (Player player : players) {
            p.add(player);
        }
        Collections.shuffle(p);

        return p;
    }

    public void startGame(final Collection<Player> players) {
        List<Player> p = shufflePlayers(players);

        for (Player player : p) {
            player.setObjective(null);
        }

    }

    public static void testHibernate() throws NonexistentEntityException {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("WarESIIPU");
        EntityManager manager = factory.createEntityManager();
        GameLoader gl = new GameLoader(0, factory);

        World w = gl.getWorld();
        List<Objective> objectives = new ArrayList<>(gl.getObjectives());

        //Query query = manager.createQuery("select codPartida from Partida as p where p.codPartida = (select max(codPartida) from Partida)");
        Query query = manager.createQuery("select max(p.codPartida) from Partida as p");
        //query.setParameter("cod", 1);

        //List<Partida> list = query.getFirstResult();
        System.out.println("oi");
        System.out.println("Novo Código: " + ((int) query.getResultList().get(0) + 1));

        Mundo mundo = manager.find(Mundo.class, 0);
        System.out.println("");
        System.out.println(mundo.getNome());
        System.out.println("");
        for (Continente continente : mundo.getContinenteCollection()) {
            System.out.println(continente.getNome());
            for (Territorio territorio : continente.getTerritorioCollection()) {
                System.out.println("----> " + territorio.getNome());
            }
            System.out.println("");
        }
    }

    public static void testAttackProbabilities() {
        AttackProbabilityFactory probabilityFactory = new AttackProbabilityFactory();
        int n = 10;
        for (int i = 1; i < n + 2; i++) {
            for (int j = 1; j < n + 1; j++) {
                System.out.println("Attacker:\t" + i + "\tDefenders:\t" + j);
                AttackProbability attackProbability = probabilityFactory.getAttackProbability(i, j);
                System.out.println("Probabilidade do Ataque vencer:\t" + attackProbability.getAttackerWins());
                System.out.println("Probabilidade da Defesa vencer:\t" + attackProbability.getDefenderWins());
                System.out.println("Total:\t\t\t" + (attackProbability.getAttackerWins() + attackProbability.getDefenderWins()));
                System.out.println("");
            }
        }
    }

    public static void testExchangesValues(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println("Exchange: " + (i + 1) + "\tvalues: " + getExchangeBonus(i));
        }
    }

    public static void main(String[] args) throws NonexistentEntityException, Exception {
        AttackProbabilityFactory probabilityFactory = new AttackProbabilityFactory();
        System.out.println(probabilityFactory.getAttackProbability(50, 30));
        //testAttackProbabilities();
        //botTest();
    }

    public static int getExchangeBonus(int exchange) {
        if (exchange < 5) {
            return (4 + 2 * exchange);
        } else {
            return (5 * (exchange - 2));
        }
    }

    public static void botTest() throws NonexistentEntityException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("WarESIIPU");
        EntityManager manager = factory.createEntityManager();
        GameLoader gl = new GameLoader(0, factory);

        Player[] players = new Player[gl.getColors().size()];

        Color[] colors = new Color[players.length];
        int i = 0;
        for (Color color : gl.getColors()) {
            colors[i] = color;
            i++;
        }

        Game game = new Game(players, gl.getWorld(), colors, gl.getCards());

        WeightEquationTerritoryValue[] weses = new WeightEquationTerritoryValue[players.length];
        Random r = new Random();

        List<Objective> obj = new ArrayList<>(gl.getObjectives());
        Collections.shuffle(obj);
        for (i = 0; i < players.length; i++) {
            players[i] = new BasicBot(null, game);
            players[i].setColor(colors[i]);
            players[i].setObjective(obj.get(r.nextInt(obj.size())));
            obj.remove(players[i].getObjective());
            weses[i] = new WeightEquationTerritoryValue(game, players[i], 2, 0.5, 0.7, 0.5, 1.0, 1.5);
        }
        System.out.println("\n\n");
        game.distributeTerritories();

        for (i = 0; i < players.length; i++) {
            System.out.println("Player: " + players[i].getColor().getName());
            System.out.println("Objective: " + players[i].getObjective().toString());
            for (Territory territory : game.getWorld().getTerritoriesByOwner(players[i])) {
                System.out.println("----> " + territory.getName() + "\tImportance: " + weses[i].getTerritoryValue(territory));
            }
            System.out.println("\n");
        }
    }

}
