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
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Cor;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Objetivo;

/**
 * 
 * @author Victor
 */
public class MundoJpaController implements Serializable {

    public MundoJpaController(EntityManagerFactory emf) {
	this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Mundo mundo) throws PreexistingEntityException,
	    Exception {
	if (mundo.getContinenteCollection() == null) {
	    mundo.setContinenteCollection(new ArrayList<Continente>());
	}
	if (mundo.getCorCollection() == null) {
	    mundo.setCorCollection(new ArrayList<Cor>());
	}
	if (mundo.getObjetivoCollection() == null) {
	    mundo.setObjetivoCollection(new ArrayList<Objetivo>());
	}
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Collection<Continente> attachedContinenteCollection = new ArrayList<Continente>();
	    for (Continente continenteCollectionContinenteToAttach : mundo
		    .getContinenteCollection()) {
		continenteCollectionContinenteToAttach = em.getReference(
			continenteCollectionContinenteToAttach.getClass(),
			continenteCollectionContinenteToAttach
				.getCodContinente());
		attachedContinenteCollection
			.add(continenteCollectionContinenteToAttach);
	    }
	    mundo.setContinenteCollection(attachedContinenteCollection);
	    Collection<Cor> attachedCorCollection = new ArrayList<Cor>();
	    for (Cor corCollectionCorToAttach : mundo.getCorCollection()) {
		corCollectionCorToAttach = em.getReference(
			corCollectionCorToAttach.getClass(),
			corCollectionCorToAttach.getCodCor());
		attachedCorCollection.add(corCollectionCorToAttach);
	    }
	    mundo.setCorCollection(attachedCorCollection);
	    Collection<Objetivo> attachedObjetivoCollection = new ArrayList<Objetivo>();
	    for (Objetivo objetivoCollectionObjetivoToAttach : mundo
		    .getObjetivoCollection()) {
		objetivoCollectionObjetivoToAttach = em.getReference(
			objetivoCollectionObjetivoToAttach.getClass(),
			objetivoCollectionObjetivoToAttach.getCodObjetivo());
		attachedObjetivoCollection
			.add(objetivoCollectionObjetivoToAttach);
	    }
	    mundo.setObjetivoCollection(attachedObjetivoCollection);
	    em.persist(mundo);
	    for (Continente continenteCollectionContinente : mundo
		    .getContinenteCollection()) {
		Mundo oldCodMundoOfContinenteCollectionContinente = continenteCollectionContinente
			.getCodMundo();
		continenteCollectionContinente.setCodMundo(mundo);
		continenteCollectionContinente = em
			.merge(continenteCollectionContinente);
		if (oldCodMundoOfContinenteCollectionContinente != null) {
		    oldCodMundoOfContinenteCollectionContinente
			    .getContinenteCollection().remove(
				    continenteCollectionContinente);
		    oldCodMundoOfContinenteCollectionContinente = em
			    .merge(oldCodMundoOfContinenteCollectionContinente);
		}
	    }
	    for (Cor corCollectionCor : mundo.getCorCollection()) {
		Mundo oldCodMundoOfCorCollectionCor = corCollectionCor
			.getCodMundo();
		corCollectionCor.setCodMundo(mundo);
		corCollectionCor = em.merge(corCollectionCor);
		if (oldCodMundoOfCorCollectionCor != null) {
		    oldCodMundoOfCorCollectionCor.getCorCollection().remove(
			    corCollectionCor);
		    oldCodMundoOfCorCollectionCor = em
			    .merge(oldCodMundoOfCorCollectionCor);
		}
	    }
	    for (Objetivo objetivoCollectionObjetivo : mundo
		    .getObjetivoCollection()) {
		Mundo oldCodMundoOfObjetivoCollectionObjetivo = objetivoCollectionObjetivo
			.getCodMundo();
		objetivoCollectionObjetivo.setCodMundo(mundo);
		objetivoCollectionObjetivo = em
			.merge(objetivoCollectionObjetivo);
		if (oldCodMundoOfObjetivoCollectionObjetivo != null) {
		    oldCodMundoOfObjetivoCollectionObjetivo
			    .getObjetivoCollection().remove(
				    objetivoCollectionObjetivo);
		    oldCodMundoOfObjetivoCollectionObjetivo = em
			    .merge(oldCodMundoOfObjetivoCollectionObjetivo);
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    if (findMundo(mundo.getCodMundo()) != null) {
		throw new PreexistingEntityException("Mundo " + mundo
			+ " already exists.", ex);
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Mundo mundo) throws IllegalOrphanException,
	    NonexistentEntityException, Exception {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Mundo persistentMundo = em.find(Mundo.class, mundo.getCodMundo());
	    Collection<Continente> continenteCollectionOld = persistentMundo
		    .getContinenteCollection();
	    Collection<Continente> continenteCollectionNew = mundo
		    .getContinenteCollection();
	    Collection<Cor> corCollectionOld = persistentMundo
		    .getCorCollection();
	    Collection<Cor> corCollectionNew = mundo.getCorCollection();
	    Collection<Objetivo> objetivoCollectionOld = persistentMundo
		    .getObjetivoCollection();
	    Collection<Objetivo> objetivoCollectionNew = mundo
		    .getObjetivoCollection();
	    List<String> illegalOrphanMessages = null;
	    for (Continente continenteCollectionOldContinente : continenteCollectionOld) {
		if (!continenteCollectionNew
			.contains(continenteCollectionOldContinente)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Continente "
			    + continenteCollectionOldContinente
			    + " since its codMundo field is not nullable.");
		}
	    }
	    for (Cor corCollectionOldCor : corCollectionOld) {
		if (!corCollectionNew.contains(corCollectionOldCor)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Cor "
			    + corCollectionOldCor
			    + " since its codMundo field is not nullable.");
		}
	    }
	    for (Objetivo objetivoCollectionOldObjetivo : objetivoCollectionOld) {
		if (!objetivoCollectionNew
			.contains(objetivoCollectionOldObjetivo)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Objetivo "
			    + objetivoCollectionOldObjetivo
			    + " since its codMundo field is not nullable.");
		}
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    Collection<Continente> attachedContinenteCollectionNew = new ArrayList<Continente>();
	    for (Continente continenteCollectionNewContinenteToAttach : continenteCollectionNew) {
		continenteCollectionNewContinenteToAttach = em.getReference(
			continenteCollectionNewContinenteToAttach.getClass(),
			continenteCollectionNewContinenteToAttach
				.getCodContinente());
		attachedContinenteCollectionNew
			.add(continenteCollectionNewContinenteToAttach);
	    }
	    continenteCollectionNew = attachedContinenteCollectionNew;
	    mundo.setContinenteCollection(continenteCollectionNew);
	    Collection<Cor> attachedCorCollectionNew = new ArrayList<Cor>();
	    for (Cor corCollectionNewCorToAttach : corCollectionNew) {
		corCollectionNewCorToAttach = em.getReference(
			corCollectionNewCorToAttach.getClass(),
			corCollectionNewCorToAttach.getCodCor());
		attachedCorCollectionNew.add(corCollectionNewCorToAttach);
	    }
	    corCollectionNew = attachedCorCollectionNew;
	    mundo.setCorCollection(corCollectionNew);
	    Collection<Objetivo> attachedObjetivoCollectionNew = new ArrayList<Objetivo>();
	    for (Objetivo objetivoCollectionNewObjetivoToAttach : objetivoCollectionNew) {
		objetivoCollectionNewObjetivoToAttach = em.getReference(
			objetivoCollectionNewObjetivoToAttach.getClass(),
			objetivoCollectionNewObjetivoToAttach.getCodObjetivo());
		attachedObjetivoCollectionNew
			.add(objetivoCollectionNewObjetivoToAttach);
	    }
	    objetivoCollectionNew = attachedObjetivoCollectionNew;
	    mundo.setObjetivoCollection(objetivoCollectionNew);
	    mundo = em.merge(mundo);
	    for (Continente continenteCollectionNewContinente : continenteCollectionNew) {
		if (!continenteCollectionOld
			.contains(continenteCollectionNewContinente)) {
		    Mundo oldCodMundoOfContinenteCollectionNewContinente = continenteCollectionNewContinente
			    .getCodMundo();
		    continenteCollectionNewContinente.setCodMundo(mundo);
		    continenteCollectionNewContinente = em
			    .merge(continenteCollectionNewContinente);
		    if (oldCodMundoOfContinenteCollectionNewContinente != null
			    && !oldCodMundoOfContinenteCollectionNewContinente
				    .equals(mundo)) {
			oldCodMundoOfContinenteCollectionNewContinente
				.getContinenteCollection().remove(
					continenteCollectionNewContinente);
			oldCodMundoOfContinenteCollectionNewContinente = em
				.merge(oldCodMundoOfContinenteCollectionNewContinente);
		    }
		}
	    }
	    for (Cor corCollectionNewCor : corCollectionNew) {
		if (!corCollectionOld.contains(corCollectionNewCor)) {
		    Mundo oldCodMundoOfCorCollectionNewCor = corCollectionNewCor
			    .getCodMundo();
		    corCollectionNewCor.setCodMundo(mundo);
		    corCollectionNewCor = em.merge(corCollectionNewCor);
		    if (oldCodMundoOfCorCollectionNewCor != null
			    && !oldCodMundoOfCorCollectionNewCor.equals(mundo)) {
			oldCodMundoOfCorCollectionNewCor.getCorCollection()
				.remove(corCollectionNewCor);
			oldCodMundoOfCorCollectionNewCor = em
				.merge(oldCodMundoOfCorCollectionNewCor);
		    }
		}
	    }
	    for (Objetivo objetivoCollectionNewObjetivo : objetivoCollectionNew) {
		if (!objetivoCollectionOld
			.contains(objetivoCollectionNewObjetivo)) {
		    Mundo oldCodMundoOfObjetivoCollectionNewObjetivo = objetivoCollectionNewObjetivo
			    .getCodMundo();
		    objetivoCollectionNewObjetivo.setCodMundo(mundo);
		    objetivoCollectionNewObjetivo = em
			    .merge(objetivoCollectionNewObjetivo);
		    if (oldCodMundoOfObjetivoCollectionNewObjetivo != null
			    && !oldCodMundoOfObjetivoCollectionNewObjetivo
				    .equals(mundo)) {
			oldCodMundoOfObjetivoCollectionNewObjetivo
				.getObjetivoCollection().remove(
					objetivoCollectionNewObjetivo);
			oldCodMundoOfObjetivoCollectionNewObjetivo = em
				.merge(oldCodMundoOfObjetivoCollectionNewObjetivo);
		    }
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		Integer id = mundo.getCodMundo();
		if (findMundo(id) == null) {
		    throw new NonexistentEntityException("The mundo with id "
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
	    Mundo mundo;
	    try {
		mundo = em.getReference(Mundo.class, id);
		mundo.getCodMundo();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The mundo with id " + id
			+ " no longer exists.", enfe);
	    }
	    List<String> illegalOrphanMessages = null;
	    Collection<Continente> continenteCollectionOrphanCheck = mundo
		    .getContinenteCollection();
	    for (Continente continenteCollectionOrphanCheckContinente : continenteCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Mundo ("
				+ mundo
				+ ") cannot be destroyed since the Continente "
				+ continenteCollectionOrphanCheckContinente
				+ " in its continenteCollection field has a non-nullable codMundo field.");
	    }
	    Collection<Cor> corCollectionOrphanCheck = mundo.getCorCollection();
	    for (Cor corCollectionOrphanCheckCor : corCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Mundo ("
				+ mundo
				+ ") cannot be destroyed since the Cor "
				+ corCollectionOrphanCheckCor
				+ " in its corCollection field has a non-nullable codMundo field.");
	    }
	    Collection<Objetivo> objetivoCollectionOrphanCheck = mundo
		    .getObjetivoCollection();
	    for (Objetivo objetivoCollectionOrphanCheckObjetivo : objetivoCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Mundo ("
				+ mundo
				+ ") cannot be destroyed since the Objetivo "
				+ objetivoCollectionOrphanCheckObjetivo
				+ " in its objetivoCollection field has a non-nullable codMundo field.");
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    em.remove(mundo);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Mundo> findMundoEntities() {
	return findMundoEntities(true, -1, -1);
    }

    public List<Mundo> findMundoEntities(int maxResults, int firstResult) {
	return findMundoEntities(false, maxResults, firstResult);
    }

    private List<Mundo> findMundoEntities(boolean all, int maxResults,
	    int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Mundo.class));
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

    public Mundo findMundo(Integer id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Mundo.class, id);
	} finally {
	    em.close();
	}
    }

    public int getMundoCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Mundo> rt = cq.from(Mundo.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
