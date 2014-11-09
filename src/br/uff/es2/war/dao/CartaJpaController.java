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
import br.uff.es2.war.entity.Carta;
import br.uff.es2.war.entity.Jogadorcarta;
import br.uff.es2.war.entity.Territorio;

/**
 * 
 * @author Victor
 */
public class CartaJpaController implements Serializable {

    public CartaJpaController(EntityManagerFactory emf) {
	this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Carta carta) {
	if (carta.getJogadorcartaCollection() == null) {
	    carta.setJogadorcartaCollection(new ArrayList<Jogadorcarta>());
	}
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Territorio codTerritorio = carta.getCodTerritorio();
	    if (codTerritorio != null) {
		codTerritorio = em.getReference(codTerritorio.getClass(),
			codTerritorio.getCodTerritorio());
		carta.setCodTerritorio(codTerritorio);
	    }
	    Collection<Jogadorcarta> attachedJogadorcartaCollection = new ArrayList<Jogadorcarta>();
	    for (Jogadorcarta jogadorcartaCollectionJogadorcartaToAttach : carta
		    .getJogadorcartaCollection()) {
		jogadorcartaCollectionJogadorcartaToAttach = em.getReference(
			jogadorcartaCollectionJogadorcartaToAttach.getClass(),
			jogadorcartaCollectionJogadorcartaToAttach
				.getJogadorcartaPK());
		attachedJogadorcartaCollection
			.add(jogadorcartaCollectionJogadorcartaToAttach);
	    }
	    carta.setJogadorcartaCollection(attachedJogadorcartaCollection);
	    em.persist(carta);
	    if (codTerritorio != null) {
		Carta oldCartaOfCodTerritorio = codTerritorio.getCarta();
		if (oldCartaOfCodTerritorio != null) {
		    oldCartaOfCodTerritorio.setCodTerritorio(null);
		    oldCartaOfCodTerritorio = em.merge(oldCartaOfCodTerritorio);
		}
		codTerritorio.setCarta(carta);
		codTerritorio = em.merge(codTerritorio);
	    }
	    for (Jogadorcarta jogadorcartaCollectionJogadorcarta : carta
		    .getJogadorcartaCollection()) {
		Carta oldCartaOfJogadorcartaCollectionJogadorcarta = jogadorcartaCollectionJogadorcarta
			.getCarta();
		jogadorcartaCollectionJogadorcarta.setCarta(carta);
		jogadorcartaCollectionJogadorcarta = em
			.merge(jogadorcartaCollectionJogadorcarta);
		if (oldCartaOfJogadorcartaCollectionJogadorcarta != null) {
		    oldCartaOfJogadorcartaCollectionJogadorcarta
			    .getJogadorcartaCollection().remove(
				    jogadorcartaCollectionJogadorcarta);
		    oldCartaOfJogadorcartaCollectionJogadorcarta = em
			    .merge(oldCartaOfJogadorcartaCollectionJogadorcarta);
		}
	    }
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Carta carta) throws IllegalOrphanException,
	    NonexistentEntityException, Exception {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Carta persistentCarta = em.find(Carta.class, carta.getCodCarta());
	    Territorio codTerritorioOld = persistentCarta.getCodTerritorio();
	    Territorio codTerritorioNew = carta.getCodTerritorio();
	    Collection<Jogadorcarta> jogadorcartaCollectionOld = persistentCarta
		    .getJogadorcartaCollection();
	    Collection<Jogadorcarta> jogadorcartaCollectionNew = carta
		    .getJogadorcartaCollection();
	    List<String> illegalOrphanMessages = null;
	    for (Jogadorcarta jogadorcartaCollectionOldJogadorcarta : jogadorcartaCollectionOld) {
		if (!jogadorcartaCollectionNew
			.contains(jogadorcartaCollectionOldJogadorcarta)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Jogadorcarta "
			    + jogadorcartaCollectionOldJogadorcarta
			    + " since its carta field is not nullable.");
		}
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    if (codTerritorioNew != null) {
		codTerritorioNew = em.getReference(codTerritorioNew.getClass(),
			codTerritorioNew.getCodTerritorio());
		carta.setCodTerritorio(codTerritorioNew);
	    }
	    Collection<Jogadorcarta> attachedJogadorcartaCollectionNew = new ArrayList<Jogadorcarta>();
	    for (Jogadorcarta jogadorcartaCollectionNewJogadorcartaToAttach : jogadorcartaCollectionNew) {
		jogadorcartaCollectionNewJogadorcartaToAttach = em
			.getReference(
				jogadorcartaCollectionNewJogadorcartaToAttach
					.getClass(),
				jogadorcartaCollectionNewJogadorcartaToAttach
					.getJogadorcartaPK());
		attachedJogadorcartaCollectionNew
			.add(jogadorcartaCollectionNewJogadorcartaToAttach);
	    }
	    jogadorcartaCollectionNew = attachedJogadorcartaCollectionNew;
	    carta.setJogadorcartaCollection(jogadorcartaCollectionNew);
	    carta = em.merge(carta);
	    if (codTerritorioOld != null
		    && !codTerritorioOld.equals(codTerritorioNew)) {
		codTerritorioOld.setCarta(null);
		codTerritorioOld = em.merge(codTerritorioOld);
	    }
	    if (codTerritorioNew != null
		    && !codTerritorioNew.equals(codTerritorioOld)) {
		Carta oldCartaOfCodTerritorio = codTerritorioNew.getCarta();
		if (oldCartaOfCodTerritorio != null) {
		    oldCartaOfCodTerritorio.setCodTerritorio(null);
		    oldCartaOfCodTerritorio = em.merge(oldCartaOfCodTerritorio);
		}
		codTerritorioNew.setCarta(carta);
		codTerritorioNew = em.merge(codTerritorioNew);
	    }
	    for (Jogadorcarta jogadorcartaCollectionNewJogadorcarta : jogadorcartaCollectionNew) {
		if (!jogadorcartaCollectionOld
			.contains(jogadorcartaCollectionNewJogadorcarta)) {
		    Carta oldCartaOfJogadorcartaCollectionNewJogadorcarta = jogadorcartaCollectionNewJogadorcarta
			    .getCarta();
		    jogadorcartaCollectionNewJogadorcarta.setCarta(carta);
		    jogadorcartaCollectionNewJogadorcarta = em
			    .merge(jogadorcartaCollectionNewJogadorcarta);
		    if (oldCartaOfJogadorcartaCollectionNewJogadorcarta != null
			    && !oldCartaOfJogadorcartaCollectionNewJogadorcarta
				    .equals(carta)) {
			oldCartaOfJogadorcartaCollectionNewJogadorcarta
				.getJogadorcartaCollection().remove(
					jogadorcartaCollectionNewJogadorcarta);
			oldCartaOfJogadorcartaCollectionNewJogadorcarta = em
				.merge(oldCartaOfJogadorcartaCollectionNewJogadorcarta);
		    }
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		Integer id = carta.getCodCarta();
		if (findCarta(id) == null) {
		    throw new NonexistentEntityException("The carta with id "
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
	    Carta carta;
	    try {
		carta = em.getReference(Carta.class, id);
		carta.getCodCarta();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The carta with id " + id
			+ " no longer exists.", enfe);
	    }
	    List<String> illegalOrphanMessages = null;
	    Collection<Jogadorcarta> jogadorcartaCollectionOrphanCheck = carta
		    .getJogadorcartaCollection();
	    for (Jogadorcarta jogadorcartaCollectionOrphanCheckJogadorcarta : jogadorcartaCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Carta ("
				+ carta
				+ ") cannot be destroyed since the Jogadorcarta "
				+ jogadorcartaCollectionOrphanCheckJogadorcarta
				+ " in its jogadorcartaCollection field has a non-nullable carta field.");
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    Territorio codTerritorio = carta.getCodTerritorio();
	    if (codTerritorio != null) {
		codTerritorio.setCarta(null);
		codTerritorio = em.merge(codTerritorio);
	    }
	    em.remove(carta);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Carta> findCartaEntities() {
	return findCartaEntities(true, -1, -1);
    }

    public List<Carta> findCartaEntities(int maxResults, int firstResult) {
	return findCartaEntities(false, maxResults, firstResult);
    }

    private List<Carta> findCartaEntities(boolean all, int maxResults,
	    int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Carta.class));
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

    public Carta findCarta(Integer id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Carta.class, id);
	} finally {
	    em.close();
	}
    }

    public int getCartaCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Carta> rt = cq.from(Carta.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
