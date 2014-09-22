package br.uff.es2.war.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
 
/**
 * During the turn change phase the current player changes.
 * @author Arthur Pitzer
 */
public class TurnChangePhase implements GamePhase {

    private final List<GamePhase> subPhases;
    private Game game;

    public TurnChangePhase() {
	subPhases = new LinkedList<>();
    }

    public void add(GamePhase subPhase) {
	subPhases.add(subPhase);
    }

    @Override
    public void execute(Game game) {
	this.game = game;
	Iterator<Player> turns = game.getTurns();
	if (turns.hasNext()) {
	    game.setCurrentPlayer(turns.next());
	    beginTurn();
	    executeSubPhases();
	}
    }

    private void beginTurn() {
	for (Player player : game.getPlayers())
	    player.beginTurn(game.getCurrentPlayer());
    }

    private void executeSubPhases() {
	for (GamePhase subPhase : subPhases)
	    subPhase.execute(game);
    }
}
