package br.uff.es2.war.model;

import br.uff.gamemachine.GameState;

/**
 * This phase is executed after a player achieve his objective.
 * 
 * @author Arthur Pitzer
 */
public class GameOver implements GameState<Game> {

    @Override
    public GameState<Game> execute(Game context) {
	return null;
    }
    
}
