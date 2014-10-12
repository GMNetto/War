package br.uff.es2.war.model;

import java.util.Iterator;

import br.uff.es2.war.events.EventBus;
import br.uff.es2.war.events.LocalEventBus;
import br.uff.es2.war.util.CyclicIterator;

/**
 * Holds the state of a War game. The game is updated by the players 
 * during the execution of the game phases.
 * 
 * @author Arthur Pitzer
 */
public class Game {
    
    private final EventBus events;
    private final Player[] players;
    private final Color[] colors;
    private final World world;
    private final Iterator<Player> turns;
    private Player currentPlayer;
    private Player winner;
    
    public Game(Player[] players, World world, Color[] colors) {
	events = new LocalEventBus();
	this.players = players;
	this.world = world;
	this.colors = colors;
	turns = new CyclicIterator<Player>(players);
    }
    
    public Player[] getPlayers(){
	return players;
    }
    
    public Color[] getColors() {
	return colors;
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
    
    public EventBus getEvents() {
	return events;
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
