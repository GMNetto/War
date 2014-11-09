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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "objetivo")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Objetivo.findAll", query = "SELECT o FROM Objetivo o"),
	@NamedQuery(name = "Objetivo.findByCodObjetivo", query = "SELECT o FROM Objetivo o WHERE o.codObjetivo = :codObjetivo"),
	@NamedQuery(name = "Objetivo.findByDescricao", query = "SELECT o FROM Objetivo o WHERE o.descricao = :descricao") })
public class Objetivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Cod_Objetivo")
    private Integer codObjetivo;
    @Basic(optional = false)
    @Column(name = "Descricao")
    private String descricao;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "objetivo")
    private Objterritorio objterritorio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codObjetivo")
    private Collection<Jogam> jogamCollection;
    @OneToMany(mappedBy = "codObjetivo")
    private Collection<Historico> historicoCollection;
    @JoinColumn(name = "Cod_Mundo", referencedColumnName = "Cod_Mundo")
    @ManyToOne(optional = false)
    private Mundo codMundo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "objetivo")
    private Objconqcont objconqcont;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "objetivo")
    private Collection<Objderjogador> objderjogadorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "objetivo1")
    private Collection<Objderjogador> objderjogadorCollection1;

    public Objetivo() {
    }

    public Objetivo(Integer codObjetivo) {
	this.codObjetivo = codObjetivo;
    }

    public Objetivo(Integer codObjetivo, String descricao) {
	this.codObjetivo = codObjetivo;
	this.descricao = descricao;
    }

    public Integer getCodObjetivo() {
	return codObjetivo;
    }

    public void setCodObjetivo(Integer codObjetivo) {
	this.codObjetivo = codObjetivo;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public Objterritorio getObjterritorio() {
	return objterritorio;
    }

    public void setObjterritorio(Objterritorio objterritorio) {
	this.objterritorio = objterritorio;
    }

    @XmlTransient
    public Collection<Jogam> getJogamCollection() {
	return jogamCollection;
    }

    public void setJogamCollection(Collection<Jogam> jogamCollection) {
	this.jogamCollection = jogamCollection;
    }

    @XmlTransient
    public Collection<Historico> getHistoricoCollection() {
	return historicoCollection;
    }

    public void setHistoricoCollection(Collection<Historico> historicoCollection) {
	this.historicoCollection = historicoCollection;
    }

    public Mundo getCodMundo() {
	return codMundo;
    }

    public void setCodMundo(Mundo codMundo) {
	this.codMundo = codMundo;
    }

    public Objconqcont getObjconqcont() {
	return objconqcont;
    }

    public void setObjconqcont(Objconqcont objconqcont) {
	this.objconqcont = objconqcont;
    }

    @XmlTransient
    public Collection<Objderjogador> getObjderjogadorCollection() {
	return objderjogadorCollection;
    }

    public void setObjderjogadorCollection(
	    Collection<Objderjogador> objderjogadorCollection) {
	this.objderjogadorCollection = objderjogadorCollection;
    }

    @XmlTransient
    public Collection<Objderjogador> getObjderjogadorCollection1() {
	return objderjogadorCollection1;
    }

    public void setObjderjogadorCollection1(
	    Collection<Objderjogador> objderjogadorCollection1) {
	this.objderjogadorCollection1 = objderjogadorCollection1;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (codObjetivo != null ? codObjetivo.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof Objetivo)) {
	    return false;
	}
	Objetivo other = (Objetivo) object;
	if ((this.codObjetivo == null && other.codObjetivo != null)
		|| (this.codObjetivo != null && !this.codObjetivo
			.equals(other.codObjetivo)))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "br.uff.es2.war.entity.Objetivo[ codObjetivo=" + codObjetivo
		+ " ]";
    }

}
