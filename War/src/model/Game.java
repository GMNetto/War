package model;

import java.util.Set;

public class Game implements Runnable {
    
    private final Set<Player> players;
    private final Set<Continent> map;
    private boolean inGame;
    
    public Game(Set<Player> players, Set<Continent> map) {
	this.players = players;
	this.map = map;
    }
    
    @Override
    public void run() {
	setupTurn();
	inGame = true;
	while(inGame){
	}
    }
    
    private void setupTurn(){
	
    }
}
