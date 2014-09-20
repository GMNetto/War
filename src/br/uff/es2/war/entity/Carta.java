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
@Table(name = "carta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carta.findAll", query = "SELECT c FROM Carta c"),
    @NamedQuery(name = "Carta.findByCodCarta", query = "SELECT c FROM Carta c WHERE c.codCarta = :codCarta"),
    @NamedQuery(name = "Carta.findByForma", query = "SELECT c FROM Carta c WHERE c.forma = :forma")})
public class Carta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Cod_Carta")
    private Integer codCarta;
    @Column(name = "Forma")
    private Integer forma;
    @JoinColumn(name = "Cod_Territorio", referencedColumnName = "Cod_Territorio")
    @OneToOne
    private Territorio codTerritorio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carta")
    private Collection<Jogadorcarta> jogadorcartaCollection;

    public Carta() {
    }

    public Carta(Integer codCarta) {
        this.codCarta = codCarta;
    }

    public Integer getCodCarta() {
        return codCarta;
    }

    public void setCodCarta(Integer codCarta) {
        this.codCarta = codCarta;
    }

    public Integer getForma() {
        return forma;
    }

    public void setForma(Integer forma) {
        this.forma = forma;
    }

    public Territorio getCodTerritorio() {
        return codTerritorio;
    }

    public void setCodTerritorio(Territorio codTerritorio) {
        this.codTerritorio = codTerritorio;
    }

    @XmlTransient
    public Collection<Jogadorcarta> getJogadorcartaCollection() {
        return jogadorcartaCollection;
    }

    public void setJogadorcartaCollection(Collection<Jogadorcarta> jogadorcartaCollection) {
        this.jogadorcartaCollection = jogadorcartaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCarta != null ? codCarta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carta)) {
            return false;
        }
        Carta other = (Carta) object;
        if ((this.codCarta == null && other.codCarta != null) || (this.codCarta != null && !this.codCarta.equals(other.codCarta)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "br.uff.es2.war.entity.Carta[ codCarta=" + codCarta + " ]";
    }
    
}
