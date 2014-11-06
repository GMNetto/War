package br.uff.es2.war.model.phases;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.phases.GameState;
import br.uff.es2.war.events.TerritoryConquestEvent;
import br.uff.es2.war.events.Action;

/**
 * @author Arthur Pitzer
 */
public class SoldierMovementPhase implements GameState<Game>,
	Action<TerritoryConquestEvent> {

    private static final int CARD_LIMIT = 5;
    private Player conqueror;

    @Override
    public GameState<Game> execute(Game context) {
	Player current = context.getCurrentPlayer();
	drawCard(context);
	current.moveSoldiers();
	if(context.isOver())
	    return new GameOver();
	return new TurnChangePhase();
    }

    private void drawCard(Game game) {
	if (conqueror != null) {
	    conqueror.addCard(game.drawCard());
	    conqueror = null;
	}
    }

    @Override
    public void onAction(TerritoryConquestEvent event) {
	conqueror = event.getNewOwner();
    }
}
