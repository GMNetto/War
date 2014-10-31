package br.uff.es2.war.model.phases;

import br.uff.es2.war.model.Game;

/**
 * Used to represent empty phase dependency
 * 
 * @author Arthur Pitzer
 */
class EmptyPhase implements GameState<Game> {

    @Override
    public GameState<Game> execute(Game game) {
	return null;
    }

}
