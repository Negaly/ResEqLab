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
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/hh/mm");
//		String strdate = sdf.format(start.getTime());
//		String enddate = sdf.format(end.getTime());
//		System.out.println(strdate);
//
//		this.end = enddate;
//		this.start = strdate;
		
		String startDate = dateFormat(start);
		String endDate = dateFormat(end);
			
		 this.end = endDate;
		 this.start =  startDate;

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
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/hh/mm");
//		String enddate = sdf.format(end.getTime());
		String endDate = dateFormat(end);

		this.end = endDate;
	}

	public String getStart() {
		return start;
	}

	public void setStart(Calendar start) {
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/hh/mm");
//		String strdate = sdf.format(start.getTime());
//		this.start = strdate;
		
		String startDate = dateFormat(start);

		this.end = startDate;
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

	public String dateFormat(Calendar calendar){
		calendar.get(Calendar.YEAR);
		calendar.get(Calendar.HOUR);
		
		String date = calendar.get(Calendar.MONTH) + "-" +
			 	  	  calendar.get(Calendar.DAY_OF_MONTH) + "-" +
				 	  calendar.get(Calendar.YEAR) + "   " +
				 	  calendar.get(Calendar.HOUR) + ":" +
				 	  calendar.get(Calendar.MINUTE);
		return date;
	}
}
