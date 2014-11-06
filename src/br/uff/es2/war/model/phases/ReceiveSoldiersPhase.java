package br.uff.es2.war.model.phases;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

/**
 * During the receive soldier phase the current player receives a number of
 * soldiers equal to the number of territories he owns. During this phase the
 * player can exchange his cards to receive extra soldiers.
 * 
 * @author Arthur Pitzer
 */
public class ReceiveSoldiersPhase implements GameState<Game> {

    private static final int MINIMUN_CARD_QUANTITY_TO_EXCHANGE = 3;
    private static final int EXCHANGE_OWNED_TERRITORY_BONUS = 2;

    @Override
    public GameState<Game> execute(Game game) {
	World world = game.getWorld();
	Player current = game.getCurrentPlayer();
        continentBonusAllocation(current, world);
	Set<Territory> territories = world.getTerritoriesByOwner(current);
        int bonus = cardBonus(current, game) + continentBonus(current, world);
	int soldierQuantity = Math.max((territories.size() / 2), 3) + bonus;
        
	current.distributeSoldiers(soldierQuantity, territories);
	
        if (game.isOver())
	    return new GameOver();
        
        if (game.getNumberOfTurns() < game.getPlayers().length + 1) {
            return new TurnChangePhase();
        }
        
	return new CombatPhase();
    }

    private int cardBonus(Player player, Game game) {
	List<Card> cards = player.exchangeCards();
        
        if (! checkExchange(cards))
            return 0;
        
	for (Card card : cards) {
	    game.addCard(card);
	    if (card.getTerritory() != null && card.getTerritory().getOwner().equals(player))
		card.getTerritory().addSoldiers(EXCHANGE_OWNED_TERRITORY_BONUS);
	}
	game.incrementExchangeCounter();
        player.getCards().removeAll(cards);
        return game.getExchangeBonus();
    }
    
    private void continentBonusAllocation(Player player, World world) {
        Set<Territory> territories = world.getTerritoriesByOwner(player);
        	
        for (Continent continent : world)
            if (territories.containsAll(continent))
		player.distributeSoldiers(continent.getTotalityBonus(), continent);
    }

    private int continentBonus(Player player, World world) {
	int bonus = 0;
	Set<Territory> territories = world.getTerritoriesByOwner(player);
	for (Continent continent : world)
	    if (territories.containsAll(continent))
		bonus += continent.getTotalityBonus();
	return bonus;
    }
    
    private boolean checkExchange(List<Card> cards) {
        if (cards == null || cards.size() < MINIMUN_CARD_QUANTITY_TO_EXCHANGE)
	    return false;
        
        int[] figures = new int[3];
        
        for (Card card : cards) {
            if (card.getFigure() == 0) {
                figures[0]++;
                figures[1]++;
                figures[2]++;
            } else {
                figures[card.getFigure() - 1]++;
            }
        }
        
        for (int figure : figures) {
            if (figure >= MINIMUN_CARD_QUANTITY_TO_EXCHANGE) 
                return true;
        }
        
        for (int figure : figures) {
            if (figure < 1)
                return false;
        }
        
        return false;
    }
}
