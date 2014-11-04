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
        jc.desbloqueiaTerritorios(jc.getTerritorios());
        jc.bloqueiaTerririosAdversarios();
        jc.getAtacaController().actionAtaca();
        jc.getAtacaController().setTerritorioDestino(null);
        jc.getAtacaController().setTerritorioOrigem(null); 
        jc.getAtacaController().esconde1();
        jc.getAtacaController().esconde2();
        
    }

    
    @Override
    public void AcaoBotao(TerritorioUI ter) {
        jc.getAtacaController().actionAtaca();
        
        if(jc.getAtacaController().hasTerritorioOrigem()){
            jc.getAtacaController().setTerritorioDestino(ter);
            jc.getAtacaController().mostra2();
            jc.getAtacaController().centraliza2(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            jc.setTextFase2(jc.getAtacaController().getTerOrigem().getNome() +" -> "+ter.getNome());
        }
        else{
            jc.getAtacaController().setTerritorioOrigem(ter);
            jc.bloqueiaTerririosNaoVizinhos(ter);
            jc.getAtacaController().centraliza1(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            jc.getAtacaController().mostra1();
            jc.setTextFase2(ter.getNome()+" ->");
        }
        
    }

    @Override
    public AcaoTerritorioStrategy ProxFase() {
        jc.getAtacaController().esconde1();
        jc.getAtacaController().esconde2();
        return new AcaoTerritorioMovimenta(jc);
    }
    
}
