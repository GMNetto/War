/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author anacarolinegomesvargas
 * 
 */
public class TelaInicialController implements Initializable {

    @FXML
    private AnchorPane parent;
    
    @FXML
    private Button btn_joga;

    @FXML
    private ImageView img_fundo;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // load the image
        Image image = new Image("tela1.jpg");

        img_fundo.setImage(image);
        
        //trocando de tela
       
        this.btn_joga.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
               FXMLLoader fxmlLoader = new FXMLLoader();
               URL location = getClass().getResource("TelaNovoJogo.fxml");
               fxmlLoader.setLocation(location);
               try {
                    parent.getChildren().setAll((AnchorPane)fxmlLoader.load(location.openStream()));
               } catch (IOException ex) {
                    Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
               }
                
               TelaNovoJogoController tnjc=(TelaNovoJogoController)fxmlLoader.getController();
               //para passar controller
               //ec.setControlerGUI(cg);
            }
        });
        
    }

}
