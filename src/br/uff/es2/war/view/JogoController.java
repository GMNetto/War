package br.uff.es2.war.view;

import br.uff.es2.war.events.Action;
import br.uff.es2.war.events.DistributeSoldiersEvent;
import br.uff.es2.war.events.ExchangeCardsEvent;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.client.ClientSidePlayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
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

    // textos da caixa de informação
    private Text txt_fase1;
    private Text txt_fase2;
    private Text txt_ataque1;
    private Text txt_ataque2;

    private Pane pane_jogadores;
    private Circle cor_jog;

    private AcaoTerritorioStrategy acaoTerr;
    private Game game;

    private int maxQuantityToDistribute;
    private int quantityBeforeDistribution;

    public JogoController(Pane pane_aloca, Pane pane_mov, Group info_bar,
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

	this.ac = new AlocaController(pane_aloca, pane_mov, raio, this);
	this.atc = new AtacaController(pane_ataca1, pane_ataca2, raio, this);
	this.jc = new JanelaInfoController(pane_sub_janela, this);

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
			List<TerritorioUI> territoriesToUnlock = new ArrayList<>();
			for (Territory territory : territoriosRecebidos) {
			    for (TerritorioUI ui : territorios) {
				if (ui.getModel().equals(territory))
				    territoriesToUnlock.add(ui);
			    }
			}
			desbloqueiaTerritorios(territoriesToUnlock);
		    }
		});
    }

    public AcaoTerritorioStrategy getAcaoTerr() {
	return acaoTerr;
    }

    public void setAcaoTerr(AcaoTerritorioStrategy acTerr) {
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
	for (TerritorioUI territorioUI : this.territorios) {
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

    public void LimpaMovimentaçao() {

	for (TerritorioUI terr : territorios) {
	    terr.setQtdMov(0);
	}

    }

    public void desbloqueiaTerritorios(List<TerritorioUI> territorios) {
	for (TerritorioUI terr : territorios) {
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
	this.game = game;
	territorios = createTerritoryUI(game.getWorld().getTerritories());
	Color minhaCor = jogador.getColor();
	cor_jog.setFill(MapaCores.getPaint(minhaCor));
	int total = 1;
	for (Player jog : game.getPlayers()) {
	    if (!jog.getColor().getName().equals(minhaCor.getName())) {
		Text tx = (Text) pane_jogadores.lookup("#txt_jog" + total);
		Circle c = (Circle) pane_jogadores.lookup("#cor_jog" + total);
		c.setFill(MapaCores.getPaint(jog.getColor()));
		c.setVisible(true);
		tx.setVisible(true);
		total++;
	    }

	}

    }

    private List<TerritorioUI> createTerritoryUI(Set<Territory> territories) {
	List<TerritorioUI> widgets = new LinkedList<TerritorioUI>();
	for (Territory territory : game.getWorld().getTerritories()) {
	    TerritorioUI widget = new TerritorioUI();
	    widget.setModel(territory);
	    widgets.add(widget);
	}
	return widgets;
    }
}
