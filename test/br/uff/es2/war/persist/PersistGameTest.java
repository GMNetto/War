/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.persist;

import br.uff.es2.war.controller.PersistGame;
import br.uff.es2.war.controller.WorldController;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Partida;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.StubPlayer;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Gustavo
 */
public class PersistGameTest {
    public static void main(String[] args){
        try {
            EntityManagerFactory eMF=Persistence.createEntityManagerFactory("WarESIIPU");
            WorldController wC=new WorldController(0, eMF);
            World w=wC.getWorld();
            Map<Territory,Integer> tI=new HashMap();
            Player pl=new StubPlayer();
            Map<Player,Integer> pI=new HashMap();
            pI.put(pl, 1);
            w.getTerritoryByName("Brasil").setOwner(pl);
            tI.put(w.getTerritoryByName("Brasil"), 17);
            PersistGame pG=new PersistGame(eMF);
            pG.setiDPlayers(pI);
            pG.setiDs(tI);
            Partida p=new Partida(1);
            pG.persistOcupacao(w.getTerritoryByName("Brasil"), 3, p);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistGameTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersistGameTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
