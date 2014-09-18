/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "jogam")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jogam.findAll", query = "SELECT j FROM Jogam j"),
    @NamedQuery(name = "Jogam.findByCodPartida", query = "SELECT j FROM Jogam j WHERE j.jogamPK.codPartida = :codPartida"),
    @NamedQuery(name = "Jogam.findByCodJogador", query = "SELECT j FROM Jogam j WHERE j.jogamPK.codJogador = :codJogador")})
public class Jogam implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JogamPK jogamPK;
    @JoinColumn(name = "Cod_Cor", referencedColumnName = "Cod_Cor")
    @ManyToOne(optional = false)
    private Cor codCor;
    @JoinColumn(name = "Cod_Jogador", referencedColumnName = "Cod_Jogador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jogador jogador;
    @JoinColumn(name = "Cod_Objetivo", referencedColumnName = "Cod_Objetivo")
    @ManyToOne(optional = false)
    private Objetivo codObjetivo;
    @JoinColumn(name = "Cod_Partida", referencedColumnName = "Cod_Partida", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partida partida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jogam")
    private Collection<Ocupacao> ocupacaoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jogam")
    private Collection<Jogadorcarta> jogadorcartaCollection;

    public Jogam() {
    }

    public Jogam(JogamPK jogamPK) {
        this.jogamPK = jogamPK;
    }

    public Jogam(int codPartida, int codJogador) {
        this.jogamPK = new JogamPK(codPartida, codJogador);
    }

    public JogamPK getJogamPK() {
        return jogamPK;
    }

    public void setJogamPK(JogamPK jogamPK) {
        this.jogamPK = jogamPK;
    }

    public Cor getCodCor() {
        return codCor;
    }

    public void setCodCor(Cor codCor) {
        this.codCor = codCor;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
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

    @XmlTransient
    public Collection<Ocupacao> getOcupacaoCollection() {
        return ocupacaoCollection;
    }

    public void setOcupacaoCollection(Collection<Ocupacao> ocupacaoCollection) {
        this.ocupacaoCollection = ocupacaoCollection;
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
        hash += (jogamPK != null ? jogamPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jogam)) {
            return false;
        }
        Jogam other = (Jogam) object;
        if ((this.jogamPK == null && other.jogamPK != null) || (this.jogamPK != null && !this.jogamPK.equals(other.jogamPK)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "br.uff.es2.war.entity.Jogam[ jogamPK=" + jogamPK + " ]";
    }
    
}
