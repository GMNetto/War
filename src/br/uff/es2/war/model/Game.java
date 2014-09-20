package br.uff.es2.war.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Game implements Runnable {
    
    private final Player[] players;
    private final World world;
    private final Iterator<Player> turns;
    private boolean running;
    
    public Game(Player[] players, World world) {
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
        return null;
    }
    
    public void mainPhase(){
	Player current = turns.next();
	beginTurn(current);
	addSoldiers(current);
	attack(current);
	moveSoldiers(current);
    } 
    
    private void beginTurn(Player current){
	for(Player player : players)
	    player.beginTurn(current);
    }
    
    public void addSoldiers(Player current){
	int soldierQuantity = world.getTerritoriesByOwner(current).size();
	Territory[] distribution = current.distributeSoldiers(soldierQuantity);
	updateSoldiers(distribution);
    }
    
    private void updateSoldiers(Territory[] distribution){
	for(Territory update : distribution){
	    Territory territory = world.getTerritoryByName(update.getName());
	    territory.setSoldiers(update.getSoldiers());
	}
	updateWorld();
    }
    
    private void attack(Player attacker){
	Combat combat = attacker.declareCombat();
	while(combat != null){
	    Player defender = combat.getDefendingTerritory().getOwner();
	    defender.answerCombat(combat);
	    CombatResult result = combat.resolve();
	    attacker.setCombatResult(result);
	    defender.setCombatResult(result);
	    updateWorld();
	}
    }
    
    private void updateWorld(){
	for(Player player : players)
	    player.setWorld(world);
    }
    
    private void moveSoldiers(Player current){
	
    }
}
