package es.upm.dit.isst.resource;

import java.io.IOException;
import java.util.ArrayList;
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
		ResourceDAO dao = ResourceDAOImpl.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		List<Resource> resources = new ArrayList<Resource>();

		if (user != null) {
			// if (true){
			System.out.println(user);
			url = userService.createLogoutURL(req.getRequestURI());
			urlLinktext = "Logout";
		}
		resources = dao.getResources();

		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("resources",
				new ArrayList<Resource>(resources));
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);

		RequestDispatcher view = req
				.getRequestDispatcher("ResourceResApplication.jsp");
		view.forward(req, resp);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		ResourceDAO daoresource = ResourceDAOImpl.getInstance();
		ReserveDAO daoreserve = ReserveDAOImpl.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getUserId();


		//		if (user != null) {
//			// if (true){
//			System.out.println(user);
//			url = userService.createLogoutURL(req.getRequestURI());
//			urlLinktext = "Logout";
//		}
		String id = req.getParameter("id");
		String date = req.getParameter("date");
		String hour = req.getParameter("mishoras");
		String title = req.getParameter("title");
		EntityManager em = EMFService.get().createEntityManager();
		Resource resource = em.find(Resource.class, Long.parseLong(id));
		//falta definir tiempo de sesion
		daoreserve.add(hour,hour,date,date,user,Long.parseLong(id));
		
		try{
			daoresource.addReserve(Long.parseLong(id),user);
			resp.sendRedirect("/main");
			
		}
		finally{
			resp.sendRedirect("/reserve");
		}
		

	}
	
}