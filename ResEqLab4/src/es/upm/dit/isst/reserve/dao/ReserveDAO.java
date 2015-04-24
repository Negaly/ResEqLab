package es.upm.dit.isst.reserve.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.users.User;

import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;

public interface ReserveDAO {

	public void remove(long id);

	List<Reserve> listReserves();

	List<Reserve> getReserves();

	long add(Calendar start, Calendar end, String user, long resource);

	public List<Reserve> getReserves(String nickname);

	public Reserve getReserve(long parseLong);

	public void update(long id,Calendar start, Calendar end);
	
	public int[][] mapCheck(Resource[][] ResourceMap, Reserve hora);
}
