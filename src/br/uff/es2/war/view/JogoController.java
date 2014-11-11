/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import br.uff.es2.war.entity.Territorio;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.client.ClientSidePlayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * 
 * @author anacarolinegomesvargas
 */
public class JogoController {

    private AlocaController ac;
    private AtacaController atc;
    private JanelaInfoController jc;
    private List<TerritorioUI> territorios;
    private int raio;
    
    private ClientSidePlayer jogador;
    
    //textos da caixa de informação
    private Text txt_fase1;
    private Text txt_fase2;
    private Text txt_ataque1;
    private Text txt_ataque2;
    
    private AcaoTerritorioStrategy acaoTerr;
    private Game game;
            
    public JogoController(Pane pane_aloca, Pane pane_mov,Group info_bar,Pane pane_ataca1, Pane pane_ataca2, Pane pane_sub_janela) {
        this.raio=10;
        this.txt_fase1=(Text) info_bar.lookup("#pane_info_box").lookup("#txt_fase1");
        this.txt_fase2=(Text) info_bar.lookup("#pane_info_box").lookup("#txt_fase2");
        this.txt_ataque1=(Text) info_bar.lookup("#pane_info_box").lookup("#txt_ataque1");
        this.txt_ataque2=(Text) info_bar.lookup("#pane_info_box").lookup("#txt_ataque2");
        
        this.ac = new AlocaController(pane_aloca,pane_mov, raio, this);
        this.atc= new AtacaController(pane_ataca1, pane_ataca2, raio, this);
        this.jc= new JanelaInfoController(pane_sub_janela, this);
    }

    public ClientSidePlayer getPlayer() {
        return jogador;
    }

    public void setPlayer(ClientSidePlayer player) {
        this.jogador = player;
    }

    public AcaoTerritorioStrategy getAcaoTerr() {
	return acaoTerr;
    }

    public void setAcaoTerr(AcaoTerritorioStrategy acTerr) {
	this.acaoTerr = acTerr;
    }

    public int getMaxExercitosAloca() {
	return 10;
    }
    
    public void setTextFase(String txt,String txt2,String txt3,String txt4){
        this.txt_fase1.setText(txt);
        this.txt_fase2.setText(txt2);
        this.txt_ataque1.setText(txt3);
        this.txt_ataque2.setText(txt4);
    }
    public void setTextFase1(String txt){
        this.txt_fase1.setText(txt);
    }
    
    public void setTextFase2(String txt){
        this.txt_fase2.setText(txt);
    }
     
    public void setTextAtaque1(String txt){
        this.txt_ataque1.setText(txt);
    }
    
    public void setTextAtaque2(String txt){
        this.txt_ataque2.setText(txt);
    }
    public AlocaController getAlocaController() {
	return ac;
    }

    public AtacaController getAtacaController() {
        return atc;
    }

    public JanelaInfoController getJanelaController() {
        return jc;
    }
    
    public List<TerritorioUI> getTerritorios() {
	return territorios;
    }

    public ClientSidePlayer getJogador() {
	return jogador;
    }

    public int getRaio() {
	return raio;
    }
    
    public void LimpaMovimentaçao(){
        
        for ( TerritorioUI terr : territorios){
            terr.setQtdMov(0);
        }
        
    }
    
    public void desbloqueiaTerritorios(List<TerritorioUI> territorios){
        
        for ( TerritorioUI terr : territorios){
            terr.desbloqueia();
        }
    }

    public void bloqueiaTerritorios(List<TerritorioUI> territorios) {

	for (TerritorioUI terr : territorios) {
	    terr.bloqueia();
	}

    }

    public void bloqueiaTerririosAdversarios() {
	// bloqueia territorios que não pertencem ao usuário
	// Utilizado para a fase de alocação
	for (TerritorioUI terr : territorios) {
	    if (!terr.isDono(jogador)) {
		// territorio de adversário
		terr.bloqueia();
	    }
	}
    }

    public void bloqueiaTerririosNaoVizinhos(TerritorioUI territorio) {
	// bloqueia territorios que não são vizinhos e territorios que pertencem
	// ao usuário
	// Utilizado para a fase de ataque

	// bloqueia todos os territorios
	bloqueiaTerritorios(this.territorios);

	// agora desbloquei apenas os vizinhos necessários
	for (TerritorioUI terr : territorio.getViz()) {
	    if (!terr.isDono(jogador)) {
		// territorio vizinho e não pertence ao jogador
		terr.desbloqueia();
	    }
	}

    }

    public void bloqueiaTerririosNaoVizinhosAdversarios(TerritorioUI territorio) {
	// bloqueia territorios que não são vizinhos e territorios que pertencem
	// não pertencem ao usuário
	// Utilizado para a fase de moviementação

	// bloqueia todos os territorios
	bloqueiaTerritorios(this.territorios);

	// agora desbloqueia apenas os vizinhos necessários
	for (TerritorioUI terr : territorio.getViz()) {
	    if (terr.isDono(jogador)) {
		// territorio vizinho e pertence ao jogador
		terr.desbloqueia();
	    }
	}
    }

    public void setGame(Game game) {
	territorios = createTerritoryUI(game.getWorld().getTerritories());
	this.game = game;
    }

    private List<TerritorioUI> createTerritoryUI(Set<Territory> territories) {
	List<TerritorioUI> widgets = new LinkedList<TerritorioUI>();
	for(Territory territory : game.getWorld().getTerritories()){
	    TerritorioUI widget = new TerritorioUI();
	    widget.setModel(territory);
	    widgets.add(widget);
	}
	return widgets;
    }
}
