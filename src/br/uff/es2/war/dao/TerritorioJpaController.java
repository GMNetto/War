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
import br.uff.es2.war.entity.Carta;
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Ocupacao;
import br.uff.es2.war.entity.Territorio;

/**
 * 
 * @author Victor
 */
public class TerritorioJpaController implements Serializable {

    public TerritorioJpaController(EntityManagerFactory emf) {
	this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Territorio territorio)
	    throws PreexistingEntityException, Exception {
	if (territorio.getTerritorioCollection() == null) {
	    territorio.setTerritorioCollection(new ArrayList<Territorio>());
	}
	if (territorio.getTerritorioCollection1() == null) {
	    territorio.setTerritorioCollection1(new ArrayList<Territorio>());
	}
	if (territorio.getOcupacaoCollection() == null) {
	    territorio.setOcupacaoCollection(new ArrayList<Ocupacao>());
	}
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Carta carta = territorio.getCarta();
	    if (carta != null) {
		carta = em.getReference(carta.getClass(), carta.getCodCarta());
		territorio.setCarta(carta);
	    }
	    Continente codContinente = territorio.getCodContinente();
	    if (codContinente != null) {
		codContinente = em.getReference(codContinente.getClass(),
			codContinente.getCodContinente());
		territorio.setCodContinente(codContinente);
	    }
	    Collection<Territorio> attachedTerritorioCollection = new ArrayList<Territorio>();
	    for (Territorio territorioCollectionTerritorioToAttach : territorio
		    .getTerritorioCollection()) {
		territorioCollectionTerritorioToAttach = em.getReference(
			territorioCollectionTerritorioToAttach.getClass(),
			territorioCollectionTerritorioToAttach
				.getCodTerritorio());
		attachedTerritorioCollection
			.add(territorioCollectionTerritorioToAttach);
	    }
	    territorio.setTerritorioCollection(attachedTerritorioCollection);
	    Collection<Territorio> attachedTerritorioCollection1 = new ArrayList<Territorio>();
	    for (Territorio territorioCollection1TerritorioToAttach : territorio
		    .getTerritorioCollection1()) {
		territorioCollection1TerritorioToAttach = em.getReference(
			territorioCollection1TerritorioToAttach.getClass(),
			territorioCollection1TerritorioToAttach
				.getCodTerritorio());
		attachedTerritorioCollection1
			.add(territorioCollection1TerritorioToAttach);
	    }
	    territorio.setTerritorioCollection1(attachedTerritorioCollection1);
	    Collection<Ocupacao> attachedOcupacaoCollection = new ArrayList<Ocupacao>();
	    for (Ocupacao ocupacaoCollectionOcupacaoToAttach : territorio
		    .getOcupacaoCollection()) {
		ocupacaoCollectionOcupacaoToAttach = em.getReference(
			ocupacaoCollectionOcupacaoToAttach.getClass(),
			ocupacaoCollectionOcupacaoToAttach.getOcupacaoPK());
		attachedOcupacaoCollection
			.add(ocupacaoCollectionOcupacaoToAttach);
	    }
	    territorio.setOcupacaoCollection(attachedOcupacaoCollection);
	    em.persist(territorio);
	    if (carta != null) {
		Territorio oldCodTerritorioOfCarta = carta.getCodTerritorio();
		if (oldCodTerritorioOfCarta != null) {
		    oldCodTerritorioOfCarta.setCarta(null);
		    oldCodTerritorioOfCarta = em.merge(oldCodTerritorioOfCarta);
		}
		carta.setCodTerritorio(territorio);
		carta = em.merge(carta);
	    }
	    if (codContinente != null) {
		codContinente.getTerritorioCollection().add(territorio);
		codContinente = em.merge(codContinente);
	    }
	    for (Territorio territorioCollectionTerritorio : territorio
		    .getTerritorioCollection()) {
		territorioCollectionTerritorio.getTerritorioCollection().add(
			territorio);
		territorioCollectionTerritorio = em
			.merge(territorioCollectionTerritorio);
	    }
	    for (Territorio territorioCollection1Territorio : territorio
		    .getTerritorioCollection1()) {
		territorioCollection1Territorio.getTerritorioCollection().add(
			territorio);
		territorioCollection1Territorio = em
			.merge(territorioCollection1Territorio);
	    }
	    for (Ocupacao ocupacaoCollectionOcupacao : territorio
		    .getOcupacaoCollection()) {
		Territorio oldTerritorioOfOcupacaoCollectionOcupacao = ocupacaoCollectionOcupacao
			.getTerritorio();
		ocupacaoCollectionOcupacao.setTerritorio(territorio);
		ocupacaoCollectionOcupacao = em
			.merge(ocupacaoCollectionOcupacao);
		if (oldTerritorioOfOcupacaoCollectionOcupacao != null) {
		    oldTerritorioOfOcupacaoCollectionOcupacao
			    .getOcupacaoCollection().remove(
				    ocupacaoCollectionOcupacao);
		    oldTerritorioOfOcupacaoCollectionOcupacao = em
			    .merge(oldTerritorioOfOcupacaoCollectionOcupacao);
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    if (findTerritorio(territorio.getCodTerritorio()) != null) {
		throw new PreexistingEntityException("Territorio " + territorio
			+ " already exists.", ex);
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Territorio territorio) throws IllegalOrphanException,
	    NonexistentEntityException, Exception {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Territorio persistentTerritorio = em.find(Territorio.class,
		    territorio.getCodTerritorio());
	    Carta cartaOld = persistentTerritorio.getCarta();
	    Carta cartaNew = territorio.getCarta();
	    Continente codContinenteOld = persistentTerritorio
		    .getCodContinente();
	    Continente codContinenteNew = territorio.getCodContinente();
	    Collection<Territorio> territorioCollectionOld = persistentTerritorio
		    .getTerritorioCollection();
	    Collection<Territorio> territorioCollectionNew = territorio
		    .getTerritorioCollection();
	    Collection<Territorio> territorioCollection1Old = persistentTerritorio
		    .getTerritorioCollection1();
	    Collection<Territorio> territorioCollection1New = territorio
		    .getTerritorioCollection1();
	    Collection<Ocupacao> ocupacaoCollectionOld = persistentTerritorio
		    .getOcupacaoCollection();
	    Collection<Ocupacao> ocupacaoCollectionNew = territorio
		    .getOcupacaoCollection();
	    List<String> illegalOrphanMessages = null;
	    for (Ocupacao ocupacaoCollectionOldOcupacao : ocupacaoCollectionOld) {
		if (!ocupacaoCollectionNew
			.contains(ocupacaoCollectionOldOcupacao)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Ocupacao "
			    + ocupacaoCollectionOldOcupacao
			    + " since its territorio field is not nullable.");
		}
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    if (cartaNew != null) {
		cartaNew = em.getReference(cartaNew.getClass(),
			cartaNew.getCodCarta());
		territorio.setCarta(cartaNew);
	    }
	    if (codContinenteNew != null) {
		codContinenteNew = em.getReference(codContinenteNew.getClass(),
			codContinenteNew.getCodContinente());
		territorio.setCodContinente(codContinenteNew);
	    }
	    Collection<Territorio> attachedTerritorioCollectionNew = new ArrayList<Territorio>();
	    for (Territorio territorioCollectionNewTerritorioToAttach : territorioCollectionNew) {
		territorioCollectionNewTerritorioToAttach = em.getReference(
			territorioCollectionNewTerritorioToAttach.getClass(),
			territorioCollectionNewTerritorioToAttach
				.getCodTerritorio());
		attachedTerritorioCollectionNew
			.add(territorioCollectionNewTerritorioToAttach);
	    }
	    territorioCollectionNew = attachedTerritorioCollectionNew;
	    territorio.setTerritorioCollection(territorioCollectionNew);
	    Collection<Territorio> attachedTerritorioCollection1New = new ArrayList<Territorio>();
	    for (Territorio territorioCollection1NewTerritorioToAttach : territorioCollection1New) {
		territorioCollection1NewTerritorioToAttach = em.getReference(
			territorioCollection1NewTerritorioToAttach.getClass(),
			territorioCollection1NewTerritorioToAttach
				.getCodTerritorio());
		attachedTerritorioCollection1New
			.add(territorioCollection1NewTerritorioToAttach);
	    }
	    territorioCollection1New = attachedTerritorioCollection1New;
	    territorio.setTerritorioCollection1(territorioCollection1New);
	    Collection<Ocupacao> attachedOcupacaoCollectionNew = new ArrayList<Ocupacao>();
	    for (Ocupacao ocupacaoCollectionNewOcupacaoToAttach : ocupacaoCollectionNew) {
		ocupacaoCollectionNewOcupacaoToAttach = em.getReference(
			ocupacaoCollectionNewOcupacaoToAttach.getClass(),
			ocupacaoCollectionNewOcupacaoToAttach.getOcupacaoPK());
		attachedOcupacaoCollectionNew
			.add(ocupacaoCollectionNewOcupacaoToAttach);
	    }
	    ocupacaoCollectionNew = attachedOcupacaoCollectionNew;
	    territorio.setOcupacaoCollection(ocupacaoCollectionNew);
	    territorio = em.merge(territorio);
	    if (cartaOld != null && !cartaOld.equals(cartaNew)) {
		cartaOld.setCodTerritorio(null);
		cartaOld = em.merge(cartaOld);
	    }
	    if (cartaNew != null && !cartaNew.equals(cartaOld)) {
		Territorio oldCodTerritorioOfCarta = cartaNew
			.getCodTerritorio();
		if (oldCodTerritorioOfCarta != null) {
		    oldCodTerritorioOfCarta.setCarta(null);
		    oldCodTerritorioOfCarta = em.merge(oldCodTerritorioOfCarta);
		}
		cartaNew.setCodTerritorio(territorio);
		cartaNew = em.merge(cartaNew);
	    }
	    if (codContinenteOld != null
		    && !codContinenteOld.equals(codContinenteNew)) {
		codContinenteOld.getTerritorioCollection().remove(territorio);
		codContinenteOld = em.merge(codContinenteOld);
	    }
	    if (codContinenteNew != null
		    && !codContinenteNew.equals(codContinenteOld)) {
		codContinenteNew.getTerritorioCollection().add(territorio);
		codContinenteNew = em.merge(codContinenteNew);
	    }
	    for (Territorio territorioCollectionOldTerritorio : territorioCollectionOld) {
		if (!territorioCollectionNew
			.contains(territorioCollectionOldTerritorio)) {
		    territorioCollectionOldTerritorio.getTerritorioCollection()
			    .remove(territorio);
		    territorioCollectionOldTerritorio = em
			    .merge(territorioCollectionOldTerritorio);
		}
	    }
	    for (Territorio territorioCollectionNewTerritorio : territorioCollectionNew) {
		if (!territorioCollectionOld
			.contains(territorioCollectionNewTerritorio)) {
		    territorioCollectionNewTerritorio.getTerritorioCollection()
			    .add(territorio);
		    territorioCollectionNewTerritorio = em
			    .merge(territorioCollectionNewTerritorio);
		}
	    }
	    for (Territorio territorioCollection1OldTerritorio : territorioCollection1Old) {
		if (!territorioCollection1New
			.contains(territorioCollection1OldTerritorio)) {
		    territorioCollection1OldTerritorio
			    .getTerritorioCollection().remove(territorio);
		    territorioCollection1OldTerritorio = em
			    .merge(territorioCollection1OldTerritorio);
		}
	    }
	    for (Territorio territorioCollection1NewTerritorio : territorioCollection1New) {
		if (!territorioCollection1Old
			.contains(territorioCollection1NewTerritorio)) {
		    territorioCollection1NewTerritorio
			    .getTerritorioCollection().add(territorio);
		    territorioCollection1NewTerritorio = em
			    .merge(territorioCollection1NewTerritorio);
		}
	    }
	    for (Ocupacao ocupacaoCollectionNewOcupacao : ocupacaoCollectionNew) {
		if (!ocupacaoCollectionOld
			.contains(ocupacaoCollectionNewOcupacao)) {
		    Territorio oldTerritorioOfOcupacaoCollectionNewOcupacao = ocupacaoCollectionNewOcupacao
			    .getTerritorio();
		    ocupacaoCollectionNewOcupacao.setTerritorio(territorio);
		    ocupacaoCollectionNewOcupacao = em
			    .merge(ocupacaoCollectionNewOcupacao);
		    if (oldTerritorioOfOcupacaoCollectionNewOcupacao != null
			    && !oldTerritorioOfOcupacaoCollectionNewOcupacao
				    .equals(territorio)) {
			oldTerritorioOfOcupacaoCollectionNewOcupacao
				.getOcupacaoCollection().remove(
					ocupacaoCollectionNewOcupacao);
			oldTerritorioOfOcupacaoCollectionNewOcupacao = em
				.merge(oldTerritorioOfOcupacaoCollectionNewOcupacao);
		    }
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		Integer id = territorio.getCodTerritorio();
		if (findTerritorio(id) == null) {
		    throw new NonexistentEntityException(
			    "The territorio with id " + id
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

    public void destroy(Integer id) throws IllegalOrphanException,
	    NonexistentEntityException {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Territorio territorio;
	    try {
		territorio = em.getReference(Territorio.class, id);
		territorio.getCodTerritorio();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The territorio with id "
			+ id + " no longer exists.", enfe);
	    }
	    List<String> illegalOrphanMessages = null;
	    Collection<Ocupacao> ocupacaoCollectionOrphanCheck = territorio
		    .getOcupacaoCollection();
	    for (Ocupacao ocupacaoCollectionOrphanCheckOcupacao : ocupacaoCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Territorio ("
				+ territorio
				+ ") cannot be destroyed since the Ocupacao "
				+ ocupacaoCollectionOrphanCheckOcupacao
				+ " in its ocupacaoCollection field has a non-nullable territorio field.");
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    Carta carta = territorio.getCarta();
	    if (carta != null) {
		carta.setCodTerritorio(null);
		carta = em.merge(carta);
	    }
	    Continente codContinente = territorio.getCodContinente();
	    if (codContinente != null) {
		codContinente.getTerritorioCollection().remove(territorio);
		codContinente = em.merge(codContinente);
	    }
	    Collection<Territorio> territorioCollection = territorio
		    .getTerritorioCollection();
	    for (Territorio territorioCollectionTerritorio : territorioCollection) {
		territorioCollectionTerritorio.getTerritorioCollection()
			.remove(territorio);
		territorioCollectionTerritorio = em
			.merge(territorioCollectionTerritorio);
	    }
	    Collection<Territorio> territorioCollection1 = territorio
		    .getTerritorioCollection1();
	    for (Territorio territorioCollection1Territorio : territorioCollection1) {
		territorioCollection1Territorio.getTerritorioCollection()
			.remove(territorio);
		territorioCollection1Territorio = em
			.merge(territorioCollection1Territorio);
	    }
	    em.remove(territorio);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Territorio> findTerritorioEntities() {
	return findTerritorioEntities(true, -1, -1);
    }

    public List<Territorio> findTerritorioEntities(int maxResults,
	    int firstResult) {
	return findTerritorioEntities(false, maxResults, firstResult);
    }

    private List<Territorio> findTerritorioEntities(boolean all,
	    int maxResults, int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Territorio.class));
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

    public Territorio findTerritorio(Integer id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Territorio.class, id);
	} finally {
	    em.close();
	}
    }

    public int getTerritorioCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Territorio> rt = cq.from(Territorio.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
