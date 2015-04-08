package es.upm.dit.isst.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class ReservarResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void alertHTML(PrintWriter out, String message) throws IOException {
		out.println("<script type=\"text/javascript\">");
		out.println("alert('" + message + "');");
		
		out.println("</script>");

	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		// /////////////USER////////////////////
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		boolean userAdmin = false;
		if (userService.isUserLoggedIn()) {
			userAdmin = userService.isUserAdmin();
		}
		req.getSession().setAttribute("userAdmin", userAdmin);

		String url = userService.createLoginURL("/createUser");
		String urlLinktext = "Login";
		List<Resource> resources = new ArrayList<Resource>();

		if (user != null) {
			url = userService.createLogoutURL(req.getRequestURI());
			urlLinktext = "Logout";

			// /////////////////Crear Reserva y añadirla al recurso/////

			ResourceDAO resourcedao = ResourceDAOImpl.getInstance();

			resources = resourcedao.getResources();

			req.getSession().setAttribute("user", user);
			req.getSession().setAttribute("resources",
					new ArrayList<Resource>(resources));
			req.getSession().setAttribute("url", url);
			req.getSession().setAttribute("urlLinktext", urlLinktext);

			RequestDispatcher view = req
					.getRequestDispatcher("ResourceResApplication.jsp");
			view.forward(req, resp);
		} else {
			resp.sendRedirect(url);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getUserId();

		if (user != null) {// ///LEVEL/////
			ResourceDAO daoresource = ResourceDAOImpl.getInstance();
			ReserveDAO daoreserve = ReserveDAOImpl.getInstance();

			String resourceId = req.getParameter("id");
			String startdate = req.getParameter("date");
			String starthour = req.getParameter("mishoras");
			String title = req.getParameter("title");

			Resource resource = daoresource.getResource(Long
					.parseLong(resourceId));
			int sessionTime = resource.getSessionTime();
			String endhour = starthour;

			// Cambiamos de string al formato de Calendar
			String enddate = startdate;
			System.out.println(enddate);
			int Syear = Integer.parseInt(startdate.split("-")[0]);
			System.out.println("Syear: "+Syear);
			int Smonth = Integer.parseInt(startdate.split("-")[1]);
			System.out.println("Smonth: "+Syear);
			int Sday = Integer.parseInt(startdate.split("-")[2]);
			System.out.println("Sday: "+Syear);
			int Shour = Integer.parseInt(starthour.split(":")[0]);
			System.out.println("Shour: "+Syear);
			int Smin = Integer.parseInt(starthour.split(":")[1]);
			System.out.println("Smin: "+Syear);
			
			int Eyear = Integer.parseInt(enddate.split("-")[0]);
			int Emonth = Integer.parseInt(enddate.split("-")[1]);
			int Eday = Integer.parseInt(enddate.split("-")[2]);
			int Ehour = Integer.parseInt(endhour.split(":")[0] + sessionTime);
			int Emin = Integer.parseInt(endhour.split(":")[1]);

			Calendar start = new GregorianCalendar(Syear, Smonth, Sday, Shour,
					Smin);
			Calendar end = new GregorianCalendar(Eyear, Emonth, Eday, Ehour,
					Emin);

			// COMPROBAMOS QUE EL RECURSO NO ESTA OCUPADO EN ESE MOMENTO
			ReserveDAO reservedao = ReserveDAOImpl.getInstance();
			List<Reserve> reserves = new ArrayList<Reserve>();
			reserves = reservedao.getReserves();
			for (Reserve reserve : reserves) {
			}
			// falta definir tiempo de sesion
			daoreserve.add(start, end, user, Long.parseLong(resourceId));
			PrintWriter out = resp.getWriter();
			try {
				daoresource.addReserve(Long.parseLong(resourceId), user);
				//alertHTML(out, "Reservado el recurso " + title + "!!");
				req.getSession().setAttribute("dialogo", "Reserva realizada!");
				//System.out.println("llego aqui");
				resp.sendRedirect("/mandamail?title="+title+"&date="+startdate+"&mishoras="+starthour);
				

			} finally {
				out.println("<script>location='/reserve';</script>");

			}

		} else {
			resp.sendRedirect(userService.createLoginURL("/createUser"));
		}

	}
}