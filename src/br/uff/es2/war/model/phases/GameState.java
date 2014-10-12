package br.uff.es2.war.model.phases;

public interface GameState<T> {
    
    /**
     * Operations on a game context.
     * @param context context of a game
     * @return next state of the game. Should return null to finish the game.
     * @see GameMachine
     */
    GameState<T> execute(T context);

}
