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
import br.uff.es2.war.entity.Historico;
import br.uff.es2.war.entity.Jogam;
import br.uff.es2.war.entity.Mundo;
import br.uff.es2.war.entity.Objconqcont;
import br.uff.es2.war.entity.Objderjogador;
import br.uff.es2.war.entity.Objetivo;
import br.uff.es2.war.entity.Objterritorio;

/**
 * 
 * @author Victor
 */
public class ObjetivoJpaController implements Serializable {

    public ObjetivoJpaController(EntityManagerFactory emf) {
	this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Objetivo objetivo) {
	if (objetivo.getJogamCollection() == null) {
	    objetivo.setJogamCollection(new ArrayList<Jogam>());
	}
	if (objetivo.getHistoricoCollection() == null) {
	    objetivo.setHistoricoCollection(new ArrayList<Historico>());
	}
	if (objetivo.getObjderjogadorCollection() == null) {
	    objetivo.setObjderjogadorCollection(new ArrayList<Objderjogador>());
	}
	if (objetivo.getObjderjogadorCollection1() == null) {
	    objetivo.setObjderjogadorCollection1(new ArrayList<Objderjogador>());
	}
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Objterritorio objterritorio = objetivo.getObjterritorio();
	    if (objterritorio != null) {
		objterritorio = em.getReference(objterritorio.getClass(),
			objterritorio.getCodObjetivo());
		objetivo.setObjterritorio(objterritorio);
	    }
	    Mundo codMundo = objetivo.getCodMundo();
	    if (codMundo != null) {
		codMundo = em.getReference(codMundo.getClass(),
			codMundo.getCodMundo());
		objetivo.setCodMundo(codMundo);
	    }
	    Objconqcont objconqcont = objetivo.getObjconqcont();
	    if (objconqcont != null) {
		objconqcont = em.getReference(objconqcont.getClass(),
			objconqcont.getCodObjetivo());
		objetivo.setObjconqcont(objconqcont);
	    }
	    Collection<Jogam> attachedJogamCollection = new ArrayList<Jogam>();
	    for (Jogam jogamCollectionJogamToAttach : objetivo
		    .getJogamCollection()) {
		jogamCollectionJogamToAttach = em.getReference(
			jogamCollectionJogamToAttach.getClass(),
			jogamCollectionJogamToAttach.getJogamPK());
		attachedJogamCollection.add(jogamCollectionJogamToAttach);
	    }
	    objetivo.setJogamCollection(attachedJogamCollection);
	    Collection<Historico> attachedHistoricoCollection = new ArrayList<Historico>();
	    for (Historico historicoCollectionHistoricoToAttach : objetivo
		    .getHistoricoCollection()) {
		historicoCollectionHistoricoToAttach = em.getReference(
			historicoCollectionHistoricoToAttach.getClass(),
			historicoCollectionHistoricoToAttach.getCodPartida());
		attachedHistoricoCollection
			.add(historicoCollectionHistoricoToAttach);
	    }
	    objetivo.setHistoricoCollection(attachedHistoricoCollection);
	    Collection<Objderjogador> attachedObjderjogadorCollection = new ArrayList<Objderjogador>();
	    for (Objderjogador objderjogadorCollectionObjderjogadorToAttach : objetivo
		    .getObjderjogadorCollection()) {
		objderjogadorCollectionObjderjogadorToAttach = em
			.getReference(
				objderjogadorCollectionObjderjogadorToAttach
					.getClass(),
				objderjogadorCollectionObjderjogadorToAttach
					.getObjderjogadorPK());
		attachedObjderjogadorCollection
			.add(objderjogadorCollectionObjderjogadorToAttach);
	    }
	    objetivo.setObjderjogadorCollection(attachedObjderjogadorCollection);
	    Collection<Objderjogador> attachedObjderjogadorCollection1 = new ArrayList<Objderjogador>();
	    for (Objderjogador objderjogadorCollection1ObjderjogadorToAttach : objetivo
		    .getObjderjogadorCollection1()) {
		objderjogadorCollection1ObjderjogadorToAttach = em
			.getReference(
				objderjogadorCollection1ObjderjogadorToAttach
					.getClass(),
				objderjogadorCollection1ObjderjogadorToAttach
					.getObjderjogadorPK());
		attachedObjderjogadorCollection1
			.add(objderjogadorCollection1ObjderjogadorToAttach);
	    }
	    objetivo.setObjderjogadorCollection1(attachedObjderjogadorCollection1);
	    em.persist(objetivo);
	    if (objterritorio != null) {
		Objetivo oldObjetivoOfObjterritorio = objterritorio
			.getObjetivo();
		if (oldObjetivoOfObjterritorio != null) {
		    oldObjetivoOfObjterritorio.setObjterritorio(null);
		    oldObjetivoOfObjterritorio = em
			    .merge(oldObjetivoOfObjterritorio);
		}
		objterritorio.setObjetivo(objetivo);
		objterritorio = em.merge(objterritorio);
	    }
	    if (codMundo != null) {
		codMundo.getObjetivoCollection().add(objetivo);
		codMundo = em.merge(codMundo);
	    }
	    if (objconqcont != null) {
		Objetivo oldObjetivoOfObjconqcont = objconqcont.getObjetivo();
		if (oldObjetivoOfObjconqcont != null) {
		    oldObjetivoOfObjconqcont.setObjconqcont(null);
		    oldObjetivoOfObjconqcont = em
			    .merge(oldObjetivoOfObjconqcont);
		}
		objconqcont.setObjetivo(objetivo);
		objconqcont = em.merge(objconqcont);
	    }
	    for (Jogam jogamCollectionJogam : objetivo.getJogamCollection()) {
		Objetivo oldCodObjetivoOfJogamCollectionJogam = jogamCollectionJogam
			.getCodObjetivo();
		jogamCollectionJogam.setCodObjetivo(objetivo);
		jogamCollectionJogam = em.merge(jogamCollectionJogam);
		if (oldCodObjetivoOfJogamCollectionJogam != null) {
		    oldCodObjetivoOfJogamCollectionJogam.getJogamCollection()
			    .remove(jogamCollectionJogam);
		    oldCodObjetivoOfJogamCollectionJogam = em
			    .merge(oldCodObjetivoOfJogamCollectionJogam);
		}
	    }
	    for (Historico historicoCollectionHistorico : objetivo
		    .getHistoricoCollection()) {
		Objetivo oldCodObjetivoOfHistoricoCollectionHistorico = historicoCollectionHistorico
			.getCodObjetivo();
		historicoCollectionHistorico.setCodObjetivo(objetivo);
		historicoCollectionHistorico = em
			.merge(historicoCollectionHistorico);
		if (oldCodObjetivoOfHistoricoCollectionHistorico != null) {
		    oldCodObjetivoOfHistoricoCollectionHistorico
			    .getHistoricoCollection().remove(
				    historicoCollectionHistorico);
		    oldCodObjetivoOfHistoricoCollectionHistorico = em
			    .merge(oldCodObjetivoOfHistoricoCollectionHistorico);
		}
	    }
	    for (Objderjogador objderjogadorCollectionObjderjogador : objetivo
		    .getObjderjogadorCollection()) {
		Objetivo oldObjetivoOfObjderjogadorCollectionObjderjogador = objderjogadorCollectionObjderjogador
			.getObjetivo();
		objderjogadorCollectionObjderjogador.setObjetivo(objetivo);
		objderjogadorCollectionObjderjogador = em
			.merge(objderjogadorCollectionObjderjogador);
		if (oldObjetivoOfObjderjogadorCollectionObjderjogador != null) {
		    oldObjetivoOfObjderjogadorCollectionObjderjogador
			    .getObjderjogadorCollection().remove(
				    objderjogadorCollectionObjderjogador);
		    oldObjetivoOfObjderjogadorCollectionObjderjogador = em
			    .merge(oldObjetivoOfObjderjogadorCollectionObjderjogador);
		}
	    }
	    for (Objderjogador objderjogadorCollection1Objderjogador : objetivo
		    .getObjderjogadorCollection1()) {
		Objetivo oldObjetivo1OfObjderjogadorCollection1Objderjogador = objderjogadorCollection1Objderjogador
			.getObjetivo1();
		objderjogadorCollection1Objderjogador.setObjetivo1(objetivo);
		objderjogadorCollection1Objderjogador = em
			.merge(objderjogadorCollection1Objderjogador);
		if (oldObjetivo1OfObjderjogadorCollection1Objderjogador != null) {
		    oldObjetivo1OfObjderjogadorCollection1Objderjogador
			    .getObjderjogadorCollection1().remove(
				    objderjogadorCollection1Objderjogador);
		    oldObjetivo1OfObjderjogadorCollection1Objderjogador = em
			    .merge(oldObjetivo1OfObjderjogadorCollection1Objderjogador);
		}
	    }
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Objetivo objetivo) throws IllegalOrphanException,
	    NonexistentEntityException, Exception {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Objetivo persistentObjetivo = em.find(Objetivo.class,
		    objetivo.getCodObjetivo());
	    Objterritorio objterritorioOld = persistentObjetivo
		    .getObjterritorio();
	    Objterritorio objterritorioNew = objetivo.getObjterritorio();
	    Mundo codMundoOld = persistentObjetivo.getCodMundo();
	    Mundo codMundoNew = objetivo.getCodMundo();
	    Objconqcont objconqcontOld = persistentObjetivo.getObjconqcont();
	    Objconqcont objconqcontNew = objetivo.getObjconqcont();
	    Collection<Jogam> jogamCollectionOld = persistentObjetivo
		    .getJogamCollection();
	    Collection<Jogam> jogamCollectionNew = objetivo
		    .getJogamCollection();
	    Collection<Historico> historicoCollectionOld = persistentObjetivo
		    .getHistoricoCollection();
	    Collection<Historico> historicoCollectionNew = objetivo
		    .getHistoricoCollection();
	    Collection<Objderjogador> objderjogadorCollectionOld = persistentObjetivo
		    .getObjderjogadorCollection();
	    Collection<Objderjogador> objderjogadorCollectionNew = objetivo
		    .getObjderjogadorCollection();
	    Collection<Objderjogador> objderjogadorCollection1Old = persistentObjetivo
		    .getObjderjogadorCollection1();
	    Collection<Objderjogador> objderjogadorCollection1New = objetivo
		    .getObjderjogadorCollection1();
	    List<String> illegalOrphanMessages = null;
	    if (objterritorioOld != null
		    && !objterritorioOld.equals(objterritorioNew)) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages.add("You must retain Objterritorio "
			+ objterritorioOld
			+ " since its objetivo field is not nullable.");
	    }
	    if (objconqcontOld != null
		    && !objconqcontOld.equals(objconqcontNew)) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages.add("You must retain Objconqcont "
			+ objconqcontOld
			+ " since its objetivo field is not nullable.");
	    }
	    for (Jogam jogamCollectionOldJogam : jogamCollectionOld) {
		if (!jogamCollectionNew.contains(jogamCollectionOldJogam)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Jogam "
			    + jogamCollectionOldJogam
			    + " since its codObjetivo field is not nullable.");
		}
	    }
	    for (Objderjogador objderjogadorCollectionOldObjderjogador : objderjogadorCollectionOld) {
		if (!objderjogadorCollectionNew
			.contains(objderjogadorCollectionOldObjderjogador)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Objderjogador "
			    + objderjogadorCollectionOldObjderjogador
			    + " since its objetivo field is not nullable.");
		}
	    }
	    for (Objderjogador objderjogadorCollection1OldObjderjogador : objderjogadorCollection1Old) {
		if (!objderjogadorCollection1New
			.contains(objderjogadorCollection1OldObjderjogador)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Objderjogador "
			    + objderjogadorCollection1OldObjderjogador
			    + " since its objetivo1 field is not nullable.");
		}
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    if (objterritorioNew != null) {
		objterritorioNew = em.getReference(objterritorioNew.getClass(),
			objterritorioNew.getCodObjetivo());
		objetivo.setObjterritorio(objterritorioNew);
	    }
	    if (codMundoNew != null) {
		codMundoNew = em.getReference(codMundoNew.getClass(),
			codMundoNew.getCodMundo());
		objetivo.setCodMundo(codMundoNew);
	    }
	    if (objconqcontNew != null) {
		objconqcontNew = em.getReference(objconqcontNew.getClass(),
			objconqcontNew.getCodObjetivo());
		objetivo.setObjconqcont(objconqcontNew);
	    }
	    Collection<Jogam> attachedJogamCollectionNew = new ArrayList<Jogam>();
	    for (Jogam jogamCollectionNewJogamToAttach : jogamCollectionNew) {
		jogamCollectionNewJogamToAttach = em.getReference(
			jogamCollectionNewJogamToAttach.getClass(),
			jogamCollectionNewJogamToAttach.getJogamPK());
		attachedJogamCollectionNew.add(jogamCollectionNewJogamToAttach);
	    }
	    jogamCollectionNew = attachedJogamCollectionNew;
	    objetivo.setJogamCollection(jogamCollectionNew);
	    Collection<Historico> attachedHistoricoCollectionNew = new ArrayList<Historico>();
	    for (Historico historicoCollectionNewHistoricoToAttach : historicoCollectionNew) {
		historicoCollectionNewHistoricoToAttach = em
			.getReference(historicoCollectionNewHistoricoToAttach
				.getClass(),
				historicoCollectionNewHistoricoToAttach
					.getCodPartida());
		attachedHistoricoCollectionNew
			.add(historicoCollectionNewHistoricoToAttach);
	    }
	    historicoCollectionNew = attachedHistoricoCollectionNew;
	    objetivo.setHistoricoCollection(historicoCollectionNew);
	    Collection<Objderjogador> attachedObjderjogadorCollectionNew = new ArrayList<Objderjogador>();
	    for (Objderjogador objderjogadorCollectionNewObjderjogadorToAttach : objderjogadorCollectionNew) {
		objderjogadorCollectionNewObjderjogadorToAttach = em
			.getReference(
				objderjogadorCollectionNewObjderjogadorToAttach
					.getClass(),
				objderjogadorCollectionNewObjderjogadorToAttach
					.getObjderjogadorPK());
		attachedObjderjogadorCollectionNew
			.add(objderjogadorCollectionNewObjderjogadorToAttach);
	    }
	    objderjogadorCollectionNew = attachedObjderjogadorCollectionNew;
	    objetivo.setObjderjogadorCollection(objderjogadorCollectionNew);
	    Collection<Objderjogador> attachedObjderjogadorCollection1New = new ArrayList<Objderjogador>();
	    for (Objderjogador objderjogadorCollection1NewObjderjogadorToAttach : objderjogadorCollection1New) {
		objderjogadorCollection1NewObjderjogadorToAttach = em
			.getReference(
				objderjogadorCollection1NewObjderjogadorToAttach
					.getClass(),
				objderjogadorCollection1NewObjderjogadorToAttach
					.getObjderjogadorPK());
		attachedObjderjogadorCollection1New
			.add(objderjogadorCollection1NewObjderjogadorToAttach);
	    }
	    objderjogadorCollection1New = attachedObjderjogadorCollection1New;
	    objetivo.setObjderjogadorCollection1(objderjogadorCollection1New);
	    objetivo = em.merge(objetivo);
	    if (objterritorioNew != null
		    && !objterritorioNew.equals(objterritorioOld)) {
		Objetivo oldObjetivoOfObjterritorio = objterritorioNew
			.getObjetivo();
		if (oldObjetivoOfObjterritorio != null) {
		    oldObjetivoOfObjterritorio.setObjterritorio(null);
		    oldObjetivoOfObjterritorio = em
			    .merge(oldObjetivoOfObjterritorio);
		}
		objterritorioNew.setObjetivo(objetivo);
		objterritorioNew = em.merge(objterritorioNew);
	    }
	    if (codMundoOld != null && !codMundoOld.equals(codMundoNew)) {
		codMundoOld.getObjetivoCollection().remove(objetivo);
		codMundoOld = em.merge(codMundoOld);
	    }
	    if (codMundoNew != null && !codMundoNew.equals(codMundoOld)) {
		codMundoNew.getObjetivoCollection().add(objetivo);
		codMundoNew = em.merge(codMundoNew);
	    }
	    if (objconqcontNew != null
		    && !objconqcontNew.equals(objconqcontOld)) {
		Objetivo oldObjetivoOfObjconqcont = objconqcontNew
			.getObjetivo();
		if (oldObjetivoOfObjconqcont != null) {
		    oldObjetivoOfObjconqcont.setObjconqcont(null);
		    oldObjetivoOfObjconqcont = em
			    .merge(oldObjetivoOfObjconqcont);
		}
		objconqcontNew.setObjetivo(objetivo);
		objconqcontNew = em.merge(objconqcontNew);
	    }
	    for (Jogam jogamCollectionNewJogam : jogamCollectionNew) {
		if (!jogamCollectionOld.contains(jogamCollectionNewJogam)) {
		    Objetivo oldCodObjetivoOfJogamCollectionNewJogam = jogamCollectionNewJogam
			    .getCodObjetivo();
		    jogamCollectionNewJogam.setCodObjetivo(objetivo);
		    jogamCollectionNewJogam = em.merge(jogamCollectionNewJogam);
		    if (oldCodObjetivoOfJogamCollectionNewJogam != null
			    && !oldCodObjetivoOfJogamCollectionNewJogam
				    .equals(objetivo)) {
			oldCodObjetivoOfJogamCollectionNewJogam
				.getJogamCollection().remove(
					jogamCollectionNewJogam);
			oldCodObjetivoOfJogamCollectionNewJogam = em
				.merge(oldCodObjetivoOfJogamCollectionNewJogam);
		    }
		}
	    }
	    for (Historico historicoCollectionOldHistorico : historicoCollectionOld) {
		if (!historicoCollectionNew
			.contains(historicoCollectionOldHistorico)) {
		    historicoCollectionOldHistorico.setCodObjetivo(null);
		    historicoCollectionOldHistorico = em
			    .merge(historicoCollectionOldHistorico);
		}
	    }
	    for (Historico historicoCollectionNewHistorico : historicoCollectionNew) {
		if (!historicoCollectionOld
			.contains(historicoCollectionNewHistorico)) {
		    Objetivo oldCodObjetivoOfHistoricoCollectionNewHistorico = historicoCollectionNewHistorico
			    .getCodObjetivo();
		    historicoCollectionNewHistorico.setCodObjetivo(objetivo);
		    historicoCollectionNewHistorico = em
			    .merge(historicoCollectionNewHistorico);
		    if (oldCodObjetivoOfHistoricoCollectionNewHistorico != null
			    && !oldCodObjetivoOfHistoricoCollectionNewHistorico
				    .equals(objetivo)) {
			oldCodObjetivoOfHistoricoCollectionNewHistorico
				.getHistoricoCollection().remove(
					historicoCollectionNewHistorico);
			oldCodObjetivoOfHistoricoCollectionNewHistorico = em
				.merge(oldCodObjetivoOfHistoricoCollectionNewHistorico);
		    }
		}
	    }
	    for (Objderjogador objderjogadorCollectionNewObjderjogador : objderjogadorCollectionNew) {
		if (!objderjogadorCollectionOld
			.contains(objderjogadorCollectionNewObjderjogador)) {
		    Objetivo oldObjetivoOfObjderjogadorCollectionNewObjderjogador = objderjogadorCollectionNewObjderjogador
			    .getObjetivo();
		    objderjogadorCollectionNewObjderjogador
			    .setObjetivo(objetivo);
		    objderjogadorCollectionNewObjderjogador = em
			    .merge(objderjogadorCollectionNewObjderjogador);
		    if (oldObjetivoOfObjderjogadorCollectionNewObjderjogador != null
			    && !oldObjetivoOfObjderjogadorCollectionNewObjderjogador
				    .equals(objetivo)) {
			oldObjetivoOfObjderjogadorCollectionNewObjderjogador
				.getObjderjogadorCollection()
				.remove(objderjogadorCollectionNewObjderjogador);
			oldObjetivoOfObjderjogadorCollectionNewObjderjogador = em
				.merge(oldObjetivoOfObjderjogadorCollectionNewObjderjogador);
		    }
		}
	    }
	    for (Objderjogador objderjogadorCollection1NewObjderjogador : objderjogadorCollection1New) {
		if (!objderjogadorCollection1Old
			.contains(objderjogadorCollection1NewObjderjogador)) {
		    Objetivo oldObjetivo1OfObjderjogadorCollection1NewObjderjogador = objderjogadorCollection1NewObjderjogador
			    .getObjetivo1();
		    objderjogadorCollection1NewObjderjogador
			    .setObjetivo1(objetivo);
		    objderjogadorCollection1NewObjderjogador = em
			    .merge(objderjogadorCollection1NewObjderjogador);
		    if (oldObjetivo1OfObjderjogadorCollection1NewObjderjogador != null
			    && !oldObjetivo1OfObjderjogadorCollection1NewObjderjogador
				    .equals(objetivo)) {
			oldObjetivo1OfObjderjogadorCollection1NewObjderjogador
				.getObjderjogadorCollection1()
				.remove(objderjogadorCollection1NewObjderjogador);
			oldObjetivo1OfObjderjogadorCollection1NewObjderjogador = em
				.merge(oldObjetivo1OfObjderjogadorCollection1NewObjderjogador);
		    }
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		Integer id = objetivo.getCodObjetivo();
		if (findObjetivo(id) == null) {
		    throw new NonexistentEntityException(
			    "The objetivo with id " + id + " no longer exists.");
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
	    Objetivo objetivo;
	    try {
		objetivo = em.getReference(Objetivo.class, id);
		objetivo.getCodObjetivo();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The objetivo with id "
			+ id + " no longer exists.", enfe);
	    }
	    List<String> illegalOrphanMessages = null;
	    Objterritorio objterritorioOrphanCheck = objetivo
		    .getObjterritorio();
	    if (objterritorioOrphanCheck != null) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Objetivo ("
				+ objetivo
				+ ") cannot be destroyed since the Objterritorio "
				+ objterritorioOrphanCheck
				+ " in its objterritorio field has a non-nullable objetivo field.");
	    }
	    Objconqcont objconqcontOrphanCheck = objetivo.getObjconqcont();
	    if (objconqcontOrphanCheck != null) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Objetivo ("
				+ objetivo
				+ ") cannot be destroyed since the Objconqcont "
				+ objconqcontOrphanCheck
				+ " in its objconqcont field has a non-nullable objetivo field.");
	    }
	    Collection<Jogam> jogamCollectionOrphanCheck = objetivo
		    .getJogamCollection();
	    for (Jogam jogamCollectionOrphanCheckJogam : jogamCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Objetivo ("
				+ objetivo
				+ ") cannot be destroyed since the Jogam "
				+ jogamCollectionOrphanCheckJogam
				+ " in its jogamCollection field has a non-nullable codObjetivo field.");
	    }
	    Collection<Objderjogador> objderjogadorCollectionOrphanCheck = objetivo
		    .getObjderjogadorCollection();
	    for (Objderjogador objderjogadorCollectionOrphanCheckObjderjogador : objderjogadorCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Objetivo ("
				+ objetivo
				+ ") cannot be destroyed since the Objderjogador "
				+ objderjogadorCollectionOrphanCheckObjderjogador
				+ " in its objderjogadorCollection field has a non-nullable objetivo field.");
	    }
	    Collection<Objderjogador> objderjogadorCollection1OrphanCheck = objetivo
		    .getObjderjogadorCollection1();
	    for (Objderjogador objderjogadorCollection1OrphanCheckObjderjogador : objderjogadorCollection1OrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages
			.add("This Objetivo ("
				+ objetivo
				+ ") cannot be destroyed since the Objderjogador "
				+ objderjogadorCollection1OrphanCheckObjderjogador
				+ " in its objderjogadorCollection1 field has a non-nullable objetivo1 field.");
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    Mundo codMundo = objetivo.getCodMundo();
	    if (codMundo != null) {
		codMundo.getObjetivoCollection().remove(objetivo);
		codMundo = em.merge(codMundo);
	    }
	    Collection<Historico> historicoCollection = objetivo
		    .getHistoricoCollection();
	    for (Historico historicoCollectionHistorico : historicoCollection) {
		historicoCollectionHistorico.setCodObjetivo(null);
		historicoCollectionHistorico = em
			.merge(historicoCollectionHistorico);
	    }
	    em.remove(objetivo);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Objetivo> findObjetivoEntities() {
	return findObjetivoEntities(true, -1, -1);
    }

    public List<Objetivo> findObjetivoEntities(int maxResults, int firstResult) {
	return findObjetivoEntities(false, maxResults, firstResult);
    }

    private List<Objetivo> findObjetivoEntities(boolean all, int maxResults,
	    int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Objetivo.class));
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

    public Objetivo findObjetivo(Integer id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Objetivo.class, id);
	} finally {
	    em.close();
	}
    }

    public int getObjetivoCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Objetivo> rt = cq.from(Objetivo.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
