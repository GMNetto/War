package br.uff.es2.war.events;

import java.util.Set;

import br.uff.es2.war.model.Territory;

public class DistributeSoldiersEvent {
    
    private final int quantity;
    private final Set<Territory> territories;

    public DistributeSoldiersEvent(int quantity, Set<Territory> territories) {
	this.quantity = quantity;
	this.territories = territories;
    }
    
    public int getQuantity() {
	return quantity;
    }
    
    public Set<Territory> getTerritories() {
	return territories;
    }
}
