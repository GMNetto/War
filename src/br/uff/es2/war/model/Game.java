package br.uff.es2.war.model;

import java.util.Iterator;
import br.uff.es2.war.util.CyclicIterator;

/**
 * Holds the state of a War game. The game is updated by the players 
 * during the execution of the game phases.
 * 
 * @author Arthur Pitzer
 */
public class Game {
    
    private final Player[] players;
    private final World world;
    private final Iterator<Player> turns;
    private Player currentPlayer;
    private Player winner;
    
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
    
    public void passTurn(){
	if(turns.hasNext())
	    currentPlayer = turns.next();
    }
    
    public Player getCurrentPlayer() {
	return currentPlayer;
    }
    
    public boolean isOver(){
	if(winner != null)
	    return true;
	for(Player player : players){
	    if(player.getObjective().wasAchieved()){
		winner = player;
		return true;
	    }
	}
	return false;
    }
    
    public Player getWinner() {
	return isOver()? winner : null;
    }
}
