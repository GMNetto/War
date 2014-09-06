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
import br.uff.es2.war.entity.Objterritorio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Gustavo
 */
public class ObjterritorioJpaController implements Serializable {

    public ObjterritorioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Objterritorio objterritorio) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Objetivo objetivoOrphanCheck = objterritorio.getObjetivo();
        if (objetivoOrphanCheck != null) {
            Objterritorio oldObjterritorioOfObjetivo = objetivoOrphanCheck.getObjterritorio();
            if (oldObjterritorioOfObjetivo != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Objetivo " + objetivoOrphanCheck + " already has an item of type Objterritorio whose objetivo column cannot be null. Please make another selection for the objetivo field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objetivo objetivo = objterritorio.getObjetivo();
            if (objetivo != null) {
                objetivo = em.getReference(objetivo.getClass(), objetivo.getCodObjetivo());
                objterritorio.setObjetivo(objetivo);
            }
            em.persist(objterritorio);
            if (objetivo != null) {
                objetivo.setObjterritorio(objterritorio);
                objetivo = em.merge(objetivo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findObjterritorio(objterritorio.getCodObjetivo()) != null) {
                throw new PreexistingEntityException("Objterritorio " + objterritorio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Objterritorio objterritorio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objterritorio persistentObjterritorio = em.find(Objterritorio.class, objterritorio.getCodObjetivo());
            Objetivo objetivoOld = persistentObjterritorio.getObjetivo();
            Objetivo objetivoNew = objterritorio.getObjetivo();
            List<String> illegalOrphanMessages = null;
            if (objetivoNew != null && !objetivoNew.equals(objetivoOld)) {
                Objterritorio oldObjterritorioOfObjetivo = objetivoNew.getObjterritorio();
                if (oldObjterritorioOfObjetivo != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Objetivo " + objetivoNew + " already has an item of type Objterritorio whose objetivo column cannot be null. Please make another selection for the objetivo field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (objetivoNew != null) {
                objetivoNew = em.getReference(objetivoNew.getClass(), objetivoNew.getCodObjetivo());
                objterritorio.setObjetivo(objetivoNew);
            }
            objterritorio = em.merge(objterritorio);
            if (objetivoOld != null && !objetivoOld.equals(objetivoNew)) {
                objetivoOld.setObjterritorio(null);
                objetivoOld = em.merge(objetivoOld);
            }
            if (objetivoNew != null && !objetivoNew.equals(objetivoOld)) {
                objetivoNew.setObjterritorio(objterritorio);
                objetivoNew = em.merge(objetivoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = objterritorio.getCodObjetivo();
                if (findObjterritorio(id) == null) {
                    throw new NonexistentEntityException("The objterritorio with id " + id + " no longer exists.");
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
            Objterritorio objterritorio;
            try {
                objterritorio = em.getReference(Objterritorio.class, id);
                objterritorio.getCodObjetivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The objterritorio with id " + id + " no longer exists.", enfe);
            }
            Objetivo objetivo = objterritorio.getObjetivo();
            if (objetivo != null) {
                objetivo.setObjterritorio(null);
                objetivo = em.merge(objetivo);
            }
            em.remove(objterritorio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Objterritorio> findObjterritorioEntities() {
        return findObjterritorioEntities(true, -1, -1);
    }

    public List<Objterritorio> findObjterritorioEntities(int maxResults, int firstResult) {
        return findObjterritorioEntities(false, maxResults, firstResult);
    }

    private List<Objterritorio> findObjterritorioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Objterritorio.class));
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

    public Objterritorio findObjterritorio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Objterritorio.class, id);
        } finally {
            em.close();
        }
    }

    public int getObjterritorioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Objterritorio> rt = cq.from(Objterritorio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
