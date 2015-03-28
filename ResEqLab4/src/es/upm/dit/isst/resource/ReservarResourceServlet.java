package es.upm.dit.isst.resource;

import java.io.IOException;
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

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO resourcedao = ResourceDAOImpl.getInstance();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		boolean userAdmin = false;
		if (userService.isUserLoggedIn()) {
			userAdmin = userService.isUserAdmin();
		}
		req.getSession().setAttribute("userAdmin", userAdmin);

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		List<Resource> resources = new ArrayList<Resource>();

		if (user != null) {
			url = userService.createLogoutURL(req.getRequestURI());
			urlLinktext = "Logout";
		}
		resources = resourcedao.getResources();

		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("resources",
				new ArrayList<Resource>(resources));
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);

		RequestDispatcher view = req
				.getRequestDispatcher("ResourceResApplication.jsp");
		view.forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		ResourceDAO daoresource = ResourceDAOImpl.getInstance();
		ReserveDAO daoreserve = ReserveDAOImpl.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getUserId();

		String resourceId = req.getParameter("id");
		String startdate = req.getParameter("date");
		String starthour = req.getParameter("mishoras");
		String title = req.getParameter("title");

		Resource resource =daoresource.getResource(Long.parseLong(resourceId));
		int sessionTime = resource.getSessionTime();
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
		int Ehour = Integer.parseInt(endhour.split(":")[0] + sessionTime);
		int Emin = Integer.parseInt(endhour.split(":")[1]);

		Calendar start = new GregorianCalendar(Syear, Smonth, Sday, Shour, Smin);
		Calendar end = new GregorianCalendar(Eyear, Emonth, Eday, Ehour, Emin);

		// COMPROBAMOS QUE EL RECURSO NO ESTA OCUPADO EN ESE MOMENTO
		ReserveDAO reservedao = ReserveDAOImpl.getInstance();
		List<Reserve> reserves = new ArrayList<Reserve>();
		reserves = reservedao.getReserves();

		for (Reserve reserve : reserves) {
		}

		// falta definir tiempo de sesion
		daoreserve.add(start, end, user, Long.parseLong(resourceId));

		try {
			daoresource.addReserve(Long.parseLong(resourceId), user);// la id
																		// que
																		// metes
			// es la de la
			// reserva,
			// CUIDADO
			resp.sendRedirect("/main");

		} finally {
			resp.sendRedirect("/reserve");
		}

	}
}