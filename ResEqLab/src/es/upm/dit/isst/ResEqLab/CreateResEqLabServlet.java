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

import es.upm.dit.isst.ResEqLab.dao.TodoDAO;
import es.upm.dit.isst.ResEqLab.dao.TodoDAOImpl;
import es.upm.dit.isst.ResEqLab.model.Recurso;

public class CreateResEqLabServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		TodoDAO dao = TodoDAOImpl.getInstance();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		List<Todo> todos = new ArrayList<Todo>();

		if (user != null) {
			url = userService.createLogoutURL(req.getRequestURI());
			urlLinktext = "Logout";
			todos = dao.getTodos(user.getNickname());
		}
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("todos", new ArrayList<Todo>(todos));
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		RequestDispatcher view = req
				.getRequestDispatcher("TodoApplication.jsp");
		view.forward(req, resp);
	}