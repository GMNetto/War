/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 *
 * @author anacarolinegomesvargas
 */
public class JogoController {
    
    private AlocaController ac;
    private List<TerritorioUI> territorios;
    private int jogador;
    private int raio;

    private AcaoTerritorioStrategy acaoTerr;
            
    public JogoController(int jogador, Pane pane_aloca, Pane pane_mov) {
        this.jogador = jogador;
        this.raio=10;
        this.ac = new AlocaController(pane_aloca,pane_mov, raio, this);
        
        
        inicializaParaTestes();
        
    }

    public AcaoTerritorioStrategy getAcaoTerr() {
        return acaoTerr;
    }
    
    public void setAcaoTerr( AcaoTerritorioStrategy acTerr){
        this.acaoTerr=acTerr;
    }
    
    public int getMaxExercitosAloca(){
        return 10;
    }

    public AlocaController getAlocaController() {
        return ac;
    }

    public List<TerritorioUI> getTerritorios() {
        return territorios;
    }

    
    public int getJogador() {
        return jogador;
    }

    public int getRaio() {
        return raio;
    }
    
    public void desbloqueiaTerritorios(List<TerritorioUI> territorios){
        
        for ( TerritorioUI terr : territorios){
            terr.desbloqueia();
        }
        
    }
    
    public void bloqueiaTerritorios(List<TerritorioUI> territorios){
        
        for ( TerritorioUI terr : territorios){
            terr.bloqueia();
        }
        
    }
   
    public void bloqueiaTerririosAdversarios(){
        //bloqueia territorios que não pertencem ao usuário
        // Utilizado para a fase de alocação
        
        for ( TerritorioUI terr : territorios){
            if(!terr.isDono(jogador)){
                //territorio de adversário
                terr.bloqueia();
            }
        }
    }
    
    public void bloqueiaTerririosNaoVizinhos(TerritorioUI territorio){
        //bloqueia territorios que não são vizinhos e territorios que pertencem ao usuário
        // Utilizado para a fase de ataque
        
        //bloqueia todos os territorios
        bloqueiaTerritorios(this.territorios);
        
        // agora desbloquei apenas os vizinhos necessários
        for ( TerritorioUI terr : territorio.getViz()){
            if(!terr.isDono(jogador)){
                //territorio vizinho e não pertence ao jogador
                terr.desbloqueia();
            }
        }
        
    }
    
    public void bloqueiaTerririosNaoVizinhosAdversarios(TerritorioUI territorio){
        //bloqueia territorios que não são vizinhos e territorios que pertencem não pertencem ao usuário
        // Utilizado para a fase de moviementação
        
        //bloqueia todos os territorios
        bloqueiaTerritorios(this.territorios);
        
        // agora desbloqueia apenas os vizinhos necessários
        for ( TerritorioUI terr : territorio.getViz()){
            if(terr.isDono(jogador)){
                //territorio vizinho e pertence ao jogador
                terr.desbloqueia();
            }
        }
    
    }
    
    
    private void inicializaParaTestes(){
        
        //criando territorios
        territorios = new ArrayList<>();
        territorios.add(new TerritorioUI(null, "America do norte"));
        territorios.add(new TerritorioUI(null, "America do Sul"));
        territorios.add(new TerritorioUI(null, "Europa"));
        territorios.add(new TerritorioUI(null, "África"));
        territorios.add(new TerritorioUI(null, "Ásia"));
        territorios.add(new TerritorioUI(null, "Oceania"));

        //America do norte
        territorios.get(0).addVizinho(territorios.get(1));//america do sul
        territorios.get(0).addVizinho(territorios.get(2));//europa

        //America do sul
        territorios.get(1).addVizinho(territorios.get(0));//america do norte
        territorios.get(1).addVizinho(territorios.get(3));//africa
        
        //europa
        territorios.get(2).addVizinho(territorios.get(0));//america do norte
        territorios.get(2).addVizinho(territorios.get(3));//africa
        territorios.get(2).addVizinho(territorios.get(4));//asia
        
        //africa
        territorios.get(3).addVizinho(territorios.get(1));//america dosul
        territorios.get(3).addVizinho(territorios.get(2));//europa
        territorios.get(3).addVizinho(territorios.get(4));//asia
        
        
        //asia
        territorios.get(4).addVizinho(territorios.get(2));//europa
        territorios.get(4).addVizinho(territorios.get(3));//africa
        territorios.get(4).addVizinho(territorios.get(5));//oceania
        
        //oceania
        territorios.get(5).addVizinho(territorios.get(4));//asia
        
        
        // distribuindo territorios para 3 jogadores aleatoriamente
        Random gerador = new Random();
        
        for ( TerritorioUI terr : territorios){
            terr.setDono(gerador.nextInt(3));
        }
    }
    
}
