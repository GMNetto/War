/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.dao;

import br.uff.es2.war.dao.exceptions.IllegalOrphanException;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.uff.es2.war.entity.Jogam;
import java.util.ArrayList;
import java.util.Collection;
import br.uff.es2.war.entity.Historico;
import br.uff.es2.war.entity.Jogador;
import br.uff.es2.war.entity.Partida;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class JogadorJpaController implements Serializable {

    public JogadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jogador jogador) {
        if (jogador.getJogamCollection() == null) {
            jogador.setJogamCollection(new ArrayList<Jogam>());
        }
        if (jogador.getHistoricoCollection() == null) {
            jogador.setHistoricoCollection(new ArrayList<Historico>());
        }
        if (jogador.getPartidaCollection() == null) {
            jogador.setPartidaCollection(new ArrayList<Partida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Jogam> attachedJogamCollection = new ArrayList<Jogam>();
            for (Jogam jogamCollectionJogamToAttach : jogador.getJogamCollection()) {
                jogamCollectionJogamToAttach = em.getReference(jogamCollectionJogamToAttach.getClass(), jogamCollectionJogamToAttach.getJogamPK());
                attachedJogamCollection.add(jogamCollectionJogamToAttach);
            }
            jogador.setJogamCollection(attachedJogamCollection);
            Collection<Historico> attachedHistoricoCollection = new ArrayList<Historico>();
            for (Historico historicoCollectionHistoricoToAttach : jogador.getHistoricoCollection()) {
                historicoCollectionHistoricoToAttach = em.getReference(historicoCollectionHistoricoToAttach.getClass(), historicoCollectionHistoricoToAttach.getCodPartida());
                attachedHistoricoCollection.add(historicoCollectionHistoricoToAttach);
            }
            jogador.setHistoricoCollection(attachedHistoricoCollection);
            Collection<Partida> attachedPartidaCollection = new ArrayList<Partida>();
            for (Partida partidaCollectionPartidaToAttach : jogador.getPartidaCollection()) {
                partidaCollectionPartidaToAttach = em.getReference(partidaCollectionPartidaToAttach.getClass(), partidaCollectionPartidaToAttach.getCodPartida());
                attachedPartidaCollection.add(partidaCollectionPartidaToAttach);
            }
            jogador.setPartidaCollection(attachedPartidaCollection);
            em.persist(jogador);
            for (Jogam jogamCollectionJogam : jogador.getJogamCollection()) {
                Jogador oldJogadorOfJogamCollectionJogam = jogamCollectionJogam.getJogador();
                jogamCollectionJogam.setJogador(jogador);
                jogamCollectionJogam = em.merge(jogamCollectionJogam);
                if (oldJogadorOfJogamCollectionJogam != null) {
                    oldJogadorOfJogamCollectionJogam.getJogamCollection().remove(jogamCollectionJogam);
                    oldJogadorOfJogamCollectionJogam = em.merge(oldJogadorOfJogamCollectionJogam);
                }
            }
            for (Historico historicoCollectionHistorico : jogador.getHistoricoCollection()) {
                Jogador oldCodJogadorOfHistoricoCollectionHistorico = historicoCollectionHistorico.getCodJogador();
                historicoCollectionHistorico.setCodJogador(jogador);
                historicoCollectionHistorico = em.merge(historicoCollectionHistorico);
                if (oldCodJogadorOfHistoricoCollectionHistorico != null) {
                    oldCodJogadorOfHistoricoCollectionHistorico.getHistoricoCollection().remove(historicoCollectionHistorico);
                    oldCodJogadorOfHistoricoCollectionHistorico = em.merge(oldCodJogadorOfHistoricoCollectionHistorico);
                }
            }
            for (Partida partidaCollectionPartida : jogador.getPartidaCollection()) {
                Jogador oldCodJogadorOfPartidaCollectionPartida = partidaCollectionPartida.getCodJogador();
                partidaCollectionPartida.setCodJogador(jogador);
                partidaCollectionPartida = em.merge(partidaCollectionPartida);
                if (oldCodJogadorOfPartidaCollectionPartida != null) {
                    oldCodJogadorOfPartidaCollectionPartida.getPartidaCollection().remove(partidaCollectionPartida);
                    oldCodJogadorOfPartidaCollectionPartida = em.merge(oldCodJogadorOfPartidaCollectionPartida);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jogador jogador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jogador persistentJogador = em.find(Jogador.class, jogador.getCodJogador());
            Collection<Jogam> jogamCollectionOld = persistentJogador.getJogamCollection();
            Collection<Jogam> jogamCollectionNew = jogador.getJogamCollection();
            Collection<Historico> historicoCollectionOld = persistentJogador.getHistoricoCollection();
            Collection<Historico> historicoCollectionNew = jogador.getHistoricoCollection();
            Collection<Partida> partidaCollectionOld = persistentJogador.getPartidaCollection();
            Collection<Partida> partidaCollectionNew = jogador.getPartidaCollection();
            List<String> illegalOrphanMessages = null;
            for (Jogam jogamCollectionOldJogam : jogamCollectionOld) {
                if (!jogamCollectionNew.contains(jogamCollectionOldJogam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Jogam " + jogamCollectionOldJogam + " since its jogador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Jogam> attachedJogamCollectionNew = new ArrayList<Jogam>();
            for (Jogam jogamCollectionNewJogamToAttach : jogamCollectionNew) {
                jogamCollectionNewJogamToAttach = em.getReference(jogamCollectionNewJogamToAttach.getClass(), jogamCollectionNewJogamToAttach.getJogamPK());
                attachedJogamCollectionNew.add(jogamCollectionNewJogamToAttach);
            }
            jogamCollectionNew = attachedJogamCollectionNew;
            jogador.setJogamCollection(jogamCollectionNew);
            Collection<Historico> attachedHistoricoCollectionNew = new ArrayList<Historico>();
            for (Historico historicoCollectionNewHistoricoToAttach : historicoCollectionNew) {
                historicoCollectionNewHistoricoToAttach = em.getReference(historicoCollectionNewHistoricoToAttach.getClass(), historicoCollectionNewHistoricoToAttach.getCodPartida());
                attachedHistoricoCollectionNew.add(historicoCollectionNewHistoricoToAttach);
            }
            historicoCollectionNew = attachedHistoricoCollectionNew;
            jogador.setHistoricoCollection(historicoCollectionNew);
            Collection<Partida> attachedPartidaCollectionNew = new ArrayList<Partida>();
            for (Partida partidaCollectionNewPartidaToAttach : partidaCollectionNew) {
                partidaCollectionNewPartidaToAttach = em.getReference(partidaCollectionNewPartidaToAttach.getClass(), partidaCollectionNewPartidaToAttach.getCodPartida());
                attachedPartidaCollectionNew.add(partidaCollectionNewPartidaToAttach);
            }
            partidaCollectionNew = attachedPartidaCollectionNew;
            jogador.setPartidaCollection(partidaCollectionNew);
            jogador = em.merge(jogador);
            for (Jogam jogamCollectionNewJogam : jogamCollectionNew) {
                if (!jogamCollectionOld.contains(jogamCollectionNewJogam)) {
                    Jogador oldJogadorOfJogamCollectionNewJogam = jogamCollectionNewJogam.getJogador();
                    jogamCollectionNewJogam.setJogador(jogador);
                    jogamCollectionNewJogam = em.merge(jogamCollectionNewJogam);
                    if (oldJogadorOfJogamCollectionNewJogam != null && !oldJogadorOfJogamCollectionNewJogam.equals(jogador)) {
                        oldJogadorOfJogamCollectionNewJogam.getJogamCollection().remove(jogamCollectionNewJogam);
                        oldJogadorOfJogamCollectionNewJogam = em.merge(oldJogadorOfJogamCollectionNewJogam);
                    }
                }
            }
            for (Historico historicoCollectionOldHistorico : historicoCollectionOld) {
                if (!historicoCollectionNew.contains(historicoCollectionOldHistorico)) {
                    historicoCollectionOldHistorico.setCodJogador(null);
                    historicoCollectionOldHistorico = em.merge(historicoCollectionOldHistorico);
                }
            }
            for (Historico historicoCollectionNewHistorico : historicoCollectionNew) {
                if (!historicoCollectionOld.contains(historicoCollectionNewHistorico)) {
                    Jogador oldCodJogadorOfHistoricoCollectionNewHistorico = historicoCollectionNewHistorico.getCodJogador();
                    historicoCollectionNewHistorico.setCodJogador(jogador);
                    historicoCollectionNewHistorico = em.merge(historicoCollectionNewHistorico);
                    if (oldCodJogadorOfHistoricoCollectionNewHistorico != null && !oldCodJogadorOfHistoricoCollectionNewHistorico.equals(jogador)) {
                        oldCodJogadorOfHistoricoCollectionNewHistorico.getHistoricoCollection().remove(historicoCollectionNewHistorico);
                        oldCodJogadorOfHistoricoCollectionNewHistorico = em.merge(oldCodJogadorOfHistoricoCollectionNewHistorico);
                    }
                }
            }
            for (Partida partidaCollectionOldPartida : partidaCollectionOld) {
                if (!partidaCollectionNew.contains(partidaCollectionOldPartida)) {
                    partidaCollectionOldPartida.setCodJogador(null);
                    partidaCollectionOldPartida = em.merge(partidaCollectionOldPartida);
                }
            }
            for (Partida partidaCollectionNewPartida : partidaCollectionNew) {
                if (!partidaCollectionOld.contains(partidaCollectionNewPartida)) {
                    Jogador oldCodJogadorOfPartidaCollectionNewPartida = partidaCollectionNewPartida.getCodJogador();
                    partidaCollectionNewPartida.setCodJogador(jogador);
                    partidaCollectionNewPartida = em.merge(partidaCollectionNewPartida);
                    if (oldCodJogadorOfPartidaCollectionNewPartida != null && !oldCodJogadorOfPartidaCollectionNewPartida.equals(jogador)) {
                        oldCodJogadorOfPartidaCollectionNewPartida.getPartidaCollection().remove(partidaCollectionNewPartida);
                        oldCodJogadorOfPartidaCollectionNewPartida = em.merge(oldCodJogadorOfPartidaCollectionNewPartida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jogador.getCodJogador();
                if (findJogador(id) == null) {
                    throw new NonexistentEntityException("The jogador with id " + id + " no longer exists.");
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
            Jogador jogador;
            try {
                jogador = em.getReference(Jogador.class, id);
                jogador.getCodJogador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jogador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Jogam> jogamCollectionOrphanCheck = jogador.getJogamCollection();
            for (Jogam jogamCollectionOrphanCheckJogam : jogamCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jogador (" + jogador + ") cannot be destroyed since the Jogam " + jogamCollectionOrphanCheckJogam + " in its jogamCollection field has a non-nullable jogador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Historico> historicoCollection = jogador.getHistoricoCollection();
            for (Historico historicoCollectionHistorico : historicoCollection) {
                historicoCollectionHistorico.setCodJogador(null);
                historicoCollectionHistorico = em.merge(historicoCollectionHistorico);
            }
            Collection<Partida> partidaCollection = jogador.getPartidaCollection();
            for (Partida partidaCollectionPartida : partidaCollection) {
                partidaCollectionPartida.setCodJogador(null);
                partidaCollectionPartida = em.merge(partidaCollectionPartida);
            }
            em.remove(jogador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jogador> findJogadorEntities() {
        return findJogadorEntities(true, -1, -1);
    }

    public List<Jogador> findJogadorEntities(int maxResults, int firstResult) {
        return findJogadorEntities(false, maxResults, firstResult);
    }

    private List<Jogador> findJogadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jogador.class));
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

    public Jogador findJogador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jogador.class, id);
        } finally {
            em.close();
        }
    }

    public int getJogadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jogador> rt = cq.from(Jogador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
