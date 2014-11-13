package br.uff.es2.war.view.states;

import javafx.application.Platform;
import br.uff.es2.war.view.GameController;

/**
 * State of the view. Each state can have different enabled 
 * components and display different messages. 
 * @author Arthur Pitzer
 */
public abstract class ViewState {
    
    public void execute(final GameController controller){
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		innerExecute(controller);
	    }
	});
    }
    
    protected abstract void innerExecute(GameController controller);

}
