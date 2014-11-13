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
public class AttackTerritoryStrategy implements TerritoryUIStrategy{
    
    private GameController2 gameCont2;
    
    public AttackTerritoryStrategy(GameController2 jc) {
        this.gameCont2=jc;
        jc.getBtn_prox().setVisible(true);
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
    public void buttonAction(TerritoryUI ter) {
        gameCont2.getAtacaController().actionAtaca();
        
        if(gameCont2.getAtacaController().hasTerritorioOrigem()){
            gameCont2.getAtacaController().setTerritorioDestino(ter);
            gameCont2.getAtacaController().mostra2();
            gameCont2.getAtacaController().centraliza2(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            gameCont2.setTextFase2(gameCont2.getAtacaController().getTerOrigem().getNome() +" -> "+ter.getNome());
        }
        else{
            gameCont2.getAtacaController().setTerritorioOrigem(ter);
            gameCont2.bloqueiaTerririosNaoVizinhos(ter);
            gameCont2.getAtacaController().centraliza1(ter.getCirculo().getCenterX(), ter.getCirculo().getCenterY());
            gameCont2.getAtacaController().mostra1();
            gameCont2.setTextFase2(ter.getNome()+" ->");
        }
        
    }

    @Override
    public TerritoryUIStrategy nextPhase() {
        finishPhase();
        return new MoveTerritoryStrategy(gameCont2);
    }

    @Override
    public void finishPhase() {
        gameCont2.getAtacaController().esconde1();
        gameCont2.getAtacaController().esconde2();
    }
    
}
