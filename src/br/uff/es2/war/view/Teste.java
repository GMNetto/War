/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.controller.GameController;
import br.uff.es2.war.dao.MundoJpaController;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Territorio;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Victor
 */
public class Teste {

    private EntityManagerFactory emf;

    public Teste(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void loadWorld() throws NonexistentEntityException {
        GameController wc = new GameController(0, emf);

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
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("WarESIIPU");
            Teste t = new Teste(emf);

            t.loadWorld();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
