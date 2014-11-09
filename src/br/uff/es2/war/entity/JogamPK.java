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
public class JogamPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Cod_Partida")
    private int codPartida;
    @Basic(optional = false)
    @Column(name = "Cod_Jogador")
    private int codJogador;

    public JogamPK() {
    }

    public JogamPK(int codPartida, int codJogador) {
	this.codPartida = codPartida;
	this.codJogador = codJogador;
    }

    public int getCodPartida() {
	return codPartida;
    }

    public void setCodPartida(int codPartida) {
	this.codPartida = codPartida;
    }

    public int getCodJogador() {
	return codJogador;
    }

    public void setCodJogador(int codJogador) {
	this.codJogador = codJogador;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (int) codPartida;
	hash += (int) codJogador;
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof JogamPK)) {
	    return false;
	}
	JogamPK other = (JogamPK) object;
	if (this.codPartida != other.codPartida)
	    return false;
	if (this.codJogador != other.codJogador)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "br.uff.es2.war.entity.JogamPK[ codPartida=" + codPartida
		+ ", codJogador=" + codJogador + " ]";
    }

}
