package br.uff.es2.war.view.states;

import br.uff.es2.war.view.GameController;

/**
 * State of the view. Each state can have different enabled 
 * components and display different messages. 
 * @author Arthur Pitzer
 */
public interface ViewState {
    
    void execute(GameController controller);

}
