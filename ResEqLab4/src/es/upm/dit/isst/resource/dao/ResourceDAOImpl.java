package es.upm.dit.isst.resource.dao;

import java.util.Calendar;
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
	public void add(String title, String description, int sessionTime) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Resource resource = new Resource(title, description, sessionTime);
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
	public void addReserve(long reserveid, String user,long resourceid) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Resource resource = em.find(Resource.class, resourceid);
			// if (resource.getReserves().contains(id)) {
			// System.out.println("Recurso ya reservado");

			// } else {
			System.out.println(reserveid);

			resource.addReserve(reserveid);
			em.merge(resource);
			System.out.println("Reservo");

			// }

		} finally {
			em.close();
			System.out.println("LlegueFinally :)" + reserveid);

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

	@Override
	public void modifyResource(long resourceId, String title,
			String description, int sessionTime, boolean available) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Resource resource = em.find(Resource.class, resourceId);
			resource.setTitle(title);
			resource.setDescription(description);
			resource.setSessionTime(sessionTime);
			resource.setAvailable(available);
			em.merge(resource);
		} finally {
			em.close();
		}
	}

	@Override
	public boolean proDisp(String startDate, String endDate, Resource resource) {
		return false;
	}

	@Override
	public void removeReserve(String reserveId, String resourceId) {
			EntityManager em = EMFService.get().createEntityManager();
		try {
			Resource resource = em.find(Resource.class, Long.parseLong(resourceId));
			resource.removeReserve(reserveId);
			em.merge(resource);
		} finally {
			em.close();
		}
		
	}

	// @Override
	// public boolean proDisp(Resource resource, Resource resource1) {
	//
	//
	// for (Long reserve : resource.getReserves()){
	//
	//
	// }
	// String sFecha = (startDate.split("  ")[0]);
	// String sHora = (startDate.split("  ")[1]);
	//
	// int sMonth = Integer.parseInt(sFecha.split("-")[0]);
	// int sDay = Integer.parseInt(sFecha.split("-")[1]);
	// int sYear = Integer.parseInt(sFecha.split("-")[2]);
	//
	// int sHour = Integer.parseInt(sHora.split(":")[0]);
	// int sMinutes = Integer.parseInt(sHora.split(":")[1]);
	//
	//
	// String eFecha = (startDate.split("  ")[0]);
	// String eHora = (startDate.split("  ")[1]);
	//
	// int eMonth = Integer.parseInt(eFecha.split("-")[0]);
	// int eDay = Integer.parseInt(eFecha.split("-")[1]);
	// int eYear = Integer.parseInt(eFecha.split("-")[2]);
	//
	// int eHour = Integer.parseInt(eHora.split(":")[0]);
	// int eMinutes = Integer.parseInt(eHora.split(":")[1]);
	//
	// if(sFecha != eFecha){ return true;}
	// if(sFecha == eFecha){ return false;}
	// return false;
	// }

	// @Override
	// public boolean dateProbe(int sHour, int sMinutes,int eHour, int eMinutes)
	// {
	//
	// if()
	//
	// return false;
	// }

}
