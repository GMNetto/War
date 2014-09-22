package br.uff.es2.war.model;
 
/**
 * Each player has an objective. The first player 
 * to achieve his objective becomes the winner.
 * 
 * @author Arthur Pitzer
 */
public interface Objective {
    
    boolean wasAchieved();

}
