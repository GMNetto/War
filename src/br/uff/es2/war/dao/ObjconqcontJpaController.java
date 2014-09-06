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
import br.uff.es2.war.entity.Objetivo;
import br.uff.es2.war.entity.Continente;
import br.uff.es2.war.entity.Objconqcont;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Gustavo
 */
public class ObjconqcontJpaController implements Serializable {

    public ObjconqcontJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Objconqcont objconqcont) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (objconqcont.getContinenteCollection() == null) {
            objconqcont.setContinenteCollection(new ArrayList<Continente>());
        }
        List<String> illegalOrphanMessages = null;
        Objetivo objetivoOrphanCheck = objconqcont.getObjetivo();
        if (objetivoOrphanCheck != null) {
            Objconqcont oldObjconqcontOfObjetivo = objetivoOrphanCheck.getObjconqcont();
            if (oldObjconqcontOfObjetivo != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Objetivo " + objetivoOrphanCheck + " already has an item of type Objconqcont whose objetivo column cannot be null. Please make another selection for the objetivo field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objetivo objetivo = objconqcont.getObjetivo();
            if (objetivo != null) {
                objetivo = em.getReference(objetivo.getClass(), objetivo.getCodObjetivo());
                objconqcont.setObjetivo(objetivo);
            }
            Collection<Continente> attachedContinenteCollection = new ArrayList<Continente>();
            for (Continente continenteCollectionContinenteToAttach : objconqcont.getContinenteCollection()) {
                continenteCollectionContinenteToAttach = em.getReference(continenteCollectionContinenteToAttach.getClass(), continenteCollectionContinenteToAttach.getCodContinente());
                attachedContinenteCollection.add(continenteCollectionContinenteToAttach);
            }
            objconqcont.setContinenteCollection(attachedContinenteCollection);
            em.persist(objconqcont);
            if (objetivo != null) {
                objetivo.setObjconqcont(objconqcont);
                objetivo = em.merge(objetivo);
            }
            for (Continente continenteCollectionContinente : objconqcont.getContinenteCollection()) {
                continenteCollectionContinente.getObjconqcontCollection().add(objconqcont);
                continenteCollectionContinente = em.merge(continenteCollectionContinente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findObjconqcont(objconqcont.getCodObjetivo()) != null) {
                throw new PreexistingEntityException("Objconqcont " + objconqcont + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Objconqcont objconqcont) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objconqcont persistentObjconqcont = em.find(Objconqcont.class, objconqcont.getCodObjetivo());
            Objetivo objetivoOld = persistentObjconqcont.getObjetivo();
            Objetivo objetivoNew = objconqcont.getObjetivo();
            Collection<Continente> continenteCollectionOld = persistentObjconqcont.getContinenteCollection();
            Collection<Continente> continenteCollectionNew = objconqcont.getContinenteCollection();
            List<String> illegalOrphanMessages = null;
            if (objetivoNew != null && !objetivoNew.equals(objetivoOld)) {
                Objconqcont oldObjconqcontOfObjetivo = objetivoNew.getObjconqcont();
                if (oldObjconqcontOfObjetivo != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Objetivo " + objetivoNew + " already has an item of type Objconqcont whose objetivo column cannot be null. Please make another selection for the objetivo field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (objetivoNew != null) {
                objetivoNew = em.getReference(objetivoNew.getClass(), objetivoNew.getCodObjetivo());
                objconqcont.setObjetivo(objetivoNew);
            }
            Collection<Continente> attachedContinenteCollectionNew = new ArrayList<Continente>();
            for (Continente continenteCollectionNewContinenteToAttach : continenteCollectionNew) {
                continenteCollectionNewContinenteToAttach = em.getReference(continenteCollectionNewContinenteToAttach.getClass(), continenteCollectionNewContinenteToAttach.getCodContinente());
                attachedContinenteCollectionNew.add(continenteCollectionNewContinenteToAttach);
            }
            continenteCollectionNew = attachedContinenteCollectionNew;
            objconqcont.setContinenteCollection(continenteCollectionNew);
            objconqcont = em.merge(objconqcont);
            if (objetivoOld != null && !objetivoOld.equals(objetivoNew)) {
                objetivoOld.setObjconqcont(null);
                objetivoOld = em.merge(objetivoOld);
            }
            if (objetivoNew != null && !objetivoNew.equals(objetivoOld)) {
                objetivoNew.setObjconqcont(objconqcont);
                objetivoNew = em.merge(objetivoNew);
            }
            for (Continente continenteCollectionOldContinente : continenteCollectionOld) {
                if (!continenteCollectionNew.contains(continenteCollectionOldContinente)) {
                    continenteCollectionOldContinente.getObjconqcontCollection().remove(objconqcont);
                    continenteCollectionOldContinente = em.merge(continenteCollectionOldContinente);
                }
            }
            for (Continente continenteCollectionNewContinente : continenteCollectionNew) {
                if (!continenteCollectionOld.contains(continenteCollectionNewContinente)) {
                    continenteCollectionNewContinente.getObjconqcontCollection().add(objconqcont);
                    continenteCollectionNewContinente = em.merge(continenteCollectionNewContinente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = objconqcont.getCodObjetivo();
                if (findObjconqcont(id) == null) {
                    throw new NonexistentEntityException("The objconqcont with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objconqcont objconqcont;
            try {
                objconqcont = em.getReference(Objconqcont.class, id);
                objconqcont.getCodObjetivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The objconqcont with id " + id + " no longer exists.", enfe);
            }
            Objetivo objetivo = objconqcont.getObjetivo();
            if (objetivo != null) {
                objetivo.setObjconqcont(null);
                objetivo = em.merge(objetivo);
            }
            Collection<Continente> continenteCollection = objconqcont.getContinenteCollection();
            for (Continente continenteCollectionContinente : continenteCollection) {
                continenteCollectionContinente.getObjconqcontCollection().remove(objconqcont);
                continenteCollectionContinente = em.merge(continenteCollectionContinente);
            }
            em.remove(objconqcont);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Objconqcont> findObjconqcontEntities() {
        return findObjconqcontEntities(true, -1, -1);
    }

    public List<Objconqcont> findObjconqcontEntities(int maxResults, int firstResult) {
        return findObjconqcontEntities(false, maxResults, firstResult);
    }

    private List<Objconqcont> findObjconqcontEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Objconqcont.class));
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

    public Objconqcont findObjconqcont(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Objconqcont.class, id);
        } finally {
            em.close();
        }
    }

    public int getObjconqcontCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Objconqcont> rt = cq.from(Objconqcont.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
