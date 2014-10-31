/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Victor
 */
@Entity
@Table(name = "historico")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Historico.findAll", query = "SELECT h FROM Historico h"),
	@NamedQuery(name = "Historico.findByCodPartida", query = "SELECT h FROM Historico h WHERE h.codPartida = :codPartida") })
public class Historico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cod_Partida")
    private Integer codPartida;
    @JoinColumn(name = "Cod_Jogador", referencedColumnName = "Cod_Jogador")
    @ManyToOne
    private Jogador codJogador;
    @JoinColumn(name = "Cod_Objetivo", referencedColumnName = "Cod_Objetivo")
    @ManyToOne
    private Objetivo codObjetivo;
    @JoinColumn(name = "Cod_Partida", referencedColumnName = "Cod_Partida", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Partida partida;

    public Historico() {
    }

    public Historico(Integer codPartida) {
	this.codPartida = codPartida;
    }

    public Integer getCodPartida() {
	return codPartida;
    }

    public void setCodPartida(Integer codPartida) {
	this.codPartida = codPartida;
    }

    public Jogador getCodJogador() {
	return codJogador;
    }

    public void setCodJogador(Jogador codJogador) {
	this.codJogador = codJogador;
    }

    public Objetivo getCodObjetivo() {
	return codObjetivo;
    }

    public void setCodObjetivo(Objetivo codObjetivo) {
	this.codObjetivo = codObjetivo;
    }

    public Partida getPartida() {
	return partida;
    }

    public void setPartida(Partida partida) {
	this.partida = partida;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (codPartida != null ? codPartida.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof Historico)) {
	    return false;
	}
	Historico other = (Historico) object;
	if ((this.codPartida == null && other.codPartida != null)
		|| (this.codPartida != null && !this.codPartida
			.equals(other.codPartida)))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "br.uff.es2.war.entity.Historico[ codPartida=" + codPartida
		+ " ]";
    }

}
