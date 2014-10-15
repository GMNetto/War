package br.uff.es2.war.model;

import br.uff.es2.war.entity.Partida;
import br.uff.es2.war.util.CyclicIterator;
import java.util.Date;
import java.util.Iterator;

/**
 * Holds the state of a War game. The game is updated by the players during the
 * execution of the game phases.
 *
 * @author Arthur Pitzer
 */
public class Game {

    private final Player[] players;
    private final World world;
    private final Iterator<Player> turns;
    private Player currentPlayer;
    private Player winner;
    private Date startDate, endDate;
    private int numberOfTurns = 0;

    public Game(Player[] players, World world) {
        this.players = players;
        this.world = world;
        turns = new CyclicIterator<Player>(players);
        startDate = new Date();
        //Game precisará atender Partida com Datas e código.
    }

    public Player[] getPlayers() {
        return players;
    }

    public World getWorld() {
        return world;
    }

    public void passTurn() {
        if (turns.hasNext()) {
            currentPlayer = turns.next();
            numberOfTurns++;
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isOver() {
        if (winner != null)
            return true;
        for (Player player : players) {
            if (player.getObjective().wasAchieved()) {
                winner = player;
                return true;
            }
        }
        return false;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Iterator<Player> getTurns() {
        return turns;
    }

    public Player getWinner() {
        return isOver() ? winner : null;
    }

    public int getNumberOfTurns() {
        //Mudar para pegar o número de turnos mesmo, e não o número de jogadas
        return numberOfTurns;
    }
    
    
}
