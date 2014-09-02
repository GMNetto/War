package br.uff.es2.war.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Game implements Runnable {
    
    private final Player[] players;
    private final Set<Continent> world;
    private final Iterator<Player> turns;
    private boolean running;
    
    public Game(Player[] players, Set<Continent> world) {
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
	distributeTerritories();
	for(Player player : players){
	    player.set(world);
	    player.set(players);
	    player.set(randomObjective());
	}
    }
    
    private void readColors(){
	List<Color> remaining = new ArrayList<Color>();
	for(Color color : Color.values())
	    remaining.add(color);
	for(Player player : players){
	    Color[] colors = new Color[remaining.size()];
	    colors = remaining.toArray(colors);
	    Color color = player.chooseColor(colors);
	    remaining.remove(color);
	    player.set(color);
	}
    }
    
    private void distributeTerritories(){
	List<Territory> territories = allTerritories();
	int territoriesPerPlayers = territories.size() / players.length;
	for(Player player : players){
	    for(int i = 0; i < territoriesPerPlayers && territories.size() > 0; i++){
		Territory territory = removeAtRandom(territories);
		territory.setOwner(player);
		territory.setSoldiers(1);
	    }
	}
    }
    
    private List<Territory> allTerritories(){
	List<Territory> territories = new ArrayList<>();
	for(Continent continent : world)
	    territories.addAll(continent);
	return territories;
    }
    
    private Territory removeAtRandom(List<Territory> territory){
	int index = (int) (Math.random() * (territory.size() - 1));
	return territory.remove(index);
    }
    
    private Objective randomObjective() {
	return null;
    }
    
    public void mainPhase(){
	Player current = turns.next();
    }
}
