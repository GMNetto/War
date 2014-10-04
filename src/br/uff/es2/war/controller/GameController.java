/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.controller;

import br.uff.es2.war.dao.MundoJpaController;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Territorio;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Point2D;
import javax.persistence.EntityManagerFactory;

/**
 * This class is a controller for a specific game. It is able to load the data
 * from the database and serve both the model and the graphic interface.
 *
 * @author Victor Guimarães
 */
public class GameController {

    /**
     * The grahp which describe the World Map.
     */
    /**
     * A {@link Map} of {@link Territory} and {@link Point2D} to be used by the
     * graphic interface.
     */
    private Map<Territory, Point2D> territoryPoint;
    
    private final Game game;

    /**
     * Default constructor with the needed parameters.
     *
     * @param worldID the world's id in the database
     * @param emf the {@link EntityManagerFactory} to access the database
     * @throws NonexistentEntityException In case the id does not exist
     */
    public GameController(int worldID, EntityManagerFactory emf) throws NonexistentEntityException {
        this.game=new Game(loadPlayers(),loadWorld(worldID, emf));
    }

    /**
     * Loads the needed information about the world map from the database.
     *
     * @param worldID the world's id in the database
     * @param emf the {@link EntityManagerFactory} to access the database
     * @throws NonexistentEntityException In case the id does not exist
     */
    private World loadWorld(int worldID, EntityManagerFactory emf) throws NonexistentEntityException {
        MundoJpaController worldJpaController = new MundoJpaController(emf);
        Mundo mundo = worldJpaController.findMundo(worldID);

        if (mundo == null) {
            throw new NonexistentEntityException(ExceptionCauses.NONEXISTENT_ENTITY.toString());
        }

        World world = new World(mundo.getNome());
        /*
         Map<String, Territory> territoryByName = new HashMap<>();
         Set<Territorio> territories = new HashSet<>();
         Territory t;

         for (Continente continent : mundo.getContinenteCollection()) {
         Continent c = new Continent(continent.getNome());

         for (Territorio territory : continent.getTerritorioCollection()) {
         t = new Territory(territory.getNome());
         territoryByName.put(t.getName(), t);
         c.add(t);
         territories.add(territory);
         }
         this.world.add(c);
         }

         this.territoryPoint = new HashMap<>();
         for (Territorio territory : territories) {
         t = territoryByName.get(territory.getNome());
         for (Territorio neighbor : territory.getTerritorioCollection()) {
         t.addBorder(territoryByName.get(neighbor.getNome()));
         }
         for (Territorio neighbor : territory.getTerritorioCollection1()) {
         t.addBorder(territoryByName.get(neighbor.getNome()));
         }
         territoryPoint.put(t, new Point2D(territory.getPosicaoX(), territory.getPosicaoY()));
         }*/
        Map<Territorio, Territory> terByTer = new HashMap<>();
        this.territoryPoint = new HashMap<>();
        Territory t;
        for (Continente continent : mundo.getContinenteCollection()) {
            Continent c = new Continent(continent.getNome(),world);
            for (Territorio territory : continent.getTerritorioCollection()) {
                t = new Territory(territory,c);
                this.territoryPoint.put(t, new Point2D(territory.getPosicaoX(), territory.getPosicaoY()));
                terByTer.put(territory, t);
                c.add(t);
            }
            world.add(c);
        }

        for (Territory territory : terByTer.values()) {
            for (Territorio neighbor : territory.getTerritorio().getTerritorioCollection()) {
                territory.addBorder(terByTer.get(neighbor));
            }
            for (Territorio neighbor : territory.getTerritorio().getTerritorioCollection1()) {
                territory.addBorder(terByTer.get(neighbor));
            }
        }

        return world;
    }

    /**
     * Getter for the {@link World}.
     *Just for test
     * @return the {@link World}
     */
    public World getWorld() {
        return game.getWorld();
    }

    /**
     * Getter fot the {@link Map} of {@link Territory} and {@link Point2D}.
     *
     * @return the {@link Map} of {@link Territory} and {@link Point2D}
     */
    public Map<Territory, Point2D> getTerritoryPoint() {
        return territoryPoint;
    }

    private Player[] loadPlayers() {
        return null;
    }

}
