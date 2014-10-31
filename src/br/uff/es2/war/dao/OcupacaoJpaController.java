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
import br.uff.es2.war.entity.Jogam;
import br.uff.es2.war.entity.Ocupacao;
import br.uff.es2.war.entity.OcupacaoPK;
import br.uff.es2.war.entity.Territorio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * 
 * @author Victor
 */
public class OcupacaoJpaController implements Serializable {

    public OcupacaoJpaController(EntityManagerFactory emf) {
	this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Ocupacao ocupacao) throws PreexistingEntityException,
	    Exception {
	if (ocupacao.getOcupacaoPK() == null) {
	    ocupacao.setOcupacaoPK(new OcupacaoPK());
	}
	ocupacao.getOcupacaoPK().setCodPartida(
		ocupacao.getJogam().getJogamPK().getCodPartida());
	ocupacao.getOcupacaoPK().setCodTerritorio(
		ocupacao.getTerritorio().getCodTerritorio());
	ocupacao.getOcupacaoPK().setCodJogador(
		ocupacao.getJogam().getJogamPK().getCodJogador());
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Jogam jogam = ocupacao.getJogam();
	    if (jogam != null) {
		jogam = em.getReference(jogam.getClass(), jogam.getJogamPK());
		ocupacao.setJogam(jogam);
	    }
	    Territorio territorio = ocupacao.getTerritorio();
	    if (territorio != null) {
		territorio = em.getReference(territorio.getClass(),
			territorio.getCodTerritorio());
		ocupacao.setTerritorio(territorio);
	    }
	    em.persist(ocupacao);
	    if (jogam != null) {
		jogam.getOcupacaoCollection().add(ocupacao);
		jogam = em.merge(jogam);
	    }
	    if (territorio != null) {
		territorio.getOcupacaoCollection().add(ocupacao);
		territorio = em.merge(territorio);
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    if (findOcupacao(ocupacao.getOcupacaoPK()) != null) {
		throw new PreexistingEntityException("Ocupacao " + ocupacao
			+ " already exists.", ex);
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Ocupacao ocupacao) throws NonexistentEntityException,
	    Exception {
	ocupacao.getOcupacaoPK().setCodPartida(
		ocupacao.getJogam().getJogamPK().getCodPartida());
	ocupacao.getOcupacaoPK().setCodTerritorio(
		ocupacao.getTerritorio().getCodTerritorio());
	ocupacao.getOcupacaoPK().setCodJogador(
		ocupacao.getJogam().getJogamPK().getCodJogador());
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Ocupacao persistentOcupacao = em.find(Ocupacao.class,
		    ocupacao.getOcupacaoPK());
	    Jogam jogamOld = persistentOcupacao.getJogam();
	    Jogam jogamNew = ocupacao.getJogam();
	    Territorio territorioOld = persistentOcupacao.getTerritorio();
	    Territorio territorioNew = ocupacao.getTerritorio();
	    if (jogamNew != null) {
		jogamNew = em.getReference(jogamNew.getClass(),
			jogamNew.getJogamPK());
		ocupacao.setJogam(jogamNew);
	    }
	    if (territorioNew != null) {
		territorioNew = em.getReference(territorioNew.getClass(),
			territorioNew.getCodTerritorio());
		ocupacao.setTerritorio(territorioNew);
	    }
	    ocupacao = em.merge(ocupacao);
	    if (jogamOld != null && !jogamOld.equals(jogamNew)) {
		jogamOld.getOcupacaoCollection().remove(ocupacao);
		jogamOld = em.merge(jogamOld);
	    }
	    if (jogamNew != null && !jogamNew.equals(jogamOld)) {
		jogamNew.getOcupacaoCollection().add(ocupacao);
		jogamNew = em.merge(jogamNew);
	    }
	    if (territorioOld != null && !territorioOld.equals(territorioNew)) {
		territorioOld.getOcupacaoCollection().remove(ocupacao);
		territorioOld = em.merge(territorioOld);
	    }
	    if (territorioNew != null && !territorioNew.equals(territorioOld)) {
		territorioNew.getOcupacaoCollection().add(ocupacao);
		territorioNew = em.merge(territorioNew);
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		OcupacaoPK id = ocupacao.getOcupacaoPK();
		if (findOcupacao(id) == null) {
		    throw new NonexistentEntityException(
			    "The ocupacao with id " + id + " no longer exists.");
		}
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void destroy(OcupacaoPK id) throws NonexistentEntityException {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Ocupacao ocupacao;
	    try {
		ocupacao = em.getReference(Ocupacao.class, id);
		ocupacao.getOcupacaoPK();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The ocupacao with id "
			+ id + " no longer exists.", enfe);
	    }
	    Jogam jogam = ocupacao.getJogam();
	    if (jogam != null) {
		jogam.getOcupacaoCollection().remove(ocupacao);
		jogam = em.merge(jogam);
	    }
	    Territorio territorio = ocupacao.getTerritorio();
	    if (territorio != null) {
		territorio.getOcupacaoCollection().remove(ocupacao);
		territorio = em.merge(territorio);
	    }
	    em.remove(ocupacao);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Ocupacao> findOcupacaoEntities() {
	return findOcupacaoEntities(true, -1, -1);
    }

    public List<Ocupacao> findOcupacaoEntities(int maxResults, int firstResult) {
	return findOcupacaoEntities(false, maxResults, firstResult);
    }

    private List<Ocupacao> findOcupacaoEntities(boolean all, int maxResults,
	    int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Ocupacao.class));
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

    public Ocupacao findOcupacao(OcupacaoPK id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Ocupacao.class, id);
	} finally {
	    em.close();
	}
    }

    public int getOcupacaoCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Ocupacao> rt = cq.from(Ocupacao.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
