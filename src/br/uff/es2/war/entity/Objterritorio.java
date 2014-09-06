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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gustavo
 */
@Entity
@Table(name = "objterritorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objterritorio.findAll", query = "SELECT o FROM Objterritorio o"),
    @NamedQuery(name = "Objterritorio.findByCodObjetivo", query = "SELECT o FROM Objterritorio o WHERE o.codObjetivo = :codObjetivo"),
    @NamedQuery(name = "Objterritorio.findByQtdTerritorio", query = "SELECT o FROM Objterritorio o WHERE o.qtdTerritorio = :qtdTerritorio"),
    @NamedQuery(name = "Objterritorio.findByMinExercito", query = "SELECT o FROM Objterritorio o WHERE o.minExercito = :minExercito")})
public class Objterritorio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cod_Objetivo")
    private Integer codObjetivo;
    @Basic(optional = false)
    @Column(name = "Qtd_Territorio")
    private int qtdTerritorio;
    @Column(name = "Min_Exercito")
    private Integer minExercito;
    @JoinColumn(name = "Cod_Objetivo", referencedColumnName = "Cod_Objetivo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Objetivo objetivo;

    public Objterritorio() {
    }

    public Objterritorio(Integer codObjetivo) {
        this.codObjetivo = codObjetivo;
    }

    public Objterritorio(Integer codObjetivo, int qtdTerritorio) {
        this.codObjetivo = codObjetivo;
        this.qtdTerritorio = qtdTerritorio;
    }

    public Integer getCodObjetivo() {
        return codObjetivo;
    }

    public void setCodObjetivo(Integer codObjetivo) {
        this.codObjetivo = codObjetivo;
    }

    public int getQtdTerritorio() {
        return qtdTerritorio;
    }

    public void setQtdTerritorio(int qtdTerritorio) {
        this.qtdTerritorio = qtdTerritorio;
    }

    public Integer getMinExercito() {
        return minExercito;
    }

    public void setMinExercito(Integer minExercito) {
        this.minExercito = minExercito;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objterritorio)) {
            return false;
        }
        Objterritorio other = (Objterritorio) object;
        if ((this.codObjetivo == null && other.codObjetivo != null) || (this.codObjetivo != null && !this.codObjetivo.equals(other.codObjetivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.uff.es2.war.entity.Objterritorio[ codObjetivo=" + codObjetivo + " ]";
    }
    
}
