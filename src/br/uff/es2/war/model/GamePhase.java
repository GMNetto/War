package br.uff.es2.war.model;

/**
 * Phase of the game flow. Can update the Game state.
 * @author Arthur Pitzer
 */
public interface GamePhase {
    
    void execute(Game game);

}
