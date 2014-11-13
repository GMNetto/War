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
        jc.getAllocTerritoryCont().actionMovimenta();
        jc.getAllocTerritoryCont().setTerritorioDestino(null);
        jc.getAllocTerritoryCont().setTerritorioOrigem(null); 
        jc.getAllocTerritoryCont().esconde();
        jc.getAllocTerritoryCont().escondeMov();
    }

    @Override
    public void buttonAction(TerritoryUI ter) {
        gameCont2.getAllocTerritoryCont().actionMovimenta();
        
        if(gameCont2.getAllocTerritoryCont().hasTerritorioOrigem()){
            gameCont2.getAllocTerritoryCont().setTerritorioDestino(ter);
            gameCont2.getAllocTerritoryCont().mostra();
            gameCont2.getAllocTerritoryCont().centraliza(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            gameCont2.setTextFase2(gameCont2.getAllocTerritoryCont().getTerOrigem().getNome() +" -> "+ter.getNome());
        }
        else{
            gameCont2.getAllocTerritoryCont().setTerritorioOrigem(ter);
            gameCont2.bloqueiaTerririosNaoVizinhosAdversarios(ter);
            gameCont2.getAllocTerritoryCont().centralizaMov(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            gameCont2.getAllocTerritoryCont().mostraMov();
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
        gameCont2.getAllocTerritoryCont().esconde();
        gameCont2.getAllocTerritoryCont().escondeMov();
    }
}
