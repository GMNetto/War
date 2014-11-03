package br.uff.es2.war.events;

import br.uff.es2.war.model.Color;

public class ChooseColorEvent {
    
    private final Color[] colors;

    public ChooseColorEvent(Color[] colors) {
	this.colors = colors;
    }
    
    public Color[] getColors() {
	return colors;
    }
}
