/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import br.uff.es2.war.events.Action;
import br.uff.es2.war.events.AddCardEvent;
import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.phases.ReceiveSoldiersPhase;
import java.util.ArrayList;
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
    
    private List<Card> cartas;
    private List<Card> cartasTroca;
    
    private GameController2 gameController2;

    public PopUpController(Pane pane_sub_janela, final GameController2 gameCont2) {
        this.gameController2=gameCont2;
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
                //quando o botão de troca está visível, sair manda mensagem para o servidor
                //cliente não quer realizar a troca
                if(pane_cartas.isVisible()&&btn_trocar.isVisible()){
                    gameController2.getPlayer().exchangeCards(new ArrayList<Card>());
                }
                
                esconde();
            }
        });

        cartas=new LinkedList<Card>();
        cartasTroca=new LinkedList<Card>();
        
        
        this.btn_trocar.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                if(ReceiveSoldiersPhase.checkExchange(cartasTroca)){
                    //verificar se as cartas podem ser trocadas
                    //envia troca para o servidor
                    gameCont2.getPlayer().exchangeCards(cartasTroca);
                    //apaga cartas trocadas
                    for(Card carta:cartasTroca){
                        cartas.remove(carta);
                    }
                    cartasTroca.clear();
                    //atualiza exibicão das cartas
                    atualizaCartas();
                    esconde();
                   
                }
            }
        });
        
        //Pegar cartas do jogo
        
        gameCont2.getPlayer().getEvents().subscribe(AddCardEvent.class,
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
        //não exibe o botão de troca
        btn_trocar.setVisible(false);
    }

    public List<Card> getCartas() {
        return cartas;
    }

    public List<Card> getCartasTroca() {
        return cartasTroca;
    }
    
    public void bloqueiaParaTroca(){
        //é obrigatório realizar a troca, bloqueia até ser ativado o trocar
        trocarCartas();
        btn_janela_x.setVisible(false);
        
    }
    
    public void trocarCartas(){
        //libera o botão para trocar cartas
        btn_trocar.setVisible(true);
    }
    
    
}