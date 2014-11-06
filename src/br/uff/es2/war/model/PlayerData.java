package br.uff.es2.war.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.model.objective.Objective;

public class PlayerData implements Player {

    protected Game game;
    protected Objective objective;
    protected Color color;
    protected Collection<Card> cards;
    
    public PlayerData(){
	cards = new LinkedList<>();
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
    public Color getColor() {
	return color;
    }

    @Override
    public void setColor(Color color) {
	this.color = color;
    }
    
    @Override
    public Collection<Card> getCards() {
	return cards;
    }

    @Override
    public Color chooseColor(Color[] colors) {
	throw new NotImplementedException();
    }

    @Override
    public void beginTurn(Player current) {
	throw new NotImplementedException();
    }

    @Override
    public void distributeSoldiers(int soldierQuantity,
	    Set<Territory> territories) {
	throw new NotImplementedException();
    }

    @Override
    public Combat declareCombat() {
	throw new NotImplementedException();
    }

    @Override
    public void answerCombat(Combat combat) {
	throw new NotImplementedException();
    }

    @Override
    public void moveSoldiers() {
	throw new NotImplementedException();
    }

    @Override
    public void addCard(Card drawCard) {
	throw new NotImplementedException();
    }

    @Override
    public List<Card> exchangeCards() {
	throw new NotImplementedException();
    }
}
