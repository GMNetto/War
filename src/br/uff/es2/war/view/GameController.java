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
import br.uff.es2.war.model.Game;
import br.uff.es2.war.network.client.ClientSidePlayer;
import br.uff.es2.war.view.widget.TerritoryUI;
import br.uff.es2.war.view.widget.WaitTerritoryStrategy;

/**
 * 
 * @author anacarolinegomesvargas
 * 
 *         O objetivo dessa classe é controlar esxusivamente as ações da tela do
 *         jogo indemendetemente da integração com o modelo, para resaizar a
 *         comunicação com o modelo exite outra classe responsável para isso
 *         (JogoController)
 */
public class GameController implements Initializable {

    @FXML
    private Pane pane_map;
    @FXML
    private Parent parent;

    @FXML
    private Button btn_prox;

    @FXML
    private Button btn_carta;

    @FXML
    private Button btn_obj;

    @FXML
    private Button btn_troca;

    @FXML
    private Button btn_z_mais;

    @FXML
    private Button btn_z_menos;

    @FXML
    private Pane pane_aloca;

    @FXML
    private Pane pane_mov;

    @FXML
    private Pane pane_ataca1;

    @FXML
    private Pane pane_ataca2;

    @FXML
    private Pane pane_sub_janela;

    @FXML
    private ImageView background;
    
    private Image backgroundImage;

    @FXML
    private ImageView background2;
    
    private Image backgroundImage2;

    @FXML
    private Group group_info_bar;

    @FXML
    private Text txt_obj;

    private float scaleMin;

    private GameController2 controller2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	backgroundImage = new Image("war1.jpg");
	Image backgroundImage2 = new Image("tela2.jpg");
	background.setImage(backgroundImage);
	background2.setImage(backgroundImage2);
	scaleMin = Math.max((float) (800 / backgroundImage.getWidth()),
		(float) (450 / backgroundImage.getHeight()));
	pane_map.setPrefSize(backgroundImage.getWidth(), backgroundImage.getHeight());
	controller2 = new GameController2(pane_aloca, pane_mov,
		group_info_bar, pane_ataca1, pane_ataca2, pane_sub_janela);
	addEvents();
    }
    
    private void addEvents(){
	this.btn_z_mais.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		zoomIn();
	    }
	});

	this.btn_z_menos.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
		zoomOut();
	    }
	});

	this.btn_prox.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
		controller2.setAcaoTerr(controller2.getAcaoTerr().nextPhase());
	    }
	});

	this.btn_carta.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
		controller2.getJanelaController().mostra();
		controller2.getJanelaController().mostraCartas();
	    }
	});

	this.btn_obj.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
		controller2.getJanelaController().mostra();
		controller2.getJanelaController().mostraObj();
	    }
	});

	this.btn_troca.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
		controller2.getJanelaController().mostra();
		controller2.getJanelaController().mostraInfo();

	    }
	});
    }
    
    private void zoomIn(){
	if (pane_map.getScaleX() < 1) {
	    double novo = Math.min(1, pane_map.getScaleX() + 0.1);
	    pane_map.setScaleX(novo);
	    pane_map.setScaleY(novo);
	    pane_map.setPrefSize(backgroundImage.getWidth() * novo,
		    backgroundImage.getHeight() * novo);
	}
    }
    
    private void zoomOut(){
	if (pane_map.getScaleX() > scaleMin) {
	    double novo = Math.max(scaleMin, pane_map.getScaleX() - 0.1);
	    pane_map.setScaleX(novo);
	    pane_map.setScaleY(novo);
	    pane_map.setPrefSize(backgroundImage.getWidth() * novo,
		    backgroundImage.getHeight() * novo);
	}
    }

    private void criarCirculo(final TerritoryUI terr) {
	int x = terr.getX();
	int y = terr.getY();
	final Circle circle = new Circle();
	circle.setRadius(controller2.getRaio());
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
		    controller2.getAcaoTerr().buttonAction(terr);
		}
	    }
	});
	pane_map.getChildren().add(circle);

	Text text = new Text();
	text.setFont(new Font(controller2.getRaio()));
	text.setText(terr.getQtd() + "");
	text.setX(x - 4 - controller2.getRaio() / 2);
	text.setY(y + 2);
	text.setTextAlignment(TextAlignment.CENTER);
	text.setWrappingWidth(controller2.getRaio() * 1.5);
	text.setCursor(Cursor.HAND);
	text.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent arg0) {
		if (!terr.isBloqueado()) {
		    controller2.getAcaoTerr().buttonAction(terr);
		}

	    }
	});
	pane_map.getChildren().add(text);

	terr.setCirculo(circle);
	terr.setTexto(text);
    }

    private void desenhaTerritorios() {
	for (TerritoryUI ui : controller2.getTerritorios()) {
	    criarCirculo(ui);
	    ui.getCirculo().setFill(ColorMap.getPaint(ui.getDono().getColor()));
	}
    }

    public void setPlayer(ClientSidePlayer player) {
	controller2.setPlayer(player);
	// txt_obj.setText(player.getObjective().toString());
    }

    public void setGame(Game game) {
	controller2.setGame(game);
	desenhaTerritorios();
	controller2.setAcaoTerr(new WaitTerritoryStrategy(controller2));
    }
}
