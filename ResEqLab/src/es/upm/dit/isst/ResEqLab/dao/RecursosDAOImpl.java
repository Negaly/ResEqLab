package es.upm.dit.isst.ResEqLab.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.ResEqLab.model.Recurso;

public class RecursosDAOImpl implements RecursosDAO {

	private static RecursosDAOImpl instance;
	private RecursosDAOImpl() {
	}

	public static RecursosDAOImpl getInstance() {
		if (instance == null)
			instance = new RecursosDAOImpl();
		return instance;
	}
	@Override
	public List<Recurso> listRecursos() {
		// TODO Auto-generated method stub
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Recurso m");
		List<Recurso> recursos = q.getResultList();
		return recursos;
	}

	@Override
	public void add(String userId, String title, String description) {
		// TODO Auto-generated method stub
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Recurso recurso = new Recurso(userId,title, description);
			em.persist(recurso);
			em.close();
		}
	}

	@Override
	public List<Recurso> getRecursos(String userId) {
		// TODO Auto-generated method stub
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from recurso t where t.author =:userId");
		q.setParameter("userId", userId);
		List<Recurso> recursos = q.getResultList();
		return recursos;
	}

	@Override
	public void remove(long id) {
		// TODO Auto-generated method stub
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Recurso recurso = em.find(Recurso.class, id);
			em.remove(recurso);
		} finally {
			em.close();
		}
	}

	@Override
	public List<String> getUsers() {
		// TODO Auto-generated method stub
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select distinct t.author from Recurso t");
		List<String> users = q.getResultList();
		return users;
	}

	
}
