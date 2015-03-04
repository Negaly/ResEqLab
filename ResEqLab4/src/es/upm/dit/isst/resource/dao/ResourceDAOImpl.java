package es.upm.dit.isst.resource.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.resource.model.Resource;

public class ResourceDAOImpl implements ResourceDAO {

	private static ResourceDAOImpl instance;

	private ResourceDAOImpl() {
	}

	public static ResourceDAOImpl getInstance(){
		if (instance == null)
			instance = new ResourceDAOImpl();
		return instance;
	}


	@Override
	public List<Resource> listResources() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Resources m");
		List<Resource> resources = q.getResultList();
		return resources;
	}

	@Override
	public void add(String userId, String title, String description) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Resource resource = new Resource(userId, title, description);
			em.persist(resource);
			em.close();
		}

	}

	@Override
	public List<Resource> getResources(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Resource t where t.author = :userId");
		q.setParameter("userId", userId);
		List<Resource> resources = q.getResultList();
		return resources;
	}

	@Override
	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Resource resource = em.find(Resource.class, id);
			em.remove(resource);
		} finally {
			em.close();
		}
	}

	@Override
	public List<String> getUsers() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select distinct t.author from Todo t");
		List<String> users = q.getResultList();
		return users;
	}
}
