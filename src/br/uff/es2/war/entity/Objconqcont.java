/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Victor
 */
@Entity
@Table(name = "objconqcont")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Objconqcont.findAll", query = "SELECT o FROM Objconqcont o"),
	@NamedQuery(name = "Objconqcont.findByCodObjetivo", query = "SELECT o FROM Objconqcont o WHERE o.codObjetivo = :codObjetivo"),
	@NamedQuery(name = "Objconqcont.findByContinentesExtras", query = "SELECT o FROM Objconqcont o WHERE o.continentesExtras = :continentesExtras") })
public class Objconqcont implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cod_Objetivo")
    private Integer codObjetivo;
    @Column(name = "Continentes_Extras")
    private Integer continentesExtras;
    @ManyToMany(mappedBy = "objconqcontCollection")
    private Collection<Continente> continenteCollection;
    @JoinColumn(name = "Cod_Objetivo", referencedColumnName = "Cod_Objetivo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Objetivo objetivo;

    public Objconqcont() {
    }

    public Objconqcont(Integer codObjetivo) {
	this.codObjetivo = codObjetivo;
    }

    public Integer getCodObjetivo() {
	return codObjetivo;
    }

    public void setCodObjetivo(Integer codObjetivo) {
	this.codObjetivo = codObjetivo;
    }

    public Integer getContinentesExtras() {
	return continentesExtras;
    }

    public void setContinentesExtras(Integer continentesExtras) {
	this.continentesExtras = continentesExtras;
    }

    @XmlTransient
    public Collection<Continente> getContinenteCollection() {
	return continenteCollection;
    }

    public void setContinenteCollection(
	    Collection<Continente> continenteCollection) {
	this.continenteCollection = continenteCollection;
    }

    public Objetivo getObjetivo() {
	return objetivo;
    }

    public void setObjetivo(Objetivo objetivo) {
	this.objetivo = objetivo;
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
	if (!(object instanceof Objconqcont)) {
	    return false;
	}
	Objconqcont other = (Objconqcont) object;
	if ((this.codObjetivo == null && other.codObjetivo != null)
		|| (this.codObjetivo != null && !this.codObjetivo
			.equals(other.codObjetivo)))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "br.uff.es2.war.entity.Objconqcont[ codObjetivo=" + codObjetivo
		+ " ]";
    }

}
