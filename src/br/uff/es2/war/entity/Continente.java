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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Victor
 */
@Entity
@Table(name = "continente")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Continente.findAll", query = "SELECT c FROM Continente c"),
	@NamedQuery(name = "Continente.findByCodContinente", query = "SELECT c FROM Continente c WHERE c.codContinente = :codContinente"),
	@NamedQuery(name = "Continente.findByNome", query = "SELECT c FROM Continente c WHERE c.nome = :nome"),
	@NamedQuery(name = "Continente.findByBonusTotalidade", query = "SELECT c FROM Continente c WHERE c.bonusTotalidade = :bonusTotalidade") })
public class Continente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Cod_Continente")
    private Integer codContinente;
    @Basic(optional = false)
    @Column(name = "Nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "Bonus_Totalidade")
    private int bonusTotalidade;
    @JoinTable(name = "objconqterr", joinColumns = { @JoinColumn(name = "Cod_Continente", referencedColumnName = "Cod_Continente") }, inverseJoinColumns = { @JoinColumn(name = "Cod_Objetivo", referencedColumnName = "Cod_Objetivo") })
    @ManyToMany
    private Collection<Objconqcont> objconqcontCollection;
    @JoinColumn(name = "Cod_Mundo", referencedColumnName = "Cod_Mundo")
    @ManyToOne(optional = false)
    private Mundo codMundo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codContinente")
    private Collection<Territorio> territorioCollection;

    public Continente() {
    }

    public Continente(Integer codContinente) {
	this.codContinente = codContinente;
    }

    public Continente(Integer codContinente, String nome, int bonusTotalidade) {
	this.codContinente = codContinente;
	this.nome = nome;
	this.bonusTotalidade = bonusTotalidade;
    }

    public Integer getCodContinente() {
	return codContinente;
    }

    public void setCodContinente(Integer codContinente) {
	this.codContinente = codContinente;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public int getBonusTotalidade() {
	return bonusTotalidade;
    }

    public void setBonusTotalidade(int bonusTotalidade) {
	this.bonusTotalidade = bonusTotalidade;
    }

    @XmlTransient
    public Collection<Objconqcont> getObjconqcontCollection() {
	return objconqcontCollection;
    }

    public void setObjconqcontCollection(
	    Collection<Objconqcont> objconqcontCollection) {
	this.objconqcontCollection = objconqcontCollection;
    }

    public Mundo getCodMundo() {
	return codMundo;
    }

    public void setCodMundo(Mundo codMundo) {
	this.codMundo = codMundo;
    }

    @XmlTransient
    public Collection<Territorio> getTerritorioCollection() {
	return territorioCollection;
    }

    public void setTerritorioCollection(
	    Collection<Territorio> territorioCollection) {
	this.territorioCollection = territorioCollection;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (codContinente != null ? codContinente.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof Continente)) {
	    return false;
	}
	Continente other = (Continente) object;
	if ((this.codContinente == null && other.codContinente != null)
		|| (this.codContinente != null && !this.codContinente
			.equals(other.codContinente)))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "br.uff.es2.war.entity.Continente[ codContinente="
		+ codContinente + " ]";
    }

}
