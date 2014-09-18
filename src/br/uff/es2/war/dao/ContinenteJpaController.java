/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.dao;

import br.uff.es2.war.dao.exceptions.IllegalOrphanException;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Continente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Objconqcont;
import java.util.ArrayList;
import java.util.Collection;
import br.uff.es2.war.entity.Territorio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class ContinenteJpaController implements Serializable {

    public ContinenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Continente continente) {
        if (continente.getObjconqcontCollection() == null) {
            continente.setObjconqcontCollection(new ArrayList<Objconqcont>());
        }
        if (continente.getTerritorioCollection() == null) {
            continente.setTerritorioCollection(new ArrayList<Territorio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mundo codMundo = continente.getCodMundo();
            if (codMundo != null) {
                codMundo = em.getReference(codMundo.getClass(), codMundo.getCodMundo());
                continente.setCodMundo(codMundo);
            }
            Collection<Objconqcont> attachedObjconqcontCollection = new ArrayList<Objconqcont>();
            for (Objconqcont objconqcontCollectionObjconqcontToAttach : continente.getObjconqcontCollection()) {
                objconqcontCollectionObjconqcontToAttach = em.getReference(objconqcontCollectionObjconqcontToAttach.getClass(), objconqcontCollectionObjconqcontToAttach.getCodObjetivo());
                attachedObjconqcontCollection.add(objconqcontCollectionObjconqcontToAttach);
            }
            continente.setObjconqcontCollection(attachedObjconqcontCollection);
            Collection<Territorio> attachedTerritorioCollection = new ArrayList<Territorio>();
            for (Territorio territorioCollectionTerritorioToAttach : continente.getTerritorioCollection()) {
                territorioCollectionTerritorioToAttach = em.getReference(territorioCollectionTerritorioToAttach.getClass(), territorioCollectionTerritorioToAttach.getCodTerritorio());
                attachedTerritorioCollection.add(territorioCollectionTerritorioToAttach);
            }
            continente.setTerritorioCollection(attachedTerritorioCollection);
            em.persist(continente);
            if (codMundo != null) {
                codMundo.getContinenteCollection().add(continente);
                codMundo = em.merge(codMundo);
            }
            for (Objconqcont objconqcontCollectionObjconqcont : continente.getObjconqcontCollection()) {
                objconqcontCollectionObjconqcont.getContinenteCollection().add(continente);
                objconqcontCollectionObjconqcont = em.merge(objconqcontCollectionObjconqcont);
            }
            for (Territorio territorioCollectionTerritorio : continente.getTerritorioCollection()) {
                Continente oldCodContinenteOfTerritorioCollectionTerritorio = territorioCollectionTerritorio.getCodContinente();
                territorioCollectionTerritorio.setCodContinente(continente);
                territorioCollectionTerritorio = em.merge(territorioCollectionTerritorio);
                if (oldCodContinenteOfTerritorioCollectionTerritorio != null) {
                    oldCodContinenteOfTerritorioCollectionTerritorio.getTerritorioCollection().remove(territorioCollectionTerritorio);
                    oldCodContinenteOfTerritorioCollectionTerritorio = em.merge(oldCodContinenteOfTerritorioCollectionTerritorio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Continente continente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Continente persistentContinente = em.find(Continente.class, continente.getCodContinente());
            Mundo codMundoOld = persistentContinente.getCodMundo();
            Mundo codMundoNew = continente.getCodMundo();
            Collection<Objconqcont> objconqcontCollectionOld = persistentContinente.getObjconqcontCollection();
            Collection<Objconqcont> objconqcontCollectionNew = continente.getObjconqcontCollection();
            Collection<Territorio> territorioCollectionOld = persistentContinente.getTerritorioCollection();
            Collection<Territorio> territorioCollectionNew = continente.getTerritorioCollection();
            List<String> illegalOrphanMessages = null;
            for (Territorio territorioCollectionOldTerritorio : territorioCollectionOld) {
                if (!territorioCollectionNew.contains(territorioCollectionOldTerritorio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Territorio " + territorioCollectionOldTerritorio + " since its codContinente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codMundoNew != null) {
                codMundoNew = em.getReference(codMundoNew.getClass(), codMundoNew.getCodMundo());
                continente.setCodMundo(codMundoNew);
            }
            Collection<Objconqcont> attachedObjconqcontCollectionNew = new ArrayList<Objconqcont>();
            for (Objconqcont objconqcontCollectionNewObjconqcontToAttach : objconqcontCollectionNew) {
                objconqcontCollectionNewObjconqcontToAttach = em.getReference(objconqcontCollectionNewObjconqcontToAttach.getClass(), objconqcontCollectionNewObjconqcontToAttach.getCodObjetivo());
                attachedObjconqcontCollectionNew.add(objconqcontCollectionNewObjconqcontToAttach);
            }
            objconqcontCollectionNew = attachedObjconqcontCollectionNew;
            continente.setObjconqcontCollection(objconqcontCollectionNew);
            Collection<Territorio> attachedTerritorioCollectionNew = new ArrayList<Territorio>();
            for (Territorio territorioCollectionNewTerritorioToAttach : territorioCollectionNew) {
                territorioCollectionNewTerritorioToAttach = em.getReference(territorioCollectionNewTerritorioToAttach.getClass(), territorioCollectionNewTerritorioToAttach.getCodTerritorio());
                attachedTerritorioCollectionNew.add(territorioCollectionNewTerritorioToAttach);
            }
            territorioCollectionNew = attachedTerritorioCollectionNew;
            continente.setTerritorioCollection(territorioCollectionNew);
            continente = em.merge(continente);
            if (codMundoOld != null && !codMundoOld.equals(codMundoNew)) {
                codMundoOld.getContinenteCollection().remove(continente);
                codMundoOld = em.merge(codMundoOld);
            }
            if (codMundoNew != null && !codMundoNew.equals(codMundoOld)) {
                codMundoNew.getContinenteCollection().add(continente);
                codMundoNew = em.merge(codMundoNew);
            }
            for (Objconqcont objconqcontCollectionOldObjconqcont : objconqcontCollectionOld) {
                if (!objconqcontCollectionNew.contains(objconqcontCollectionOldObjconqcont)) {
                    objconqcontCollectionOldObjconqcont.getContinenteCollection().remove(continente);
                    objconqcontCollectionOldObjconqcont = em.merge(objconqcontCollectionOldObjconqcont);
                }
            }
            for (Objconqcont objconqcontCollectionNewObjconqcont : objconqcontCollectionNew) {
                if (!objconqcontCollectionOld.contains(objconqcontCollectionNewObjconqcont)) {
                    objconqcontCollectionNewObjconqcont.getContinenteCollection().add(continente);
                    objconqcontCollectionNewObjconqcont = em.merge(objconqcontCollectionNewObjconqcont);
                }
            }
            for (Territorio territorioCollectionNewTerritorio : territorioCollectionNew) {
                if (!territorioCollectionOld.contains(territorioCollectionNewTerritorio)) {
                    Continente oldCodContinenteOfTerritorioCollectionNewTerritorio = territorioCollectionNewTerritorio.getCodContinente();
                    territorioCollectionNewTerritorio.setCodContinente(continente);
                    territorioCollectionNewTerritorio = em.merge(territorioCollectionNewTerritorio);
                    if (oldCodContinenteOfTerritorioCollectionNewTerritorio != null && !oldCodContinenteOfTerritorioCollectionNewTerritorio.equals(continente)) {
                        oldCodContinenteOfTerritorioCollectionNewTerritorio.getTerritorioCollection().remove(territorioCollectionNewTerritorio);
                        oldCodContinenteOfTerritorioCollectionNewTerritorio = em.merge(oldCodContinenteOfTerritorioCollectionNewTerritorio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = continente.getCodContinente();
                if (findContinente(id) == null) {
                    throw new NonexistentEntityException("The continente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Continente continente;
            try {
                continente = em.getReference(Continente.class, id);
                continente.getCodContinente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The continente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Territorio> territorioCollectionOrphanCheck = continente.getTerritorioCollection();
            for (Territorio territorioCollectionOrphanCheckTerritorio : territorioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Continente (" + continente + ") cannot be destroyed since the Territorio " + territorioCollectionOrphanCheckTerritorio + " in its territorioCollection field has a non-nullable codContinente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Mundo codMundo = continente.getCodMundo();
            if (codMundo != null) {
                codMundo.getContinenteCollection().remove(continente);
                codMundo = em.merge(codMundo);
            }
            Collection<Objconqcont> objconqcontCollection = continente.getObjconqcontCollection();
            for (Objconqcont objconqcontCollectionObjconqcont : objconqcontCollection) {
                objconqcontCollectionObjconqcont.getContinenteCollection().remove(continente);
                objconqcontCollectionObjconqcont = em.merge(objconqcontCollectionObjconqcont);
            }
            em.remove(continente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Continente> findContinenteEntities() {
        return findContinenteEntities(true, -1, -1);
    }

    public List<Continente> findContinenteEntities(int maxResults, int firstResult) {
        return findContinenteEntities(false, maxResults, firstResult);
    }

    private List<Continente> findContinenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Continente.class));
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

    public Continente findContinente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Continente.class, id);
        } finally {
            em.close();
        }
    }

    public int getContinenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Continente> rt = cq.from(Continente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
