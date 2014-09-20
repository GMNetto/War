/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "objderjogador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objderjogador.findAll", query = "SELECT o FROM Objderjogador o"),
    @NamedQuery(name = "Objderjogador.findByCodObjetivo", query = "SELECT o FROM Objderjogador o WHERE o.objderjogadorPK.codObjetivo = :codObjetivo"),
    @NamedQuery(name = "Objderjogador.findByCodCor", query = "SELECT o FROM Objderjogador o WHERE o.objderjogadorPK.codCor = :codCor"),
    @NamedQuery(name = "Objderjogador.findByCodObjetivoSec", query = "SELECT o FROM Objderjogador o WHERE o.objderjogadorPK.codObjetivoSec = :codObjetivoSec")})
public class Objderjogador implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ObjderjogadorPK objderjogadorPK;
    @JoinColumn(name = "Cod_Cor", referencedColumnName = "Cod_Cor", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cor cor;
    @JoinColumn(name = "Cod_Objetivo", referencedColumnName = "Cod_Objetivo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Objetivo objetivo;
    @JoinColumn(name = "Cod_ObjetivoSec", referencedColumnName = "Cod_Objetivo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Objetivo objetivo1;

    public Objderjogador() {
    }

    public Objderjogador(ObjderjogadorPK objderjogadorPK) {
        this.objderjogadorPK = objderjogadorPK;
    }

    public Objderjogador(int codObjetivo, int codCor, int codObjetivoSec) {
        this.objderjogadorPK = new ObjderjogadorPK(codObjetivo, codCor, codObjetivoSec);
    }

    public ObjderjogadorPK getObjderjogadorPK() {
        return objderjogadorPK;
    }

    public void setObjderjogadorPK(ObjderjogadorPK objderjogadorPK) {
        this.objderjogadorPK = objderjogadorPK;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public Objetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    public Objetivo getObjetivo1() {
        return objetivo1;
    }

    public void setObjetivo1(Objetivo objetivo1) {
        this.objetivo1 = objetivo1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objderjogadorPK != null ? objderjogadorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objderjogador)) {
            return false;
        }
        Objderjogador other = (Objderjogador) object;
        if ((this.objderjogadorPK == null && other.objderjogadorPK != null) || (this.objderjogadorPK != null && !this.objderjogadorPK.equals(other.objderjogadorPK)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "br.uff.es2.war.entity.Objderjogador[ objderjogadorPK=" + objderjogadorPK + " ]";
    }
    
}
