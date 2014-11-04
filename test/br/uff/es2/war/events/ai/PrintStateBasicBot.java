/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.events.ai;

import br.uff.es2.war.ai.BasicBot;
import br.uff.es2.war.ai.strategies.attack.AttackStrategy;
import br.uff.es2.war.ai.strategies.attack.BestEffortAttackStrategy;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.objective.Objective;
import java.util.Set;

/**
 *
 * @author Victor Guimarães
 */
public class PrintStateBasicBot extends BasicBot {

    private int round = 0;
    private String threshold = "";

    @Override
    public void beginTurn(Player current) {
        if (current.equals(this))
            round++;
        super.beginTurn(current);
    }

    @Override
    public void distributeSoldiers(int soldierQuantity, Set<Territory> territories) {
        super.distributeSoldiers(soldierQuantity, territories);
        //printStatus();
    }

    @Override
    public void moveSoldiers() {
        super.moveSoldiers(); //To change body of generated methods, choose Tools | Templates.
        printStatus();
    }

    @Override
    public void setObjective(Objective objective) {
        super.setObjective(objective);
        System.out.println("Player: " + super.getColor().getName() + "\tObjective: " + objective.toString() + "\n");
    }

    private void printStatus() {
        System.out.println("Player: " + super.getColor().getName() + "\tRound: " + round + "\tThreshold: " + getThreshold() + "\tCards: " + getCards().toString());
        for (Territory t : game.getWorld().getTerritories()) {
            if (t.getName().length() > 9)
                System.out.println("Name: " + t.getName() + "\t\tSoldiers: " + t.getSoldiers() + "\t\t\tOwner: " + t.getOwner().getColor().getName());
            else
                System.out.println("Name: " + t.getName() + "\t\t\tSoldiers: " + t.getSoldiers() + "\t\t\tOwner: " + t.getOwner().getColor().getName());
        }
        System.out.println("\n\n");
    }

    private String getThreshold() {
        if (threshold.isEmpty()) {
            AttackStrategy as = super.getAttackStrategy();
            if (as instanceof BestEffortAttackStrategy) {
                threshold = "" + ((BestEffortAttackStrategy) as).getThreshold();
            }
        }
        return threshold;
    }

}
