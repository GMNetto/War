package br.uff.es2.war.model;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.GamePhase;

/**
 * Used to represent empty phase dependency
 * 
 * @author Arthur Pitzer
 */
class EmptyPhase implements GamePhase{
    
    @Override
    public void execute(Game game) {
    }

}
