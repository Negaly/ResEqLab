package es.upm.dit.isst.resource.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Resource implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private int sessionTime;
	private List<Long> reserves; 
	private boolean available;
	
	public Resource(String title, String description,int sessionTime) {
		this.title = title;
		this.description = description;		
		this.sessionTime = sessionTime;
		this.setAvailable(true);
	}

	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Long> getReserves() {
		return reserves;
	}
	public void addReserve(Long reserve) {
		this.reserves.add(reserve);
	}
	public int getSessionTime() {
		System.out.println(sessionTime);
		return sessionTime;
	}

	public void setSessionTime(int sessionTime) {
		this.sessionTime = sessionTime;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}



} 
