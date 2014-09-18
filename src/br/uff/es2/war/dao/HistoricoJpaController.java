/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.dao;

import br.uff.es2.war.dao.exceptions.IllegalOrphanException;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.dao.exceptions.PreexistingEntityException;
import br.uff.es2.war.entity.Historico;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.uff.es2.war.entity.Jogador;
import br.uff.es2.war.entity.Objetivo;
import br.uff.es2.war.entity.Partida;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class HistoricoJpaController implements Serializable {

    public HistoricoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historico historico) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Partida partidaOrphanCheck = historico.getPartida();
        if (partidaOrphanCheck != null) {
            Historico oldHistoricoOfPartida = partidaOrphanCheck.getHistorico();
            if (oldHistoricoOfPartida != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Partida " + partidaOrphanCheck + " already has an item of type Historico whose partida column cannot be null. Please make another selection for the partida field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jogador codJogador = historico.getCodJogador();
            if (codJogador != null) {
                codJogador = em.getReference(codJogador.getClass(), codJogador.getCodJogador());
                historico.setCodJogador(codJogador);
            }
            Objetivo codObjetivo = historico.getCodObjetivo();
            if (codObjetivo != null) {
                codObjetivo = em.getReference(codObjetivo.getClass(), codObjetivo.getCodObjetivo());
                historico.setCodObjetivo(codObjetivo);
            }
            Partida partida = historico.getPartida();
            if (partida != null) {
                partida = em.getReference(partida.getClass(), partida.getCodPartida());
                historico.setPartida(partida);
            }
            em.persist(historico);
            if (codJogador != null) {
                codJogador.getHistoricoCollection().add(historico);
                codJogador = em.merge(codJogador);
            }
            if (codObjetivo != null) {
                codObjetivo.getHistoricoCollection().add(historico);
                codObjetivo = em.merge(codObjetivo);
            }
            if (partida != null) {
                partida.setHistorico(historico);
                partida = em.merge(partida);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHistorico(historico.getCodPartida()) != null) {
                throw new PreexistingEntityException("Historico " + historico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historico historico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historico persistentHistorico = em.find(Historico.class, historico.getCodPartida());
            Jogador codJogadorOld = persistentHistorico.getCodJogador();
            Jogador codJogadorNew = historico.getCodJogador();
            Objetivo codObjetivoOld = persistentHistorico.getCodObjetivo();
            Objetivo codObjetivoNew = historico.getCodObjetivo();
            Partida partidaOld = persistentHistorico.getPartida();
            Partida partidaNew = historico.getPartida();
            List<String> illegalOrphanMessages = null;
            if (partidaNew != null && !partidaNew.equals(partidaOld)) {
                Historico oldHistoricoOfPartida = partidaNew.getHistorico();
                if (oldHistoricoOfPartida != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Partida " + partidaNew + " already has an item of type Historico whose partida column cannot be null. Please make another selection for the partida field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codJogadorNew != null) {
                codJogadorNew = em.getReference(codJogadorNew.getClass(), codJogadorNew.getCodJogador());
                historico.setCodJogador(codJogadorNew);
            }
            if (codObjetivoNew != null) {
                codObjetivoNew = em.getReference(codObjetivoNew.getClass(), codObjetivoNew.getCodObjetivo());
                historico.setCodObjetivo(codObjetivoNew);
            }
            if (partidaNew != null) {
                partidaNew = em.getReference(partidaNew.getClass(), partidaNew.getCodPartida());
                historico.setPartida(partidaNew);
            }
            historico = em.merge(historico);
            if (codJogadorOld != null && !codJogadorOld.equals(codJogadorNew)) {
                codJogadorOld.getHistoricoCollection().remove(historico);
                codJogadorOld = em.merge(codJogadorOld);
            }
            if (codJogadorNew != null && !codJogadorNew.equals(codJogadorOld)) {
                codJogadorNew.getHistoricoCollection().add(historico);
                codJogadorNew = em.merge(codJogadorNew);
            }
            if (codObjetivoOld != null && !codObjetivoOld.equals(codObjetivoNew)) {
                codObjetivoOld.getHistoricoCollection().remove(historico);
                codObjetivoOld = em.merge(codObjetivoOld);
            }
            if (codObjetivoNew != null && !codObjetivoNew.equals(codObjetivoOld)) {
                codObjetivoNew.getHistoricoCollection().add(historico);
                codObjetivoNew = em.merge(codObjetivoNew);
            }
            if (partidaOld != null && !partidaOld.equals(partidaNew)) {
                partidaOld.setHistorico(null);
                partidaOld = em.merge(partidaOld);
            }
            if (partidaNew != null && !partidaNew.equals(partidaOld)) {
                partidaNew.setHistorico(historico);
                partidaNew = em.merge(partidaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historico.getCodPartida();
                if (findHistorico(id) == null) {
                    throw new NonexistentEntityException("The historico with id " + id + " no longer exists.");
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
            Historico historico;
            try {
                historico = em.getReference(Historico.class, id);
                historico.getCodPartida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historico with id " + id + " no longer exists.", enfe);
            }
            Jogador codJogador = historico.getCodJogador();
            if (codJogador != null) {
                codJogador.getHistoricoCollection().remove(historico);
                codJogador = em.merge(codJogador);
            }
            Objetivo codObjetivo = historico.getCodObjetivo();
            if (codObjetivo != null) {
                codObjetivo.getHistoricoCollection().remove(historico);
                codObjetivo = em.merge(codObjetivo);
            }
            Partida partida = historico.getPartida();
            if (partida != null) {
                partida.setHistorico(null);
                partida = em.merge(partida);
            }
            em.remove(historico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historico> findHistoricoEntities() {
        return findHistoricoEntities(true, -1, -1);
    }

    public List<Historico> findHistoricoEntities(int maxResults, int firstResult) {
        return findHistoricoEntities(false, maxResults, firstResult);
    }

    private List<Historico> findHistoricoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historico.class));
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

    public Historico findHistorico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historico.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoricoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historico> rt = cq.from(Historico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
