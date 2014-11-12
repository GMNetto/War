/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import br.uff.es2.war.events.Action;
import br.uff.es2.war.events.AddCardEvent;
import br.uff.es2.war.events.BeginTurnEvent;
import br.uff.es2.war.events.ExchangeCardsEvent;
import br.uff.es2.war.model.Card;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * 
 * @author anacarolinegomesvargas
 */
public class JanelaInfoController {
    private Pane pane_sub_janela;
    private Pane pane_obj;
    private Pane pane_info;
    private Pane pane_cartas;
   
    private Button btn_janela_x;
    private Text txt_ataca1;
    private Text txt_ataca2;
    
    private List<Card> cartas;
    
    private JogoController jc;
    

    public JanelaInfoController(Pane pane_sub_janela, final JogoController jc) {
        
        this.jc=jc;
        
        this.pane_sub_janela=pane_sub_janela;
        
        this.pane_obj = (Pane) pane_sub_janela.lookup("#pane_obj");
        this.pane_info = (Pane) pane_sub_janela.lookup("#pane_info");
        this.pane_cartas = (Pane) pane_sub_janela.lookup("#pane_cartas");
        this.btn_janela_x = (Button) pane_sub_janela.lookup("#btn_janela_x");
        esconde();
        
        this.btn_janela_x.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                esconde();
            }
        });
        
        cartas=new LinkedList<Card>();
        
        //Pegar cartas do jogo
        jc.getPlayer().getEvents().subscribe(AddCardEvent.class,
            new Action<AddCardEvent>() {
		@Override
                    public void onsAction(AddCardEvent args) {
                        cartas.add(args.getCard());
                        atualizaCartas();
                        
                    }
        });
        
        
       
    }
    public void atualizaCartas(){
        for(int i=1; i<=5;i++){
            Pane pane_carta=(Pane) pane_cartas.lookup("#pane_card"+i);
            pane_carta.setVisible(false);
        }
        
        for(int i=1; i<=cartas.size();i++){
            Text txt_forma=(Text) pane_cartas.lookup("#txt_form_card"+i);
            Text txt_nome=(Text) pane_cartas.lookup("#txt_nome_card"+i);
            CheckBox check=(CheckBox) pane_cartas.lookup("#check_card"+i);
            String forma="";
            switch(cartas.get(i).getFigure()){
                case 0:
                    forma="Coringa";
                    break;
                case 1:
                    forma="Circulo";
                    break;
                case 2:
                    forma="Triângulo";
                    break;
                case 3:
                    forma="Quadrado";
                    break;
            }
            txt_forma.setText(forma);
            txt_nome.setText("");
            if(cartas.get(i).getFigure()!=0){
                txt_nome.setText(cartas.get(i).getTerritory().getName());
            }
            check.setSelected(false);
            Pane pane_carta=(Pane) pane_cartas.lookup("#pane_card"+i);
            pane_carta.setVisible(true);
        }
    }
    
    public void esconde() {
	pane_sub_janela.setVisible(false);
    }
    
    public void mostra(){
        pane_sub_janela.setVisible(true);
    }
    
    public void mostraObj(){
        pane_obj.setVisible(true);
        pane_info.setVisible(false);
        pane_cartas.setVisible(false);
    }
    
    public void mostraInfo(){
        pane_obj.setVisible(false);
        pane_info.setVisible(true);
        pane_cartas.setVisible(false);
    }
    
    public void mostraCartas(){
        pane_obj.setVisible(false);
        pane_info.setVisible(false);
        pane_cartas.setVisible(true);
    }
    
}
