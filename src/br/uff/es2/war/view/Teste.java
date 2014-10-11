/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.controller.GameLoader;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Objetivo;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.Objective;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
        GameLoader wc = new GameLoader(0, factory);

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

    public void loadObjective() throws NonexistentEntityException {
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

    public static void main(String[] args) throws NonexistentEntityException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("WarESIIPU");
        GameLoader gl = new GameLoader(0, factory);
        World w = gl.getWorld();
        List<Objective> objectives = new ArrayList<>(gl.getObjectives());
        
        
    }

}
