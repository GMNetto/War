package br.uff.es2.war.view.states;

import br.uff.es2.war.model.Territory;
import br.uff.es2.war.view.GameController;
import br.uff.es2.war.view.GameController2;
import br.uff.es2.war.view.widget.AllocTerritoryStrategy;
import br.uff.es2.war.view.widget.TerritoryUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * State where the player distribute their soldiers on the map
 * @author Arthur Pitzer
 */
public class DistributeSoldiersState implements ViewState {

    private GameController controller;
    private GameController2 controller2;
    @Override
    public void execute(GameController controller) {
        this.controller=controller;
	controller2 = controller.getGameController2();
	controller2.setAcaoTerr(new AllocTerritoryStrategy(controller2));
        
        controller2.setTextFase("Aloque seus "+controller2.getMaxQuantityToDistribute()+" exércitos","Selecione o territorio onde deseja alocar","","");
        controller2.getBtn_prox().setVisible(false);
    }
    
    public void setMaxQuantityToDistribute(int quantity){
        controller2.setMaxQuantityToDistribute(quantity);
    }
    
    public void setTerritoriesToUnlock(Set<Territory> territories){
        List<TerritoryUI> territoriesToUnlock = new ArrayList<>();
            for (Territory territory : territories) {
                for (TerritoryUI ui : controller2.getTerritorios()) {
                    if (ui.getModel().equals(territory))
                        territoriesToUnlock.add(ui);
                }
            }
        controller2.getAllocTerritoryCont().setTerritoriesToUnlock(territoriesToUnlock);
    }
}
