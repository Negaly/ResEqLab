package es.upm.dit.isst.lab.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import es.upm.dit.isst.resource.model.Resource;

@Entity
public class lab implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title = "Unico";
	private Resource[][] mapa = new Resource[10][10];

	public lab(int vertical, int horizontal) {

		this.mapa = new Resource[vertical][horizontal];

	}

	public lab() {
		this.mapa = new Resource[5][5];

	}

	public Long getId() {
		return id;
	}

	public Resource[][] getMapa() {
		return mapa;
	}

	public void setMapa(Resource[][] mapa) {

		this.mapa = mapa;
		// System.out.println(mapa[0][0]);
	}

}
