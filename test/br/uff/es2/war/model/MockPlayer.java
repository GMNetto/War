package br.uff.es2.war.model;

import br.uff.es2.war.entity.Jogador;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.objective.Objective;
import br.uff.es2.war.util.CyclicIterator;

/**
 * @author Arthur Pitzer
 */
public class MockPlayer extends PlayerData {

    private int soldierPool;
    
    public MockPlayer() {
	super();
    }

    @Override
    public Color chooseColor(Color[] colors) {
	int index = (int) (Math.random() * (colors.length - 1));
	return colors[index];
    }

    @Override
    public void beginTurn(Player current) {

    }

    @Override
    public void distributeSoldiers(int soldierQuantity,
	    Set<Territory> territories) {
	this.soldierPool = soldierQuantity;
	Iterator<Territory> iterator = new CyclicIterator<>(territories);
	while (soldierQuantity-- > 0 && iterator.hasNext())
	    iterator.next().addSoldiers(1);
    }

    @Override
    public Combat declareCombat() {
	Set<Territory> territories = game.getWorld()
		.getTerritoriesByOwner(this);
	if (!territories.isEmpty()) {
	    Territory attacking = new LinkedList<>(territories).get(0);
	    Territory defending = new LinkedList<>(attacking.getBorders())
		    .get(0);
	    int soldiers = Math.min(attacking.getSoldiers(), 3);
	    return new Combat(attacking, defending, soldiers);
	}
	return null;
    }

    @Override
    public void answerCombat(Combat combat) {
    }

    public Object getSoldierPool() {
	return soldierPool;
    }

    @Override
    public void moveSoldiers() {
    }

    @Override
    public void addCard(Card drawCard) {
    }

    @Override
    public Card discard() {
	return null;
    }

    @Override
    public List<Card> exchangeCards() {
	return Collections.EMPTY_LIST;
    }
}
