package br.uff.es2.war.model;

import java.util.Collection;
import java.util.LinkedList;

import br.uff.es2.war.model.objective.Objective;

public abstract class PlayerData implements Player {

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
}
