/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.controller;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.uff.es2.war.entity.Cor;
import br.uff.es2.war.entity.Jogam;
import br.uff.es2.war.entity.Objetivo;
import br.uff.es2.war.entity.Ocupacao;
import br.uff.es2.war.entity.Partida;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.objective.Objective;

/**
 * 
 * @author Gustavo
 */
public class GamePersister {

    private Map<Territory, Integer> iDTerritories;
    private Map<Player, Integer> iDPlayers;
    private Map<Objective, Objetivo> iDObjectives;
    private Map<Color, Cor> iDColors;
    private EntityManager manager;

    private Game game;
    private Partida partida;
    private Set<Jogam> jogam;
    private List<Ocupacao> ocupacoes;

    public GamePersister(Map<Territory, Integer> iDTerritories, Map<Player, Integer> iDPlayers, Map<Objective, Objetivo> iDObjectives, Map<Color, Cor> iDColors, Game game, EntityManagerFactory factory) {
        this.iDTerritories = iDTerritories;
        this.iDPlayers = iDPlayers;
        this.iDObjectives = iDObjectives;
        this.iDColors = iDColors;
        this.manager = factory.createEntityManager();
        this.game = game;
        this.partida = new Partida(0);//Carregar código do banco como se fosse auto-increment
        this.ocupacoes = new LinkedList<>();
        this.jogam = new LinkedHashSet<>();
    }

    public Map<Territory, Integer> getiDs() {
	return iDTerritories;
    }

    public void setiDs(Map<Territory, Integer> iDs) {
	this.iDTerritories = iDs;
    }

    public Map<Player, Integer> getiDPlayers() {
	return iDPlayers;
    }

    public void setiDPlayers(Map<Player, Integer> iDPlayers) {
	this.iDPlayers = iDPlayers;
    }

    public Map<Objective, Objetivo> getiDObjectives() {
	return iDObjectives;
    }

    public void setiDObjectives(Map<Objective, Objetivo> iDObjectives) {
	this.iDObjectives = iDObjectives;
    }

    public Partida getPartida() {
	return partida;
    }

    public void addJogam() throws Exception {
	Jogam joga;
	for (Player player : game.getPlayers()) {
	    joga = new Jogam(partida.getCodPartida(), iDPlayers.get(player));
	    joga.setCodCor(iDColors.get(player.getColor()));
	    joga.setCodObjetivo(iDObjectives.get(player.getObjective()));
	    jogam.add(joga);
	}
    }

    public int addPartida() {
        Query query = manager.createQuery("select max(p.codPartida) from Partida as p");
        int code = ((int) query.getResultList().get(0) + 1);
        this.partida = new Partida(code, game.getStartDate(), game.getPlayers().length, game.getNumberOfTurns());
        return code;
    }

    public void addOcupacao(Territory territory) throws Exception {
	Ocupacao ocp = new Ocupacao(iDTerritories.get(territory),
		iDPlayers.get(territory.getOwner()), partida.getCodPartida());
	ocp.setTurno(game.getNumberOfTurns());
	ocp.setQntExercito(territory.getSoldiers());

	ocupacoes.add(ocp);
    }

    public void persist() {
	try {
	    manager.getTransaction().begin();
	    manager.persist(partida);

	    for (Jogam joga : jogam) {
		manager.persist(joga);
	    }

	    for (Ocupacao ocupacao : ocupacoes) {
		manager.persist(ocupacao);
	    }
	    manager.getTransaction().commit();
	} catch (Exception ex) {
	    // Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null,
	    // ex);
	    manager.getTransaction().rollback();
	}
	if (manager.isOpen()) {
	    manager.close();
	}
    }

}
