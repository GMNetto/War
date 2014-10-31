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
public class ObjderjogadorPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Cod_Objetivo")
    private int codObjetivo;
    @Basic(optional = false)
    @Column(name = "Cod_Cor")
    private int codCor;
    @Basic(optional = false)
    @Column(name = "Cod_ObjetivoSec")
    private int codObjetivoSec;

    public ObjderjogadorPK() {
    }

    public ObjderjogadorPK(int codObjetivo, int codCor, int codObjetivoSec) {
	this.codObjetivo = codObjetivo;
	this.codCor = codCor;
	this.codObjetivoSec = codObjetivoSec;
    }

    public int getCodObjetivo() {
	return codObjetivo;
    }

    public void setCodObjetivo(int codObjetivo) {
	this.codObjetivo = codObjetivo;
    }

    public int getCodCor() {
	return codCor;
    }

    public void setCodCor(int codCor) {
	this.codCor = codCor;
    }

    public int getCodObjetivoSec() {
	return codObjetivoSec;
    }

    public void setCodObjetivoSec(int codObjetivoSec) {
	this.codObjetivoSec = codObjetivoSec;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (int) codObjetivo;
	hash += (int) codCor;
	hash += (int) codObjetivoSec;
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof ObjderjogadorPK)) {
	    return false;
	}
	ObjderjogadorPK other = (ObjderjogadorPK) object;
	if (this.codObjetivo != other.codObjetivo)
	    return false;
	if (this.codCor != other.codCor)
	    return false;
	if (this.codObjetivoSec != other.codObjetivoSec)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "br.uff.es2.war.entity.ObjderjogadorPK[ codObjetivo="
		+ codObjetivo + ", codCor=" + codCor + ", codObjetivoSec="
		+ codObjetivoSec + " ]";
    }

}
