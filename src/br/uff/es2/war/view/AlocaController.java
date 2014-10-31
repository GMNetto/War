/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * 
 * @author anacarolinegomesvargas
 */
public class AlocaController {
    private Pane pane_aloca;
    private Pane pane_mov;
    private Button btn_mov_cancel;
    private ImageView img_point; 
    private Button btn_aloca_mais;
    private Button btn_aloca_cancel;
    private Button btn_aloca_menos;
    private Button btn_aloca_ok;
    private int centrox;
    private int centroy;

    private TerritorioUI terDestino, terOrigem;
    private int acrescenta;
    
     private JogoController jc;
    

    public AlocaController(Pane pane_aloca, Pane pane_mov, int raio, JogoController jc) {
        this.pane_aloca = pane_aloca;
        this.btn_aloca_mais = (Button) pane_aloca.lookup("#btn_aloca_mais");
        this.btn_aloca_cancel =(Button) pane_aloca.lookup("#btn_aloca_cancel");
        this.btn_aloca_menos = (Button) pane_aloca.lookup("#btn_aloca_menos");
        this.btn_aloca_ok = (Button) pane_aloca.lookup("#btn_aloca_ok");
        
        this.pane_mov = pane_mov;
        this.btn_mov_cancel = (Button) pane_mov.lookup("#btn_mov_cancel");
        this.img_point = (ImageView) pane_mov.lookup("#img_point");
        
        this.jc=jc;
        
        //tamanho do botão 25x25px mas considerando 30
        this.btn_aloca_mais.setLayoutX(15+raio);
        this.btn_aloca_cancel.setLayoutY(15+raio);
        
        this.btn_aloca_menos.setLayoutX(15+raio);
        this.btn_aloca_menos.setLayoutY(30+2*raio);
        
        this.btn_aloca_ok.setLayoutX(30+2*raio);
        this.btn_aloca_ok.setLayoutY(15+raio);
        
        this.centrox=28+raio;
        this.centroy=25+raio;
        
        
        this.btn_mov_cancel.setLayoutX(15+raio);
        this.btn_mov_cancel.setLayoutY(30+2*raio);
        
        
        this.img_point.setLayoutX(30+2*raio);
        this.img_point.setLayoutY(15+raio);
        
        esconde();
        escondeMov();
    }

    public void esconde() {
	pane_aloca.setVisible(false);
    }
    
    public void mostra(){
        pane_aloca.setVisible(true);
    }
    
    
    public void escondeMov(){
        pane_mov.setVisible(false);
    }
    
    public void mostraMov(){
        pane_mov.setVisible(true);
    }
    public void centraliza(double x,double y){
        pane_aloca.setLayoutX(x-centrox);
        pane_aloca.setLayoutY(y-centroy);
        pane_aloca.toFront();
    }
    
    public void centralizaMov(double x,double y){
        pane_mov.setLayoutX(x-centrox);
        pane_mov.setLayoutY(y-centroy);
        pane_mov.toFront();
    }
    
    public void setTerritorioDestino(TerritorioUI t){
        this.terDestino=t;
        this.acrescenta=0;
>>>>>>> e8122ac3271d5414da4b6e77f4e9e0712746106d
    }

    public void setTerritorioOrigem(TerritorioUI t) {
	this.terOrigem = t;
	this.acrescenta = 0;
    }

    public boolean hasTerritorioOrigem() {
	return this.terOrigem != null;
    }

    public void actionAloca(final int maxExercitos){
       
        //ações dos botões n fse de alocação
        this.btn_aloca_mais.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                //limitando para numero máximo de execitos a serem alocados
                if(acrescenta<maxExercitos){
                    acrescenta++;
                    terDestino.getTexto().setText((terDestino.getQtd()+acrescenta)+"");
                }
            }
        });
        
        this.btn_aloca_menos.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                // limitando para não retirar exercitos
                if(acrescenta>0){
                    acrescenta--;
                    terDestino.getTexto().setText((terDestino.getQtd()+acrescenta)+"");
                }
                
            }
        });
        
        
        this.btn_aloca_ok.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                terDestino.setQtd(terDestino.getQtd()+acrescenta);
                setTerritorioDestino(null);
                setTerritorioOrigem(null);
                esconde();
            }
        });
        
        this.btn_aloca_cancel.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                terDestino.getTexto().setText(terDestino.getQtd()+"");
                setTerritorioDestino(null);
                setTerritorioOrigem(null);
                esconde();
            }
        });
    }
    
     public void actionMovimenta(){
         
        //ações dos botões n fse de alocação
        this.btn_aloca_mais.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                //limitando para numero máximo de execitos a serem alocados
                //como colocar a condição de não mover exercito que já foram movidos nessa fase
                if(terOrigem.getQtdMov()==0 && acrescenta<terOrigem.getQtd()-1){
                    acrescenta++;
                    terDestino.getTexto().setText((terDestino.getQtd()+acrescenta)+"");
                    terOrigem.getTexto().setText((terOrigem.getQtd()-acrescenta)+"");
                }
                if(terOrigem.getQtdMov()>0 && acrescenta<terOrigem.getQtd()-terOrigem.getQtdMov()){
                    acrescenta++;
                    terDestino.getTexto().setText((terDestino.getQtd()+acrescenta)+"");
                    terOrigem.getTexto().setText((terOrigem.getQtd()-acrescenta)+"");
                }
            }
        });
        
        this.btn_aloca_menos.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                // limitando para não retirar exercitos
                if(acrescenta>0 ){
                    acrescenta--;
                    terDestino.getTexto().setText((terDestino.getQtd()+acrescenta)+"");
                    terOrigem.getTexto().setText((terOrigem.getQtd()-acrescenta)+"");
                }
                
            }
        });
        
        
        this.btn_aloca_ok.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                terDestino.setQtd(terDestino.getQtd()+acrescenta);
                terDestino.setQtdMov(acrescenta);
                terOrigem.setQtd(terOrigem.getQtd()-acrescenta);
                setTerritorioDestino(null);
                setTerritorioOrigem(null); 
                esconde();
                escondeMov();
                jc.desbloqueiaTerritorios(jc.getTerritorios());
                jc.bloqueiaTerririosAdversarios();
            }
        });
        
        this.btn_aloca_cancel.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                terDestino.getTexto().setText(terDestino.getQtd()+"");
                terOrigem.getTexto().setText(terOrigem.getQtd()+"");
                
                setTerritorioDestino(null);
                setTerritorioOrigem(null);
                esconde();
                escondeMov();
                jc.desbloqueiaTerritorios(jc.getTerritorios());
                jc.bloqueiaTerririosAdversarios();
            }
        });
        
        this.btn_mov_cancel.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                //cancelando acão de movimentação
                setTerritorioDestino(null);
                setTerritorioOrigem(null);
                esconde();
                escondeMov();
                jc.desbloqueiaTerritorios(jc.getTerritorios());
                jc.bloqueiaTerririosAdversarios();
            }
        });
    }

}
