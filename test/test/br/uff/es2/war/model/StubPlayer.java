package test.br.uff.es2.war.model;

import br.uff.es2.war.entity.Jogador;
import java.util.Set;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.objective.Objective;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.util.CyclicIterator;

class StubPlayer implements Player {

    private Color color;
    private int soldierPool;
    private Objective objective;
    private Game game;

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
    
    int soldierPool() {
	return soldierPool;
    }
    
    @Override
    public Combat declareCombat() {
        return null;
    }

    @Override
    public void setGame(Game game) {
	this.game = game;
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
    public void distributeSoldiers(int soldierQuantity,
	    Set<Territory> territories) {
	CyclicIterator<Territory> iterator = new CyclicIterator<Territory>(territories);
	while(soldierQuantity-- > 0)
	    iterator.next().addSoldiers(1);
    }

    @Override
    public void answerCombat(Combat combat) {
	int defendingSoldiers = Math.min(3, combat.getDefendingTerritory().getSoldiers());
	combat.setDefendingSoldiers(defendingSoldiers);
    }

    @Override
    public void moveSoldiers() {
    }

    @Override
    public Jogador getJogador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
