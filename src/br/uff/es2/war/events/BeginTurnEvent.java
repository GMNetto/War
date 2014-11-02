package br.uff.es2.war.events;

import br.uff.es2.war.model.Player;

public class BeginTurnEvent {
    
    private final Player player;
    
    public BeginTurnEvent(Player player) {
	this.player = player;
    }
    
    public Player getPlayer() {
	return player;
    }
}
