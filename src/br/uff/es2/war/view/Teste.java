/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import br.uff.es2.war.dao.MundoJpaController;
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Territorio;
import java.util.Collection;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Victor
 */
public class Teste {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WARESIIPU");
        MundoJpaController mundoJpaController = new MundoJpaController(emf);
        
        Mundo mundo = mundoJpaController.findMundo(0);
        
        Collection<Continente> continentes = mundo.getContinenteCollection();
        System.out.println("Mundo: " + mundo.getNome());
        System.out.println("");
        for (Continente continente : continentes) {
            System.out.println("Continente: " + continente.getNome());
            System.out.println("");
            for (Territorio territorio : continente.getTerritorioCollection()) {
                System.out.println("Território: " + territorio.getNome());
                for (Territorio territorio1 : territorio.getTerritorioCollection()) {
                    System.out.println("----> " + territorio1.getNome());
                }
                for (Territorio territorio1 : territorio.getTerritorioCollection1()) {
                    System.out.println("----> " + territorio1.getNome());
                }
                System.out.println("");
            }
            System.out.println("\n");
        }
        
        System.out.println("");
    }
}
