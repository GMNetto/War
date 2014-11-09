/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.uff.es2.war.dao.exceptions.IllegalOrphanException;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.dao.exceptions.PreexistingEntityException;
import br.uff.es2.war.entity.Historico;
import br.uff.es2.war.entity.Jogador;
import br.uff.es2.war.entity.Jogam;
import br.uff.es2.war.entity.Partida;

/**
 * 
 * @author Victor
 */
public class PartidaJpaController implements Serializable {

    public PartidaJpaController(EntityManagerFactory emf) {
	this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Partida partida) throws PreexistingEntityException,
	    Exception {
	if (partida.getJogamCollection() == null) {
	    partida.setJogamCollection(new ArrayList<Jogam>());
	}
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Historico historico = partida.getHistorico();
	    if (historico != null) {
		historico = em.getReference(historico.getClass(),
			historico.getCodPartida());
		partida.setHistorico(historico);
	    }
	    Jogador codJogador = partida.getCodJogador();
	    if (codJogador != null) {
		codJogador = em.getReference(codJogador.getClass(),
			codJogador.getCodJogador());
		partida.setCodJogador(codJogador);
	    }
	    Collection<Jogam> attachedJogamCollection = new ArrayList<Jogam>();
	    for (Jogam jogamCollectionJogamToAttach : partida
		    .getJogamCollection()) {
		jogamCollectionJogamToAttach = em.getReference(
			jogamCollectionJogamToAttach.getClass(),
			jogamCollectionJogamToAttach.getJogamPK());
		attachedJogamCollection.add(jogamCollectionJogamToAttach);
	    }
	    partida.setJogamCollection(attachedJogamCollection);
	    em.persist(partida);
	    if (historico != null) {
		Partida oldPartidaOfHistorico = historico.getPartida();
		if (oldPartidaOfHistorico != null) {
		    oldPartidaOfHistorico.setHistorico(null);
		    oldPartidaOfHistorico = em.merge(oldPartidaOfHistorico);
		}
		historico.setPartida(partida);
		historico = em.merge(historico);
	    }
	    if (codJogador != null) {
		codJogador.getPartidaCollection().add(partida);
		codJogador = em.merge(codJogador);
	    }
	    for (Jogam jogamCollectionJogam : partida.getJogamCollection()) {
		Partida oldPartidaOfJogamCollectionJogam = jogamCollectionJogam
			.getPartida();
		jogamCollectionJogam.setPartida(partida);
		jogamCollectionJogam = em.merge(jogamCollectionJogam);
		if (oldPartidaOfJogamCollectionJogam != null) {
		    oldPartidaOfJogamCollectionJogam.getJogamCollection()
			    .remove(jogamCollectionJogam);
		    oldPartidaOfJogamCollectionJogam = em
			    .merge(oldPartidaOfJogamCollectionJogam);
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    if (findPartida(partida.getCodPartida()) != null) {
		throw new PreexistingEntityException("Partida " + partida
			+ " already exists.", ex);
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Partida partida) throws IllegalOrphanException,
	    NonexistentEntityException, Exception {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Partida persistentPartida = em.find(Partida.class,
		    partida.getCodPartida());
	    Historico historicoOld = persistentPartida.getHistorico();
	    Historico historicoNew = partida.getHistorico();
	    Jogador codJogadorOld = persistentPartida.getCodJogador();
	    Jogador codJogadorNew = partida.getCodJogador();
	    Collection<Jogam> jogamCollectionOld = persistentPartida
		    .getJogamCollection();
	    Collection<Jogam> jogamCollectionNew = partida.getJogamCollection();
	    List<String> illegalOrphanMessages = null;
	    if (historicoOld != null && !historicoOld.equals(historicoNew)) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages.add("You must retain Historico "
			+ historicoOld
			+ " since its partida field is not nullable.");
	    }
	    for (Jogam jogamCollectionOldJogam : jogamCollectionOld) {
		if (!jogamCollectionNew.contains(jogamCollectionOldJogam)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Jogam "
			    + jogamCollectionOldJogam
			    + " since its partida field is not nullable.");
		}
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    if (historicoNew != null) {
		historicoNew = em.getReference(historicoNew.getClass(),
			historicoNew.getCodPartida());
		partida.setHistorico(historicoNew);
	    }
	    if (codJogadorNew != null) {
		codJogadorNew = em.getReference(codJogadorNew.getClass(),
			codJogadorNew.getCodJogador());
		partida.setCodJogador(codJogadorNew);
	    }
	    Collection<Jogam> attachedJogamCollectionNew = new ArrayList<Jogam>();
	    for (Jogam jogamCollectionNewJogamToAttach : jogamCollectionNew) {
		jogamCollectionNewJogamToAttach = em.getReference(
			jogamCollectionNewJogamToAttach.getClass(),
			jogamCollectionNewJogamToAttach.getJogamPK());
		attachedJogamCollectionNew.add(jogamCollectionNewJogamToAttach);
	    }
	    jogamCollectionNew = attachedJogamCollectionNew;
	    partida.setJogamCollection(jogamCollectionNew);
	    partida = em.merge(partida);
	    if (historicoNew != null && !historicoNew.equals(historicoOld)) {
		Partida oldPartidaOfHistorico = historicoNew.getPartida();
		if (oldPartidaOfHistorico != null) {
		    oldPartidaOfHistorico.setHistorico(null);
		    oldPartidaOfHistorico = em.merge(oldPartidaOfHistorico);
		}
		historicoNew.setPartida(partida);
		historicoNew = em.merge(historicoNew);
	    }
	    if (codJogadorOld != null && !codJogadorOld.equals(codJogadorNew)) {
		codJogadorOld.getPartidaCollection().remove(partida);
		codJogadorOld = em.merge(codJogadorOld);
	    }
	    if (codJogadorNew != null && !codJogadorNew.equals(codJogadorOld)) {
		codJogadorNew.getPartidaCollection().add(partida);
		codJogadorNew = em.merge(codJogadorNew);
	    }
	    for (Jogam jogamCollectionNewJogam : jogamCollectionNew) {
		if (!jogamCollectionOld.contains(jogamCollectionNewJogam)) {
		    Partida oldPartidaOfJogamCollectionNewJogam = jogamCollectionNewJogam
			    .getPartida();
		    jogamCollectionNewJogam.setPartida(partida);
		    jogamCollectionNewJogam = em.merge(jogamCollectionNewJogam);
		    if (oldPartidaOfJogamCollectionNewJogam != null
			    && !oldPartidaOfJogamCollectionNewJogam
				    .equals(partida)) {
			oldPartidaOfJogamCollectionNewJogam
				.getJogamCollection().remove(
					jogamCollectionNewJogam);
			oldPartidaOfJogamCollectionNewJogam = em
				.merge(oldPartidaOfJogamCollectionNewJogam);
		    }
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		Integer id = partida.getCodPartida();
		if (findPartida(id) == null) {
		    throw new NonexistentEntityException("The partida with id "
			    + id + " no longer exists.");
		}
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void destroy(Integer id) throws IllegalOrphanException,
	    NonexistentEntityException {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Partida partida;
	    try {
		partida = em.getReference(Partida.class, id);
		partida.getCodPartida();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The partida with id "
			+ id + " no longer exists.", enfe);
	    }
	    List<String> illegalOrphanMessages = null;
	    Historico historicoOrphanCheck = partida.getHistorico();
	    if (historicoOrphanCheck != null) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Partida ("
				+ partida
				+ ") cannot be destroyed since the Historico "
				+ historicoOrphanCheck
				+ " in its historico field has a non-nullable partida field.");
	    }
	    Collection<Jogam> jogamCollectionOrphanCheck = partida
		    .getJogamCollection();
	    for (Jogam jogamCollectionOrphanCheckJogam : jogamCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Partida ("
				+ partida
				+ ") cannot be destroyed since the Jogam "
				+ jogamCollectionOrphanCheckJogam
				+ " in its jogamCollection field has a non-nullable partida field.");
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    Jogador codJogador = partida.getCodJogador();
	    if (codJogador != null) {
		codJogador.getPartidaCollection().remove(partida);
		codJogador = em.merge(codJogador);
	    }
	    em.remove(partida);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Partida> findPartidaEntities() {
	return findPartidaEntities(true, -1, -1);
    }

    public List<Partida> findPartidaEntities(int maxResults, int firstResult) {
	return findPartidaEntities(false, maxResults, firstResult);
    }

    private List<Partida> findPartidaEntities(boolean all, int maxResults,
	    int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Partida.class));
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

    public Partida findPartida(Integer id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Partida.class, id);
	} finally {
	    em.close();
	}
    }

    public int getPartidaCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Partida> rt = cq.from(Partida.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
