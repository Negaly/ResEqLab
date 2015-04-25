package es.upm.dit.isst.lab.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.users.User;

import es.upm.dit.isst.lab.model.lab;
import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;

public interface LabDAO {

	public lab getLab();

	public void addResourcePos(int parseInt, int parseInt2, Resource resource);

	// public void remove(long id);
	//
	// List<Reserve> listReserves();
	//
	// List<Reserve> getReserves();
	//
	// void add(Calendar start, Calendar end, String user, long resource);
	//
	// public List<Reserve> getReserves(String nickname);
	//
	// public Reserve getReserve(long parseLong);
	//
	// public void update(long id,Calendar start, Calendar end);
	//
	// public boolean[][] mapCheck(Resource[][] ResourceMap, Reserve hora);
}
