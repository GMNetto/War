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
public class AcaoTerritorioAloca implements AcaoTerritorioStrategy{
    
    private JogoController jc;
    
    public AcaoTerritorioAloca(JogoController jc) {
        this.jc=jc;
        
        // para a fase de alocação é necessário bloquear os territorios que não pertencem ao jogador
        // ações de inicialização bloquear clique, trocar icone do cursor e modificar opacidade do circulo
        
        jc.bloqueiaTerririosAdversarios();
        
    }
    @Override
    public void AcaoBotao(TerritorioUI ter) {
        //Como passar o valor de maximo de exercitos?
        
    }
    
}
