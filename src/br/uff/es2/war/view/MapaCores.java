package br.uff.es2.war.view;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Paint;
import br.uff.es2.war.model.Color;

public final class MapaCores{
    
    private static final Map<Color, Paint> mapa = new HashMap<>();
    
    static{
	mapa.put(new Color("Branco"), Paint.valueOf("white"));
	mapa.put(new Color("Preto"), Paint.valueOf("gray"));
	mapa.put(new Color("Vermelho"), Paint.valueOf("red"));
	mapa.put(new Color("Amarelo"), Paint.valueOf("yellow"));
	mapa.put(new Color("Azul"), Paint.valueOf("aqua"));
	mapa.put(new Color("Verde"), Paint.valueOf("green"));
    }
    
    public static Paint getPaint(Color cor){
	return mapa.get(cor);
    }
}
