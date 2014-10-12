package br.uff.es2.war.model.phases;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.phases.GameState;

/**
 * @author Arthur Pitzer
 */
public class SoldierMovementPhase implements GameState<Game> {

    @Override
    public GameState<Game> execute(Game game) {
	Player current = game.getCurrentPlayer();
	current.moveSoldiers();
	if(game.isOver())
	    return new GameOver();
	return null;
    }
}
