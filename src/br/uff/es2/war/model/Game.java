package br.uff.es2.war.model;

import java.util.Iterator;
import br.uff.es2.war.util.CyclicIterator;

/**
 * Holds the state of a War game. The game is updated by the players 
 * during the execution of the game phases.
 * 
 * @see GameDriver
 * 
 * @author Arthur Pitzer
 */
public class Game {
    
    private final Player[] players;
    private final World world;
    private final Iterator<Player> turns;
    private Player currentPlayer;
    
    public Game(Player[] players, World world) {
	this.players = players;
	this.world = world;
	turns = new CyclicIterator<Player>(players);
    }
    
    public Player[] getPlayers(){
	return players;
    }
    
    public World getWorld(){
	return world;
    }
    
    public Iterator<Player> getTurns() {
	return turns;
    }
    
    public void setCurrentPlayer(Player currentPlayer) {
	this.currentPlayer = currentPlayer;
    }
    
    public Player getCurrentPlayer() {
	return currentPlayer;
    }
    
    public Player getWinner() {
	for(Player player : players)
	    if(player.getObjective().wasAchieved())
		return player;
	return null;
    }
}
