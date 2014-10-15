/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Objconqcont;
import br.uff.es2.war.entity.Objderjogador;
import br.uff.es2.war.entity.Objetivo;
import br.uff.es2.war.entity.Objterritorio;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.World;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class to communicate with the persistence and load the {@link FullObjective}
 * based on it.
 *
 * @author Victor Guimarães
 */
public class FullObjectiveFactory {

    /**
     * The {@link World} of the game.
     */
    private final World world;

    /**
     * The {@link Collection} of {@link Objetivo}s from the base.
     */
    private final Collection<Objetivo> objetivos;

    /**
     * The {@link Objective} modeled to the game.
     */
    private Set<Objective> objectives;

    /**
     * A cache to improve performance.
     */
    private Map<Continente, Continent> continentsMap;

    /**
     * A {@link Map} which links a {@link Objective} with its respective
     * {@link Objetivo} on the persistence.
     */
    private Map<Objective, Objetivo> objectiveCodeMap;

    /**
     * Constructor with all needed parameters.
     *
     * @param world the {@link World} of the game
     * @param objetivos the {@link Collection} of {@link Objetivo}s from the
     * base
     */
    public FullObjectiveFactory(final World world, final Collection<Objetivo> objetivos) {
        this.world = world;
        this.objetivos = objetivos;
        this.objectives = new HashSet<>();
        this.continentsMap = new HashMap<>();
        this.objectiveCodeMap = new HashMap<>();

        loadObjectives();
    }

    /**
     * Loads each {@link Objective} from the game into a {@link Set}.
     */
    private void loadObjectives() {
        Objective objective;
        for (Objetivo objetivo : objetivos) {
            objective = loadObjective(objetivo);
            objectiveCodeMap.put(objective, objetivo);
            objectives.add(objective);
        }
    }

    /**
     * Creates a {@link FullObjective} to represent a given {@link Objetivo}.
     *
     * @param objetivo the {@link Objetivo}
     * @return the {@link FullObjective}
     */
    private FullObjective loadObjective(Objetivo objetivo) {
        FullObjective opcional = null;
        Set<ParcialObjetive> parcialObjetives = new HashSet<>();

        //If the objective includes destroying a color
        if (!objetivo.getObjderjogadorCollection().isEmpty()) {
            Objderjogador objderjogador = objetivo.getObjderjogadorCollection().iterator().next();
            opcional = loadObjective(objderjogador.getObjetivo1());
            parcialObjetives.add(new DestroyColor(world, Color.valueOf(objderjogador.getCor().getNome())));
        }

        //If the objective includes conquer continents
        if (objetivo.getObjconqcont() != null) {
            parcialObjetives.addAll(loadConqCont(objetivo.getObjconqcont()));
        }

        //If the objective includes conquer territories
        if (objetivo.getObjterritorio() != null) {
            Objterritorio objterritorio = objetivo.getObjterritorio();
            parcialObjetives.add(new ConquerTerritory(world, objterritorio.getQtdTerritorio(), objterritorio.getMinExercito()));
        }

        return new FullObjective(objetivo.getDescricao(), parcialObjetives, opcional);
    }

    /**
     * Loads a {@link Set} of {@link ParcialObjetive} to represent the part of a
     * {@link ConquerContinent} or {@link ConquerExtraContinent} objective.
     *
     * @param objetivo the objective.
     * @return a {@link Set} of {@link ParcialObjetive} to represent the part of
     * a {@link ConquerContinent} or {@link ConquerExtraContinent} objective
     */
    private Set<ParcialObjetive> loadConqCont(Objconqcont objetivo) {
        Collection<Continente> continentes = objetivo.getContinenteCollection();
        int extra = objetivo.getContinentesExtras();

        Set<ParcialObjetive> parcial = new HashSet<>(continentes.size() + (extra == 0 ? 0 : 1));
        Set<Continent> continents = new HashSet<>();
        Continent continent;
        for (Continente continente : continentes) {
            continent = getContinent(continente);
            parcial.add(new ConquerContinent(world, continent));
            continents.add(continent);
        }

        if (extra != 0) {
            parcial.add(new ConquerExtraContinent(world, extra, continents));
        }

        return parcial;
    }

    /**
     * Get the {@link Continent} that represents a {@link Continente}.
     *
     * @param continente the {@link Continente}
     * @return the {@link Continent} that represents a {@link Continente}
     */
    private Continent getContinent(Continente continente) {
        if (!continentsMap.containsKey(continente)) {
            continentsMap.put(continente, world.getContinentByName(continente.getNome()));
        }

        return continentsMap.get(continente);
    }

    /**
     * Getter for the {@link World} of the game.
     *
     * @return the {@link World} of the game
     */
    public World getWorld() {
        return world;
    }

    /**
     * Getter for the {@link Objective} modeled to the game.
     *
     * @return the {@link Objective} modeled to the game
     */
    public Set<Objective> getObjectives() {
        return objectives;
    }

    /**
     * Getter for a {@link Map} which links a {@link Objective} with its
     * respective {@link Objetivo} on the persistence.
     *
     * @return a {@link Map} which links a {@link Objective} with its respective
     * {@link Objetivo} on the persistence.
     */
    public Map<Objective, Objetivo> getObjectiveCodeMap() {
        return objectiveCodeMap;
    }

}
