package br.uff.es2.war.model.phases;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.phases.GameState;
 
/**
 * During the turn change phase the current player changes.
 * @author Arthur Pitzer
 */
public class TurnChangePhase implements GameState<Game> {

    private Game game;

    @Override
    public GameState<Game> execute(Game game) {
	this.game = game;
	game.passTurn();
	beginTurn();
	return new ReceiveSoldiersPhase();
    }

    private void beginTurn() {
	for (Player player : game.getPlayers())
	    player.beginTurn(game.getCurrentPlayer());
    }
}
