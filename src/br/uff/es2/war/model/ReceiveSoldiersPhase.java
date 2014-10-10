package br.uff.es2.war.model;

import java.util.Set;

import br.uff.gamemachine.GameState;

/**
 * During the receive soldier phase the current player receives a number of
 * soldiers equal to the number of territories he owns. During this phase the
 * player can exchange his cards to receive extra soldiers.
 *
 * @author Arthur Pitzer
 */
public class ReceiveSoldiersPhase implements GameState<Game> {
    
    @Override
    public GameState<Game> execute(Game game) {
        World world = game.getWorld();
        Player current = game.getCurrentPlayer();
        Set<Territory> territories = world.getTerritoriesByOwner(current);
        int soldierQuantity = territories.size() / 2 + extraArmyFromContinents(world, territories);
        current.distributeSoldiers(soldierQuantity, territories);
        if (game.isOver()) {
            return new GameOver();
        }
        return new CombatPhase();
    }
    
    private int extraArmyFromContinents(World world, Set<Territory> myTerritories) {
        int extraQTD = 0;
        for (Continent continent : world) {
            if (myTerritories.containsAll(continent)) {
                extraQTD += continent.getTotalityBonus();
            }
        }
        return extraQTD;
    }
    
}
