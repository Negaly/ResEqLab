package es.upm.dit.isst.resource.dao;

import java.util.List;



import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;

public interface ResourceDAO {

	public List<Resource> listResources();
	public void add (String title, String description);
	public List<Resource> getResources();
	public void remove (long id);
    public void addReserve(long resourceid, String user);

}
