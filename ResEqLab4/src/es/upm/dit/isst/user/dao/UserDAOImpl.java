package es.upm.dit.isst.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.resource.dao.EMFService;
import es.upm.dit.isst.resource.model.Resource;
import es.upm.dit.isst.user.model.AppUser;

public class UserDAOImpl implements UserDAO {

	private static UserDAOImpl instance;

	private UserDAOImpl() {
	}

	public static UserDAOImpl getInstance() {
		if (instance == null)
			instance = new UserDAOImpl();
		return instance;
	}

	@Override
	public void add(String googleId, String name, int level) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			AppUser user = new AppUser(googleId, name, level);
			em.persist(user);
			em.close();
		}
	}

	@Override
	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			AppUser user = em.find(AppUser.class, id);
			em.remove(user);
		} finally {
			em.close();
		}
	}

	@Override
	public List<AppUser> getUsers() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from User t ");
		// System.out.println(q.getResultList());
		// q.setParameter("userId", userId);
		List<AppUser> users = q.getResultList();
		return users;
	}

	@Override
	public boolean appUserExists(String id) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from AppUser t ");
		List<AppUser> users = q.getResultList();
		for (AppUser user : users) {
			// System.out.println("AppUserGoogleID: "+(user.getGoogleId()));
			// System.out.println("GoogleID:        "+id);
			// System.out.println("Coinciden?"+(user.getGoogleId().equals(id)));
			if (user.getGoogleId().equals(id))
				return true;
		}

		return false;
	}

}
