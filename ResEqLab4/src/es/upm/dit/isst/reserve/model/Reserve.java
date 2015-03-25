package es.upm.dit.isst.reserve.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import es.upm.dit.isst.resource.model.Resource;

@Entity
public class Reserve implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String starthour;
	private String endhour;
	private String startdate;
	private String enddate;
	private String user;
	private long resource;
	

	public Reserve(String starthour, String endhour, String startdate,
			String enddate, String user, long resource) {
		this.starthour = starthour;
		this.endhour = endhour;
		this.startdate = startdate;
		this.enddate = enddate;
		this.user = user;
		this.resource = resource;
	}

	public Long getId() {
		return id;
	}

	public String getStarthour() {
		return starthour;
	}

	public void setStarthour(String starthour) {
		this.starthour = starthour;
	}

	public String getEndhour() {
		return endhour;
	}

	public void setEndhour(String endhour) {
		this.endhour = endhour;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public long getResource(){
		return resource;
	}
	public void setResource(long resource){
		this.resource = resource;
	}

}
