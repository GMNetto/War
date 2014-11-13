package br.uff.es2.war.view.states;

import br.uff.es2.war.model.Territory;
import javafx.application.Platform;
import br.uff.es2.war.view.GameController;
import br.uff.es2.war.view.GameController2;
import br.uff.es2.war.view.widget.TerritoryUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * State of the view. Each state can have different enabled 
 * components and display different messages. 
 * @author Arthur Pitzer
 */
public abstract class ViewState {
    
    protected GameController2 controller2;
    
    public void execute(final GameController controller){
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		innerExecute(controller);
	    }
	});
    }
    protected List<TerritoryUI> createUIs(Set<Territory> territories){
	List<TerritoryUI> territoriesToUnlock = new ArrayList<>();
	for (Territory territory : territories) {
	    for (TerritoryUI ui : controller2.getTerritorios()) {
		if (ui.getModel().equals(territory))
		    territoriesToUnlock.add(ui);
	    }
	}
	return territoriesToUnlock;
    }
    
    protected abstract void innerExecute(GameController controller);

}
