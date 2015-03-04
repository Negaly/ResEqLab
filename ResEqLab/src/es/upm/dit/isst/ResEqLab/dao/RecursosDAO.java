package es.upm.dit.isst.ResEqLab.dao;

import java.util.List;

import es.upm.dit.isst.ResEqLab.model.Recurso;


public interface RecursosDAO {
	public List<Recurso> listRecursos();

	public List<Recurso> getRecursos(String userId);

	public void remove(long id);

	public List<String> getUsers();

	void add(String userId, String title, String description);
}