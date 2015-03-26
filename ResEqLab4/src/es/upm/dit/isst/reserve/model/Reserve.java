package es.upm.dit.isst.reserve.model;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

	private String start;
	private String end;
	private String user;
	private long resource;

	public Reserve(Calendar start, Calendar end, String user, long resource) {
		// Integer.parseInt(enteroString) // reservas del
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/hh/mm");
		String strdate = sdf.format(start.getTime());
		String enddate = sdf.format(end.getTime());
		System.out.println(strdate);

		this.end = strdate;
		this.start = enddate;

		// this.end = start;
		// this.start = end;

		this.user = user;
		this.resource = resource;
	}

	public Long getId() {
		return id;
	}

	public String getEnd() {

		return end;
	}

	public void setEnd(Calendar end) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/hh/mm");
		String enddate = sdf.format(end.getTime());
		this.end = enddate;
	}

	public String getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/hh/mm");
		String strdate = sdf.format(start.getTime());
		this.start = strdate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getResource() {
		return resource;
	}

	public void setResource(long resource) {
		this.resource = resource;
	}

}
