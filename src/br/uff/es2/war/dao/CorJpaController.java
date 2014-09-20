/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.dao;

import br.uff.es2.war.dao.exceptions.IllegalOrphanException;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.entity.Cor;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Jogam;
import java.util.ArrayList;
import java.util.Collection;
import br.uff.es2.war.entity.Objderjogador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class CorJpaController implements Serializable {

    public CorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cor cor) {
        if (cor.getJogamCollection() == null) {
            cor.setJogamCollection(new ArrayList<Jogam>());
        }
        if (cor.getObjderjogadorCollection() == null) {
            cor.setObjderjogadorCollection(new ArrayList<Objderjogador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mundo codMundo = cor.getCodMundo();
            if (codMundo != null) {
                codMundo = em.getReference(codMundo.getClass(), codMundo.getCodMundo());
                cor.setCodMundo(codMundo);
            }
            Collection<Jogam> attachedJogamCollection = new ArrayList<Jogam>();
            for (Jogam jogamCollectionJogamToAttach : cor.getJogamCollection()) {
                jogamCollectionJogamToAttach = em.getReference(jogamCollectionJogamToAttach.getClass(), jogamCollectionJogamToAttach.getJogamPK());
                attachedJogamCollection.add(jogamCollectionJogamToAttach);
            }
            cor.setJogamCollection(attachedJogamCollection);
            Collection<Objderjogador> attachedObjderjogadorCollection = new ArrayList<Objderjogador>();
            for (Objderjogador objderjogadorCollectionObjderjogadorToAttach : cor.getObjderjogadorCollection()) {
                objderjogadorCollectionObjderjogadorToAttach = em.getReference(objderjogadorCollectionObjderjogadorToAttach.getClass(), objderjogadorCollectionObjderjogadorToAttach.getObjderjogadorPK());
                attachedObjderjogadorCollection.add(objderjogadorCollectionObjderjogadorToAttach);
            }
            cor.setObjderjogadorCollection(attachedObjderjogadorCollection);
            em.persist(cor);
            if (codMundo != null) {
                codMundo.getCorCollection().add(cor);
                codMundo = em.merge(codMundo);
            }
            for (Jogam jogamCollectionJogam : cor.getJogamCollection()) {
                Cor oldCodCorOfJogamCollectionJogam = jogamCollectionJogam.getCodCor();
                jogamCollectionJogam.setCodCor(cor);
                jogamCollectionJogam = em.merge(jogamCollectionJogam);
                if (oldCodCorOfJogamCollectionJogam != null) {
                    oldCodCorOfJogamCollectionJogam.getJogamCollection().remove(jogamCollectionJogam);
                    oldCodCorOfJogamCollectionJogam = em.merge(oldCodCorOfJogamCollectionJogam);
                }
            }
            for (Objderjogador objderjogadorCollectionObjderjogador : cor.getObjderjogadorCollection()) {
                Cor oldCorOfObjderjogadorCollectionObjderjogador = objderjogadorCollectionObjderjogador.getCor();
                objderjogadorCollectionObjderjogador.setCor(cor);
                objderjogadorCollectionObjderjogador = em.merge(objderjogadorCollectionObjderjogador);
                if (oldCorOfObjderjogadorCollectionObjderjogador != null) {
                    oldCorOfObjderjogadorCollectionObjderjogador.getObjderjogadorCollection().remove(objderjogadorCollectionObjderjogador);
                    oldCorOfObjderjogadorCollectionObjderjogador = em.merge(oldCorOfObjderjogadorCollectionObjderjogador);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cor cor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cor persistentCor = em.find(Cor.class, cor.getCodCor());
            Mundo codMundoOld = persistentCor.getCodMundo();
            Mundo codMundoNew = cor.getCodMundo();
            Collection<Jogam> jogamCollectionOld = persistentCor.getJogamCollection();
            Collection<Jogam> jogamCollectionNew = cor.getJogamCollection();
            Collection<Objderjogador> objderjogadorCollectionOld = persistentCor.getObjderjogadorCollection();
            Collection<Objderjogador> objderjogadorCollectionNew = cor.getObjderjogadorCollection();
            List<String> illegalOrphanMessages = null;
            for (Jogam jogamCollectionOldJogam : jogamCollectionOld) {
                if (!jogamCollectionNew.contains(jogamCollectionOldJogam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Jogam " + jogamCollectionOldJogam + " since its codCor field is not nullable.");
                }
            }
            for (Objderjogador objderjogadorCollectionOldObjderjogador : objderjogadorCollectionOld) {
                if (!objderjogadorCollectionNew.contains(objderjogadorCollectionOldObjderjogador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Objderjogador " + objderjogadorCollectionOldObjderjogador + " since its cor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codMundoNew != null) {
                codMundoNew = em.getReference(codMundoNew.getClass(), codMundoNew.getCodMundo());
                cor.setCodMundo(codMundoNew);
            }
            Collection<Jogam> attachedJogamCollectionNew = new ArrayList<Jogam>();
            for (Jogam jogamCollectionNewJogamToAttach : jogamCollectionNew) {
                jogamCollectionNewJogamToAttach = em.getReference(jogamCollectionNewJogamToAttach.getClass(), jogamCollectionNewJogamToAttach.getJogamPK());
                attachedJogamCollectionNew.add(jogamCollectionNewJogamToAttach);
            }
            jogamCollectionNew = attachedJogamCollectionNew;
            cor.setJogamCollection(jogamCollectionNew);
            Collection<Objderjogador> attachedObjderjogadorCollectionNew = new ArrayList<Objderjogador>();
            for (Objderjogador objderjogadorCollectionNewObjderjogadorToAttach : objderjogadorCollectionNew) {
                objderjogadorCollectionNewObjderjogadorToAttach = em.getReference(objderjogadorCollectionNewObjderjogadorToAttach.getClass(), objderjogadorCollectionNewObjderjogadorToAttach.getObjderjogadorPK());
                attachedObjderjogadorCollectionNew.add(objderjogadorCollectionNewObjderjogadorToAttach);
            }
            objderjogadorCollectionNew = attachedObjderjogadorCollectionNew;
            cor.setObjderjogadorCollection(objderjogadorCollectionNew);
            cor = em.merge(cor);
            if (codMundoOld != null && !codMundoOld.equals(codMundoNew)) {
                codMundoOld.getCorCollection().remove(cor);
                codMundoOld = em.merge(codMundoOld);
            }
            if (codMundoNew != null && !codMundoNew.equals(codMundoOld)) {
                codMundoNew.getCorCollection().add(cor);
                codMundoNew = em.merge(codMundoNew);
            }
            for (Jogam jogamCollectionNewJogam : jogamCollectionNew) {
                if (!jogamCollectionOld.contains(jogamCollectionNewJogam)) {
                    Cor oldCodCorOfJogamCollectionNewJogam = jogamCollectionNewJogam.getCodCor();
                    jogamCollectionNewJogam.setCodCor(cor);
                    jogamCollectionNewJogam = em.merge(jogamCollectionNewJogam);
                    if (oldCodCorOfJogamCollectionNewJogam != null && !oldCodCorOfJogamCollectionNewJogam.equals(cor)) {
                        oldCodCorOfJogamCollectionNewJogam.getJogamCollection().remove(jogamCollectionNewJogam);
                        oldCodCorOfJogamCollectionNewJogam = em.merge(oldCodCorOfJogamCollectionNewJogam);
                    }
                }
            }
            for (Objderjogador objderjogadorCollectionNewObjderjogador : objderjogadorCollectionNew) {
                if (!objderjogadorCollectionOld.contains(objderjogadorCollectionNewObjderjogador)) {
                    Cor oldCorOfObjderjogadorCollectionNewObjderjogador = objderjogadorCollectionNewObjderjogador.getCor();
                    objderjogadorCollectionNewObjderjogador.setCor(cor);
                    objderjogadorCollectionNewObjderjogador = em.merge(objderjogadorCollectionNewObjderjogador);
                    if (oldCorOfObjderjogadorCollectionNewObjderjogador != null && !oldCorOfObjderjogadorCollectionNewObjderjogador.equals(cor)) {
                        oldCorOfObjderjogadorCollectionNewObjderjogador.getObjderjogadorCollection().remove(objderjogadorCollectionNewObjderjogador);
                        oldCorOfObjderjogadorCollectionNewObjderjogador = em.merge(oldCorOfObjderjogadorCollectionNewObjderjogador);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cor.getCodCor();
                if (findCor(id) == null) {
                    throw new NonexistentEntityException("The cor with id " + id + " no longer exists.");
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
            Cor cor;
            try {
                cor = em.getReference(Cor.class, id);
                cor.getCodCor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Jogam> jogamCollectionOrphanCheck = cor.getJogamCollection();
            for (Jogam jogamCollectionOrphanCheckJogam : jogamCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cor (" + cor + ") cannot be destroyed since the Jogam " + jogamCollectionOrphanCheckJogam + " in its jogamCollection field has a non-nullable codCor field.");
            }
            Collection<Objderjogador> objderjogadorCollectionOrphanCheck = cor.getObjderjogadorCollection();
            for (Objderjogador objderjogadorCollectionOrphanCheckObjderjogador : objderjogadorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cor (" + cor + ") cannot be destroyed since the Objderjogador " + objderjogadorCollectionOrphanCheckObjderjogador + " in its objderjogadorCollection field has a non-nullable cor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Mundo codMundo = cor.getCodMundo();
            if (codMundo != null) {
                codMundo.getCorCollection().remove(cor);
                codMundo = em.merge(codMundo);
            }
            em.remove(cor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cor> findCorEntities() {
        return findCorEntities(true, -1, -1);
    }

    public List<Cor> findCorEntities(int maxResults, int firstResult) {
        return findCorEntities(false, maxResults, firstResult);
    }

    private List<Cor> findCorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cor.class));
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

    public Cor findCor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cor.class, id);
        } finally {
            em.close();
        }
    }

    public int getCorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cor> rt = cq.from(Cor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
