package es.upm.dit.isst.reserve.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;

public interface ReserveDAO {

	public void remove(long id);

	List<Reserve> listReserves();

	List<Reserve> getReserves();

	void add(Calendar start, Calendar end, String user, long resource);

}
