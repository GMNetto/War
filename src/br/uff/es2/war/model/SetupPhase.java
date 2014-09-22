package br.uff.es2.war.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * During the setup phase each player chooses 
 * his color and receives his objective.
 * 
 * @see Color
 * @see Objective
 * 
 * @author Arthur Pitzer
 */
public class SetupPhase implements GamePhase {
    
    private Game game;

    @Override
    public void execute(Game game) {
	this.game = game;
	readColors();
	game.getWorld().distributeTerritories(game.getPlayers());
	for (Player player : game.getPlayers()) {
	    player.setWorld(game.getWorld());
	    player.setObjective(randomObjective());
	}
    }
    
    public void readColors(){
	Collection<Color> remaining = new ArrayList<Color>();
	remaining.addAll(Arrays.asList(Color.values()));
	for(Player player : game.getPlayers()){
	    Color[] colors = remaining.toArray(new Color[remaining.size()]);
	    Color color = player.chooseColor(colors);
	    player.setColor(color);
	    remaining.remove(color);
	}
    }
    
    private Objective randomObjective() {
        return new Objective() {
            
	    @Override
	    public boolean wasAchieved() {
		return false;
	    }
	};
    }

}
