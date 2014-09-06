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
@Table(name = "cor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cor.findAll", query = "SELECT c FROM Cor c"),
    @NamedQuery(name = "Cor.findByCodCor", query = "SELECT c FROM Cor c WHERE c.codCor = :codCor"),
    @NamedQuery(name = "Cor.findByNome", query = "SELECT c FROM Cor c WHERE c.nome = :nome")})
public class Cor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Cod_Cor")
    private Integer codCor;
    @Basic(optional = false)
    @Column(name = "Nome")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCor")
    private Collection<Jogam> jogamCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cor")
    private Collection<Objderjogador> objderjogadorCollection;

    public Cor() {
    }

    public Cor(Integer codCor) {
        this.codCor = codCor;
    }

    public Cor(Integer codCor, String nome) {
        this.codCor = codCor;
        this.nome = nome;
    }

    public Integer getCodCor() {
        return codCor;
    }

    public void setCodCor(Integer codCor) {
        this.codCor = codCor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public Collection<Jogam> getJogamCollection() {
        return jogamCollection;
    }

    public void setJogamCollection(Collection<Jogam> jogamCollection) {
        this.jogamCollection = jogamCollection;
    }

    @XmlTransient
    public Collection<Objderjogador> getObjderjogadorCollection() {
        return objderjogadorCollection;
    }

    public void setObjderjogadorCollection(Collection<Objderjogador> objderjogadorCollection) {
        this.objderjogadorCollection = objderjogadorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCor != null ? codCor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cor)) {
            return false;
        }
        Cor other = (Cor) object;
        if ((this.codCor == null && other.codCor != null) || (this.codCor != null && !this.codCor.equals(other.codCor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.uff.es2.war.entity.Cor[ codCor=" + codCor + " ]";
    }
    
}
