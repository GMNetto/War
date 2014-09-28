package br.uff.gamemachine;

public interface GameState<T> {
    
    /**
     * Operations on a game context.
     * @param context context of a game
     * @return next state of the game. Should return null to finish the game.
     * @see GameMachine
     */
    GameState<T> execute(T context);

}
