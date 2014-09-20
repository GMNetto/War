/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Victor
 */
@Embeddable
public class JogadorcartaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Cod_Jogador")
    private int codJogador;
    @Basic(optional = false)
    @Column(name = "Cod_Partida")
    private int codPartida;
    @Basic(optional = false)
    @Column(name = "Cod_Carta")
    private int codCarta;

    public JogadorcartaPK() {
    }

    public JogadorcartaPK(int codJogador, int codPartida, int codCarta) {
        this.codJogador = codJogador;
        this.codPartida = codPartida;
        this.codCarta = codCarta;
    }

    public int getCodJogador() {
        return codJogador;
    }

    public void setCodJogador(int codJogador) {
        this.codJogador = codJogador;
    }

    public int getCodPartida() {
        return codPartida;
    }

    public void setCodPartida(int codPartida) {
        this.codPartida = codPartida;
    }

    public int getCodCarta() {
        return codCarta;
    }

    public void setCodCarta(int codCarta) {
        this.codCarta = codCarta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codJogador;
        hash += (int) codPartida;
        hash += (int) codCarta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JogadorcartaPK)) {
            return false;
        }
        JogadorcartaPK other = (JogadorcartaPK) object;
        if (this.codJogador != other.codJogador)
            return false;
        if (this.codPartida != other.codPartida)
            return false;
        if (this.codCarta != other.codCarta)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "br.uff.es2.war.entity.JogadorcartaPK[ codJogador=" + codJogador + ", codPartida=" + codPartida + ", codCarta=" + codCarta + " ]";
    }
    
}
