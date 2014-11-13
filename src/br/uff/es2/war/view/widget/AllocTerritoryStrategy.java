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
public class AllocTerritoryStrategy implements TerritoryUIStrategy {

    private GameController2 gameController2;
    private AlloctTerritoryController allocTerritoryCont;

    public AllocTerritoryStrategy(GameController2 gameCont2) {
        this.gameController2=gameCont2;
        this.allocTerritoryCont=gameCont2.getAllocTerritoryCont();
        
        // para a fase de alocação é necessário bloquear os territorios que não pertencem ao jogador
        // ações de inicialização bloquear clique, trocar icone do cursor e modificar opacidade do circulo
        allocTerritoryCont.setTerritorioDestino(null);
        allocTerritoryCont.setTerritorioOrigem(null); 
        allocTerritoryCont.esconde();
        allocTerritoryCont.escondeMov();
    }

    @Override
    public void buttonAction(TerritoryUI ter) {
	allocTerritoryCont.setTerritorioDestino(ter);
	allocTerritoryCont.actionAloca(gameController2.getMaxQuantityToDistribute());
	allocTerritoryCont.mostra();
	allocTerritoryCont.centraliza(ter.getCirculo().getCenterX(),ter.getCirculo().getCenterY());
        gameController2.setTextFase2("Selecionado "+ter.getNome());

    }

    @Override
    public TerritoryUIStrategy nextPhase() {
        finishPhase();
        return new AttackTerritoryStrategy(gameController2);
    }

    @Override
    public void finishPhase() {
        allocTerritoryCont.esconde();
    }
}
