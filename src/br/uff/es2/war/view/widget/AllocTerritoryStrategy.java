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

    private GameController2 jc;

    public AllocTerritoryStrategy(GameController2 jc) {
        this.jc=jc;
        jc.setTextFase("Aloque seu exércitos","Selecione o territorio onde deseja alocar","","");
        // para a fase de alocação é necessário bloquear os territorios que não pertencem ao jogador
        // ações de inicialização bloquear clique, trocar icone do cursor e modificar opacidade do circulo
        jc.desbloqueiaTerritorios(jc.getTerritorios());
        jc.bloqueiaTerririosAdversarios();
        jc.getAlocaController().setTerritorioDestino(null);
        jc.getAlocaController().setTerritorioOrigem(null); 
        jc.getAlocaController().esconde();
        jc.getAlocaController().escondeMov();
    }

    @Override
    public void buttonAction(TerritoryUI ter) {
	jc.getAlocaController().setTerritorioDestino(ter);
	jc.getAlocaController().actionAloca(jc.getMaxExercitosAloca());
	jc.getAlocaController().mostra();
	jc.getAlocaController().centraliza(ter.getCirculo().getCenterX(),
		ter.getCirculo().getCenterY());
        jc.setTextFase2("Selecionado "+ter.getNome());

    }

    @Override
    public TerritoryUIStrategy nextPhase() {
        jc.getAlocaController().esconde();
        return new AttackTerritoryStrategy(jc);
    }
}
