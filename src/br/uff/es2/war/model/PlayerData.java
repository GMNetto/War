package br.uff.es2.war.model;

import br.uff.es2.war.model.objective.Objective;

public abstract class PlayerData implements Player {
    
    protected Game game;
    protected Objective objective;
    protected Color color;

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
}
