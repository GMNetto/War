package br.uff.es2.war.view.states;

import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.uff.es2.war.view.GameController;
import br.uff.es2.war.view.GameController2;
import br.uff.es2.war.view.widget.AttackTerritoryController;
import br.uff.es2.war.view.widget.AttackTerritoryStrategy;
import br.uff.es2.war.view.widget.TerritoryUI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * State where the player make attacks.
 * @author Arthur Pitzer
 */
public class AttackState extends ViewState {

    @Override
    protected void innerExecute(GameController controller) {
        GameController2 c2 = controller.getGameController2();
        c2.setAcaoTerr(new AttackTerritoryStrategy(c2));
        Player current = c2.getGame().getCurrentPlayer();
        Set<Territory> ownedTerritories= c2.getGame().getWorld().getTerritoriesByOwner(current);
        
        for(TerritoryUI t : createUIs(ownedTerritories)){
            c2.bloqueiaTerririosNaoVizinhosAdversarios(t);
        }
        Set<TerritoryUI> alvos = new HashSet<>();
        for(TerritoryUI t : createUIs(ownedTerritories)){
            if(t.getModel().getSoldiers() >1){
                alvos.addAll(t.getViz());
            }
        }
        alvos.removeAll(ownedTerritories);
        c2.desbloqueiaTerritorios(alvos);
        AttackTerritoryController ac = c2.getAtacaController();
        c2.getJogador().declareCombat(new Combat(ac.getTerOrigem().getModel(), ac.getTerDestino().getModel(),ac.getnAtaca() ));
    }
}
