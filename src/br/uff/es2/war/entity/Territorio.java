/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Victor
 */
@Entity
@Table(name = "territorio")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Territorio.findAll", query = "SELECT t FROM Territorio t"),
	@NamedQuery(name = "Territorio.findByCodTerritorio", query = "SELECT t FROM Territorio t WHERE t.codTerritorio = :codTerritorio"),
	@NamedQuery(name = "Territorio.findByNome", query = "SELECT t FROM Territorio t WHERE t.nome = :nome"),
	@NamedQuery(name = "Territorio.findByPosicaoX", query = "SELECT t FROM Territorio t WHERE t.posicaoX = :posicaoX"),
	@NamedQuery(name = "Territorio.findByPosicaoY", query = "SELECT t FROM Territorio t WHERE t.posicaoY = :posicaoY") })
public class Territorio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cod_Territorio")
    private Integer codTerritorio;
    @Basic(optional = false)
    @Column(name = "Nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "Posicao_X")
    private int posicaoX;
    @Basic(optional = false)
    @Column(name = "Posicao_Y")
    private int posicaoY;
    @JoinTable(name = "fronteira", joinColumns = { @JoinColumn(name = "Cod_Local", referencedColumnName = "Cod_Territorio") }, inverseJoinColumns = { @JoinColumn(name = "Cod_Vizinho", referencedColumnName = "Cod_Territorio") })
    @ManyToMany
    private Collection<Territorio> territorioCollection;
    @ManyToMany(mappedBy = "territorioCollection")
    private Collection<Territorio> territorioCollection1;
    @OneToOne(mappedBy = "codTerritorio")
    private Carta carta;
    @JoinColumn(name = "Cod_Continente", referencedColumnName = "Cod_Continente")
    @ManyToOne(optional = false)
    private Continente codContinente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "territorio")
    private Collection<Ocupacao> ocupacaoCollection;

    public Territorio() {
    }

    public Territorio(Integer codTerritorio) {
	this.codTerritorio = codTerritorio;
    }

    public Territorio(Integer codTerritorio, String nome, int posicaoX,
	    int posicaoY) {
	this.codTerritorio = codTerritorio;
	this.nome = nome;
	this.posicaoX = posicaoX;
	this.posicaoY = posicaoY;
    }

    public Integer getCodTerritorio() {
	return codTerritorio;
    }

    public void setCodTerritorio(Integer codTerritorio) {
	this.codTerritorio = codTerritorio;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public int getPosicaoX() {
	return posicaoX;
    }

    public void setPosicaoX(int posicaoX) {
	this.posicaoX = posicaoX;
    }

    public int getPosicaoY() {
	return posicaoY;
    }

    public void setPosicaoY(int posicaoY) {
	this.posicaoY = posicaoY;
    }

    @XmlTransient
    public Collection<Territorio> getTerritorioCollection() {
	return territorioCollection;
    }

    public void setTerritorioCollection(
	    Collection<Territorio> territorioCollection) {
	this.territorioCollection = territorioCollection;
    }

    @XmlTransient
    public Collection<Territorio> getTerritorioCollection1() {
	return territorioCollection1;
    }

    public void setTerritorioCollection1(
	    Collection<Territorio> territorioCollection1) {
	this.territorioCollection1 = territorioCollection1;
    }

    public Carta getCarta() {
	return carta;
    }

    public void setCarta(Carta carta) {
	this.carta = carta;
    }

    public Continente getCodContinente() {
	return codContinente;
    }

    public void setCodContinente(Continente codContinente) {
	this.codContinente = codContinente;
    }

    @XmlTransient
    public Collection<Ocupacao> getOcupacaoCollection() {
	return ocupacaoCollection;
    }

    public void setOcupacaoCollection(Collection<Ocupacao> ocupacaoCollection) {
	this.ocupacaoCollection = ocupacaoCollection;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (codTerritorio != null ? codTerritorio.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof Territorio)) {
	    return false;
	}
	Territorio other = (Territorio) object;
	if ((this.codTerritorio == null && other.codTerritorio != null)
		|| (this.codTerritorio != null && !this.codTerritorio
			.equals(other.codTerritorio)))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "br.uff.es2.war.entity.Territorio[ codTerritorio="
		+ codTerritorio + " ]";
    }

}
