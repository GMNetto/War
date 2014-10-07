/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testejogo;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author anacarolinegomesvargas
 */
public class TerritorioUI {
    private Circle circulo;
    private Text texto;
    private String nome;
    private int dono;
    private int qtd;
    private List<TerritorioUI> viz;
    
    public TerritorioUI(Circle circulo, String nome) {
        this.nome = nome;
        this.dono = -1;
        this.qtd = 0;
        viz = new ArrayList<TerritorioUI>();
        this.circulo = circulo;
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
    
    public boolean isDono(int d){
       return (dono==d);
    }
    
    public void addViz(TerritorioUI t){
        viz.add(t);
    }

    public List<TerritorioUI> getViz() {
        return viz;
    }
    
    

}
