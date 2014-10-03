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
 * @author Victor
 */
@Entity
@Table(name = "jogador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jogador.findAll", query = "SELECT j FROM Jogador j"),
    @NamedQuery(name = "Jogador.findByCodJogador", query = "SELECT j FROM Jogador j WHERE j.codJogador = :codJogador"),
    @NamedQuery(name = "Jogador.findByLogin", query = "SELECT j FROM Jogador j WHERE j.login = :login"),
    @NamedQuery(name = "Jogador.findBySenha", query = "SELECT j FROM Jogador j WHERE j.senha = :senha"),
    @NamedQuery(name = "Jogador.findByEmail", query = "SELECT j FROM Jogador j WHERE j.email = :email")})
public class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Cod_Jogador")
    private Integer codJogador;
    @Basic(optional = false)
    @Column(name = "Login")
    private String login;
    @Basic(optional = false)
    @Column(name = "Senha")
    private String senha;
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jogador")
    private Collection<Jogam> jogamCollection;
    @OneToMany(mappedBy = "codJogador")
    private Collection<Historico> historicoCollection;
    @OneToMany(mappedBy = "codJogador")
    private Collection<Partida> partidaCollection;

    public Jogador() {
    }

    public Jogador(Integer codJogador) {
        this.codJogador = codJogador;
    }

    public Jogador(Integer codJogador, String login, String senha, String email) {
        this.codJogador = codJogador;
        this.login = login;
        this.senha = senha;
        this.email = email;
    }

    public Integer getCodJogador() {
        return codJogador;
    }

    public void setCodJogador(Integer codJogador) {
        this.codJogador = codJogador;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public Collection<Jogam> getJogamCollection() {
        return jogamCollection;
    }

    public void setJogamCollection(Collection<Jogam> jogamCollection) {
        this.jogamCollection = jogamCollection;
    }

    @XmlTransient
    public Collection<Historico> getHistoricoCollection() {
        return historicoCollection;
    }

    public void setHistoricoCollection(Collection<Historico> historicoCollection) {
        this.historicoCollection = historicoCollection;
    }

    @XmlTransient
    public Collection<Partida> getPartidaCollection() {
        return partidaCollection;
    }

    public void setPartidaCollection(Collection<Partida> partidaCollection) {
        this.partidaCollection = partidaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codJogador != null ? codJogador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jogador)) {
            return false;
        }
        Jogador other = (Jogador) object;
        if ((this.codJogador == null && other.codJogador != null) || (this.codJogador != null && !this.codJogador.equals(other.codJogador)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "br.uff.es2.war.entity.Jogador[ codJogador=" + codJogador + " ]";
    }
    
}
