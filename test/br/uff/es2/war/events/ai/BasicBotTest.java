/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.events.ai;

import br.uff.es2.war.ai.BasicBot;
import br.uff.es2.war.ai.attack.probability.AttackProbabilityFactory;
import br.uff.es2.war.ai.strategies.OffensiveTerritoryValue;
import br.uff.es2.war.ai.strategies.WeightEquationTerritoryValue;
import br.uff.es2.war.ai.strategies.WinLoseTerritoryValue;
import br.uff.es2.war.ai.strategies.attack.BestEffortAttackStrategy;
import br.uff.es2.war.ai.strategies.attack.allocation.WeightedRandomAllocationStrategy;
import br.uff.es2.war.ai.strategies.cardchange.GreedyChangeCardStrategy;
import br.uff.es2.war.ai.strategies.rearrange.FunctionBasedRearrangeStrategy;
import br.uff.es2.war.ai.strategies.rearrange.thresholdfunction.LinearThresholdFunction;
import br.uff.es2.war.controller.GameController;
import br.uff.es2.war.controller.GameLoader;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.phases.GameMachine;
import br.uff.es2.war.model.phases.SetupPhase;
import br.uff.es2.war.network.JSONWarProtocol;
import br.uff.es2.war.network.RemotePlayer;
import br.uff.es2.war.network.WarServer;
import java.util.Random;
import javax.management.InvalidAttributeValueException;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Victor Guimarães
 */
public class BasicBotTest {

    GameMachine machine;
    Game game;

    public BasicBotTest() throws NonexistentEntityException, InvalidAttributeValueException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("WarESIIPU");
        GameLoader gl = new GameLoader(0, factory);

        Color[] colors = new Color[gl.getColors().size()];
        colors = gl.getColors().toArray(colors);

        BasicBot[] players = new BasicBot[4];

        for (int i = 0; i < players.length; i++) {
            players[i] = new PrintStateBasicBot();
        }

        game = new Game(players, gl.getWorld(), colors, gl.getCards(), gl.getObjectives());

        WinLoseTerritoryValue winLoseTerritoryValue;
        WeightEquationTerritoryValue weightEquationTerritoryValue;
        WeightedRandomAllocationStrategy weightedRandomAllocation;
        OffensiveTerritoryValue offensiveTerritoryValue;

        FunctionBasedRearrangeStrategy functionBasedRearrangeStrategy;

        AttackProbabilityFactory afp = new AttackProbabilityFactory();

        machine = new GameMachine<Game>(game, (new SetupPhase()).execute(game));

        Random r = new Random();

        for (BasicBot player : players) {
            winLoseTerritoryValue = new WinLoseTerritoryValue(game, player, afp);
            weightEquationTerritoryValue = new WeightEquationTerritoryValue(game, player, 0.9, 0.1, 0.15, 0.1, 0.1, 0.3);
            offensiveTerritoryValue = new OffensiveTerritoryValue(winLoseTerritoryValue);
            weightedRandomAllocation = new WeightedRandomAllocationStrategy(offensiveTerritoryValue, weightEquationTerritoryValue, winLoseTerritoryValue);
            functionBasedRearrangeStrategy = new FunctionBasedRearrangeStrategy(new LinearThresholdFunction(), player, game, winLoseTerritoryValue, weightEquationTerritoryValue);

            player.setAllocationInstruction(weightedRandomAllocation);
            player.setAttackStrategy(new BestEffortAttackStrategy(player, game, winLoseTerritoryValue, weightEquationTerritoryValue, r.nextDouble() * 0.7 + 0.2));
            player.setRelocationStrategy(functionBasedRearrangeStrategy);
            player.setChangeCardStrategy(new GreedyChangeCardStrategy(0, player, game, weightEquationTerritoryValue));
        }

    }

    @Test
    public void RUN_GAME() {
        machine.run();

        boolean active = false;
        for (Player player : game.getPlayers()) {
            if (player.getObjective().wasAchieved()) {
                active = true;
                System.out.println("\nWinner: " + player.getColor());
                System.out.println("Objective: " + player.getObjective().toString());
                break;
            }
        }

        System.out.println("\nWorld Final State\n");
        for (Territory t : game.getWorld().getTerritories()) {
            if (t.getName().length() > 9)
                System.out.println("Name: " + t.getName() + "\t\tSoldiers: " + t.getSoldiers() + "\t\t\tOwner: " + t.getOwner().getColor().getName());
            else
                System.out.println("Name: " + t.getName() + "\t\t\tSoldiers: " + t.getSoldiers() + "\t\t\tOwner: " + t.getOwner().getColor().getName());
        }

        Assert.assertTrue(active);
    }

}
