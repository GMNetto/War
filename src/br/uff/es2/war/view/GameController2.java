package br.uff.es2.war.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import br.uff.es2.war.events.Action;
import br.uff.es2.war.events.DistributeSoldiersEvent;
import br.uff.es2.war.events.ExchangeCardsEvent;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.client.ClientSidePlayer;
import br.uff.es2.war.view.widget.AlloctTerritoryController;
import br.uff.es2.war.view.widget.AttackTerritoryController;
import br.uff.es2.war.view.widget.TerritoryUI;
import br.uff.es2.war.view.widget.TerritoryUIStrategy;

/**
 * @author anacarolinegomesvargas
 */
public class GameController2 {

    private AlloctTerritoryController ac;
    private AttackTerritoryController atc;
    private PopUpController jc;
    private List<TerritoryUI> territorios;
    private int raio;

    private ClientSidePlayer jogador;

    // textos da caixa de informação
    private Text txt_fase1;
    private Text txt_fase2;
    private Text txt_ataque1;
    private Text txt_ataque2;

    private Pane pane_jogadores;
    private Circle cor_jog;

    private TerritoryUIStrategy acaoTerr;
    private Game game;

    private int maxQuantityToDistribute;
    private int quantityBeforeDistribution;

    public GameController2(Pane pane_aloca, Pane pane_mov, Group info_bar,
	    Pane pane_ataca1, Pane pane_ataca2, Pane pane_sub_janela) {
	this.raio = 10;
	this.txt_fase1 = (Text) info_bar.lookup("#pane_info_box").lookup(
		"#txt_fase1");
	this.txt_fase2 = (Text) info_bar.lookup("#pane_info_box").lookup(
		"#txt_fase2");
	this.txt_ataque1 = (Text) info_bar.lookup("#pane_info_box").lookup(
		"#txt_ataque1");
	this.txt_ataque2 = (Text) info_bar.lookup("#pane_info_box").lookup(
		"#txt_ataque2");

	this.cor_jog = (Circle) info_bar.lookup("#cor_jog");
	this.pane_jogadores = (Pane) info_bar.lookup("#pane_jogadores");

	this.ac = new AlloctTerritoryController(pane_aloca, pane_mov, raio, this);
	this.atc = new AttackTerritoryController(pane_ataca1, pane_ataca2, raio, this);
	this.jc = new PopUpController(pane_sub_janela, this);

    }

    public ClientSidePlayer getPlayer() {
	return jogador;
    }

    public void setPlayer(final ClientSidePlayer player) {
	this.jogador = player;
	player.getEvents().subscribe(ExchangeCardsEvent.class,
		new Action<ExchangeCardsEvent>() {

		    @Override
		    public void onAction(ExchangeCardsEvent args) {
			player.exchangeCards(player.getCards());
		    }
		});

	player.getEvents().subscribe(DistributeSoldiersEvent.class,
		new Action<DistributeSoldiersEvent>() {

		    @Override
		    public void onAction(DistributeSoldiersEvent args) {
			quantityBeforeDistribution = getTotalArmys();
			maxQuantityToDistribute = args.getQuantity();
			Set<Territory> territoriosRecebidos = args
				.getTerritories();
			List<TerritoryUI> territoriesToUnlock = new ArrayList<>();
			for (Territory territory : territoriosRecebidos) {
			    for (TerritoryUI ui : territorios) {
				if (ui.getModel().equals(territory))
				    territoriesToUnlock.add(ui);
			    }
			}
			desbloqueiaTerritorios(territoriesToUnlock);
		    }
		});
    }

    public TerritoryUIStrategy getAcaoTerr() {
	return acaoTerr;
    }

    public void setAcaoTerr(TerritoryUIStrategy acTerr) {
	this.acaoTerr = acTerr;
    }

    public int getMaxExercitosAloca() {
	return quantityBeforeDistribution - getTotalArmys();
    }

    public void setMaxQuantityToDistribute(int quantityToDistribute) {
	this.maxQuantityToDistribute = quantityToDistribute;
    }

    public int getTotalArmys() {
	int counter = 0;
	for (TerritoryUI territorioUI : this.territorios) {
	    counter += territorioUI.getQtd();
	}
	return counter;
    }

