package es.upm.dit.isst.lab.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.users.User;

import es.upm.dit.isst.lab.model.lab;
import es.upm.dit.isst.reserve.dao.EMFService;
import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;

public class LabDAOImpl implements LabDAO {

	private static LabDAOImpl instance;

	private LabDAOImpl() {
	}

	public static LabDAOImpl getInstance() {
		if (instance == null)
			instance = new LabDAOImpl();
		return instance;
	}

	@Override
	public lab getLab() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from lab m");
		List<lab> labs = q.getResultList();
		lab labo;
		//System.out.println("lab.isEmpty= " + labs.isEmpty());
		if (labs.isEmpty()) {
			labo = new lab();
			em.persist(labo);
		} else {
			labo = labs.get(0);
		}

		em.close();
		return labo;
	}

	@Override
	public void addResourcePos(int x, int y, Resource resource) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from lab m");
		List<lab> labs = q.getResultList();
		System.out.println("daoImpl 1" + resource);
		lab labo = null;

		try {

			labo = labs.get(0);

			Resource[][] map = labo.getMapa();
			map[x][y] = resource;
			System.out.println("daoImpl 2 mapXY" + map[x][y]);
		
			labo.setMapa(map);
			System.out.println("daoImpl 3 laboXY" + labo.getMapa()[x][y]);
			em.merge(labo);
		} finally {
			labo.setMapaIDsPos(Integer.toString(x), Integer.toString(y),
					resource.getId());
			em.close();
		}
		// EntityManager am = EMFService.get().createEntityManager();
		//
		// q = am.createQuery("select m from lab m");
		// labs = q.getResultList();
		// labo = labs.get(0);
		// am.close();
	}
}
