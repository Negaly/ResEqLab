package es.upm.dit.isst.reserve.dao;

import java.util.Calendar;
import java.util.Date;
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

	@Override
	public List<Reserve> listReserves() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Reserve m");
		List<Reserve> reserves = q.getResultList();
		return reserves;
	}

	@Override
	public long add(Calendar start, Calendar end, String user, long resource) {
		long reserveid;
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();

			Reserve reserve = new Reserve(start, end, user, resource);
			em.persist(reserve);
			em.close();
			reserveid = reserve.getId();

			System.out.println(reserveid);
			return reserveid;
		}

	}

	@Override
	public List<Reserve> getReserves() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Reserve t ");
		System.out.println(q.getResultList());
		// q.setParameter("userId", userId);
		List<Reserve> reserves = q.getResultList();
		return reserves;
	}

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

	@Override
	public List<Reserve> getReserves(String userId) {

		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Reserve t where t.user = :userId");
		System.out.println(q);
		q.setParameter("userId", userId);
		List<Reserve> reserves = q.getResultList();
		return reserves;
	}

	@Override
	public Reserve getReserve(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Reserve reserve = em.find(Reserve.class, id);
			return reserve;
		} finally {
			em.close();
		}
	}

	@Override
	public void update(long id, Calendar start, Calendar end) {
		EntityManager em = EMFService.get().createEntityManager();
		Reserve newReserve = null;
		try {
			Reserve reserve = em.find(Reserve.class, id);
			String user = reserve.getUser();
			long resource = reserve.getResource();
			em.remove(reserve);
			newReserve = new Reserve(start, end, user, resource);

		} finally {
			em.close();
			EntityManager am = EMFService.get().createEntityManager();
			am.persist(newReserve);
			am.close();

		}
	}

	@Override
	public int[][] mapCheck(Resource[][] resourceMap, Reserve hora) {
		int[][] mapBoolean = new int[resourceMap[0].length][resourceMap.length];
		for (int i = 0; i < resourceMap.length; i++) {
			for (int j = 0; j < resourceMap[0].length; j++) {
				if (resourceMap[i][j] == null) {
					// System.out.println(mapBoolean[i][j]);
					mapBoolean[i][j] = 0;

				} else {
					System.out.println("i " + i + "j " + j);
					mapBoolean[i][j] = 1;
					for (long reserveid : resourceMap[i][j].getReserves()) {
						// System.out.println(reserveid);

						Reserve reserva = this.getReserve(reserveid);
						// System.out.println(reserva);

						if (hora.ocupado(reserva)) {
							mapBoolean[i][j] = 2;
						}
						// mapBoolean[i][j] = hora.ocupado(reserva);
						// System.out.println(mapBoolean[i][j]);
					}
				}

			}
		}

		return mapBoolean;
	}

}
