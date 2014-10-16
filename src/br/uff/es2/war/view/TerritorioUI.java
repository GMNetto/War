/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Cursor;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author anacarolinegomesvargas
 */
public class TerritorioUI {
    private Circle circulo;
    private Text texto;
    private String nome; //modelo possui
    private int dono; // modelo possui
    private int qtd; //modelo possui
    private boolean bloqueado;
    
    private List<TerritorioUI> viz; //como integrar a representação do modelo com a da interface?
    
    public TerritorioUI(Circle circulo, String nome) {
        this.nome = nome;
        this.dono = -1;
        this.qtd = 0;
        this.viz = new ArrayList<TerritorioUI>();
        this.circulo = circulo;
    }
    
    public void bloqueia(){
        this.circulo.setOpacity(0.7);
        this.circulo.setCursor(Cursor.CLOSED_HAND);
        this.bloqueado=true;
    }
    
    public void desbloqueia(){
        this.circulo.setOpacity(1);
        this.circulo.setCursor(Cursor.HAND);
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
        return nome;
    }

    public int getDono() {
        return dono;
    }

    public void setDono(int dono) {
        this.dono = dono;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
        this.texto.setText(qtd+"");
    }
    
    //comapara no modelo
    public boolean isDono(int d){
       return (dono==d);
    }
    
    public void addVizinho(TerritorioUI t){
        viz.add(t);
    }

    public List<TerritorioUI> getViz() {
        return viz;
    }
    
    

}
