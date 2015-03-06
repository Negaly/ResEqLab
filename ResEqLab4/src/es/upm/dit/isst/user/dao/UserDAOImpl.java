package es.upm.dit.isst.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.resource.dao.EMFService;

import es.upm.dit.isst.user.model.User;

public class UserDAOImpl implements UserDAO {
	
	private static UserDAOImpl instance;

	private UserDAOImpl() {
	}

	public static UserDAOImpl getInstance(){
		if (instance == null)
			instance = new UserDAOImpl();
		return instance;
	}

	
	@Override
	public void add(String nickname, String name, String pass) {
		// TODO Auto-generated method stub
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			User user = new User(nickname, name, pass);
			em.persist(user);
			em.close();
		}
	}

	@Override
	public void remove(long id) {
		// TODO Auto-generated method stub
		EntityManager em = EMFService.get().createEntityManager();
		try {
			User user = em.find(User.class, id);
			em.remove(user);
		} finally {
			em.close();
		}
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from User t ");
		System.out.println(q.getResultList());
		//q.setParameter("userId", userId);
		List<User> users = q.getResultList();
		return users;
	}

	
}
