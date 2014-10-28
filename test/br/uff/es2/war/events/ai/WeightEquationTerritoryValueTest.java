/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.events.ai;

import br.uff.es2.war.ai.strategies.WeightEquationTerritoryValue;
import br.uff.es2.war.controller.GameLoader;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.DumbPlayer;
import br.uff.es2.war.model.objective.Objective;
import br.uff.es2.war.model.objective.ObjectiveComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.management.InvalidAttributeValueException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Victor Guimarães
 */
public class WeightEquationTerritoryValueTest {

    private World world;
    private Game game;
    private SortedSet<Objective> objectives;
    private Player[] players;
    private Color[] colors;
    private double[] w;
    WeightEquationTerritoryValue[] weightEquationTerritoryValues;

    public WeightEquationTerritoryValueTest() throws NonexistentEntityException, InvalidAttributeValueException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("WarESIIPU");
        GameLoader gl = new GameLoader(0, factory);
        world = gl.getWorld();
        objectives = new TreeSet<>(new ObjectiveComparator());
        objectives.addAll(gl.getObjectives());
        players = new Player[6];
        colors = new Color[gl.getColors().size()];

        int i = 0;
        for (Color color : gl.getColors()) {
            colors[i] = color;
            i++;
        }

        for (i = 0; i < players.length; i++) {
            players[i] = new DumbPlayer(colors[i], i);
        }
        
        List<Objective> objs = new ArrayList<>(objectives);
        Collections.shuffle(objs);
        
        i = 0;
        for (Player player : players) {
            player.setObjective(objs.get(i));
            i++;
        }

        game = new Game(players, world, colors, gl.getCards());
        game.distributeTerritories();

        weightEquationTerritoryValues = new WeightEquationTerritoryValue[players.length];
        for (int j = 0; j < weightEquationTerritoryValues.length; j++) {
            weightEquationTerritoryValues[j] = new WeightEquationTerritoryValue(game, players[j], 0.9, 0.5, 0.5, 0.5, 0.5, 0.5);
        }
    }
    
    @Test
    public void TEST_VALUES() {
        for (WeightEquationTerritoryValue weightEquationTerritoryValue : weightEquationTerritoryValues) {
            System.out.println("Player: " + weightEquationTerritoryValue.getPlayer().getColor().getName());
            System.out.println(weightEquationTerritoryValue.getPlayer().getObjective());
            double value;
            for (Territory territory : game.getWorld().getTerritories()) {
                value = weightEquationTerritoryValue.getTerritoryValue(territory);
                System.out.println(territory + "\tValue: " + value);
                Assert.assertTrue(0.0 <= value && value <= 1.0);
            }
            System.out.println("\n");
        }
    }
}
