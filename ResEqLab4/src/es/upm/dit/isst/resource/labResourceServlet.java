package es.upm.dit.isst.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
			// ResourceDAO resourcedao = ResourceDAOImpl.getInstance();

			ReserveDAO reservedao = ReserveDAOImpl.getInstance();

//			reserves = new ArrayList<Reserve>();
//			reserves = reservedao.getReserves();

			// Si no eres admin, solo ves las tuyas
			if (!userAdmin && (user != null)) {// LEVEL en vez de admin
				reserves = reservedao.getReserves(user.getUserId());
			}
			// ///////////////Gestion de req y resp////////////////////////////
			System.out.println(reserves);
			
			es.upm.dit.isst.lab.model.lab lab = new lab();
			
			Resource recurso = new Resource("recurso", "Esto es un recurso",3);
			Resource ocupado = new Resource("ocupado", "Esto es un recurso",3);
			
			String[][] mapa = {{"recurso","ocupado",null,null,null},
					 {null,null,null,null,null},
					 {null,"ocupado",null,"recurso",null},
					 {"ocupado",null,null,null,null},
					 {null,null,"ocupado",null,"recurso"}};

	//		Resource[][] mapa = {{recurso,null,null,null,null},
		//			 {null,null,null,null,null},
			//		 {null,null,null,null,null},
				//	 {null,null,null,null,null},
					// {null,null,null,null,null}};

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