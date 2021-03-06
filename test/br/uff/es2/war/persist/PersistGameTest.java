/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.persist;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Persistence;

import br.uff.es2.war.controller.GameLoader;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Partida;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.DumbPlayer;

/**
 * 
 * @author Gustavo
 */
public class PersistGameTest {

    public static void main(String[] args) {
        try {
            GameLoader wC = new GameLoader(0, Persistence.createEntityManagerFactory("WarESIIPU"));
            World w = wC.getWorld();
            Map<Territory, Integer> tI = new HashMap();
            Player pl = new DumbPlayer(null, 1);
            Map<Player, Integer> pI = new HashMap();
            pI.put(pl, 1);
            w.getTerritoryByName("Brasil").setOwner(pl);
            tI.put(w.getTerritoryByName("Brasil"), 17);
            //GamePersister pG = new GamePersister(tI, pI, Persistence.createEntityManagerFactory("WarESIIPU"));
            Partida p = new Partida(1);
            //pG.persistOcupacao(w.getTerritoryByName("Brasil"), 3, p);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistGameTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersistGameTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
