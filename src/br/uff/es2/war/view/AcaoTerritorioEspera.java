/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

/**
 *
 * @author anacarolinegomesvargas
 */
public class AcaoTerritorioEspera implements AcaoTerritorioStrategy{
    
    private JogoController jc;
    
    public AcaoTerritorioEspera(JogoController jc) {
        this.jc=jc;
        jc.setTextFase("Espere a sua vez");
        jc.bloqueiaTerritorios(jc.getTerritorios());
        
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
