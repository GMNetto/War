package br.uff.es2.war.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.uff.es2.war.util.CyclicIterator;

/**
 * Executes a War game. Organizes the game flow in three moments:
 * 	-before the game loop
 * 	-during the game loop
 * 	-after the game loop
 * Each of these moments can be composed by game phases. The game flow
 * stays in loop until there is a winner.
 * @see GamePhase
 * @author Arthur Pitzer
 */
public class GameDriver implements Runnable {
    
    private final Game game;
    private final List<GamePhase> beforeLoop;
    private final List<GamePhase> inLoop;
    private final List<GamePhase> afterLoop;
    private final Iterator<GamePhase> inLoopPhases;
    
    public GameDriver(Game game) {
	this.game = game;
	beforeLoop = new LinkedList<GamePhase>();
	inLoop = new LinkedList<GamePhase>();
	afterLoop = new LinkedList<GamePhase>();
	inLoopPhases = new CyclicIterator<>(inLoop);
    }
    
    @Override
    public void run() {
	execute(beforeLoop);
	do{
	    inLoopPhases.next().execute(game);
	}while(game.getWinner() != null);
	execute(afterLoop);
    }
    
    private void execute(List<GamePhase> phases){
	for(GamePhase phase : phases)
	    phase.execute(game);
    }
    
    public void addBeforeLoop(GamePhase phase){
	beforeLoop.add(phase);
    }
    
    public void addInLoop(GamePhase phase){
	inLoop.add(phase);
    }
    
    public void addAfterLoop(GamePhase phase){
	afterLoop.add(phase);
    }
}
