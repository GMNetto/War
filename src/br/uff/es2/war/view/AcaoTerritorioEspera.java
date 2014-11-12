/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import br.uff.es2.war.events.Action;
import br.uff.es2.war.events.BeginTurnEvent;
import br.uff.es2.war.events.ChooseColorEvent;

/**
 *
 * @author anacarolinegomesvargas
 */
public class AcaoTerritorioEspera implements AcaoTerritorioStrategy{
    
    private JogoController jc;
    
    public AcaoTerritorioEspera(final JogoController jc) {
        this.jc=jc;
        jc.setTextFase("Espere a sua vez","","","");
        jc.bloqueiaTerritorios(jc.getTerritorios());
        
        jc.getPlayer().getEvents().subscribe(BeginTurnEvent.class,
            new Action<BeginTurnEvent>() {
		@Override
                    public void onAction(BeginTurnEvent args) {
                        jc.setAcaoTerr(new AcaoTerritorioAloca(jc));
                    }
        });
        
    }

    
    @Override
    public void AcaoBotao(TerritorioUI ter) {
        // não faz nada 
        
    }

    @Override
    public AcaoTerritorioStrategy ProxFase() {
        return new AcaoTerritorioAloca(jc);
    }
    
}
