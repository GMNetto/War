/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Victor
 */
@Entity
@Table(name = "mundo")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Mundo.findAll", query = "SELECT m FROM Mundo m"),
	@NamedQuery(name = "Mundo.findByCodMundo", query = "SELECT m FROM Mundo m WHERE m.codMundo = :codMundo"),
	@NamedQuery(name = "Mundo.findByNome", query = "SELECT m FROM Mundo m WHERE m.nome = :nome"),
	@NamedQuery(name = "Mundo.findByURLImagem", query = "SELECT m FROM Mundo m WHERE m.uRLImagem = :uRLImagem"),
	@NamedQuery(name = "Mundo.findByDataImagem", query = "SELECT m FROM Mundo m WHERE m.dataImagem = :dataImagem"),
	@NamedQuery(name = "Mundo.findByRaioTerritorio", query = "SELECT m FROM Mundo m WHERE m.raioTerritorio = :raioTerritorio") })
public class Mundo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cod_Mundo")
    private Integer codMundo;
    @Column(name = "Nome")
    private String nome;
    @Column(name = "URL_Imagem")
    private String uRLImagem;
    @Column(name = "Data_Imagem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataImagem;
    @Column(name = "Raio_Territorio")
    private Integer raioTerritorio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codMundo")
    private Collection<Continente> continenteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codMundo")
    private Collection<Cor> corCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codMundo")
    private Collection<Objetivo> objetivoCollection;

    public Mundo() {
    }

    public Mundo(Integer codMundo) {
	this.codMundo = codMundo;
    }

    public Integer getCodMundo() {
	return codMundo;
    }

    public void setCodMundo(Integer codMundo) {
	this.codMundo = codMundo;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getURLImagem() {
	return uRLImagem;
    }

    public void setURLImagem(String uRLImagem) {
	this.uRLImagem = uRLImagem;
    }

    public Date getDataImagem() {
	return dataImagem;
    }

    public void setDataImagem(Date dataImagem) {
	this.dataImagem = dataImagem;
    }

    public Integer getRaioTerritorio() {
	return raioTerritorio;
    }

    public void setRaioTerritorio(Integer raioTerritorio) {
	this.raioTerritorio = raioTerritorio;
    }

    @XmlTransient
    public Collection<Continente> getContinenteCollection() {
	return continenteCollection;
    }

    public void setContinenteCollection(
	    Collection<Continente> continenteCollection) {
	this.continenteCollection = continenteCollection;
    }

    @XmlTransient
    public Collection<Cor> getCorCollection() {
	return corCollection;
    }

    public void setCorCollection(Collection<Cor> corCollection) {
	this.corCollection = corCollection;
    }

    @XmlTransient
    public Collection<Objetivo> getObjetivoCollection() {
	return objetivoCollection;
    }

    public void setObjetivoCollection(Collection<Objetivo> objetivoCollection) {
	this.objetivoCollection = objetivoCollection;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (codMundo != null ? codMundo.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof Mundo)) {
	    return false;
	}
	Mundo other = (Mundo) object;
	if ((this.codMundo == null && other.codMundo != null)
		|| (this.codMundo != null && !this.codMundo
			.equals(other.codMundo)))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "br.uff.es2.war.entity.Mundo[ codMundo=" + codMundo + " ]";
    }

}
