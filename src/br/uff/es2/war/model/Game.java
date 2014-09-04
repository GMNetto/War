package br.uff.es2.war.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Game implements Runnable {
    
    private final Player[] players;
    private final WorldMap world;
    private final Iterator<Player> turns;
    private boolean running;
    
    public Game(Player[] players, WorldMap world) {
	this.players = players;
	this.world = world;
	this.turns = new CyclicIterator<Player>(Arrays.asList(players));
    }
    
    @Override
    public void run() {
	running = true;
	setupPhase();
	while(running && turns.hasNext())
	    mainPhase();
    }

    public void setupPhase(){
	readColors();
	world.distributeTerritories(players);
	for(Player player : players){
	    player.setWorld(world);
	    player.setAllPlayers(players);
	    player.setObjective(randomObjective());
	}
    }
    
    public void readColors(){
	Collection<Color> remaining = new ArrayList<Color>();
	remaining.addAll(Arrays.asList(Color.values()));
	for(Player player : players){
	    Color[] colors = remaining.toArray(new Color[remaining.size()]);
	    Color color = player.chooseColor(colors);
	    player.setColor(color);
	    remaining.remove(color);
	}
    }
    
    private Objective randomObjective() {
	throw new NotImplementedException();
    }
    
    public void mainPhase(){
	Player current = turns.next();
	beginTurn(current);
	addSoldiers(current);
	attackPhase(current);
    } 
    
    private void beginTurn(Player current){
	for(Player player : players)
	    player.beginTurn(current);
    }
    
    public void addSoldiers(Player current){
	int soldierQuantity = world.getTerritoriesByOwner(current).size();
	Map<String, Integer> soldierDistribution = current.distributeSoldiers(soldierQuantity);
	updateSoldiers(soldierDistribution);
    }
    
    private void updateSoldiers(Map<String, Integer> distribution){
	for(Entry<String, Integer> item : distribution.entrySet()){
	    Territory territory = world.getTerritoryByName(item.getKey());
	    territory.setSoldiers(item.getValue());
	}
	for(Player player : players)
	    player.setWorld(world);
    }
    
    private void attackPhase(Player current){
	
    }
}
