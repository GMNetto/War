/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Victor
 */
@Entity
@Table(name = "jogadorcarta")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Jogadorcarta.findAll", query = "SELECT j FROM Jogadorcarta j"),
	@NamedQuery(name = "Jogadorcarta.findByCodJogador", query = "SELECT j FROM Jogadorcarta j WHERE j.jogadorcartaPK.codJogador = :codJogador"),
	@NamedQuery(name = "Jogadorcarta.findByCodPartida", query = "SELECT j FROM Jogadorcarta j WHERE j.jogadorcartaPK.codPartida = :codPartida"),
	@NamedQuery(name = "Jogadorcarta.findByCodCarta", query = "SELECT j FROM Jogadorcarta j WHERE j.jogadorcartaPK.codCarta = :codCarta"),
	@NamedQuery(name = "Jogadorcarta.findByTurno", query = "SELECT j FROM Jogadorcarta j WHERE j.turno = :turno") })
public class Jogadorcarta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JogadorcartaPK jogadorcartaPK;
    @Basic(optional = false)
    @Column(name = "Turno")
    private int turno;
    @JoinColumn(name = "Cod_Carta", referencedColumnName = "Cod_Carta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Carta carta;
    @JoinColumns({
	    @JoinColumn(name = "Cod_Jogador", referencedColumnName = "Cod_Jogador", insertable = false, updatable = false),
	    @JoinColumn(name = "Cod_Partida", referencedColumnName = "Cod_Partida", insertable = false, updatable = false) })
    @ManyToOne(optional = false)
    private Jogam jogam;

    public Jogadorcarta() {
    }

    public Jogadorcarta(JogadorcartaPK jogadorcartaPK) {
	this.jogadorcartaPK = jogadorcartaPK;
    }

    public Jogadorcarta(JogadorcartaPK jogadorcartaPK, int turno) {
	this.jogadorcartaPK = jogadorcartaPK;
	this.turno = turno;
    }

    public Jogadorcarta(int codJogador, int codPartida, int codCarta) {
	this.jogadorcartaPK = new JogadorcartaPK(codJogador, codPartida,
		codCarta);
    }

    public JogadorcartaPK getJogadorcartaPK() {
	return jogadorcartaPK;
    }

    public void setJogadorcartaPK(JogadorcartaPK jogadorcartaPK) {
	this.jogadorcartaPK = jogadorcartaPK;
    }

    public int getTurno() {
	return turno;
    }

    public void setTurno(int turno) {
	this.turno = turno;
    }

    public Carta getCarta() {
	return carta;
    }

    public void setCarta(Carta carta) {
	this.carta = carta;
    }

    public Jogam getJogam() {
	return jogam;
    }

    public void setJogam(Jogam jogam) {
	this.jogam = jogam;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (jogadorcartaPK != null ? jogadorcartaPK.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof Jogadorcarta)) {
	    return false;
	}
	Jogadorcarta other = (Jogadorcarta) object;
	if ((this.jogadorcartaPK == null && other.jogadorcartaPK != null)
		|| (this.jogadorcartaPK != null && !this.jogadorcartaPK
			.equals(other.jogadorcartaPK)))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "br.uff.es2.war.entity.Jogadorcarta[ jogadorcartaPK="
		+ jogadorcartaPK + " ]";
    }

}
