 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.controller;

import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Territorio;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.FullObjectiveFactory;
import br.uff.es2.war.model.objective.Objective;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javafx.geometry.Point2D;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * This class is a controller for a specific game. It is able to load the data
 * from the database and serve both the model and the graphic interface.
 *
 * @author Victor Guimarães
 */
public class WorldController implements Observer {

    /**
     * The grahp which describe the World Map.
     */
    private World world;

    /**
     * A {@link Map} of {@link Territory} and {@link Point2D} to be used by the
     * graphic interface.
     */
    private Map<Territory, Point2D> territoryPoint;

    /**
     * A {@link Set} with all the possible {@link Objective}s for the game.
     */
    private Set<Objective> objectives;
    
    

    /**
     * Default constructor with the needed parameters.
     *
     * @param worldID the world's id in the database
     * @param factory the {@link EntityManagerFactory} to access the database
     * @throws NonexistentEntityException In case the id does not exist
     */
    public WorldController(int worldID, EntityManagerFactory factory) throws NonexistentEntityException {
        loadGame(worldID, factory);
    }

    /**
     * Loads the needed information about to start the game.
     *
     * @param worldID the world's id in the database
     * @param factory the {@link EntityManagerFactory} to access the database
     * @throws NonexistentEntityException In case the id does not exist
     */
    private void loadGame(int worldID, EntityManagerFactory factory) throws NonexistentEntityException {
        EntityManager manager = factory.createEntityManager();
        Mundo mundo = manager.find(Mundo.class, worldID);
        loadWorld(mundo);
        loadObjectives(mundo);
        manager.close();
    }

    /**
     * Loads the needed information about the world map from the database.
     *
     * @param mundo the {@link Mundo}
     * @throws NonexistentEntityException In case the id does not exist
     */
    private void loadWorld(Mundo mundo) throws NonexistentEntityException {
        if (mundo == null)
            throw new NonexistentEntityException(ExceptionCauses.NONEXISTENT_ENTITY.toString());

        this.world = new World(mundo.getNome());

        Map<String, Territory> territoryByName = new HashMap<>();
        Set<Territorio> territories = new HashSet<>();
        Territory t;

        for (Continente continent : mundo.getContinenteCollection()) {
            Continent c = new Continent(continent.getNome(), world);

            for (Territorio territory : continent.getTerritorioCollection()) {
                t = new Territory(territory.getNome(), c);
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
        }
    }

    /**
     * Loads the needed information about the {@link Objective}s.
     *
     * @param mundo the {@link Mundo}
     * @throws NonexistentEntityException In case the id does not exist
     */
    private void loadObjectives(Mundo mundo) throws NonexistentEntityException {
        FullObjectiveFactory factory = new FullObjectiveFactory(world, mundo.getObjetivoCollection());
        objectives = factory.getObjectives();
    }

    /**
     * Getter for the {@link World}.
     *
     * @return the {@link World}
     */
    public World getWorld() {
        return world;
    }

    /**
     * Getter fot the {@link Map} of {@link Territory} and {@link Point2D}.
     *
     * @return the {@link Map} of {@link Territory} and {@link Point2D}
     */
    public Map<Territory, Point2D> getTerritoryPoint() {
        return territoryPoint;
    }

    /**
     * Getter for a {@link Set} with all the possible {@link Objective}s for the
     * game.
     *
     * @return a {@link Set} with all the possible {@link Objective}s for the
     * game
     */
    public Set<Objective> getObjectives() {
        return objectives;
    }

    @Override
    public void update(Observable o, Object o1) {
        
    }

}
