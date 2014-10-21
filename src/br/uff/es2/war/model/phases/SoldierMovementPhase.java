package br.uff.es2.war.model.phases;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.phases.GameState;
import br.uff.es2.war.events.TerritoryConquestEvent;
import br.uff.es2.war.events.Action;

/**
 * @author Arthur Pitzer
 */
public class SoldierMovementPhase implements GameState<Game>, Action<TerritoryConquestEvent> {
    
    private static final int CARD_LIMIT = 5;
    private Player conqueror;
    
    @Override
    public GameState<Game> execute(Game game) {
	Player current = game.getCurrentPlayer();
	drawCard(game);
	current.moveSoldiers();
	if(game.isOver())
	    return new GameOver();
	return null;
    }
    
    private void drawCard(Game game){
	if(conqueror != null){
	    conqueror.addCard(game.drawCard());
	    if(conqueror.getCards().size() > CARD_LIMIT)
		game.addCard(conqueror.discard());
	}
    }

    @Override
    public void execute(TerritoryConquestEvent event) {
	conqueror = event.getNewOwner();
    }
}
