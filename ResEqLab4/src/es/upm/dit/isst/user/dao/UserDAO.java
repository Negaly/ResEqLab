package es.upm.dit.isst.user.dao;

import java.util.List;


import es.upm.dit.isst.user.model.User;

public interface UserDAO {
	
	public void add (String nickname, String name, String pass);
	public void remove (long id);
	public List<User> getUsers();
	

}
