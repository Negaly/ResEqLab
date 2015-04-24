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

import es.upm.dit.isst.lab.dao.LabDAO;
import es.upm.dit.isst.lab.dao.LabDAOImpl;
import es.upm.dit.isst.lab.model.lab;
import es.upm.dit.isst.reserve.dao.ReserveDAO;
import es.upm.dit.isst.reserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.resource.dao.ResourceDAO;
import es.upm.dit.isst.resource.dao.ResourceDAOImpl;
import es.upm.dit.isst.resource.model.Resource;
import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;

public class labResourceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// modificariaa esto para que liste las reservas de cada recurso, asi
		// puedo mostar el nombre del recurso mas facilmente.
		// /////////////////GESTION USER////////////////////////////////////
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
			LabDAO labdao = LabDAOImpl.getInstance();

			// reserves = new ArrayList<Reserve>();
			// reserves = reservedao.getReserves();

			// Si no eres admin, solo ves las tuyas
			if (!userAdmin && (user != null)) {// LEVEL en vez de admin
				reserves = reservedao.getReserves(user.getUserId());
			}
			// ///
			Calendar start = new GregorianCalendar(Calendar.getInstance().get(
					Calendar.YEAR),
					Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar
							.getInstance().get(Calendar.DATE), Calendar
							.getInstance().get(Calendar.HOUR), Calendar
							.getInstance().get(Calendar.MINUTE));
			try {
				String startdate = req.getParameter("date");
				String starthour = req.getParameter("mishoras");

				// Cambiamos de string al formato de Calendar
				String endhour = starthour;
				String enddate = startdate;
				System.out.println(enddate);
				int Syear = Integer.parseInt(startdate.split("-")[0]);
				int Smonth = Integer.parseInt(startdate.split("-")[1]);
				int Sday = Integer.parseInt(startdate.split("-")[2]);
				int Shour = Integer.parseInt(starthour.split(":")[0]);
				int Smin = Integer.parseInt(starthour.split(":")[1]);
				start = new GregorianCalendar(Syear, Smonth, Sday, Shour, Smin);

			} catch (Exception E) {

			} finally {

			}
			Calendar end = start;

			// ///////////////Gestion de req y resp////////////////////////////
			System.out.println(reserves);

			Resource recurso = resourcedao.getResource(Long
					.parseLong("6614661952700416"));
			Resource ocupado = new Resource("ocupado", "Esto es un recurso", 3);

			System.out.println(start);
			System.out.println(end);
			System.out.println("user");
			System.out.println("");

			Reserve reserva = new Reserve(start, end, "user", 26);
			Resource[][] map = labdao.getLab().getMapa();
			map[0][0] = recurso;
			int[][] mapa = reservedao.mapCheck(map, reserva);

			System.out.print(mapa.length);
			req.getSession().setAttribute("user", user);
			req.getSession().setAttribute("url", url);
			req.getSession().setAttribute("urlLinktext", urlLinktext);
			req.getSession().setAttribute("mapa", mapa);

			RequestDispatcher view = req
					.getRequestDispatcher("mapResources.jsp");
			view.forward(req, resp);
		} else {
			resp.sendRedirect("/map");
		}
	}
}