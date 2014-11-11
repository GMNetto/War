/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.events.Action;
import br.uff.es2.war.events.ChooseColorEvent;
import br.uff.es2.war.events.SetGameEvent;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.network.Messenger;
import br.uff.es2.war.network.ProtocolFactory;
import br.uff.es2.war.network.TCPMessenger;
import br.uff.es2.war.network.client.ClientSidePlayer;
import br.uff.es2.war.network.client.ClientSideProtocol;
import br.uff.es2.war.network.server.ServerSideProtocol;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
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
    private Button btn_conecta;

    @FXML
    private ImageView img_fundo;

    @FXML
    private Text txt_aguarde;
    @FXML
    private Text txt_erro;

    @FXML
    private Pane pane_avancada;

    @FXML
    private Pane pane_cor;

    @FXML
    private ComboBox combo_cor;

    @FXML
    private CheckBox check_avancada;

    @FXML
    private TextField input_server;

    @FXML
    private TextField input_porta;

    private ClientSidePlayer jogador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
	Image image = new Image("tela2.jpg");
	img_fundo.setImage(image);
	check_avancada.selectedProperty().addListener(
		new ChangeListener<Boolean>() {
		    public void changed(ObservableValue<? extends Boolean> ov,
			    Boolean old_val, Boolean new_val) {
			pane_avancada.setVisible(new_val);
		    }
		});

	this.btn_conecta.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		txt_aguarde.setVisible(true);
		String endereco = input_server.getText();
		int porta = Integer.parseInt(input_porta.getText());
		try {
		    Messenger mensageiro = conectarAoServidor(endereco, porta);
		    ClientSideProtocol protocolo = ProtocolFactory
			    .defaultJSONClientSideProtocol();
		    jogador = new ClientSidePlayer(mensageiro, protocolo);
		    jogador.getEvents().subscribe(ChooseColorEvent.class,
			    new Action<ChooseColorEvent>() {
				@Override
				public void onAction(ChooseColorEvent args) {
				    mostrarBotaoJogar(args.getColors());
				}
			    });
		    btn_conecta.setVisible(false);
		    txt_erro.setVisible(false);
		} catch (IOException e) {
		    txt_erro.setVisible(true);
		    txt_aguarde.setVisible(false);
		}
	    }
	});

	this.btn_joga.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		btn_joga.setDisable(true);
		jogador.chooseColor(getCorEscolhida());
		jogador.getEvents().subscribe(SetGameEvent.class,
			new Action<SetGameEvent>() {
			    @Override
			    public void onAction(SetGameEvent args) {
				iniciarTelaJogo(jogador, args.getGame());
			    }
			});
		txt_erro.setVisible(false);
	    }
	});
    }

    private void mostrarBotaoJogar(final Color[] colors) {
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		check_avancada.setVisible(false);
		pane_avancada.setVisible(false);
		btn_joga.setVisible(true);
		Object[] nomes = new Object[colors.length];
		for (int i = 0; i < nomes.length; i++)
		    nomes[i] = colors[i].getName();
		combo_cor.getItems().addAll(nomes);
		pane_cor.setVisible(true);
	    }
	});
    }

    private Messenger conectarAoServidor(String endereco, int porta)
	    throws IOException {
	Socket server = new Socket(endereco, porta);
	return new TCPMessenger(server);
    }

    private Color getCorEscolhida() {
	return new Color(combo_cor.getValue().toString());
    }

    private void iniciarTelaJogo(final ClientSidePlayer jogador, final Game game) {
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		URL location = getClass().getResource("TelaJogo.fxml");
		fxmlLoader.setLocation(location);
		try {
		    parent.getChildren()
			    .setAll((AnchorPane) fxmlLoader.load(location
				    .openStream()));
		} catch (IOException ex) {
		    Logger.getLogger(TelaNovoJogoController.class.getName())
			    .log(Level.SEVERE, null, ex);
		}
		TelaJogoController tjc = (TelaJogoController) fxmlLoader
			.getController();
		tjc.setPlayer(jogador);
		tjc.setGame(game);
	    }
	});
    }
}
