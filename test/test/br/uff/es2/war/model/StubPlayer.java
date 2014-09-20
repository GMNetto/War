package test.br.uff.es2.war.model;


import java.util.Set;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.CombatResult;
import br.uff.es2.war.model.Objective;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

class StubPlayer implements Player {

    private World world;
    private Color color;
    private int soldierPool;

    @Override
    public void setWorld(World world) {
	this.world = world;
    }

    @Override
    public void setAllPlayers(Player[] players) {
    }

    @Override
    public void setObjective(Objective objective) {
    }

    @Override
    public void setColor(Color color) {
	this.color = color;
    }

    @Override
    public Color chooseColor(Color[] colors) {
	int index = (int)(Math.random() * (colors.length - 1));
	return colors[index];
    }
    
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void beginTurn(Player current) {
    }

    @Override
    public Territory[] distributeSoldiers(int soldierQuantity) {
	this.soldierPool = soldierQuantity;
	Set<Territory> territories = world.getTerritoriesByOwner(this);
	Territory[] distribution = new Territory[territories.size()];
	distribution = territories.toArray(distribution);
	if(distribution.length > 0){
	    int newQuantity = soldierQuantity + distribution[0].getSoldiers();
	    distribution[0].setSoldiers(newQuantity);
	}
	return distribution;
    }

    int soldierPool() {
	return soldierPool;
    }
    
    @Override
    public Combat declareCombat() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void answerCombat(Combat combat) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setCombatResult(CombatResult result) {
        
    }
}
