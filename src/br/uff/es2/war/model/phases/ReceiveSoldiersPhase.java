package br.uff.es2.war.model.phases;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Continent;
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
    
    private static final int EXCHANGE_OWNED_TERRITORY_BONUS = 2;
    
    @Override
    public GameState<Game> execute(Game game) {
	World world = game.getWorld();
	Player current = game.getCurrentPlayer();
	int bonus = cardBonus(current, game) + continentBonus(current, world);
	Set<Territory> territories = world.getTerritoriesByOwner(current);
	int soldierQuantity = (territories.size() / 2) + bonus;
	current.distributeSoldiers(soldierQuantity, territories);
	if(game.isOver())
	    return new GameOver();
	return new CombatPhase();
    }
    
    private int continentBonus(Player player, World world) {
        int bonus = 0;
        Set<Territory> territories = world.getTerritoriesByOwner(player);
        for (Continent continent : world)
            if (territories.containsAll(continent))
                bonus += continent.getTotalityBonus();
        return bonus;
    }
    
    private int cardBonus(Player player, Game game){
	List<Card> cards = player.exchangeCards();
	if(cards.size() == 0)
	    return 0;
	for(Card card : cards){
	    game.addCard(card);
	    if(card.getTerritory().getOwner().equals(player))
		card.getTerritory().addSoldiers(EXCHANGE_OWNED_TERRITORY_BONUS);
	}
	game.incrementExchangeCounter();
	return game.getExchangeBonus();
    }
}
