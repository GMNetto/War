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
import br.uff.es2.war.entity.Cor;
import br.uff.es2.war.entity.Jogador;
import br.uff.es2.war.entity.Objetivo;
import br.uff.es2.war.entity.Partida;
import br.uff.es2.war.entity.Ocupacao;
import java.util.ArrayList;
import java.util.Collection;
import br.uff.es2.war.entity.Jogadorcarta;
import br.uff.es2.war.entity.Jogam;
import br.uff.es2.war.entity.JogamPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * 
 * @author Victor
 */
public class JogamJpaController implements Serializable {

    public JogamJpaController(EntityManagerFactory emf) {
	this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Jogam jogam) throws PreexistingEntityException,
	    Exception {
	if (jogam.getJogamPK() == null) {
	    jogam.setJogamPK(new JogamPK());
	}
	if (jogam.getOcupacaoCollection() == null) {
	    jogam.setOcupacaoCollection(new ArrayList<Ocupacao>());
	}
	if (jogam.getJogadorcartaCollection() == null) {
	    jogam.setJogadorcartaCollection(new ArrayList<Jogadorcarta>());
	}
	jogam.getJogamPK().setCodPartida(jogam.getPartida().getCodPartida());
	jogam.getJogamPK().setCodJogador(jogam.getJogador().getCodJogador());
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Cor codCor = jogam.getCodCor();
	    if (codCor != null) {
		codCor = em.getReference(codCor.getClass(), codCor.getCodCor());
		jogam.setCodCor(codCor);
	    }
	    Jogador jogador = jogam.getJogador();
	    if (jogador != null) {
		jogador = em.getReference(jogador.getClass(),
			jogador.getCodJogador());
		jogam.setJogador(jogador);
	    }
	    Objetivo codObjetivo = jogam.getCodObjetivo();
	    if (codObjetivo != null) {
		codObjetivo = em.getReference(codObjetivo.getClass(),
			codObjetivo.getCodObjetivo());
		jogam.setCodObjetivo(codObjetivo);
	    }
	    Partida partida = jogam.getPartida();
	    if (partida != null) {
		partida = em.getReference(partida.getClass(),
			partida.getCodPartida());
		jogam.setPartida(partida);
	    }
	    Collection<Ocupacao> attachedOcupacaoCollection = new ArrayList<Ocupacao>();
	    for (Ocupacao ocupacaoCollectionOcupacaoToAttach : jogam
		    .getOcupacaoCollection()) {
		ocupacaoCollectionOcupacaoToAttach = em.getReference(
			ocupacaoCollectionOcupacaoToAttach.getClass(),
			ocupacaoCollectionOcupacaoToAttach.getOcupacaoPK());
		attachedOcupacaoCollection
			.add(ocupacaoCollectionOcupacaoToAttach);
	    }
	    jogam.setOcupacaoCollection(attachedOcupacaoCollection);
	    Collection<Jogadorcarta> attachedJogadorcartaCollection = new ArrayList<Jogadorcarta>();
	    for (Jogadorcarta jogadorcartaCollectionJogadorcartaToAttach : jogam
		    .getJogadorcartaCollection()) {
		jogadorcartaCollectionJogadorcartaToAttach = em.getReference(
			jogadorcartaCollectionJogadorcartaToAttach.getClass(),
			jogadorcartaCollectionJogadorcartaToAttach
				.getJogadorcartaPK());
		attachedJogadorcartaCollection
			.add(jogadorcartaCollectionJogadorcartaToAttach);
	    }
	    jogam.setJogadorcartaCollection(attachedJogadorcartaCollection);
	    em.persist(jogam);
	    if (codCor != null) {
		codCor.getJogamCollection().add(jogam);
		codCor = em.merge(codCor);
	    }
	    if (jogador != null) {
		jogador.getJogamCollection().add(jogam);
		jogador = em.merge(jogador);
	    }
	    if (codObjetivo != null) {
		codObjetivo.getJogamCollection().add(jogam);
		codObjetivo = em.merge(codObjetivo);
	    }
	    if (partida != null) {
		partida.getJogamCollection().add(jogam);
		partida = em.merge(partida);
	    }
	    for (Ocupacao ocupacaoCollectionOcupacao : jogam
		    .getOcupacaoCollection()) {
		Jogam oldJogamOfOcupacaoCollectionOcupacao = ocupacaoCollectionOcupacao
			.getJogam();
		ocupacaoCollectionOcupacao.setJogam(jogam);
		ocupacaoCollectionOcupacao = em
			.merge(ocupacaoCollectionOcupacao);
		if (oldJogamOfOcupacaoCollectionOcupacao != null) {
		    oldJogamOfOcupacaoCollectionOcupacao
			    .getOcupacaoCollection().remove(
				    ocupacaoCollectionOcupacao);
		    oldJogamOfOcupacaoCollectionOcupacao = em
			    .merge(oldJogamOfOcupacaoCollectionOcupacao);
		}
	    }
	    for (Jogadorcarta jogadorcartaCollectionJogadorcarta : jogam
		    .getJogadorcartaCollection()) {
		Jogam oldJogamOfJogadorcartaCollectionJogadorcarta = jogadorcartaCollectionJogadorcarta
			.getJogam();
		jogadorcartaCollectionJogadorcarta.setJogam(jogam);
		jogadorcartaCollectionJogadorcarta = em
			.merge(jogadorcartaCollectionJogadorcarta);
		if (oldJogamOfJogadorcartaCollectionJogadorcarta != null) {
		    oldJogamOfJogadorcartaCollectionJogadorcarta
			    .getJogadorcartaCollection().remove(
				    jogadorcartaCollectionJogadorcarta);
		    oldJogamOfJogadorcartaCollectionJogadorcarta = em
			    .merge(oldJogamOfJogadorcartaCollectionJogadorcarta);
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    if (findJogam(jogam.getJogamPK()) != null) {
		throw new PreexistingEntityException("Jogam " + jogam
			+ " already exists.", ex);
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Jogam jogam) throws IllegalOrphanException,
	    NonexistentEntityException, Exception {
	jogam.getJogamPK().setCodPartida(jogam.getPartida().getCodPartida());
	jogam.getJogamPK().setCodJogador(jogam.getJogador().getCodJogador());
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Jogam persistentJogam = em.find(Jogam.class, jogam.getJogamPK());
	    Cor codCorOld = persistentJogam.getCodCor();
	    Cor codCorNew = jogam.getCodCor();
	    Jogador jogadorOld = persistentJogam.getJogador();
	    Jogador jogadorNew = jogam.getJogador();
	    Objetivo codObjetivoOld = persistentJogam.getCodObjetivo();
	    Objetivo codObjetivoNew = jogam.getCodObjetivo();
	    Partida partidaOld = persistentJogam.getPartida();
	    Partida partidaNew = jogam.getPartida();
	    Collection<Ocupacao> ocupacaoCollectionOld = persistentJogam
		    .getOcupacaoCollection();
	    Collection<Ocupacao> ocupacaoCollectionNew = jogam
		    .getOcupacaoCollection();
	    Collection<Jogadorcarta> jogadorcartaCollectionOld = persistentJogam
		    .getJogadorcartaCollection();
	    Collection<Jogadorcarta> jogadorcartaCollectionNew = jogam
		    .getJogadorcartaCollection();
	    List<String> illegalOrphanMessages = null;
	    for (Ocupacao ocupacaoCollectionOldOcupacao : ocupacaoCollectionOld) {
		if (!ocupacaoCollectionNew
			.contains(ocupacaoCollectionOldOcupacao)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Ocupacao "
			    + ocupacaoCollectionOldOcupacao
			    + " since its jogam field is not nullable.");
		}
	    }
	    for (Jogadorcarta jogadorcartaCollectionOldJogadorcarta : jogadorcartaCollectionOld) {
		if (!jogadorcartaCollectionNew
			.contains(jogadorcartaCollectionOldJogadorcarta)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Jogadorcarta "
			    + jogadorcartaCollectionOldJogadorcarta
			    + " since its jogam field is not nullable.");
		}
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    if (codCorNew != null) {
		codCorNew = em.getReference(codCorNew.getClass(),
			codCorNew.getCodCor());
		jogam.setCodCor(codCorNew);
	    }
	    if (jogadorNew != null) {
		jogadorNew = em.getReference(jogadorNew.getClass(),
			jogadorNew.getCodJogador());
		jogam.setJogador(jogadorNew);
	    }
	    if (codObjetivoNew != null) {
		codObjetivoNew = em.getReference(codObjetivoNew.getClass(),
			codObjetivoNew.getCodObjetivo());
		jogam.setCodObjetivo(codObjetivoNew);
	    }
	    if (partidaNew != null) {
		partidaNew = em.getReference(partidaNew.getClass(),
			partidaNew.getCodPartida());
		jogam.setPartida(partidaNew);
	    }
	    Collection<Ocupacao> attachedOcupacaoCollectionNew = new ArrayList<Ocupacao>();
	    for (Ocupacao ocupacaoCollectionNewOcupacaoToAttach : ocupacaoCollectionNew) {
		ocupacaoCollectionNewOcupacaoToAttach = em.getReference(
			ocupacaoCollectionNewOcupacaoToAttach.getClass(),
			ocupacaoCollectionNewOcupacaoToAttach.getOcupacaoPK());
		attachedOcupacaoCollectionNew
			.add(ocupacaoCollectionNewOcupacaoToAttach);
	    }
	    ocupacaoCollectionNew = attachedOcupacaoCollectionNew;
	    jogam.setOcupacaoCollection(ocupacaoCollectionNew);
	    Collection<Jogadorcarta> attachedJogadorcartaCollectionNew = new ArrayList<Jogadorcarta>();
	    for (Jogadorcarta jogadorcartaCollectionNewJogadorcartaToAttach : jogadorcartaCollectionNew) {
		jogadorcartaCollectionNewJogadorcartaToAttach = em
			.getReference(
				jogadorcartaCollectionNewJogadorcartaToAttach
					.getClass(),
				jogadorcartaCollectionNewJogadorcartaToAttach
					.getJogadorcartaPK());
		attachedJogadorcartaCollectionNew
			.add(jogadorcartaCollectionNewJogadorcartaToAttach);
	    }
	    jogadorcartaCollectionNew = attachedJogadorcartaCollectionNew;
	    jogam.setJogadorcartaCollection(jogadorcartaCollectionNew);
	    jogam = em.merge(jogam);
	    if (codCorOld != null && !codCorOld.equals(codCorNew)) {
		codCorOld.getJogamCollection().remove(jogam);
		codCorOld = em.merge(codCorOld);
	    }
	    if (codCorNew != null && !codCorNew.equals(codCorOld)) {
		codCorNew.getJogamCollection().add(jogam);
		codCorNew = em.merge(codCorNew);
	    }
	    if (jogadorOld != null && !jogadorOld.equals(jogadorNew)) {
		jogadorOld.getJogamCollection().remove(jogam);
		jogadorOld = em.merge(jogadorOld);
	    }
	    if (jogadorNew != null && !jogadorNew.equals(jogadorOld)) {
		jogadorNew.getJogamCollection().add(jogam);
		jogadorNew = em.merge(jogadorNew);
	    }
	    if (codObjetivoOld != null
		    && !codObjetivoOld.equals(codObjetivoNew)) {
		codObjetivoOld.getJogamCollection().remove(jogam);
		codObjetivoOld = em.merge(codObjetivoOld);
	    }
	    if (codObjetivoNew != null
		    && !codObjetivoNew.equals(codObjetivoOld)) {
		codObjetivoNew.getJogamCollection().add(jogam);
		codObjetivoNew = em.merge(codObjetivoNew);
	    }
	    if (partidaOld != null && !partidaOld.equals(partidaNew)) {
		partidaOld.getJogamCollection().remove(jogam);
		partidaOld = em.merge(partidaOld);
	    }
	    if (partidaNew != null && !partidaNew.equals(partidaOld)) {
		partidaNew.getJogamCollection().add(jogam);
		partidaNew = em.merge(partidaNew);
	    }
	    for (Ocupacao ocupacaoCollectionNewOcupacao : ocupacaoCollectionNew) {
		if (!ocupacaoCollectionOld
			.contains(ocupacaoCollectionNewOcupacao)) {
		    Jogam oldJogamOfOcupacaoCollectionNewOcupacao = ocupacaoCollectionNewOcupacao
			    .getJogam();
		    ocupacaoCollectionNewOcupacao.setJogam(jogam);
		    ocupacaoCollectionNewOcupacao = em
			    .merge(ocupacaoCollectionNewOcupacao);
		    if (oldJogamOfOcupacaoCollectionNewOcupacao != null
			    && !oldJogamOfOcupacaoCollectionNewOcupacao
				    .equals(jogam)) {
			oldJogamOfOcupacaoCollectionNewOcupacao
				.getOcupacaoCollection().remove(
					ocupacaoCollectionNewOcupacao);
			oldJogamOfOcupacaoCollectionNewOcupacao = em
				.merge(oldJogamOfOcupacaoCollectionNewOcupacao);
		    }
		}
	    }
	    for (Jogadorcarta jogadorcartaCollectionNewJogadorcarta : jogadorcartaCollectionNew) {
		if (!jogadorcartaCollectionOld
			.contains(jogadorcartaCollectionNewJogadorcarta)) {
		    Jogam oldJogamOfJogadorcartaCollectionNewJogadorcarta = jogadorcartaCollectionNewJogadorcarta
			    .getJogam();
		    jogadorcartaCollectionNewJogadorcarta.setJogam(jogam);
		    jogadorcartaCollectionNewJogadorcarta = em
			    .merge(jogadorcartaCollectionNewJogadorcarta);
		    if (oldJogamOfJogadorcartaCollectionNewJogadorcarta != null
			    && !oldJogamOfJogadorcartaCollectionNewJogadorcarta
				    .equals(jogam)) {
			oldJogamOfJogadorcartaCollectionNewJogadorcarta
				.getJogadorcartaCollection().remove(
					jogadorcartaCollectionNewJogadorcarta);
			oldJogamOfJogadorcartaCollectionNewJogadorcarta = em
				.merge(oldJogamOfJogadorcartaCollectionNewJogadorcarta);
		    }
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		JogamPK id = jogam.getJogamPK();
		if (findJogam(id) == null) {
		    throw new NonexistentEntityException("The jogam with id "
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

    public void destroy(JogamPK id) throws IllegalOrphanException,
	    NonexistentEntityException {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Jogam jogam;
	    try {
		jogam = em.getReference(Jogam.class, id);
		jogam.getJogamPK();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The jogam with id " + id
			+ " no longer exists.", enfe);
	    }
	    List<String> illegalOrphanMessages = null;
	    Collection<Ocupacao> ocupacaoCollectionOrphanCheck = jogam
		    .getOcupacaoCollection();
	    for (Ocupacao ocupacaoCollectionOrphanCheckOcupacao : ocupacaoCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Jogam ("
				+ jogam
				+ ") cannot be destroyed since the Ocupacao "
				+ ocupacaoCollectionOrphanCheckOcupacao
				+ " in its ocupacaoCollection field has a non-nullable jogam field.");
	    }
	    Collection<Jogadorcarta> jogadorcartaCollectionOrphanCheck = jogam
		    .getJogadorcartaCollection();
	    for (Jogadorcarta jogadorcartaCollectionOrphanCheckJogadorcarta : jogadorcartaCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Jogam ("
				+ jogam
				+ ") cannot be destroyed since the Jogadorcarta "
				+ jogadorcartaCollectionOrphanCheckJogadorcarta
				+ " in its jogadorcartaCollection field has a non-nullable jogam field.");
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    Cor codCor = jogam.getCodCor();
	    if (codCor != null) {
		codCor.getJogamCollection().remove(jogam);
		codCor = em.merge(codCor);
	    }
	    Jogador jogador = jogam.getJogador();
	    if (jogador != null) {
		jogador.getJogamCollection().remove(jogam);
		jogador = em.merge(jogador);
	    }
	    Objetivo codObjetivo = jogam.getCodObjetivo();
	    if (codObjetivo != null) {
		codObjetivo.getJogamCollection().remove(jogam);
		codObjetivo = em.merge(codObjetivo);
	    }
	    Partida partida = jogam.getPartida();
	    if (partida != null) {
		partida.getJogamCollection().remove(jogam);
		partida = em.merge(partida);
	    }
	    em.remove(jogam);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Jogam> findJogamEntities() {
	return findJogamEntities(true, -1, -1);
    }

    public List<Jogam> findJogamEntities(int maxResults, int firstResult) {
	return findJogamEntities(false, maxResults, firstResult);
    }

    private List<Jogam> findJogamEntities(boolean all, int maxResults,
	    int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Jogam.class));
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

    public Jogam findJogam(JogamPK id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Jogam.class, id);
	} finally {
	    em.close();
	}
    }

    public int getJogamCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Jogam> rt = cq.from(Jogam.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
