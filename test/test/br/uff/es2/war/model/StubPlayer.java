package test.br.uff.es2.war.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Objective;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.util.CyclicIterator;

/**
 * @author Arthur Pitzer
 */
class StubPlayer implements Player {
    
    private World world;
    private Objective objective;
    private Color color;
    private int soldierPool;

    @Override
    public Color chooseColor(Color[] colors) {
	int index = (int)(Math.random() * (colors.length - 1));
	return colors[index];
    }

    @Override
    public void setWorld(World world) {
	this.world = world;
    }

    @Override
    public Objective getObjective() {
	return objective;
    }

    @Override
    public void setObjective(Objective objective) {
	this.objective = objective;
    }

    @Override
    public Color getColor() {
	return color;
    }

    @Override
    public void setColor(Color color) {
	this.color = color;
    }

    @Override
    public void beginTurn(Player current) {
	
    }

    @Override
    public void distributeSoldiers(int soldierQuantity,
	    Set<Territory> territories) {
	this.soldierPool = soldierQuantity;
	Iterator<Territory> iterator = new CyclicIterator<>(territories);
	while(soldierQuantity-- > 0 && iterator.hasNext())
	    iterator.next().addSoldiers(1);
    }

    @Override
    public Combat declareCombat() {
	Set<Territory> territories = world.getTerritoriesByOwner(this);
	if(!territories.isEmpty()){
	    Territory attacking = new LinkedList<>(territories).get(0);
	    Territory defending = new LinkedList<>(attacking.getBorders()).get(0);
	    int soldiers = Math.min(attacking.getSoldiers(), 3);
	    return new Combat(attacking, defending, soldiers);
	}
	return null;
    }

    @Override
    public void answerCombat(Combat combat) {
	int available = combat.getDefendingTerritory().getSoldiers();
	combat.setDefendingSoldiers(Math.min(available, 3));
    }

    public Object getSoldierPool() {
	return soldierPool;
    }
}
