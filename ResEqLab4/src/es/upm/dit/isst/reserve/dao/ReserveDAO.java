package es.upm.dit.isst.reserve.dao;

import java.util.List;





import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;

public interface ReserveDAO {

	public void add(String starthour, String endhour, String startdate,
			String enddate, String user, long resource);
	public void remove (long id);
	
}
