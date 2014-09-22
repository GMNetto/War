package br.uff.es2.war.model;

import java.util.Set;

/**
 * During the receive soldier phase the current player 
 * receives a number of soldiers equal to the number of
 * territories he owns. During this phase the player can 
 * exchange his cards to receive extra soldiers.
 * 
 * @author Arthur Pitzer
 */
public class ReceiveSoldiersPhase implements GamePhase {
    
    @Override
    public void execute(Game game) {
	World world = game.getWorld();
	Player current = game.getCurrentPlayer();
	Set<Territory> territories = world.getTerritoriesByOwner(current);
	int soldierQuantity = territories.size();
	current.distributeSoldiers(soldierQuantity, territories);
    }
}
