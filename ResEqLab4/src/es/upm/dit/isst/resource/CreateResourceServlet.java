package es.upm.dit.isst.resource;

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

import es.upm.dit.isst.resource.dao.ResourceDAO;
import es.upm.dit.isst.resource.dao.ResourceDAOImpl;
import es.upm.dit.isst.resource.model.Resource;

public class CreateResourceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String title = checkNull(req.getParameter("title"));
		String Description = checkNull(req.getParameter("description"));
		int sessionTime = Integer.parseInt(req.getParameter("sessionTime"));


		ResourceDAO dao = ResourceDAOImpl.getInstance();
		dao.add(title, Description,sessionTime);

		resp.sendRedirect("/main");
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO dao = ResourceDAOImpl.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		List<Resource> resources = new ArrayList<Resource>();
		boolean userAdmin= false;
		if (userService.isUserLoggedIn()){
			userAdmin = userService.isUserAdmin();
		}
	    req.getSession().setAttribute("userAdmin", userAdmin);     
		if (user != null){
		//if (true){
			System.out.println(user);
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		}
	    resources = dao.getResources();

		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("resources", new ArrayList<Resource>(resources));
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		
		RequestDispatcher view = req.getRequestDispatcher("CreateResourceApplication.jsp");
        view.forward(req, resp);
		
	}
} 