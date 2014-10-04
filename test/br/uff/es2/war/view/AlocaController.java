/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author anacarolinegomesvargas
 */
public class AlocaController {
    private Pane pane_aloca;
    private Button btn_aloca_mais;
    private Button btn_aloca_cancel;
    private Button btn_aloca_menos;
    private Button btn_aloca_ok;
    private int centrox;
    private int centroy;
    
    private TerritorioUI t;
    private int acrescenta;
    

    public AlocaController(Pane pane_aloca, Button btn_aloca_mais, Button btn_aloca_cancel, Button btn_aloca_menos, Button btn_aloca_ok, int raio) {
        this.pane_aloca = pane_aloca;
        this.btn_aloca_mais = btn_aloca_mais;
        this.btn_aloca_cancel = btn_aloca_cancel;
        this.btn_aloca_menos = btn_aloca_menos;
        this.btn_aloca_ok = btn_aloca_ok;
        
        //tamanho do botão 25x25px mas considerando 30
        this.btn_aloca_mais.setLayoutX(15+raio);
        this.btn_aloca_cancel.setLayoutY(15+raio);
        
        this.btn_aloca_menos.setLayoutX(15+raio);
        this.btn_aloca_menos.setLayoutY(30+2*raio);
        
        this.btn_aloca_ok.setLayoutX(30+2*raio);
        this.btn_aloca_ok.setLayoutY(15+raio);
        
        this.centrox=28+raio;
        this.centroy=25+raio;
        
        esconde();
        
        this.btn_aloca_mais.setOnAction(new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent event) {
                acrescenta++;
                t.getTexto().setText((t.getQtd()+acrescenta)+"");
            }
        });
        
        this.btn_aloca_menos.setOnAction(new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent event) {
                
                if(acrescenta>0){
                    acrescenta--;
                    t.getTexto().setText((t.getQtd()+acrescenta)+"");
                }
                
            }
        });
        
        
        this.btn_aloca_ok.setOnAction(new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent event) {
                t.setQtd(t.getQtd()+acrescenta);
                
                esconde();
            }
        });
        
        this.btn_aloca_cancel.setOnAction(new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent event) {
                t.getTexto().setText(t.getQtd()+"");
                esconde();
            }
        });
    }
    
    public void esconde(){
        pane_aloca.setVisible(false);
    }
    
    public void mostra(){
        pane_aloca.setVisible(true);
        btn_aloca_mais.setVisible(true);
        btn_aloca_cancel.setVisible(true);
        btn_aloca_menos.setVisible(true);
        btn_aloca_ok.setVisible(true);
    }
    
    public void centraliza(double x,double y){
        pane_aloca.setLayoutX(x-centrox);
        pane_aloca.setLayoutY(y-centroy);
        pane_aloca.toFront();
    }
    
    public void setTerritorio(TerritorioUI t){
        this.t=t;
        this.acrescenta=0;
    }
    
    
}
