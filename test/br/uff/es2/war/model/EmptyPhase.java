package br.uff.es2.war.model;

import br.uff.es2.war.model.Game;
import br.uff.gamemachine.GameState;

/**
 * Used to represent empty phase dependency
 * 
 * @author Arthur Pitzer
 */
class EmptyPhase implements GameState<Game>{
    
    @Override
    public GameState<Game> execute(Game game) {
	return null;
    }

}
