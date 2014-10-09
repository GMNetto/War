/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.controller.WorldController;
import br.uff.es2.war.dao.MundoJpaController;
import br.uff.es2.war.dao.ObjetivoJpaController;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Cor;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Objetivo;
import br.uff.es2.war.entity.Territorio;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.Objective;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;

/**
 *
 * @author Victor
 */
public class Teste {

    private EntityManagerFactory factory;

    public Teste(EntityManagerFactory emf) {
        this.factory = emf;
    }

    public void loadWorld() throws NonexistentEntityException {
        WorldController wc = new WorldController(0, factory);

        World world = wc.getWorld();

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

    public static void main(String[] args) {
        try {
            System.out.println("Teste");
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("WarESIIPU");
            //EntityManager manager = factory.createEntityManager();
//            Mundo mundo = manager.find(Mundo.class, 0);
//            System.out.println(mundo.getNome());
//            
//            System.out.println("\nContinentes:");
//            for (Continente continente : mundo.getContinenteCollection()) {
//                System.out.println(continente.getNome());
//            }
//            
//            System.out.println("\nCores:");
//            for (Cor cor : mundo.getCorCollection()) {
//                System.out.println(cor.getNome());
//            }
//            
//            System.out.println("\nObjetivos:");
//            for (Objetivo objetivo : mundo.getObjetivoCollection()) {
//                System.out.println(objetivo.getDescricao());
//            }
//            manager.close();
//            factory.close();
//            
            WorldController wc = new WorldController(0, factory);
            //Teste t = new Teste(factory);
            //t.loadWorld();
            //System.out.println("");
            //t.loadObjective();
            for (Objective objective : wc.getObjectives()) {
                System.out.println(objective);
                System.out.println("");
            }
        } catch (Exception ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
