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

public class ModifylabServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO resourcedao = ResourceDAOImpl.getInstance();

		String resourceId = req.getParameter("resourceId");
		LabDAO labdao = LabDAOImpl.getInstance();
		Resource[][] map = labdao.getLab().getMapa();

		try {

			String x = req.getParameter("x");
			String y = req.getParameter("y");
			Resource resource = resourcedao.getResource(Long
					.parseLong(resourceId));
			System.out.println("servlet" + resource);
			labdao.addResourcePos(Integer.parseInt(x), Integer.parseInt(y),
					resource);

			System.out.println("servlet"
					+ labdao.getLab().getMapa()[Integer.parseInt(x)][Integer
							.parseInt(y)]);
			// map[Integer.parseInt(x)][Integer.parseInt(y)] = resourcedao
			// .getResource(Long.parseLong(resourceId));

		} catch (Exception e) {
		} finally {
		}
		// modificariaa esto para que liste las reservas de cada recurso, asi
		// puedo mostar el nombre del recurso mas facilmente.
		// /////////////////GESTION USER////////////////////////////////////
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String url = userService.createLoginURL("/createUser");
		String urlLinktext = "Login";
		boolean userAdmin = false;
		if (user != null) {
			url = userService.createLogoutURL(req.getRequestURI());
			urlLinktext = "Logout";
			if (userService.isUserLoggedIn()) {
				userAdmin = userService.isUserAdmin();
			}
			req.getSession().setAttribute("userAdmin", userAdmin);
			// ////////////////Gestion Recursos y reservas//////////////////////

			ReserveDAO reservedao = ReserveDAOImpl.getInstance();

			Calendar start = new GregorianCalendar(Calendar.getInstance().get(
					Calendar.YEAR),
					Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar
							.getInstance().get(Calendar.DATE), Calendar
							.getInstance().get(Calendar.HOUR), Calendar
							.getInstance().get(Calendar.MINUTE));

			Calendar end = start;

			// ///////////////Gestion de req y resp////////////////////////////

			Reserve reserva = new Reserve(start, end, "user", 26);
			int[][] mapa = reservedao.mapCheck(map, reserva);

			req.getSession().setAttribute("user", user);
			req.getSession().setAttribute("url", url);
			req.getSession().setAttribute("urlLinktext", urlLinktext);

			req.getSession().setAttribute("resourceID", resourceId);

			req.getSession().setAttribute("mapa", mapa);

			RequestDispatcher view = req
					.getRequestDispatcher("mapModifier.jsp");
			view.forward(req, resp);
		} else {
			resp.sendRedirect("/main");
		}
	}
}