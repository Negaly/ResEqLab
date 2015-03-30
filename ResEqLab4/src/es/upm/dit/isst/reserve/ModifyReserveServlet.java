package es.upm.dit.isst.reserve;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.reserve.dao.ReserveDAO;
import es.upm.dit.isst.reserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.dao.EMFService;
import es.upm.dit.isst.resource.dao.ResourceDAO;
import es.upm.dit.isst.resource.dao.ResourceDAOImpl;
import es.upm.dit.isst.resource.model.Resource;

public class ModifyReserveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getUserId();
		if (user != null) {
			PrintWriter out = resp.getWriter();

			String reserveId = req.getParameter("reserveId");
			String startdate = req.getParameter("date");
			String starthour = req.getParameter("mishoras");
			String title = req.getParameter("title");
			String resourceId = req.getParameter("resourceId");
			int sessionTime = Integer.parseInt(req.getParameter("sessionTime"));
			String endhour = starthour;

			// Cambiamos de string al formato de Calendar
			String enddate = startdate;
			int Syear = Integer.parseInt(startdate.split("-")[0]);
			int Smonth = Integer.parseInt(startdate.split("-")[1]);
			int Sday = Integer.parseInt(startdate.split("-")[2]);
			int Shour = Integer.parseInt(starthour.split(":")[0]);
			int Smin = Integer.parseInt(starthour.split(":")[1]);

			int Eyear = Integer.parseInt(enddate.split("-")[0]);
			int Emonth = Integer.parseInt(enddate.split("-")[1]);
			int Eday = Integer.parseInt(enddate.split("-")[2]);
			int Ehour = Integer.parseInt(endhour.split(":")[0]) + sessionTime;
			int Emin = Integer.parseInt(endhour.split(":")[1]);

			// System.out.println("Start hour: ");
			// System.out.println(Shour);
			// System.out.println("End hour: ");
			// System.out.println(Ehour);

			Calendar start = new GregorianCalendar(Syear, Smonth, Sday, Shour,
					Smin);
			Calendar end = new GregorianCalendar(Eyear, Emonth, Eday, Ehour,
					Emin);

			// COMPROBAMOS QUE EL RECURSO NO ESTA OCUPADO EN ESE MOMENTO
			ReserveDAO reservedao = ReserveDAOImpl.getInstance();
			List<Reserve> reserves = new ArrayList<Reserve>();
			reserves = reservedao.getReserves();
			// Comprobacion para que no coincida con otra reserva
			for (Reserve reserved : reserves) {
			}
			try {
				reservedao.update(Long.parseLong(reserveId), start, end);
				//alertHTML(out, "Modificada la reserva !!");
				req.getSession().setAttribute("dialogo", "Reserva modificada Correctamente!");

			} finally {
				//resp.sendRedirect("/listReserves");
				out.println("<script>location='/listReserves';</script>");

			}
		} else {
			resp.sendRedirect("/main");

		}
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		req.getSession().setAttribute("dialogo","");
		// //////////////////USER//////////////////////
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		boolean userAdmin = false;
		if (userService.isUserLoggedIn()) {
			userAdmin = userService.isUserAdmin();
		}
		req.getSession().setAttribute("userAdmin", userAdmin);
		String url = userService.createLoginURL("/createUser");
		String urlLinktext = "Login";
		if (user != null) {
			url = userService.createLogoutURL(req.getRequestURI());
			urlLinktext = "Logout";
		}
		// //////////////////RESERVE//////////////////////
		if (user != null) {// deberia de comprobarse q el user es el dueño
							// la reserva
			String reserveId = req.getParameter("reserveId");
			ReserveDAO dao = ReserveDAOImpl.getInstance();
			ResourceDAO resourcedao = ResourceDAOImpl.getInstance();

			// System.out.println(reserveId);
			Reserve reserve = dao.getReserve(Long.parseLong(reserveId));

			req.getSession().setAttribute("resourceId", reserve.getResource());
			req.getSession().setAttribute("reserveId", reserveId);
			req.getSession().setAttribute("resource",
					resourcedao.getResource(reserve.getResource()));

			req.getSession().setAttribute("user", user);
			req.getSession().setAttribute("reserve", reserve);
			req.getSession().setAttribute("url", url);
			req.getSession().setAttribute("urlLinktext", urlLinktext);

			RequestDispatcher view = req
					.getRequestDispatcher("ModifyReserveApplication.jsp");
			view.forward(req, resp);
		} else {
			resp.sendRedirect("/main");
		}
	}

	private void alertHTML(PrintWriter out, String message) throws IOException {
		out.println("<script type=\"text/javascript\">");
		out.println("alert('" + message + "');");
		out.println("</script>");

	}
}