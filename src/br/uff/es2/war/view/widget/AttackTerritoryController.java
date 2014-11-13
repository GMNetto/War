/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view.widget;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import br.uff.es2.war.view.GameController2;

/**
 * 
 * @author anacarolinegomesvargas
 */
public class AttackTerritoryController {
    private Pane pane_ataca1;
    private Pane pane_ataca2;
    
    private ImageView img_point; 
    private Button btn_ataca1_cancel;
    private Button btn_ataca1_mais;
    private Button btn_ataca1_menos;
    private Button btn_ataca2_cancel;
    private Button btn_ataca2_mais;
    private Button btn_ataca2_menos;
    private Button btn_ataca2_ok;
    private Text txt_ataca1;
    private Text txt_ataca2;
    private TextField input_ataca1;
    private TextField input_ataca2;
    private int centrox;
    private int centroy;

    private TerritoryUI terDestino, terOrigem;
    private int nAtaca,nMove;
    
     private GameController2 jc;

    public int getnAtaca() {
        return nAtaca;
    }

    public int getnMove() {
        return nMove;
    }
    

    public AttackTerritoryController(Pane pane_ataca1, Pane pane_ataca2, int raio, GameController2 jc) {
        this.pane_ataca1 = pane_ataca1;
        this.btn_ataca1_cancel = (Button) pane_ataca1.lookup("#btn_ataca1_cancel");
        this.btn_ataca1_mais =(Button) pane_ataca1.lookup("#btn_ataca1_mais");
        this.btn_ataca1_menos = (Button) pane_ataca1.lookup("#btn_ataca1_menos");
        this.img_point = (ImageView) pane_ataca1.lookup("#img_point2");
        this.txt_ataca1 = (Text) pane_ataca1.lookup("#txt_ataca1");
        this.input_ataca1 = (TextField) pane_ataca1.lookup("#input_ataca1");
     
        this.pane_ataca2 = pane_ataca2;
        this.btn_ataca2_cancel = (Button) pane_ataca2.lookup("#btn_ataca2_cancel");
        this.btn_ataca2_ok = (Button) pane_ataca2.lookup("#btn_ataca2_ok");
        this.btn_ataca2_mais =(Button) pane_ataca2.lookup("#btn_ataca2_mais");
        this.btn_ataca2_menos = (Button) pane_ataca2.lookup("#btn_ataca2_menos");
        this.txt_ataca2 = (Text) pane_ataca2.lookup("#txt_ataca2");
        this.input_ataca2 = (TextField) pane_ataca2.lookup("#input_ataca2");
        
        
        this.jc=jc;
        
        //tamanho do botão 25x25px mas considerando 30
        this.btn_ataca1_cancel.setLayoutY(15+raio);
        this.img_point.setLayoutX(30+2*raio);
        this.img_point.setLayoutY(15+raio);
        this.btn_ataca1_menos.setLayoutY(30+2*raio);
        this.btn_ataca1_mais.setLayoutX(30+2*raio);
        this.btn_ataca1_mais.setLayoutY(30+2*raio);
        this.input_ataca1.setLayoutX(12+raio);
        this.input_ataca1.setLayoutY(30+2*raio);
        this.txt_ataca1.setLayoutY(70+2*raio);
        
        this.btn_ataca2_cancel.setLayoutY(15+raio);
        this.btn_ataca2_ok.setLayoutX(30+2*raio);
        this.btn_ataca2_ok.setLayoutY(15+raio);
        this.btn_ataca2_menos.setLayoutY(30+2*raio);
        this.btn_ataca2_mais.setLayoutX(30+2*raio);
        this.btn_ataca2_mais.setLayoutY(30+2*raio);
        this.input_ataca2.setLayoutX(12+raio);
        this.input_ataca2.setLayoutY(30+2*raio);
        this.txt_ataca2.setLayoutY(70+2*raio);
        
        this.centrox=28+raio;
        this.centroy=25+raio;
        
        esconde1();
        esconde2();
    }

    public void esconde1() {
	pane_ataca1.setVisible(false);
    }
    
    public void mostra1(){
        pane_ataca1.setVisible(true);
    }
    
    
    public void esconde2(){
        pane_ataca2.setVisible(false);
    }
    
    public void mostra2(){
        pane_ataca2.setVisible(true);
    }
    public void centraliza1(double x,double y){
        pane_ataca1.setLayoutX(x-centrox);
        pane_ataca1.setLayoutY(y-centroy);
        pane_ataca1.toFront();
    }
    
    public void centraliza2(double x,double y){
        pane_ataca2.setLayoutX(x-centrox);
        pane_ataca2.setLayoutY(y-centroy);
        pane_ataca2.toFront();
    }
    
    public void setTerritorioDestino(TerritoryUI t){
        this.terDestino=t;
        this.nMove=0;
        input_ataca2.setText(nMove+"");
    }

    public void setTerritorioOrigem(TerritoryUI t) {
	this.terOrigem = t;
	this.nAtaca = 0;
        input_ataca1.setText(nAtaca+"");
    }

    public TerritoryUI getTerDestino() {
        return terDestino;
    }

    public TerritoryUI getTerOrigem() {
        return terOrigem;
    }

    
    public boolean hasTerritorioOrigem() {
	return this.terOrigem != null;
    }

    public void actionAtaca(){
       
        //ações dos botões na fase de ataque
        this.btn_ataca1_mais.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                //limitando para numero máximo de execitos para atacar max 3
                if(nAtaca<Math.min(3,terOrigem.getQtd()-1)){
                    nAtaca++;
                    input_ataca1.setText(nAtaca+"");
                }
            }
        });
        
        this.btn_ataca1_menos.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                // limitando para não retirar exercitos
                if(nAtaca>0){
                    nAtaca--;
                    input_ataca1.setText(nAtaca+"");
                }
                
            }
        });
        
        this.btn_ataca1_cancel.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                input_ataca1.setText("0");
                setTerritorioDestino(null);
                setTerritorioOrigem(null);
                jc.setTextFase2("Selecione o territorio de origem e o destino do ataque");
                jc.desbloqueiaTerritorios(jc.getTerritorios());
                jc.bloqueiaTerririosAdversarios();
                esconde1();
                esconde2();
            }
        });
        
        this.btn_ataca2_mais.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                //limitando para numero máximo de execitos para atacar max 3
                if(nMove<Math.min(3,terOrigem.getQtd()-1)){
                    nMove++;
                    input_ataca2.setText(nMove+"");
                }
            }
        });
        
        this.btn_ataca2_menos.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                // limitando para não retirar exercitos
                if(nMove>0){
                    nMove--;
                    input_ataca2.setText(nMove+"");
                }
                
            }
        });
        
        this.btn_ataca2_cancel.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                input_ataca2.setText("0");
                setTerritorioDestino(null);
                setTerritorioOrigem(null);
                jc.setTextFase2("Selecione o territorio de origem e o destino do ataque");
                jc.desbloqueiaTerritorios(jc.getTerritorios());
                jc.bloqueiaTerririosAdversarios();
                esconde1();
                esconde2();
            }
        });
        this.btn_ataca2_ok.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                input_ataca2.setText("0");
                setTerritorioDestino(null);
                setTerritorioOrigem(null);
                jc.setTextFase2("Selecione o territorio de origem e o destino do ataque");
                jc.desbloqueiaTerritorios(jc.getTerritorios());
                jc.bloqueiaTerririosAdversarios();
                esconde1();
                esconde2();
            }
        });
        
        
    }

}
