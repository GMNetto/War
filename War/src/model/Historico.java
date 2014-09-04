package model;
// Generated 04/09/2014 19:22:09 by Hibernate Tools 3.6.0



/**
 * Historico generated by hbm2java
 */
public class Historico  implements java.io.Serializable {


     private int codPartida;
     private Jogador jogador;
     private Partida partida;
     private Objetivo objetivo;

    public Historico() {
    }

	
    public Historico(Partida partida) {
        this.partida = partida;
    }
    public Historico(Jogador jogador, Partida partida, Objetivo objetivo) {
       this.jogador = jogador;
       this.partida = partida;
       this.objetivo = objetivo;
    }
   
    public int getCodPartida() {
        return this.codPartida;
    }
    
    public void setCodPartida(int codPartida) {
        this.codPartida = codPartida;
    }
    public Jogador getJogador() {
        return this.jogador;
    }
    
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
    public Partida getPartida() {
        return this.partida;
    }
    
    public void setPartida(Partida partida) {
        this.partida = partida;
    }
    public Objetivo getObjetivo() {
        return this.objetivo;
    }
    
    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }




}


