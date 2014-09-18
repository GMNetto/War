/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "ocupacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocupacao.findAll", query = "SELECT o FROM Ocupacao o"),
    @NamedQuery(name = "Ocupacao.findByCodTerritorio", query = "SELECT o FROM Ocupacao o WHERE o.ocupacaoPK.codTerritorio = :codTerritorio"),
    @NamedQuery(name = "Ocupacao.findByTurno", query = "SELECT o FROM Ocupacao o WHERE o.turno = :turno"),
    @NamedQuery(name = "Ocupacao.findByQntExercito", query = "SELECT o FROM Ocupacao o WHERE o.qntExercito = :qntExercito"),
    @NamedQuery(name = "Ocupacao.findByCodJogador", query = "SELECT o FROM Ocupacao o WHERE o.ocupacaoPK.codJogador = :codJogador"),
    @NamedQuery(name = "Ocupacao.findByCodPartida", query = "SELECT o FROM Ocupacao o WHERE o.ocupacaoPK.codPartida = :codPartida")})
public class Ocupacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OcupacaoPK ocupacaoPK;
    @Column(name = "Turno")
    private Integer turno;
    @Column(name = "Qnt_Exercito")
    private Integer qntExercito;
    @JoinColumns({
        @JoinColumn(name = "Cod_Jogador", referencedColumnName = "Cod_Jogador", insertable = false, updatable = false),
        @JoinColumn(name = "Cod_Partida", referencedColumnName = "Cod_Partida", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Jogam jogam;
    @JoinColumn(name = "Cod_Territorio", referencedColumnName = "Cod_Territorio", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Territorio territorio;

    public Ocupacao() {
    }

    public Ocupacao(OcupacaoPK ocupacaoPK) {
        this.ocupacaoPK = ocupacaoPK;
    }

    public Ocupacao(int codTerritorio, int codJogador, int codPartida) {
        this.ocupacaoPK = new OcupacaoPK(codTerritorio, codJogador, codPartida);
    }

    public OcupacaoPK getOcupacaoPK() {
        return ocupacaoPK;
    }

    public void setOcupacaoPK(OcupacaoPK ocupacaoPK) {
        this.ocupacaoPK = ocupacaoPK;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public Integer getQntExercito() {
        return qntExercito;
    }

    public void setQntExercito(Integer qntExercito) {
        this.qntExercito = qntExercito;
    }

    public Jogam getJogam() {
        return jogam;
    }

    public void setJogam(Jogam jogam) {
        this.jogam = jogam;
    }

    public Territorio getTerritorio() {
        return territorio;
    }

    public void setTerritorio(Territorio territorio) {
        this.territorio = territorio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ocupacaoPK != null ? ocupacaoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocupacao)) {
            return false;
        }
        Ocupacao other = (Ocupacao) object;
        if ((this.ocupacaoPK == null && other.ocupacaoPK != null) || (this.ocupacaoPK != null && !this.ocupacaoPK.equals(other.ocupacaoPK)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "br.uff.es2.war.entity.Ocupacao[ ocupacaoPK=" + ocupacaoPK + " ]";
    }
    
}
