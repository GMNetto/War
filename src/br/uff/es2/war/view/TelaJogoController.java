/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
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
 * 
 * O objetivo dessa classe é controlar esxusivamente as ações da tela do jogo indemendetemente da
 * integração com o modelo, para resaizar a comunicação com o modelo exite outra classe responsável 
 * para isso (JogoController)
 */
public class TelaJogoController implements Initializable {

    
    @FXML
    private Pane pane_map;
     @FXML
    private Parent parent;
     
    //botoes para teste, mudança de estado do jogo
    @FXML
    private Button btn_m1;
    @FXML
    private Button btn_m2;
    @FXML
    private Button btn_m3;

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
    
    // controlador responsável por se comunicar com o modelo e interagir com a view
    private JogoController gameController;

    private void criarCirculo(final TerritorioUI terr, int x, int y) {
        final Circle circle = new Circle();
        circle.setRadius(gameController.getRaio());
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
                new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent arg0) {
                        if(!terr.isBloqueado()){
                            gameController.getAcaoTerr().AcaoBotao(terr);
                        }

                    }
                });
        pane_map.getChildren().add(circle);

        Text text = new Text();
        text.setFont(new Font(gameController.getRaio()));
        text.setText(terr.getQtd()+"");
        text.setX(x - gameController.getRaio() / 2);
        text.setY(y);
        text.setCursor(Cursor.HAND);

        pane_map.getChildren().add(text);

        terr.setCirculo(circle);
        terr.setTexto(text);

    }

    private void desenhaTerritorios() {

        for (int i=0;i<gameController.getTerritorios().size();i++){
            
            //switch provisório, apenas para teste
            int x = 0,y = 0;
            switch(i){
                case 0:
                    x=100;y=100;break;
                case 1:
                    x=170;y=300;break;
                case 2:
                    x=450;y=100;break;
                case 3:
                    x=430;y=250;break;
                case 4:
                    x=580;y=100;break;
                case 5:
                    x=680;y=400;break;
            }
            
            //outro switch provisório, apenas para teste
            Paint cor = null;
            switch(gameController.getTerritorios().get(i).getDono()){
                case 0:
                    cor=Paint.valueOf("RED");
                    break;
                case 1:
                    cor=Paint.valueOf("AQUA");
                    break;
                case 2:
                    cor=Paint.valueOf("GREEN");
                    break;
            }
            
            criarCirculo(gameController.getTerritorios().get(i), x, y);
            gameController.getTerritorios().get(i).getCirculo().setFill(cor);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // load the image
        Image image = new Image("war.jpg");

        // simple displays ImageView the image as is
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        
        
        pane_map.getChildren().add(iv1);
        
        this.gameController =new JogoController(0,pane_aloca, btn_aloca_mais, btn_aloca_cancel, btn_aloca_menos, btn_aloca_ok);
        desenhaTerritorios();
        
        gameController.setAcaoTerr(new AcaoTerritorioMovimenta(gameController));
        
        
        //alterando fase do jogo
        this.btn_m1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("modo de alocacao");
                gameController.setAcaoTerr(new AcaoTerritorioAloca(gameController));
            }
        });
        
        this.btn_m2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("modo de ataque");
                gameController.setAcaoTerr(new AcaoTerritorioAloca(gameController));
            }
        });
        
        this.btn_m3.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("modo de movomentacao");
                gameController.setAcaoTerr(new AcaoTerritorioMovimenta(gameController));
            }
        });
       
    }

}