/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.br.uff.es2.war.model;

import br.uff.es2.war.controller.WorldController;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Victor
 */
public class GameCLI {

    private EntityManagerFactory emf;// = Persistence.createEntityManagerFactory("WARESIIPU");

    public GameCLI(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void loadWorld() throws NonexistentEntityException {
        WorldController wc = new WorldController(0, emf);

        World w = wc.getWorld();

        System.out.println(w);
        System.out.println();
        for (Continent continent : w) {
            System.out.println(continent);
            for (Territory territory : continent) {
                System.out.println(territory);
                for (Territory border : territory.getBorders()) {
                    System.out.println("---->\t" + border.getName());
                }
            }
        }

    }

}
