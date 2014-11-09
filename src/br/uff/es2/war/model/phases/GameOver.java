package br.uff.es2.war.model.phases;

import br.uff.es2.war.events.GameOverEvent;
import br.uff.es2.war.model.Game;

/**
 * This phase is executed after a player achieve his objective.
 * 
 * @author Arthur Pitzer
 */
public class GameOver implements GameState<Game> {

    @Override
    public GameState<Game> execute(Game context) {
	context.getEvents().publish(new GameOverEvent(context.getWinner()));
	return null;
    }

}
