package br.uff.es2.war.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import br.uff.es2.war.model.objective.Objective;
import br.uff.gamemachine.GameState;

/**
 * During the setup phase each player chooses 
 * his color and receives his objective.
 * 
 * @see Color
 * @see Objective
 * 
 * @author Arthur Pitzer
 */
public class SetupPhase implements GameState<Game> {
    
    private Game game;

    @Override
    public GameState<Game> execute(Game game) {
	this.game = game;
	readColors();
	game.getWorld().distributeTerritories(game.getPlayers(),game);
	for (Player player : game.getPlayers()) {
	    player.setGame(game);
	    player.setObjective(randomObjective());
	}
	return new TurnChangePhase();
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

            @Override
            public boolean isNeeded(Territory territory) {
                return false;
            }
	};
    }

}
