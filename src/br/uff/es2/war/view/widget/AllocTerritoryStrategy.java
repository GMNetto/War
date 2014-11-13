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

    public AllocTerritoryStrategy(GameController2 gameCont2) {
        this.gameController2=gameCont2;
        gameCont2.getBtn_prox().setVisible(true);
        gameCont2.setTextFase("Aloque seus "+gameCont2.getMaxQuantityToDistribute()+" exércitos","Selecione o territorio onde deseja alocar","","");
        // para a fase de alocação é necessário bloquear os territorios que não pertencem ao jogador
        // ações de inicialização bloquear clique, trocar icone do cursor e modificar opacidade do circulo
        
        gameCont2.getAlocaController().setTerritorioDestino(null);
        gameCont2.getAlocaController().setTerritorioOrigem(null); 
        gameCont2.getAlocaController().esconde();
        gameCont2.getAlocaController().escondeMov();
    }

    @Override
    public void buttonAction(TerritoryUI ter) {
	gameController2.getAlocaController().setTerritorioDestino(ter);
	gameController2.getAlocaController().actionAloca(gameController2.getMaxQuantityToDistribute());
	gameController2.getAlocaController().mostra();
	gameController2.getAlocaController().centraliza(ter.getCirculo().getCenterX(),
		ter.getCirculo().getCenterY());
        gameController2.setTextFase2("Selecionado "+ter.getNome());

    }

    @Override
    public TerritoryUIStrategy nextPhase() {
        gameController2.getAlocaController().esconde();
        return new AttackTerritoryStrategy(gameController2);
    }
}
