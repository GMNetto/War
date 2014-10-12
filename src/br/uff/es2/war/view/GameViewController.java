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
public class GameViewController {
    
    private AlocaController ac;
    private List<TerritorioUI> territorios;
    private int jogador;
    private int raio;

    public GameViewController(int jogador, Pane pane_aloca, Button btn_aloca_mais, Button btn_aloca_cancel, Button btn_aloca_menos, Button btn_aloca_ok) {
        this.jogador = jogador;
        this.ac = new AlocaController(pane_aloca, btn_aloca_mais, btn_aloca_cancel, btn_aloca_menos, btn_aloca_ok, raio);
        this.raio=10;
    }

    public AlocaController getAc() {
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
        territorios.get(0).setQtd(0);
        territorios.get(0).addVizinho(territorios.get(1));//america do sul
        territorios.get(0).addVizinho(territorios.get(2));//europa

        //America do sul
        territorios.get(1).setQtd(0);
        territorios.get(1).addVizinho(territorios.get(0));//america do norte
        territorios.get(1).addVizinho(territorios.get(3));//africa
        
        //europa
        territorios.get(2).setQtd(0);
        territorios.get(2).addVizinho(territorios.get(0));//america do norte
        territorios.get(2).addVizinho(territorios.get(3));//africa
        territorios.get(2).addVizinho(territorios.get(4));//asia
        
        //africa
        territorios.get(3).setQtd(0);
        territorios.get(3).addVizinho(territorios.get(1));//america dosul
        territorios.get(3).addVizinho(territorios.get(2));//europa
        territorios.get(3).addVizinho(territorios.get(4));//asia
        
        
        //asia
        territorios.get(4).setQtd(0);
        territorios.get(4).addVizinho(territorios.get(2));//europa
        territorios.get(4).addVizinho(territorios.get(3));//africa
        territorios.get(4).addVizinho(territorios.get(5));//oceania
        
        //oceania
        territorios.get(5).setQtd(0);
        territorios.get(5).addVizinho(territorios.get(4));//asia
        
        
        // distribuindo territorios para 3 jogadores aleatoriamente
        Random gerador = new Random();
        
        for ( TerritorioUI terr : territorios){
            terr.setDono(gerador.nextInt(2));
        }
    }
    
}
