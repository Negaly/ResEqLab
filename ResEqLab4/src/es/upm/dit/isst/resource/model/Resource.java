package es.upm.dit.isst.resource.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import es.upm.dit.isst.reserve.model.Reserve;

@Entity
public class Resource implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private List<Long> reserves; 
	
	public Resource(String title, String description) {
		this.title = title;
		this.description = description;
		
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



} 
