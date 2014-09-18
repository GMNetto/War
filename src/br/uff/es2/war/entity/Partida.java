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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "partida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
    @NamedQuery(name = "Partida.findByCodPartida", query = "SELECT p FROM Partida p WHERE p.codPartida = :codPartida"),
    @NamedQuery(name = "Partida.findByDataInicio", query = "SELECT p FROM Partida p WHERE p.dataInicio = :dataInicio"),
    @NamedQuery(name = "Partida.findByDataFim", query = "SELECT p FROM Partida p WHERE p.dataFim = :dataFim"),
    @NamedQuery(name = "Partida.findByTroca", query = "SELECT p FROM Partida p WHERE p.troca = :troca"),
    @NamedQuery(name = "Partida.findByQtdJogador", query = "SELECT p FROM Partida p WHERE p.qtdJogador = :qtdJogador"),
    @NamedQuery(name = "Partida.findByRodada", query = "SELECT p FROM Partida p WHERE p.rodada = :rodada")})
public class Partida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cod_Partida")
    private Integer codPartida;
    @Basic(optional = false)
    @Column(name = "Data_Inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Column(name = "Data_Fim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;
    @Column(name = "Troca")
    private Integer troca;
    @Basic(optional = false)
    @Column(name = "Qtd_Jogador")
    private int qtdJogador;
    @Basic(optional = false)
    @Column(name = "Rodada")
    private int rodada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partida")
    private Collection<Jogam> jogamCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "partida")
    private Historico historico;
    @JoinColumn(name = "Cod_Jogador", referencedColumnName = "Cod_Jogador")
    @ManyToOne
    private Jogador codJogador;

    public Partida() {
    }

    public Partida(Integer codPartida) {
        this.codPartida = codPartida;
    }

    public Partida(Integer codPartida, Date dataInicio, int qtdJogador, int rodada) {
        this.codPartida = codPartida;
        this.dataInicio = dataInicio;
        this.qtdJogador = qtdJogador;
        this.rodada = rodada;
    }

    public Integer getCodPartida() {
        return codPartida;
    }

    public void setCodPartida(Integer codPartida) {
        this.codPartida = codPartida;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getTroca() {
        return troca;
    }

    public void setTroca(Integer troca) {
        this.troca = troca;
    }

    public int getQtdJogador() {
        return qtdJogador;
    }

    public void setQtdJogador(int qtdJogador) {
        this.qtdJogador = qtdJogador;
    }

    public int getRodada() {
        return rodada;
    }

    public void setRodada(int rodada) {
        this.rodada = rodada;
    }

    @XmlTransient
    public Collection<Jogam> getJogamCollection() {
        return jogamCollection;
    }

    public void setJogamCollection(Collection<Jogam> jogamCollection) {
        this.jogamCollection = jogamCollection;
    }

    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    public Jogador getCodJogador() {
        return codJogador;
    }

    public void setCodJogador(Jogador codJogador) {
        this.codJogador = codJogador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPartida != null ? codPartida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.codPartida == null && other.codPartida != null) || (this.codPartida != null && !this.codPartida.equals(other.codPartida)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "br.uff.es2.war.entity.Partida[ codPartida=" + codPartida + " ]";
    }
    
}
