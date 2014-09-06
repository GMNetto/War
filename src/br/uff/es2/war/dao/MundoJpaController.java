/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.dao;

import br.uff.es2.war.dao.exceptions.IllegalOrphanException;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Mundo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Gustavo
 */
public class MundoJpaController implements Serializable {

    public MundoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mundo mundo) throws PreexistingEntityException, Exception {
        if (mundo.getContinenteCollection() == null) {
            mundo.setContinenteCollection(new ArrayList<Continente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Continente> attachedContinenteCollection = new ArrayList<Continente>();
            for (Continente continenteCollectionContinenteToAttach : mundo.getContinenteCollection()) {
                continenteCollectionContinenteToAttach = em.getReference(continenteCollectionContinenteToAttach.getClass(), continenteCollectionContinenteToAttach.getCodContinente());
                attachedContinenteCollection.add(continenteCollectionContinenteToAttach);
            }
            mundo.setContinenteCollection(attachedContinenteCollection);
            em.persist(mundo);
            for (Continente continenteCollectionContinente : mundo.getContinenteCollection()) {
                Mundo oldCodMundoOfContinenteCollectionContinente = continenteCollectionContinente.getCodMundo();
                continenteCollectionContinente.setCodMundo(mundo);
                continenteCollectionContinente = em.merge(continenteCollectionContinente);
                if (oldCodMundoOfContinenteCollectionContinente != null) {
                    oldCodMundoOfContinenteCollectionContinente.getContinenteCollection().remove(continenteCollectionContinente);
                    oldCodMundoOfContinenteCollectionContinente = em.merge(oldCodMundoOfContinenteCollectionContinente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMundo(mundo.getCodMundo()) != null) {
                throw new PreexistingEntityException("Mundo " + mundo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mundo mundo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mundo persistentMundo = em.find(Mundo.class, mundo.getCodMundo());
            Collection<Continente> continenteCollectionOld = persistentMundo.getContinenteCollection();
            Collection<Continente> continenteCollectionNew = mundo.getContinenteCollection();
            List<String> illegalOrphanMessages = null;
            for (Continente continenteCollectionOldContinente : continenteCollectionOld) {
                if (!continenteCollectionNew.contains(continenteCollectionOldContinente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Continente " + continenteCollectionOldContinente + " since its codMundo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Continente> attachedContinenteCollectionNew = new ArrayList<Continente>();
            for (Continente continenteCollectionNewContinenteToAttach : continenteCollectionNew) {
                continenteCollectionNewContinenteToAttach = em.getReference(continenteCollectionNewContinenteToAttach.getClass(), continenteCollectionNewContinenteToAttach.getCodContinente());
                attachedContinenteCollectionNew.add(continenteCollectionNewContinenteToAttach);
            }
            continenteCollectionNew = attachedContinenteCollectionNew;
            mundo.setContinenteCollection(continenteCollectionNew);
            mundo = em.merge(mundo);
            for (Continente continenteCollectionNewContinente : continenteCollectionNew) {
                if (!continenteCollectionOld.contains(continenteCollectionNewContinente)) {
                    Mundo oldCodMundoOfContinenteCollectionNewContinente = continenteCollectionNewContinente.getCodMundo();
                    continenteCollectionNewContinente.setCodMundo(mundo);
                    continenteCollectionNewContinente = em.merge(continenteCollectionNewContinente);
                    if (oldCodMundoOfContinenteCollectionNewContinente != null && !oldCodMundoOfContinenteCollectionNewContinente.equals(mundo)) {
                        oldCodMundoOfContinenteCollectionNewContinente.getContinenteCollection().remove(continenteCollectionNewContinente);
                        oldCodMundoOfContinenteCollectionNewContinente = em.merge(oldCodMundoOfContinenteCollectionNewContinente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mundo.getCodMundo();
                if (findMundo(id) == null) {
                    throw new NonexistentEntityException("The mundo with id " + id + " no longer exists.");
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
            Mundo mundo;
            try {
                mundo = em.getReference(Mundo.class, id);
                mundo.getCodMundo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mundo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Continente> continenteCollectionOrphanCheck = mundo.getContinenteCollection();
            for (Continente continenteCollectionOrphanCheckContinente : continenteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mundo (" + mundo + ") cannot be destroyed since the Continente " + continenteCollectionOrphanCheckContinente + " in its continenteCollection field has a non-nullable codMundo field.");
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

    private List<Mundo> findMundoEntities(boolean all, int maxResults, int firstResult) {
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
