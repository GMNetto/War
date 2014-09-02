package test.br.uff.es2.war.model;

import java.util.Set;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Objective;
import br.uff.es2.war.model.Player;

class StubPlayer implements Player {
    
    private Color color;

    @Override
    public void set(Set<Continent> map) {
    }

    @Override
    public void set(Player[] players) {
    }

    @Override
    public void set(Objective randomObjective) {
    }

    @Override
    public void set(Color color) {
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
}
