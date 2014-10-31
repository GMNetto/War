/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Victor
 */
@Embeddable
public class OcupacaoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Cod_Territorio")
    private int codTerritorio;
    @Basic(optional = false)
    @Column(name = "Cod_Jogador")
    private int codJogador;
    @Basic(optional = false)
    @Column(name = "Cod_Partida")
    private int codPartida;

    public OcupacaoPK() {
    }

    public OcupacaoPK(int codTerritorio, int codJogador, int codPartida) {
	this.codTerritorio = codTerritorio;
	this.codJogador = codJogador;
	this.codPartida = codPartida;
    }

    public int getCodTerritorio() {
	return codTerritorio;
    }

    public void setCodTerritorio(int codTerritorio) {
	this.codTerritorio = codTerritorio;
    }

    public int getCodJogador() {
	return codJogador;
    }

    public void setCodJogador(int codJogador) {
	this.codJogador = codJogador;
    }

    public int getCodPartida() {
	return codPartida;
    }

    public void setCodPartida(int codPartida) {
	this.codPartida = codPartida;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (int) codTerritorio;
	hash += (int) codJogador;
	hash += (int) codPartida;
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof OcupacaoPK)) {
	    return false;
	}
	OcupacaoPK other = (OcupacaoPK) object;
	if (this.codTerritorio != other.codTerritorio)
	    return false;
	if (this.codJogador != other.codJogador)
	    return false;
	if (this.codPartida != other.codPartida)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "br.uff.es2.war.entity.OcupacaoPK[ codTerritorio="
		+ codTerritorio + ", codJogador=" + codJogador
		+ ", codPartida=" + codPartida + " ]";
    }

}
