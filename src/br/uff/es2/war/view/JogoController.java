/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import java.net.URL;
import java.util.ResourceBundle;
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
 */
public class JogoController implements Initializable {

    
    @FXML
    private Pane pane_map;
    @FXML
    private Button btn_m2;
     @FXML
    private Parent parent;

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
    private GameViewController gameController;

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
        //circle.setCursor(Cursor.HAND);
        circle.setOnMouseClicked(
                new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent arg0) {
                        //usar state ou strategy para identificar qual ação tomar
                        
                        //ação de alocar execito
                        //ac.setTerritorioDestino(t);
                        //ac.centraliza(circle.getCenterX(), circle.getCenterY());
                        //ac.mostra();

                    }
                });
        pane_map.getChildren().add(circle);

        Text text = new Text();
        text.setFont(new Font(gameController.getRaio()));
        text.setText("0");
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
                case 1:
                    cor=Paint.valueOf("AQUA");
                case 2:
                    cor=Paint.valueOf("GREEN");
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
        
        this.gameController =new GameViewController(0,pane_aloca, btn_aloca_mais, btn_aloca_cancel, btn_aloca_menos, btn_aloca_ok);
        desenhaTerritorios();
       
    }

}
