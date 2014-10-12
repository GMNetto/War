package br.uff.es2.war.model.phases;

import java.util.Set;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.phases.GameState;

/**
 * During the receive soldier phase the current player 
 * receives a number of soldiers equal to the number of
 * territories he owns. During this phase the player can 
 * exchange his cards to receive extra soldiers.
 * 
 * @author Arthur Pitzer
 */
public class ReceiveSoldiersPhase implements GameState<Game> {
    
    @Override
    public GameState<Game> execute(Game game) {
	World world = game.getWorld();
	Player current = game.getCurrentPlayer();
	Set<Territory> territories = world.getTerritoriesByOwner(current);
	int soldierQuantity = territories.size();
	current.distributeSoldiers(soldierQuantity, territories);
	if(game.isOver())
	    return new GameOver();
	return new CombatPhase();
    }
}
