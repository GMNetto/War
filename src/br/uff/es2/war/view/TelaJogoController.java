/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.events.SetGameEvent;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.client.ClientSidePlayer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
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
import javafx.scene.text.TextAlignment;

/**
 * 
 * @author anacarolinegomesvargas
 * 
 *         O objetivo dessa classe é controlar esxusivamente as ações da tela do
 *         jogo indemendetemente da integração com o modelo, para resaizar a
 *         comunicação com o modelo exite outra classe responsável para isso
 *         (JogoController)
 */
public class TelaJogoController implements Initializable {

    @FXML
    private Pane pane_map;
    @FXML
    private Parent parent;

    // botao para mudança de estado do jogo
    @FXML
    private Button btn_prox;

    @FXML
    private Button btn_carta;
    @FXML
    private Button btn_obj;
    @FXML
    private Button btn_troca;

    // painel de alocação
    @FXML
    private Pane pane_aloca;
    @FXML
    private Pane pane_mov;
    // painel de ataque
    @FXML
    private Pane pane_ataca1;
    @FXML
    private Pane pane_ataca2;
    // janela modal
    @FXML
    private Pane pane_sub_janela;

    @FXML
    private ImageView img_fundo;
    @FXML
    private ImageView img_fundo2;

    @FXML
    private Group group_info_bar;

    // controlador responsável por se comunicar com o modelo e interagir com a
    // view
    private JogoController gameController;

    private void criarCirculo(final TerritorioUI terr) {
	int x = terr.getX();
	int y = terr.getY();
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
	circle.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent arg0) {
		if (!terr.isBloqueado()) {
		    gameController.getAcaoTerr().AcaoBotao(terr);
		}

	    }
	});
	pane_map.getChildren().add(circle);

	Text text = new Text();
	text.setFont(new Font(gameController.getRaio()));
	text.setText(terr.getQtd() + "");
	text.setX(x - 4 - gameController.getRaio() / 2);
	text.setY(y + 2);
	text.setTextAlignment(TextAlignment.CENTER);
	text.setWrappingWidth(gameController.getRaio() * 1.5);
	text.setCursor(Cursor.HAND);
	text.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent arg0) {
		if (!terr.isBloqueado()) {
		    gameController.getAcaoTerr().AcaoBotao(terr);
		}

	    }
	});
	pane_map.getChildren().add(text);

	terr.setCirculo(circle);
	terr.setTexto(text);

    }

    private void desenhaTerritorios() {
	for(TerritorioUI ui : gameController.getTerritorios()){
	    criarCirculo(ui);
	    ui.getCirculo().setFill(MapaCores.getPaint(ui.getDono().getColor()));
	}
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// load the image
	Image image = new Image("war1.jpg");

	img_fundo.setImage(image);
	Image image2 = new Image("tela2.jpg");

	img_fundo2.setImage(image2);

	this.gameController = new JogoController(pane_aloca, pane_mov,
		group_info_bar, pane_ataca1, pane_ataca2, pane_sub_janela);

	

	// alterando fase do jogo
	this.btn_prox.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
		gameController.setAcaoTerr(gameController.getAcaoTerr()
			.ProxFase());
	    }
	});

	this.btn_carta.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
		gameController.getJanelaController().mostra();
		gameController.getJanelaController().mostraCartas();
	    }
	});

	this.btn_obj.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
		gameController.getJanelaController().mostra();
		gameController.getJanelaController().mostraObj();

	    }
	});

	this.btn_troca.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
		gameController.getJanelaController().mostra();
		gameController.getJanelaController().mostraInfo();

	    }
	});
    }

    public void setPlayer(ClientSidePlayer player) {
	gameController.setPlayer(player);
    }
    
    public void setGame(Game game){
	gameController.setGame(game);
	desenhaTerritorios();
	gameController.setAcaoTerr(new AcaoTerritorioAloca(gameController));
    }
}
