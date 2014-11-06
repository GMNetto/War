package br.uff.es2.war.events;

import br.uff.es2.war.model.Game;

public class SetGameEvent {
    
    private final Game game;
    
    public SetGameEvent(Game game) {
	this.game = game;
    }
    
    public Game getGame() {
	return game;
    }
}