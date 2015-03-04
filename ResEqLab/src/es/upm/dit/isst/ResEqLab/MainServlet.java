package es.upm.dit.isst.ResEqLab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.ResEqLab.dao.RecursosDAO;
import es.upm.dit.isst.ResEqLab.dao.RecursosDAOImpl;

import es.upm.dit.isst.ResEqLab.model.Recurso;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		RecursosDAO dao = RecursosDAOImpl.getInstance();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		List<Recurso> recursos = new ArrayList<Recurso>();

		if (user != null) {
			url = userService.createLogoutURL(req.getRequestURI());
			urlLinktext = "Logout";
			recursos = dao.getRecursos(user.getNickname());
		}
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("recursos", new ArrayList<Recurso>(recursos));
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		RequestDispatcher view = req
				.getRequestDispatcher("TodoApplication.jsp");
		view.forward(req, resp);
	}
}