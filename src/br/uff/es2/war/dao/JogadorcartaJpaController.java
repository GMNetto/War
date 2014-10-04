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
import br.uff.es2.war.entity.Carta;
import br.uff.es2.war.entity.Jogadorcarta;
import br.uff.es2.war.entity.JogadorcartaPK;
import br.uff.es2.war.entity.Jogam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class JogadorcartaJpaController implements Serializable {

    public JogadorcartaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jogadorcarta jogadorcarta) throws PreexistingEntityException, Exception {
        if (jogadorcarta.getJogadorcartaPK() == null) {
            jogadorcarta.setJogadorcartaPK(new JogadorcartaPK());
        }
        jogadorcarta.getJogadorcartaPK().setCodCarta(jogadorcarta.getCarta().getCodCarta());
        jogadorcarta.getJogadorcartaPK().setCodJogador(jogadorcarta.getJogam().getJogamPK().getCodJogador());
        jogadorcarta.getJogadorcartaPK().setCodPartida(jogadorcarta.getJogam().getJogamPK().getCodPartida());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carta carta = jogadorcarta.getCarta();
            if (carta != null) {
                carta = em.getReference(carta.getClass(), carta.getCodCarta());
                jogadorcarta.setCarta(carta);
            }
            Jogam jogam = jogadorcarta.getJogam();
            if (jogam != null) {
                jogam = em.getReference(jogam.getClass(), jogam.getJogamPK());
                jogadorcarta.setJogam(jogam);
            }
            em.persist(jogadorcarta);
            if (carta != null) {
                carta.getJogadorcartaCollection().add(jogadorcarta);
                carta = em.merge(carta);
            }
            if (jogam != null) {
                jogam.getJogadorcartaCollection().add(jogadorcarta);
                jogam = em.merge(jogam);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJogadorcarta(jogadorcarta.getJogadorcartaPK()) != null) {
                throw new PreexistingEntityException("Jogadorcarta " + jogadorcarta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jogadorcarta jogadorcarta) throws NonexistentEntityException, Exception {
        jogadorcarta.getJogadorcartaPK().setCodCarta(jogadorcarta.getCarta().getCodCarta());
        jogadorcarta.getJogadorcartaPK().setCodJogador(jogadorcarta.getJogam().getJogamPK().getCodJogador());
        jogadorcarta.getJogadorcartaPK().setCodPartida(jogadorcarta.getJogam().getJogamPK().getCodPartida());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jogadorcarta persistentJogadorcarta = em.find(Jogadorcarta.class, jogadorcarta.getJogadorcartaPK());
            Carta cartaOld = persistentJogadorcarta.getCarta();
            Carta cartaNew = jogadorcarta.getCarta();
            Jogam jogamOld = persistentJogadorcarta.getJogam();
            Jogam jogamNew = jogadorcarta.getJogam();
            if (cartaNew != null) {
                cartaNew = em.getReference(cartaNew.getClass(), cartaNew.getCodCarta());
                jogadorcarta.setCarta(cartaNew);
            }
            if (jogamNew != null) {
                jogamNew = em.getReference(jogamNew.getClass(), jogamNew.getJogamPK());
                jogadorcarta.setJogam(jogamNew);
            }
            jogadorcarta = em.merge(jogadorcarta);
            if (cartaOld != null && !cartaOld.equals(cartaNew)) {
                cartaOld.getJogadorcartaCollection().remove(jogadorcarta);
                cartaOld = em.merge(cartaOld);
            }
            if (cartaNew != null && !cartaNew.equals(cartaOld)) {
                cartaNew.getJogadorcartaCollection().add(jogadorcarta);
                cartaNew = em.merge(cartaNew);
            }
            if (jogamOld != null && !jogamOld.equals(jogamNew)) {
                jogamOld.getJogadorcartaCollection().remove(jogadorcarta);
                jogamOld = em.merge(jogamOld);
            }
            if (jogamNew != null && !jogamNew.equals(jogamOld)) {
                jogamNew.getJogadorcartaCollection().add(jogadorcarta);
                jogamNew = em.merge(jogamNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                JogadorcartaPK id = jogadorcarta.getJogadorcartaPK();
                if (findJogadorcarta(id) == null) {
                    throw new NonexistentEntityException("The jogadorcarta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(JogadorcartaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jogadorcarta jogadorcarta;
            try {
                jogadorcarta = em.getReference(Jogadorcarta.class, id);
                jogadorcarta.getJogadorcartaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jogadorcarta with id " + id + " no longer exists.", enfe);
            }
            Carta carta = jogadorcarta.getCarta();
            if (carta != null) {
                carta.getJogadorcartaCollection().remove(jogadorcarta);
                carta = em.merge(carta);
            }
            Jogam jogam = jogadorcarta.getJogam();
            if (jogam != null) {
                jogam.getJogadorcartaCollection().remove(jogadorcarta);
                jogam = em.merge(jogam);
            }
            em.remove(jogadorcarta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jogadorcarta> findJogadorcartaEntities() {
        return findJogadorcartaEntities(true, -1, -1);
    }

    public List<Jogadorcarta> findJogadorcartaEntities(int maxResults, int firstResult) {
        return findJogadorcartaEntities(false, maxResults, firstResult);
    }

    private List<Jogadorcarta> findJogadorcartaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jogadorcarta.class));
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

    public Jogadorcarta findJogadorcarta(JogadorcartaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jogadorcarta.class, id);
        } finally {
            em.close();
        }
    }

    public int getJogadorcartaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jogadorcarta> rt = cq.from(Jogadorcarta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
