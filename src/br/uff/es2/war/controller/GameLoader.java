 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.controller;

import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Cor;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Territorio;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.FullObjectiveFactory;
import br.uff.es2.war.model.objective.Objective;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Point2D;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * This class is used to load a game from the persistence. It is able to load
 * the data from the database and serve both the model and the graphic
 * interface.
 *
 * @author Victor Guimarães
 */
public class GameLoader {

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
     * A {@link Map} to link {@link Territory} with its code on the persistence.
     */
    private Map<Territory, Integer> iDOfTerritory;

    /**
     * A {@link Set} of possible {@link Color}s to be used on the game.
     */
    private Set<Color> colors;

    /**
     * A {@link Map} to link {@link Color} with its @{link Cor} on the persistence.
     */
    private Map<Color, Cor> iDOfColor;

    /**
     * Default constructor with the needed parameters.
     *
     * @param worldID the world's id in the database
     * @param factory the {@link EntityManagerFactory} to access the database
     * @throws NonexistentEntityException In case the id does not exist
     */
    public GameLoader(int worldID, EntityManagerFactory factory) throws NonexistentEntityException {
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
        loadColors(mundo);
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

        iDOfTerritory = new HashMap<>();

        for (Continente continent : mundo.getContinenteCollection()) {
            Continent c = new Continent(continent.getNome(), world, continent.getBonusTotalidade());

            for (Territorio territory : continent.getTerritorioCollection()) {
                t = new Territory(territory.getNome(), c);
                territoryByName.put(t.getName(), t);
                c.add(t);
                territories.add(territory);
                iDOfTerritory.put(t, territory.getCodTerritorio());
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

    private void loadColors(Mundo mundo) {
        this.colors = new HashSet<>();
        this.iDOfColor = new HashMap<>();
        Color color;
        for (Cor cor : mundo.getCorCollection()) {
            color = Color.valueOf(cor.getNome());
            colors.add(color);
            iDOfColor.put(color, cor);
        }
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

    /**
     * Getter for a {@link Map} to link {@link Territory} with its code on the
     * persistence.
     *
     * @return a {@link Map} to link {@link Territory} with its code on the
     * persistence
     */
    public Map<Territory, Integer> getiDOfTerritory() {
        return iDOfTerritory;
    }

    /**
     * Getter for a {@link Set} of possible {@link Color}s to be used on the
     * game.
     *
     * @return A {@link Set} of possible {@link Color}s to be used on the game
     */
    public Set<Color> getColors() {
        return colors;
    }

    /**
     * Getter for a {@link Map} to link {@link Color} with its @{link Cor} on the
     * persistence.
     *
     * @return a {@link Map} to link {@link Color} with its @{link Cor} on the
     * persistence
     */
    public Map<Color, Cor> getiDOfColor() {
        return iDOfColor;
    }

}
