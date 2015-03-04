package es.upm.dit.isst.resource.dao;

import java.util.List;

import es.upm.dit.isst.resource.model.Resource;

public interface ResourceDAO {

	public List<Resource> listResources();
	public void add (String userId, String title, String description);
	public List<Resource> getResources(String userId);
	public void remove (long id);
	public List<String> getUsers();
	
}
