package br.uff.es2.war.model.phases;

/**
 * Execute a game state and its successor states until the game end.
 * @author Arthur Pitzer
 * @param <T> Context of the game
 */
public class GameMachine<T> implements Runnable {
    
    private final T context;
    private GameState<T> initial;
    
    public GameMachine(T context, GameState<T> initial) {
	this.context = context;
	this.initial = initial;
    }
    
    @Override
    public void run() {
	GameState<T> state = initial;
	while((state = state.execute(context)) != null);
    }
}
