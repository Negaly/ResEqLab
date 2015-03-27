package es.upm.dit.isst.resource.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.users.User;

import es.upm.dit.isst.reserve.dao.EMFService;
import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;

public class ResourceDAOImpl implements ResourceDAO {

	private static ResourceDAOImpl instance;

	private ResourceDAOImpl() {
	}

	public static ResourceDAOImpl getInstance() {
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
	public void add(String title, String description) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Resource resource = new Resource(title, description);
			em.persist(resource);
			em.close();
		}

	}

	@Override
	public List<Resource> getResources() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Resource t ");
		System.out.println(q.getResultList());
		// q.setParameter("userId", userId);
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
	public void addReserve(long id, String user) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Resource resource = em.find(Resource.class, id);
				if (resource.getReserves().contains(id)) {
					System.out.println("Recurso ya reservado");
					

				} else {
					resource.addReserve(id);
					em.merge(resource);
					System.out.println("Reservo");

				}
			
		} finally {
			em.close();
			System.out.println("LlegueFinally :)" +id);

		}
	}

	@Override
	public Resource getResource(long resourceId) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Resource resource = em.find(Resource.class, resourceId);
			return resource;
		} finally {
			em.close();
		}
	}

}
