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
		// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/hh/mm");
		// String strdate = sdf.format(start.getTime());
		// String enddate = sdf.format(end.getTime());
		// System.out.println(strdate);
		//
		// this.end = enddate;
		// this.start = strdate;

		String startDate = dateFormat(start);
		String endDate = dateFormat(end);

		this.end = endDate;
		this.start = startDate;

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
		// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/hh/mm");
		// String enddate = sdf.format(end.getTime());
		String endDate = dateFormat(end);

		this.end = endDate;
	}

	public String getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/hh/mm");
		// String strdate = sdf.format(start.getTime());
		// this.start = strdate;

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

	public String dateFormat(Calendar calendar) {

		String day;
		String month;
		String hour;
		String minute;

		if (calendar.get(Calendar.MONTH) <= 9) {
			month = "0" + calendar.get(Calendar.MONTH);
		} else {
			month = "" + calendar.get(Calendar.MONTH);
		}

		if (calendar.get(Calendar.DAY_OF_MONTH) <= 9) {
			day = "0" + calendar.get(Calendar.DAY_OF_MONTH);
		} else {
			day = "" + calendar.get(Calendar.DAY_OF_MONTH);
		}

		if (calendar.get(Calendar.HOUR_OF_DAY) <= 9) {
			hour = "0" + calendar.get(Calendar.HOUR_OF_DAY);
		} else {
			hour = "" + calendar.get(Calendar.HOUR_OF_DAY);
		}

		if (calendar.get(Calendar.MINUTE) <= 9) {
			minute = "0" + calendar.get(Calendar.MINUTE);
		} else {
			minute = "" + calendar.get(Calendar.MINUTE);
		}

		String date = month + "-" + day + "-" + calendar.get(Calendar.YEAR)
				+ "   " + hour + ":" + minute;
		return date;
	}

	public boolean ocupado(Reserve reserve2) {
		// // TODO comprobar que funciona bien
		// System.out.println("Mi fecha:  " + this.getStart());
		// System.out.println("Nueva fecha:  " + reserve2.getStart());
		String mifecha = this.getStart().split(" ")[0];
		String nuevafecha = reserve2.getStart().split(" ")[0];
		// System.out.println("Mi fecha:  " + mifecha);
		// System.out.println("Nueva fecha:  " + nuevafecha);

		// solo comrpuebo si mismo dia
		if (mifecha.equals(nuevafecha)) {
			// System.out.println("Mismo dia");
			// compruebo que la hora sea la misma o parecida

			int Smihora = Integer.parseInt(this.getStart().split("   ")[1]
					.split(":")[0]);
			int Smimin = Integer.parseInt(this.getStart().split("   ")[1]
					.split(":")[1]);
			int Snuevahora = Integer
					.parseInt(reserve2.getStart().split("   ")[1].split(":")[0]);
			int Snuevomin = Integer
					.parseInt(reserve2.getStart().split("   ")[1].split(":")[1]);
			int Emihora = Integer.parseInt(this.getEnd().split("   ")[1]
					.split(":")[0]);
			int Emimin = Integer.parseInt(this.getEnd().split("   ")[1]
					.split(":")[1]);
			int Enuevahora = Integer.parseInt(reserve2.getEnd().split("   ")[1]
					.split(":")[0]);
			int Enuevomin = Integer.parseInt(reserve2.getEnd().split("   ")[1]
					.split(":")[1]);
			// System.out.println("Mi start y nuevo fin");
			// System.out.println("Mi hora:  " + Smihora);
			// System.out.println("Mi min:  " + Smimin);
			// System.out.println("nuevahora:  " + Enuevahora);
			// System.out.println("nuevomin:  " + Enuevomin);
			// System.out.println("Mi fin y nuevo start");
			// System.out.println("Mi hora:  " + Emihora);
			// System.out.println("Mi min:  " + Emimin);
			// System.out.println("nuevahora:  " + Snuevahora);
			// System.out.println("nuevomin:  " + Snuevomin);
			if (Enuevahora < Smihora || Emihora < Snuevahora)
				return false; // reservo
			else {
				if (Enuevahora == Smihora) {
					if (Enuevomin <= Smimin)
						return false; // reservo
				} else if (Emihora == Snuevahora) {
					if (Emimin <= Snuevomin)
						return false; // reservo
				}
				return true;
			}
		}
		return false;
	}
}
