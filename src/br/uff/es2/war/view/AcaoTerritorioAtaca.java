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
public class AcaoTerritorioAtaca implements AcaoTerritorioStrategy{
    
    private JogoController jc;
    
    public AcaoTerritorioAtaca(JogoController jc) {
        this.jc=jc;
        jc.setTextFase("Ataque!!","Selecione o territorio de origem e o destino do ataque","","");
        jc.bloqueiaTerritorios(jc.getTerritorios());
        
    }

    
    @Override
    public void AcaoBotao(TerritorioUI ter) {
        // não faz nada 
        
    }

    @Override
    public AcaoTerritorioStrategy ProxFase() {
        return new AcaoTerritorioMovimenta(jc);
    }
    
}
