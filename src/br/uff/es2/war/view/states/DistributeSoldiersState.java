package br.uff.es2.war.view.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Territory;
import br.uff.es2.war.view.GameController;
import br.uff.es2.war.view.GameController2;
import br.uff.es2.war.view.widget.AllocTerritoryStrategy;
import br.uff.es2.war.view.widget.TerritoryUI;

/**
 * State where the player distribute their soldiers on the map
 * 
 * @author Arthur Pitzer
 */
public class DistributeSoldiersState extends ViewState {

    private int quantity;
    private Set<Territory> territories;
    private GameController controller;
    

    @Override
    protected void innerExecute(GameController controller) {
	this.controller = controller;
	controller2 = controller.getGameController2();
	controller2.setMaxQuantityToDistribute(quantity);
	controller2.getAllocTerritoryCont().setTerritoriesToUnlock(createUIs(territories));
	controller2.setAcaoTerr(new AllocTerritoryStrategy(controller2));
	controller2.setTextFase(
		"Aloque seus " + controller2.getMaxQuantityToDistribute()
			+ " exércitos",
		"Selecione o territorio onde deseja alocar", "", "");
	controller2.getBtn_prox().setVisible(false);
	
    }

    public void setMaxQuantityToDistribute(int quantity) {
	this.quantity = quantity;
    }

    public void setTerritoriesToUnlock(Set<Territory> territories) {
	this.territories = territories; 
    }
    
    
}
