/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.events.Action;
import br.uff.es2.war.events.SetGameEvent;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.network.Messenger;
import br.uff.es2.war.network.ProtocolFactory;
import br.uff.es2.war.network.client.ClientSidePlayer;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author anacarolinegomesvargas
 * 

 */
public class TelaNovoJogoController implements Initializable {

    @FXML
    private AnchorPane parent;
    
    @FXML
    private Button btn_joga;

    @FXML
    private ImageView img_fundo;
    
    @FXML
    private Text txt_aguarde;
    
    @FXML
    private Pane pane_avancada;
    
    @FXML
    private ComboBox combo_cor;
    
    @FXML
    private CheckBox check_avancada;
    
    @FXML
    private TextField input_server;
    
    @FXML
    private TextField input_porta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // load the image
        Image image = new Image("tela2.jpg");

        img_fundo.setImage(image);
        
        combo_cor.getItems().addAll("Branco","Preto","Vermelho","Amarelo","Azul","Verde");
        
        check_avancada.selectedProperty().addListener(new ChangeListener<Boolean>() {
        public void changed(ObservableValue<? extends Boolean> ov,
            Boolean old_val, Boolean new_val) {
                pane_avancada.setVisible(new_val);
        }
    });
       
        //Iniciando comunicação com servidor
       
        this.btn_joga.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                txt_aguarde.setVisible(true);
                
                Socket server = null;
                try {
                    server = new Socket(input_server.getText(), Integer.valueOf(input_porta.getText()));
                } catch (IOException ex) {
                    Logger.getLogger(TelaNovoJogoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //iniciando comunicação com servidor
                final ClientSidePlayer player = new ClientSidePlayer((Messenger) server, ProtocolFactory.defaultJSONClientSideProtocol());
                // informando cor do jogador
                player.chooseColor(new Color((String) combo_cor.getValue()));
                
                player.getEvents().subscribe(SetGameEvent.class, new Action<SetGameEvent>(){
                    @Override
                    public void onAction(SetGameEvent args){
                        //trocar de tela e passar o player
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        URL location = getClass().getResource("TelaJogo.fxml");
                        fxmlLoader.setLocation(location);
                        try {
                            parent.getChildren().setAll((AnchorPane)fxmlLoader.load(location.openStream()));
                        } catch (IOException ex) {
                            Logger.getLogger(TelaNovoJogoController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                
                        TelaJogoController tjc=(TelaJogoController)fxmlLoader.getController();
                        
                        tjc.setPlayer(player);
                    }
                });
            }
        });
    }

}
