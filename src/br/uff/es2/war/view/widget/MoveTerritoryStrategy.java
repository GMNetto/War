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

    private GameController2 gameCont2;

    public MoveTerritoryStrategy(GameController2 jc) {
        this.gameCont2=jc;
        jc.getBtn_prox().setVisible(true);
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
        gameCont2.getAlocaController().actionMovimenta();
        
        if(gameCont2.getAlocaController().hasTerritorioOrigem()){
            gameCont2.getAlocaController().setTerritorioDestino(ter);
            gameCont2.getAlocaController().mostra();
            gameCont2.getAlocaController().centraliza(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            gameCont2.setTextFase2(gameCont2.getAlocaController().getTerOrigem().getNome() +" -> "+ter.getNome());
        }
        else{
            gameCont2.getAlocaController().setTerritorioOrigem(ter);
            gameCont2.bloqueiaTerririosNaoVizinhosAdversarios(ter);
            gameCont2.getAlocaController().centralizaMov(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            gameCont2.getAlocaController().mostraMov();
            gameCont2.setTextFase2(ter.getNome()+" ->");
        }
    }

    @Override
    public TerritoryUIStrategy nextPhase() {
        finishPhase();
        return new WaitTerritoryStrategy(gameCont2);
    }

    @Override
    public void finishPhase() {
        gameCont2.LimpaMovimentaçao();
        gameCont2.getAlocaController().esconde();
        gameCont2.getAlocaController().escondeMov();
    }
}
