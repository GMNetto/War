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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * @author anacarolinegomesvargas
 */
public class PopUpController {
    
    private Pane pane_sub_janela;
    private Pane pane_obj;
    private Pane pane_info;
    private Pane pane_cartas;
    private Button btn_janela_x;
    private Button btn_trocar;
    private Text txt_ataca1;
    private Text txt_ataca2;
    private List<Card> cartas;
    private List<Card> cartasTroca;
    private GameController2 jc;

    public PopUpController(Pane pane_sub_janela, final GameController2 jc) {
        this.jc=jc;
        this.pane_sub_janela=pane_sub_janela;
        this.pane_obj = (Pane) pane_sub_janela.lookup("#pane_obj");
        this.pane_info = (Pane) pane_sub_janela.lookup("#pane_info");
        this.pane_cartas = (Pane) pane_sub_janela.lookup("#pane_cartas");
        this.btn_janela_x = (Button) pane_sub_janela.lookup("#btn_janela_x");
        
        this.btn_trocar = (Button) pane_sub_janela.lookup("#btn_trocar");
        
        esconde();
        
        this.btn_janela_x.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                esconde();
            }
        });

        cartas=new LinkedList<Card>();
        cartasTroca=new LinkedList<Card>();
        
        
        this.btn_trocar.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                if(cartasTroca.size()==3){
                    //verificar se as cartas podem ser trocadas
                    int circulo=0,quadrado=0,triangulo=0,coringa=0;
                    for(Card carta: cartasTroca){
                        switch(carta.getFigure()){
                            case 0:coringa++;break;
                            case 1:circulo++;break;
                            case 2:triangulo++;break;
                            case 3:quadrado++;break;
                        }
                    }
                    if(circulo==3||triangulo==3||quadrado==3||(circulo==1&&triangulo==1&&quadrado==1)||
                            (circulo+coringa)==3||(triangulo+coringa)==3||(quadrado+coringa)==3||
                            (circulo==1&&triangulo==1&&coringa==1)||
                            (coringa==1&&triangulo==1&&quadrado==1)||
                            (circulo==1&&coringa==1&&quadrado==1)){
                        //envia troca para o servidor
                        jc.getPlayer().exchangeCards(cartasTroca);
                        //apaga cartas trocadas
                        for(Card carta:cartasTroca){
                            cartas.remove(carta);
                        }
                        cartasTroca.clear();
                        //atualiza exibicão das cartas
                        atualizaCartas();
                        
                    }
                }
            }
        });
        
        //Pegar cartas do jogo
        /*
        jc.getPlayer().getEvents().subscribe(AddCardEvent.class,
            new Action<AddCardEvent>() {
		@Override
                    public void onAction(AddCardEvent args) {
                        cartas.add(args.getCard());
                        atualizaCartas();
                        
                    }
        });
        
        
        //verificando selecão de cartas
        final CheckBox check_card1=(CheckBox) pane_cartas.lookup("#check_card1");
        check_card1.selectedProperty().addListener(
		new ChangeListener<Boolean>() {
		    public void changed(ObservableValue<? extends Boolean> ov,
			    Boolean old_val, Boolean new_val) {
                        
                        selecionaCarta(new_val,0,check_card1);
		    }
		});
        
        final CheckBox check_card2=(CheckBox) pane_cartas.lookup("#check_card2");
        check_card2.selectedProperty().addListener(
		new ChangeListener<Boolean>() {
		    public void changed(ObservableValue<? extends Boolean> ov,
			    Boolean old_val, Boolean new_val) {
			selecionaCarta(new_val,1,check_card2);
		    }
		});
        
        final CheckBox check_card3=(CheckBox) pane_cartas.lookup("#check_card3");
        check_card3.selectedProperty().addListener(
		new ChangeListener<Boolean>() {
		    public void changed(ObservableValue<? extends Boolean> ov,
			    Boolean old_val, Boolean new_val) {
			selecionaCarta(new_val,2,check_card3);
		    }
		});
        final CheckBox check_card4=(CheckBox) pane_cartas.lookup("#check_card4");
        check_card4.selectedProperty().addListener(
		new ChangeListener<Boolean>() {
		    public void changed(ObservableValue<? extends Boolean> ov,
			    Boolean old_val, Boolean new_val) {
			selecionaCarta(new_val,3,check_card4);
		    }
		});
        
        final CheckBox check_card5=(CheckBox) pane_cartas.lookup("#check_card5");
        check_card5.selectedProperty().addListener(
		new ChangeListener<Boolean>() {
		    public void changed(ObservableValue<? extends Boolean> ov,
			    Boolean old_val, Boolean new_val) {
			selecionaCarta(new_val,4,check_card5);
		    }
		});
		*/
    }
    
    public void selecionaCarta(Boolean new_val,int n, CheckBox check){
        if(new_val && cartasTroca.size()<3){
            //pode selecionar mais uma carta
            cartasTroca.add(cartas.get(n));
        }
        if(new_val && cartasTroca.size()==3){
            //bloquear novas selecões
            check.setSelected(false);
        }
        if(!new_val){
            //deselecionado a carta
            cartasTroca.remove(cartas.get(n));
        }
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