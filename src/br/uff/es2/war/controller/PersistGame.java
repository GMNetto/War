/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.controller;

import br.uff.es2.war.dao.JogamJpaController;
import br.uff.es2.war.dao.OcupacaoJpaController;
import br.uff.es2.war.dao.PartidaJpaController;
import br.uff.es2.war.dao.TerritorioJpaController;
import br.uff.es2.war.entity.Jogam;
import br.uff.es2.war.entity.Objetivo;
import br.uff.es2.war.entity.Ocupacao;
import br.uff.es2.war.entity.Partida;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.objective.Objective;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Gustavo
 */
public class PersistGame {
    private Map<Territory,Integer> iDs;
    private Map<Player,Integer> iDPlayers;
    private Map<Objective,Integer> iDObjectives;
    private EntityManagerFactory factory;
    
    public PersistGame(Map<Territory,Integer> iDs,Map<Player,Integer> iDPlayers,Map<Objective,Integer> iDObjectives,EntityManagerFactory factory){
        this.iDs=iDs;
        this.iDPlayers=iDPlayers;
        this.factory=factory;
        this.iDObjectives=iDObjectives;
    }
    
    public PersistGame(EntityManagerFactory factory){
         this.factory=factory;
    }
    

    public Map<Territory, Integer> getiDs() {
        return iDs;
    }

    public void setiDs(Map<Territory, Integer> iDs) {
        this.iDs = iDs;
    }

    public Map<Player, Integer> getiDPlayers() {
        return iDPlayers;
    }

    public void setiDPlayers(Map<Player, Integer> iDPlayers) {
        this.iDPlayers = iDPlayers;
    }

    public Map<Objective, Integer> getiDObjectives() {
        return iDObjectives;
    }

    public void setiDObjectives(Map<Objective, Integer> iDObjectives) {
        this.iDObjectives = iDObjectives;
    }
    
    public void persistJogam(Player jogador,Partida match,Objective obj) throws Exception{
        JogamJpaController jJP=new JogamJpaController(factory);
        Jogam jogam=new Jogam(match.getCodPartida(),(Integer)iDPlayers.get(jogador).intValue());
        Objetivo objetivo=new Objetivo((Integer)iDObjectives.get(obj),null);
        jogam.setCodObjetivo(objetivo);
        jJP.create(jogam);
    }
    
    public void persistPartida(Partida match) throws Exception{
        PartidaJpaController pJC=new PartidaJpaController(factory);
        pJC.create(match);
    }
    
    public void persistOcupacao(Territory territory,int turn,Partida match) throws Exception{
        OcupacaoJpaController oJC=new OcupacaoJpaController(factory);
        Ocupacao ocp=new Ocupacao((Integer)iDs.get(territory), (Integer)iDPlayers.get(territory.getOwner()), match.getCodPartida());
        ocp.setTurno(turn);
        //is missing Jogam
        ocp.setJogam(null);
        oJC.create(ocp);
    }
    
}
