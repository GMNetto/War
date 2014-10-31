/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.server;

import br.uff.es2.war.controller.GameController;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.model.MockGame;
import br.uff.es2.war.network.Messenger;
import br.uff.es2.war.network.ProceduralMessenger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * This test is still incomplete because there is no players to really test it. What is known is that the persistence
 * loader are working with the server.
 * @author Gustavo
 */
public class ServerTest {
    
    GameController gameController;
    
    public ServerTest(){
        
    }
    @Test
    public void TEST_SERVER(){
        ProceduralMessenger[] messengers=new ProceduralMessenger[6];
        for (int i = 0; i < messengers.length; i++) {
           messengers[i]=new ProceduralMessenger("Testando server.");
        }
        try {
            gameController=new GameController(messengers);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}