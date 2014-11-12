/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * @author anacarolinegomesvargas
 */
public class PopUpController {
    
    private Pane pane_sub_janela;
    private Pane pane_obj;
    private Pane pane_info;
    private Pane pane_cartas;
    private Button btn_janela_x;
    private Text txt_ataca1;
    private Text txt_ataca2;
    private GameController2 jc;

    public PopUpController(Pane pane_sub_janela, GameController2 jc) {
        this.jc=jc;
        this.pane_sub_janela=pane_sub_janela;
        this.pane_obj = (Pane) pane_sub_janela.lookup("#pane_obj");
        this.pane_info = (Pane) pane_sub_janela.lookup("#pane_info");
        this.pane_cartas = (Pane) pane_sub_janela.lookup("#pane_cartas");
        this.btn_janela_x = (Button) pane_sub_janela.lookup("#btn_janela_x");
        esconde();
        
        this.btn_janela_x.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                esconde();
            }
        });
    }

    public void esconde() {
	pane_sub_janela.setVisible(false);
    }
    
    public void mostra(){
        pane_sub_janela.setVisible(true);
    }
    
    public void mostraObj(){
        pane_obj.setVisible(true);
        pane_info.setVisible(false);
        pane_cartas.setVisible(false);
    }
    
    public void mostraInfo(){
        pane_obj.setVisible(false);
        pane_info.setVisible(true);
        pane_cartas.setVisible(false);
    }
    
    public void mostraCartas(){
        pane_obj.setVisible(false);
        pane_info.setVisible(false);
        pane_cartas.setVisible(true);
    }
    
}
