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

public class ModifyResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		String resourceId = req.getParameter("resourceId");

		ResourceDAO dao = ResourceDAOImpl.getInstance();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		boolean userAdmin = false;
		if (userService.isUserLoggedIn()) {
			userAdmin = userService.isUserAdmin();
		}
		req.getSession().setAttribute("userAdmin", userAdmin);

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";

		if (user != null) {
			url = userService.createLogoutURL(req.getRequestURI());
			urlLinktext = "Logout";
		}

		Resource resource = dao.getResource(Long.parseLong(resourceId));
		String description = resource.getDescription();
		String title = resource.getTitle();

		req.getSession().setAttribute("title", title);
		req.getSession().setAttribute("description", description);
		req.getSession().setAttribute("resourceId", resourceId);
		req.getSession().setAttribute("user", user);

		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);

		RequestDispatcher view = req
				.getRequestDispatcher("ModifyResourceApplication.jsp");
		view.forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		ResourceDAO daoresource = ResourceDAOImpl.getInstance();
		ReserveDAO daoreserve = ReserveDAOImpl.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getUserId();
		boolean available = true;
		String resourceId = req.getParameter("resourceId");
		String title = checkNull(req.getParameter("title"));
		String description = checkNull(req.getParameter("description"));
		// String availableString = req.getParameter("available");
		String availableString = req.getParameter("available");
		int sessionTime = Integer.parseInt(req.getParameter("sessionTime"));
		System.out.println("availableStringeq: "+availableString);
		int a = Integer.parseInt(availableString);
		System.out.println("a: "+a);

		if (a== 0)
			available = false;

		System.out.println("Available: " + available);

		try {
			daoresource.modifyResource(Long.parseLong(resourceId), title,
					description, sessionTime, available);
			resp.sendRedirect("/main");

		} finally {
			resp.sendRedirect("/main");
		}

	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}