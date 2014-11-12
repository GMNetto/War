/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view.widget;

import br.uff.es2.war.view.GameController2;

/**
 * 
 * @author anacarolinegomesvargas
 */
public class MoveTerritoryStrategy implements TerritoryUIStrategy {

    private GameController2 jc;

    public MoveTerritoryStrategy(GameController2 jc) {
        this.jc=jc;
        jc.setTextFase("Movimente exércitos","Selecione um territorio de origem e um de destino","","");
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
    public void buttonAction(TerritoryUI ter) {
        jc.getAlocaController().actionMovimenta();
        
        if(jc.getAlocaController().hasTerritorioOrigem()){
            jc.getAlocaController().setTerritorioDestino(ter);
            jc.getAlocaController().mostra();
            jc.getAlocaController().centraliza(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            jc.setTextFase2(jc.getAlocaController().getTerOrigem().getNome() +" -> "+ter.getNome());
        }
        else{
            jc.getAlocaController().setTerritorioOrigem(ter);
            jc.bloqueiaTerririosNaoVizinhosAdversarios(ter);
            jc.getAlocaController().centralizaMov(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            jc.getAlocaController().mostraMov();
            jc.setTextFase2(ter.getNome()+" ->");
        }
    }

    @Override
    public TerritoryUIStrategy nextPhase() {
        jc.LimpaMovimentaçao();
        jc.getAlocaController().esconde();
        jc.getAlocaController().escondeMov();
        return new WaitTerritoryStrategy(jc);
    }
}
