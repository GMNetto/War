package br.uff.es2.war.events;

import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

public class TerritoryConquestEvent {

    private Player oldOwner;
    private Player newOwner;
    private Territory territory;

    public TerritoryConquestEvent(Player oldOwner, Player newOwner,
	    Territory territory) {
	this.oldOwner = oldOwner;
	this.newOwner = newOwner;
	this.territory = territory;
    }

    public Player getNewOwner() {
	return newOwner;
    }

    public Player getOldOwner() {
	return oldOwner;
    }

    public Territory getTerritory() {
	return territory;
    }
}
