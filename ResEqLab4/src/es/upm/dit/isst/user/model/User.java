package es.upm.dit.isst.user.model;
import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nickname;
	private String name;
	private String pass;
	//private List<Resource> resources;
	
	public User(String nickname, String name, String pass){
		this.nickname = nickname;
		this.name = name;
		this.pass = pass;
		//this.resources = resources;
	}
	public Long getId() {
		return id;
	}
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	//public List<Resource> getResources() {
	//	return resources;
	//}

	


} 
