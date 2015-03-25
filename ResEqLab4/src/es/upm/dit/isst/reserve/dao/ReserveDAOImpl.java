package es.upm.dit.isst.reserve.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.users.User;

import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;

public class ReserveDAOImpl implements ReserveDAO {

	private static ReserveDAOImpl instance;

	private ReserveDAOImpl() {
	}

	public static ReserveDAOImpl getInstance() {
		if (instance == null)
			instance = new ReserveDAOImpl();
		return instance;
	}

	//@Override
//	public List<Reserve> listReserves() {
//		EntityManager em = EMFService.get().createEntityManager();
//		// read the existing entries
//		Query q = em.createQuery("select m from Reserves m");
//		List<Reserve> reserves = q.getResultList();
//		return reserves;
//	}

	@Override
	public void add(String starthour, String endhour, String startdate, String enddate, String user, long resource) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			
			Reserve reserve = new Reserve(starthour, endhour, startdate, enddate, user, resource);
			em.persist(reserve);
			em.close();
		}

	}
//
//	@Override
//	public List<Reserve> getReserves() {
//		EntityManager em = EMFService.get().createEntityManager();
//		Query q = em.createQuery("select t from Reserve t ");
//		System.out.println(q.getResultList());
//		// q.setParameter("userId", userId);
//		List<Reserve> reserves = q.getResultList();
//		return reserves;
//	}

	@Override
	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Reserve reserve = em.find(Reserve.class, id);
			em.remove(reserve);
		} finally {
			em.close();
		}
	}

	



}
