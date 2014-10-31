/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.dao;

import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.uff.es2.war.entity.Cor;
import br.uff.es2.war.entity.Objderjogador;
import br.uff.es2.war.entity.ObjderjogadorPK;
import br.uff.es2.war.entity.Objetivo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * 
 * @author Victor
 */
public class ObjderjogadorJpaController implements Serializable {

    public ObjderjogadorJpaController(EntityManagerFactory emf) {
	this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Objderjogador objderjogador)
	    throws PreexistingEntityException, Exception {
	if (objderjogador.getObjderjogadorPK() == null) {
	    objderjogador.setObjderjogadorPK(new ObjderjogadorPK());
	}
	objderjogador.getObjderjogadorPK().setCodObjetivoSec(
		objderjogador.getObjetivo1().getCodObjetivo());
	objderjogador.getObjderjogadorPK().setCodObjetivo(
		objderjogador.getObjetivo().getCodObjetivo());
	objderjogador.getObjderjogadorPK().setCodCor(
		objderjogador.getCor().getCodCor());
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Cor cor = objderjogador.getCor();
	    if (cor != null) {
		cor = em.getReference(cor.getClass(), cor.getCodCor());
		objderjogador.setCor(cor);
	    }
	    Objetivo objetivo = objderjogador.getObjetivo();
	    if (objetivo != null) {
		objetivo = em.getReference(objetivo.getClass(),
			objetivo.getCodObjetivo());
		objderjogador.setObjetivo(objetivo);
	    }
	    Objetivo objetivo1 = objderjogador.getObjetivo1();
	    if (objetivo1 != null) {
		objetivo1 = em.getReference(objetivo1.getClass(),
			objetivo1.getCodObjetivo());
		objderjogador.setObjetivo1(objetivo1);
	    }
	    em.persist(objderjogador);
	    if (cor != null) {
		cor.getObjderjogadorCollection().add(objderjogador);
		cor = em.merge(cor);
	    }
	    if (objetivo != null) {
		objetivo.getObjderjogadorCollection().add(objderjogador);
		objetivo = em.merge(objetivo);
	    }
	    if (objetivo1 != null) {
		objetivo1.getObjderjogadorCollection().add(objderjogador);
		objetivo1 = em.merge(objetivo1);
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    if (findObjderjogador(objderjogador.getObjderjogadorPK()) != null) {
		throw new PreexistingEntityException("Objderjogador "
			+ objderjogador + " already exists.", ex);
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Objderjogador objderjogador)
	    throws NonexistentEntityException, Exception {
	objderjogador.getObjderjogadorPK().setCodObjetivoSec(
		objderjogador.getObjetivo1().getCodObjetivo());
	objderjogador.getObjderjogadorPK().setCodObjetivo(
		objderjogador.getObjetivo().getCodObjetivo());
	objderjogador.getObjderjogadorPK().setCodCor(
		objderjogador.getCor().getCodCor());
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Objderjogador persistentObjderjogador = em.find(
		    Objderjogador.class, objderjogador.getObjderjogadorPK());
	    Cor corOld = persistentObjderjogador.getCor();
	    Cor corNew = objderjogador.getCor();
	    Objetivo objetivoOld = persistentObjderjogador.getObjetivo();
	    Objetivo objetivoNew = objderjogador.getObjetivo();
	    Objetivo objetivo1Old = persistentObjderjogador.getObjetivo1();
	    Objetivo objetivo1New = objderjogador.getObjetivo1();
	    if (corNew != null) {
		corNew = em.getReference(corNew.getClass(), corNew.getCodCor());
		objderjogador.setCor(corNew);
	    }
	    if (objetivoNew != null) {
		objetivoNew = em.getReference(objetivoNew.getClass(),
			objetivoNew.getCodObjetivo());
		objderjogador.setObjetivo(objetivoNew);
	    }
	    if (objetivo1New != null) {
		objetivo1New = em.getReference(objetivo1New.getClass(),
			objetivo1New.getCodObjetivo());
		objderjogador.setObjetivo1(objetivo1New);
	    }
	    objderjogador = em.merge(objderjogador);
	    if (corOld != null && !corOld.equals(corNew)) {
		corOld.getObjderjogadorCollection().remove(objderjogador);
		corOld = em.merge(corOld);
	    }
	    if (corNew != null && !corNew.equals(corOld)) {
		corNew.getObjderjogadorCollection().add(objderjogador);
		corNew = em.merge(corNew);
	    }
	    if (objetivoOld != null && !objetivoOld.equals(objetivoNew)) {
		objetivoOld.getObjderjogadorCollection().remove(objderjogador);
		objetivoOld = em.merge(objetivoOld);
	    }
	    if (objetivoNew != null && !objetivoNew.equals(objetivoOld)) {
		objetivoNew.getObjderjogadorCollection().add(objderjogador);
		objetivoNew = em.merge(objetivoNew);
	    }
	    if (objetivo1Old != null && !objetivo1Old.equals(objetivo1New)) {
		objetivo1Old.getObjderjogadorCollection().remove(objderjogador);
		objetivo1Old = em.merge(objetivo1Old);
	    }
	    if (objetivo1New != null && !objetivo1New.equals(objetivo1Old)) {
		objetivo1New.getObjderjogadorCollection().add(objderjogador);
		objetivo1New = em.merge(objetivo1New);
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		ObjderjogadorPK id = objderjogador.getObjderjogadorPK();
		if (findObjderjogador(id) == null) {
		    throw new NonexistentEntityException(
			    "The objderjogador with id " + id
				    + " no longer exists.");
		}
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void destroy(ObjderjogadorPK id) throws NonexistentEntityException {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Objderjogador objderjogador;
	    try {
		objderjogador = em.getReference(Objderjogador.class, id);
		objderjogador.getObjderjogadorPK();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException(
			"The objderjogador with id " + id
				+ " no longer exists.", enfe);
	    }
	    Cor cor = objderjogador.getCor();
	    if (cor != null) {
		cor.getObjderjogadorCollection().remove(objderjogador);
		cor = em.merge(cor);
	    }
	    Objetivo objetivo = objderjogador.getObjetivo();
	    if (objetivo != null) {
		objetivo.getObjderjogadorCollection().remove(objderjogador);
		objetivo = em.merge(objetivo);
	    }
	    Objetivo objetivo1 = objderjogador.getObjetivo1();
	    if (objetivo1 != null) {
		objetivo1.getObjderjogadorCollection().remove(objderjogador);
		objetivo1 = em.merge(objetivo1);
	    }
	    em.remove(objderjogador);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Objderjogador> findObjderjogadorEntities() {
	return findObjderjogadorEntities(true, -1, -1);
    }

    public List<Objderjogador> findObjderjogadorEntities(int maxResults,
	    int firstResult) {
	return findObjderjogadorEntities(false, maxResults, firstResult);
    }

    private List<Objderjogador> findObjderjogadorEntities(boolean all,
	    int maxResults, int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Objderjogador.class));
	    Query q = em.createQuery(cq);
	    if (!all) {
		q.setMaxResults(maxResults);
		q.setFirstResult(firstResult);
	    }
	    return q.getResultList();
	} finally {
	    em.close();
	}
    }

    public Objderjogador findObjderjogador(ObjderjogadorPK id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Objderjogador.class, id);
	} finally {
	    em.close();
	}
    }

    public int getObjderjogadorCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Objderjogador> rt = cq.from(Objderjogador.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
