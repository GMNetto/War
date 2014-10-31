package br.uff.es2.war.events;

import br.uff.es2.war.model.Player;

public class GameOverEvent {

    private final Player winner;

    public GameOverEvent(Player winner) {
	this.winner = winner;
    }

    public Player getWinner() {
	return winner;
    }
}
