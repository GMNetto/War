/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author anacarolinegomesvargas
 */
public class JogoController implements Initializable {
    
    private int raio=10;
    private int jog =1;
    @FXML
    private Pane pane_map;
    
    //painel de alocação
    @FXML
    private Pane pane_aloca;
    @FXML
    private Button btn_aloca_mais;
    @FXML
    private Button btn_aloca_cancel;
    @FXML
    private Button btn_aloca_menos;
    @FXML
    private Button btn_aloca_ok;
    
    private AlocaController ac;

    private List<TerritorioUI> terr ;
    
    private void criarCirculo(final TerritorioUI t,int x,int y){
         final Circle circle = new Circle();
        circle.setRadius(raio);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setStroke(Paint.valueOf("Black"));
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(1.0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0));
        
        circle.setEffect(dropShadow);
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseClicked(
            new EventHandler<MouseEvent>(){
 
                @Override
                public void handle(MouseEvent arg0) {
                    
                        ac.setTerritorio(t);
                        ac.centraliza(circle.getCenterX(), circle.getCenterY());
                        ac.mostra();
                   
                    
            }
        });
        pane_map.getChildren().add(circle);
        
        Text text = new Text();
        text.setFont(new Font(raio));
        text.setText("0");
        text.setX(x-raio/2);
        text.setY(y);
        text.setCursor(Cursor.HAND);
        
        pane_map.getChildren().add(text);
        
        t.setCirculo(circle);
        t.setTexto(text);
        
    }

    
    private void inicializarMapa(){
        
        terr = new ArrayList<TerritorioUI>();
        terr.add(new TerritorioUI(null,"America do norte"));
        terr.add(new TerritorioUI(null,"America do Sul"));
        terr.add(new TerritorioUI(null,"Europa"));
        terr.add(new TerritorioUI(null,"África"));
        terr.add(new TerritorioUI(null,"Ásia"));
        terr.add(new TerritorioUI(null,"Oceania"));
        
        criarCirculo(terr.get(0),100,100);
        terr.get(0).setDono(0);
        terr.get(0).setQtd(0);
        terr.get(0).getCirculo().setFill(Paint.valueOf("RED"));
        
        criarCirculo(terr.get(1),170,300);
        terr.get(1).setDono(1);
        terr.get(1).setQtd(0);
        terr.get(1).getCirculo().setFill(Paint.valueOf("AQUA"));
        
        criarCirculo(terr.get(2),450,100);
        terr.get(2).setDono(3);
        terr.get(2).setQtd(0);
        terr.get(2).getCirculo().setFill(Paint.valueOf("GREEN"));
        
        
        criarCirculo(terr.get(3),430,250);
        terr.get(3).setDono(2);
        terr.get(3).setQtd(0);
        terr.get(3).getCirculo().setFill(Paint.valueOf("AQUA"));
        
        criarCirculo(terr.get(4),580,100);
        terr.get(4).setDono(3);
        terr.get(4).setQtd(0);
        terr.get(4).getCirculo().setFill(Paint.valueOf("GREEN"));
        
        criarCirculo(terr.get(5),680,400);
        terr.get(5).setDono(0);
        terr.get(5).setQtd(0);
        terr.get(5).getCirculo().setFill(Paint.valueOf("RED"));
        
        
    }
  
    public void desbloqueiaTerritorios(List<TerritorioUI> territorios){
        
    }
    public void bloqueiaTerririosAdversarios(){
        //bloqueia territorios que não pertencem ao usuário
        // Utilizado para a fase de alocação
    }
    
    public void bloqueiaTerririosNaoVizinhos(){
        //bloqueia territorios que não são vizinhos e territorios que pertencem ao usuário
        // Utilizado para a fase de ataque
    }
    
    public void bloqueiaTerririosNaoVizinhosAdversarios(){
        //bloqueia territorios que não são vizinhos e territorios que pertencem não pertencem ao usuário
        // Utilizado para a fase de moviementação
    
    }
    
    public void initialize(URL url, ResourceBundle rb){
        // load the image
         Image image = new Image("war.jpg");
 
         // simple displays ImageView the image as is
         ImageView iv1 = new ImageView();
         iv1.setImage(image);

       
        pane_map.getChildren().add(iv1);
        inicializarMapa();
        ac= new AlocaController(pane_aloca,btn_aloca_mais,btn_aloca_cancel,btn_aloca_menos,btn_aloca_ok,raio);
         
    } 

    
}
