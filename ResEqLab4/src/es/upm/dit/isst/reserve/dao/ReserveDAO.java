package es.upm.dit.isst.reserve.dao;

import java.util.List;




import es.upm.dit.isst.reserve.model.Reserve;

public interface ReserveDAO {

	public List<Reserve> listReserves();
	
	public void add(String starthour, String endhour, String startdate,
			String enddate, String user);
	public void remove (long id);
	public List<Reserve> getReserves();
}
