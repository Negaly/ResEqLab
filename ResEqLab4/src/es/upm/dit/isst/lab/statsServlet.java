package es.upm.dit.isst.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.types.resources.comparators.Size;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.lab.model.lab;
import es.upm.dit.isst.reserve.dao.ReserveDAO;
import es.upm.dit.isst.reserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.resource.dao.ResourceDAO;
import es.upm.dit.isst.resource.dao.ResourceDAOImpl;
import es.upm.dit.isst.resource.model.Resource;
import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;
import es.upm.dit.isst.user.dao.UserDAO;
import es.upm.dit.isst.user.dao.UserDAOImpl;

public class statsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String url = userService.createLoginURL("/createUser");
		String urlLinktext = "Login";
		List<Reserve> reserves = null;
		boolean userAdmin = false;

		if (user != null) {
			url = userService.createLogoutURL(req.getRequestURI());
			urlLinktext = "Logout";
			if (userService.isUserLoggedIn()) {
				userAdmin = userService.isUserAdmin();
			}
			req.getSession().setAttribute("userAdmin", userAdmin);

			// ////////////////Gestion Recursos y reservas//////////////////////
			ResourceDAO resourcedao = ResourceDAOImpl.getInstance();
			ReserveDAO reservedao = ReserveDAOImpl.getInstance();
			UserDAO userdao = UserDAOImpl.getInstance();

			// Si no eres admin, solo ves las tuyas
			if (!userAdmin && (user != null)) {// LEVEL en vez de admin
				reserves = reservedao.getReserves(user.getUserId());
			}

			// ///////////////Gestion de req y resp////////////////////////////
			// System.out.println(reserves);

			es.upm.dit.isst.lab.model.lab lab = new lab();

			// Calendar start = new GregorianCalendar(Calendar.getInstance(), 1,
			// 1, 0, 0);
			// Calendar end = new GregorianCalendar(2015, 1, 1, 1, 1);
			// //////OCUPACION HOY//////////
			Calendar now = new GregorianCalendar(Calendar.getInstance().get(
					Calendar.YEAR),
					Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar
							.getInstance().get(Calendar.DATE), Calendar
							.getInstance().get(Calendar.HOUR), Calendar
							.getInstance().get(Calendar.MINUTE));
			Reserve reserva = new Reserve(now, now, "user", 23);
			// int nVisitas = 2;
			int nUsuarios = userdao.getUsers().size();
			int nRecursos = resourcedao.getResources().size();
			int nReservas = reservedao.getReserves().size();

			int nOcupadas = 0;

			for (Resource a : resourcedao.getResources()) {
				for (Long b : a.getReserves()) {
					Reserve reserva2 = reservedao.getReserve(b);
					// System.out.println("reserva " + reserva.getStart());
					// System.out.println("reserva2 " + reserva2.getStart());

					if (reserva.ocupado(reserva2)) {

						nOcupadas++;
						// System.out.println("Recursos = " + nRecursos
						// + "  Ocupadas = " + nOcupadas);
					}
				}
			}
			// hora.ocupado(reserva);
			// //////////////////////SEMANA OCUPADA////////////////////

			// get today and clear time of day
			// get start of this week in milliseconds
			Calendar monday = now;
			monday.set(Calendar.DAY_OF_WEEK, monday.getFirstDayOfWeek() + 2);

			int nResMonday = getNumReservas(monday, resourcedao.getResources(),
					reservedao);
			// ////

			Calendar thuesday = monday;
			thuesday.set(Calendar.DAY_OF_WEEK, now.getFirstDayOfWeek() + 3);
			int nResThuesday = getNumReservas(thuesday,
					resourcedao.getResources(), reservedao);
			// ////////
			Calendar wednesday = monday;
			thuesday.set(Calendar.DAY_OF_WEEK, now.getFirstDayOfWeek() + 4);
			int nResWednesday = getNumReservas(wednesday,
					resourcedao.getResources(), reservedao);
			// ////////
			Calendar thursday = monday;
			thuesday.set(Calendar.DAY_OF_WEEK, now.getFirstDayOfWeek() + 5);
			int nResThursday = getNumReservas(thursday,
					resourcedao.getResources(), reservedao);
			// ////////
			Calendar Friday = monday;
			thuesday.set(Calendar.DAY_OF_WEEK, now.getFirstDayOfWeek() + 6);
			int nResFriday = getNumReservas(Friday, resourcedao.getResources(),
					reservedao);

			req.getSession().setAttribute("nResMonday", nResMonday);
			req.getSession().setAttribute("nResThuesday", nResThuesday);
			req.getSession().setAttribute("nResWednesday", nResWednesday);
			req.getSession().setAttribute("nResThursday", nResThursday);
			req.getSession().setAttribute("nResFriday", nResFriday);
			// req.getSession().setAttribute("nResSaturday", nResSaturday);
			// req.getSession().setAttribute("nResSunday", nResSunday);

			// req.getSession().setAttribute("nVisitas" , nVisitas);
			req.getSession().setAttribute("nUsuarios", nUsuarios);
			req.getSession().setAttribute("nRecursos", nRecursos);
			req.getSession().setAttribute("nReservas", nReservas);
			req.getSession().setAttribute("user", user);

			req.getSession().setAttribute("nOcupadas", nOcupadas);

			RequestDispatcher view = req.getRequestDispatcher("ShowStats.jsp");
			view.forward(req, resp);
		} else {
			resp.sendRedirect(url);
		}
	}

	private int getNumReservas(Calendar day, List<Resource> resources,
			ReserveDAO reservedao) {
		int ocupadas = 0;

		Calendar dayEnd = new GregorianCalendar(day.get(Calendar.YEAR),
				day.get(Calendar.MONTH) + 1, day.get(Calendar.DATE), 23, 0);
		dayEnd.clear(Calendar.MINUTE);
		day.clear(Calendar.MINUTE);
		day.set(Calendar.HOUR_OF_DAY, 0);

		// System.out.println("dayHour " + day.get(Calendar.HOUR_OF_DAY));

		Reserve reserva = new Reserve(day, dayEnd, "user", 23);
		// System.out.println(reserva.getStart());
		for (Resource a : resources) {
			for (Long b : a.getReserves()) {
				Reserve reserva2 = reservedao.getReserve(b);
				// System.out.println("reserva " + reserva.getStart());
				// System.out.println("reserva2 " + reserva2.getStart());
				// System.out.println("reserva " + reserva.getEnd());
				// System.out.println("reserva2 " + reserva2.getEnd());

				if (reserva2.ocupado(reserva)) {

					ocupadas++;

				}
			}
		}
		return ocupadas;
	}
}