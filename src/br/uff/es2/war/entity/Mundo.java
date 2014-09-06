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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gustavo
 */
@Entity
@Table(name = "mundo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mundo.findAll", query = "SELECT m FROM Mundo m"),
    @NamedQuery(name = "Mundo.findByCodMundo", query = "SELECT m FROM Mundo m WHERE m.codMundo = :codMundo"),
    @NamedQuery(name = "Mundo.findByNome", query = "SELECT m FROM Mundo m WHERE m.nome = :nome")})
public class Mundo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cod_Mundo")
    private Integer codMundo;
    @Column(name = "Nome")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codMundo")
    private Collection<Continente> continenteCollection;

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

    @XmlTransient
    public Collection<Continente> getContinenteCollection() {
        return continenteCollection;
    }

    public void setContinenteCollection(Collection<Continente> continenteCollection) {
        this.continenteCollection = continenteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMundo != null ? codMundo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mundo)) {
            return false;
        }
        Mundo other = (Mundo) object;
        if ((this.codMundo == null && other.codMundo != null) || (this.codMundo != null && !this.codMundo.equals(other.codMundo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.uff.es2.war.entity.Mundo[ codMundo=" + codMundo + " ]";
    }
    
}
