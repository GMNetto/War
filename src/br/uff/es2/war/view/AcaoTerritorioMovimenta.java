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
public class AcaoTerritorioMovimenta implements AcaoTerritorioStrategy {

    private JogoController jc;

    public AcaoTerritorioMovimenta(JogoController jc) {
        this.jc=jc;
        jc.setTextFase("Movimente exercitos");
        // para a fase de movimentação é necessário bloquear os territorios que não pertencem ao jogador
        // ações de inicialização bloquear clique, trocar icone do cursor e modificar opacidade do circulo
        jc.desbloqueiaTerritorios(jc.getTerritorios());
        jc.bloqueiaTerririosAdversarios();
        jc.getAlocaController().actionMovimenta();
        jc.getAlocaController().setTerritorioDestino(null);
        jc.getAlocaController().setTerritorioOrigem(null); 
        jc.getAlocaController().esconde();
        jc.getAlocaController().escondeMov();
    }

    @Override
    public void AcaoBotao(TerritorioUI ter) {
        jc.getAlocaController().actionMovimenta();
        
        if(jc.getAlocaController().hasTerritorioOrigem()){
            jc.getAlocaController().setTerritorioDestino(ter);
            jc.getAlocaController().mostra();
            jc.getAlocaController().centraliza(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
        }
        else{
            jc.getAlocaController().setTerritorioOrigem(ter);
            jc.bloqueiaTerririosNaoVizinhosAdversarios(ter);
            jc.getAlocaController().centralizaMov(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            jc.getAlocaController().mostraMov();
        }
    }

    @Override
    public AcaoTerritorioStrategy ProxFase() {
        jc.LimpaMovimentaçao();
        return new AcaoTerritorioEspera(jc);
    }
}
