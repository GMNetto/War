package br.uff.es2.war.view;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Paint;
import br.uff.es2.war.model.Color;

public final class ColorMap{
    
    private static final Map<Color, Paint> map = new HashMap<>();
    
    static{
	map.put(new Color("Branco"), Paint.valueOf("white"));
	map.put(new Color("Preto"), Paint.valueOf("gray"));
	map.put(new Color("Vermelho"), Paint.valueOf("red"));
	map.put(new Color("Amarelo"), Paint.valueOf("yellow"));
	map.put(new Color("Azul"), Paint.valueOf("aqua"));
	map.put(new Color("Verde"), Paint.valueOf("green"));
    }
    
    public static Paint getPaint(Color color){
	return map.get(color);
    }
}