    public void setTextFase(String txt, String txt2, String txt3, String txt4) {
	this.txt_fase1.setText(txt);
	this.txt_fase2.setText(txt2);
	this.txt_ataque1.setText(txt3);
	this.txt_ataque2.setText(txt4);
    }

    public void setTextFase1(String txt) {
	this.txt_fase1.setText(txt);
    }

    public void setTextFase2(String txt) {
	this.txt_fase2.setText(txt);
    }

    public void setTextAtaque1(String txt) {
	this.txt_ataque1.setText(txt);
    }

    public void setTextAtaque2(String txt) {
	this.txt_ataque2.setText(txt);
    }

    public AlloctTerritoryController getAlocaController() {
	return ac;
    }

    public AttackTerritoryController getAtacaController() {
	return atc;
    }

    public PopUpController getJanelaController() {
	return jc;
    }

    public List<TerritoryUI> getTerritorios() {
	return territorios;
    }

    public ClientSidePlayer getJogador() {
	return jogador;
    }

    public int getRaio() {
	return raio;
    }

    public void LimpaMovimentaçao() {

	for (TerritoryUI terr : territorios) {
	    terr.setQtdMov(0);
	}

    }

    public void desbloqueiaTerritorios(List<TerritoryUI> territorios) {
	for (TerritoryUI terr : territorios) {
	    terr.desbloqueia();
	}
    }

    public void bloqueiaTerritorios(List<TerritoryUI> territorios) {
	for (TerritoryUI terr : territorios) {
	    terr.bloqueia();
	}
    }

    public void bloqueiaTerririosAdversarios() {
	// bloqueia territorios que não pertencem ao usuário
	// Utilizado para a fase de alocação
	for (TerritoryUI terr : territorios) {
	    if (!terr.isDono(jogador)) {
		// territorio de adversário
		terr.bloqueia();
	    }
	}
    }

    public void bloqueiaTerririosNaoVizinhos(TerritoryUI territorio) {
	// bloqueia territorios que não são vizinhos e territorios que pertencem
	// ao usuário
	// Utilizado para a fase de ataque

	// bloqueia todos os territorios
	bloqueiaTerritorios(this.territorios);

	// agora desbloquei apenas os vizinhos necessários
	for (TerritoryUI terr : territorio.getViz()) {
	    if (!terr.isDono(jogador)) {
		// territorio vizinho e não pertence ao jogador
		terr.desbloqueia();
	    }
	}

    }

    public void bloqueiaTerririosNaoVizinhosAdversarios(TerritoryUI territorio) {
	// bloqueia territorios que não são vizinhos e territorios que pertencem
	// não pertencem ao usuário
	// Utilizado para a fase de moviementação

	// bloqueia todos os territorios
	bloqueiaTerritorios(this.territorios);

	// agora desbloqueia apenas os vizinhos necessários
	for (TerritoryUI terr : territorio.getViz()) {
	    if (terr.isDono(jogador)) {
		// territorio vizinho e pertence ao jogador
		terr.desbloqueia();
	    }
	}
    }

    public void setGame(Game game) {
	this.game = game;
	territorios = createTerritoryUI(game.getWorld().getTerritories());
	Color minhaCor = jogador.getColor();
	cor_jog.setFill(ColorMap.getPaint(minhaCor));
	int total = 1;
	for (Player jog : game.getPlayers()) {
	    if (!jog.getColor().getName().equals(minhaCor.getName())) {
		Text tx = (Text) pane_jogadores.lookup("#txt_jog" + total);
		Circle c = (Circle) pane_jogadores.lookup("#cor_jog" + total);
		c.setFill(ColorMap.getPaint(jog.getColor()));
		c.setVisible(true);
		tx.setVisible(true);
		total++;
	    }

	}

    }

    private List<TerritoryUI> createTerritoryUI(Set<Territory> territories) {
	List<TerritoryUI> widgets = new LinkedList<TerritoryUI>();
	for (Territory territory : game.getWorld().getTerritories()) {
	    TerritoryUI widget = new TerritoryUI();
	    widget.setModel(territory);
	    widgets.add(widget);
	}
	return widgets;
    }
}
