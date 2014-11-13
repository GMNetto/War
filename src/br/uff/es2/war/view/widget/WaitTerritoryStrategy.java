/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view.widget;

import br.uff.es2.war.events.Action;
import br.uff.es2.war.events.BeginTurnEvent;
import br.uff.es2.war.events.ChooseColorEvent;
import br.uff.es2.war.view.GameController2;

/**
 *
 * @author anacarolinegomesvargas
 */
public class WaitTerritoryStrategy implements TerritoryUIStrategy{
    
    private GameController2 jc;
    
    public WaitTerritoryStrategy(final GameController2 jc) {
        
        
        /*
        jc.getPlayer().getEvents().subscribe(BeginTurnEvent.class,
            new Action<BeginTurnEvent>() {
		@Override
                    public void onAction(BeginTurnEvent args) {
                        jc.setAcaoTerr(new AllocTerritoryStrategy(jc));
                    }
        });
                */
    }

    @Override
    public void buttonAction(TerritoryUI ter) {
        // não faz nada 
    }

    @Override
    public TerritoryUIStrategy nextPhase() {
        return new AllocTerritoryStrategy(jc);
    }
    
}
