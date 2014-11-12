/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view.widget;

import java.util.ArrayList;
import java.util.List;

import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import javafx.scene.Cursor;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * 
 * @author anacarolinegomesvargas
 */
public class TerritoryUI {
    private Circle circulo;
    private Text texto;
    private Territory model;
    private boolean bloqueado;
    private int qtdMov;
    
    private List<TerritoryUI> viz; //como integrar a representação do modelo com a da interface?
    
    public TerritoryUI() {
        this.viz = new ArrayList<TerritoryUI>();
    }
    
    public void setModel(Territory model) {
	this.model = model;
    }
    
    public void bloqueia(){
        this.circulo.setOpacity(0.5);
        this.circulo.setCursor(Cursor.CLOSED_HAND);
        this.texto.setCursor(Cursor.CLOSED_HAND);
        this.bloqueado=true;
    }
    
    public void desbloqueia(){
        this.circulo.setOpacity(1);
        this.circulo.setCursor(Cursor.HAND);
        this.texto.setCursor(Cursor.HAND);
        this.bloqueado=false;
        
    }
   
    public Text getTexto() {
	return texto;
    }

    public void setTexto(Text texto) {
	this.texto = texto;
    }

    public Circle getCirculo() {
	return circulo;
    }

    public void setCirculo(Circle circulo) {
	this.circulo = circulo;
    }

    public String getNome() {
	return model.getName();
    }

    public Player getDono() {
	return model.getOwner();
    }

    public void setDono(Player player) {
	this.model.setOwner(player);
    }

    public int getQtd() {
	return model.getSoldiers();
    }

    public void setQtd(int qtd) {
	model.setSoldiers(qtd);
	this.texto.setText(String.valueOf(qtd));
    }

    public int getQtdMov() {
        return qtdMov;
    }

    public void setQtdMov(int qtdMov) {
        this.qtdMov = qtdMov;
    }
    
    public boolean isDono(Player player){
	return model.getOwner().getColor().getName().equals(player.getColor().getName());
    }

    public void setVizinhos(List<TerritoryUI> terrs) {
        //setando os vizinhos da classe territorioUI
	for(TerritoryUI ter: terrs){
            
            for(Territory t: model.getBorders()){
                
                if(ter.getModel().equals(t)){
                    viz.add(ter);
                }
            }
        }
    }

    public List<TerritoryUI> getViz() {
	return viz;
    }

    public boolean isBloqueado() {
	return this.bloqueado;
    }
    public int getX(){
	return model.getX();
    }
    
    public int getY(){
	return model.getY();
    }
    
    public Territory getModel(){
        return this.model;
    }
    
}
