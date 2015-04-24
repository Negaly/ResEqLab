package es.upm.dit.isst.resource;

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

import es.upm.dit.isst.lab.model.lab;
import es.upm.dit.isst.reserve.dao.ReserveDAO;
import es.upm.dit.isst.reserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.resource.dao.ResourceDAO;
import es.upm.dit.isst.resource.dao.ResourceDAOImpl;
import es.upm.dit.isst.resource.model.Resource;
import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.model.Resource;
import es.upm.dit.isst.user.dao.UserDAO;
import es.upm.dit.isst.user.dao.UserDAOImpl;

public class statsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
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
			UserDAO userdao = UserDAOImpl.getInstance();

			// Si no eres admin, solo ves las tuyas
			if (!userAdmin && (user != null)) {// LEVEL en vez de admin
				reserves = reservedao.getReserves(user.getUserId());
			}
			
			// ///////////////Gestion de req y resp////////////////////////////
			System.out.println(reserves);

			es.upm.dit.isst.lab.model.lab lab = new lab();
			
			//Calendar start = new GregorianCalendar(Calendar.getInstance(), 1, 1, 0, 0);
			//Calendar end = new GregorianCalendar(2015, 1, 1, 1, 1);	
			
					
			Reserve reserva = new Reserve(Calendar.getInstance(), Calendar.getInstance(), "user", 23);
			System.out.println(Calendar.getInstance());
			//int nVisitas = 2;
			int nUsuarios = userdao.getUsers().size();
			int nRecursos = resourcedao.getResources().size();
			int nReservas = reservedao.getReserves().size();
			
			int nOcupadas = 0;
			
			for (Resource a: resourcedao.getResources()){
				for(Long b: a.getReserves()){
					Reserve reserva2 = reservedao.getReserve(b);
					if (reserva.ocupado(reserva2))
						nOcupadas++;
						
				}
			}
			//hora.ocupado(reserva);
			////////////////////////////////////////////
		
			//req.getSession().setAttribute("nVisitas" , nVisitas);
			req.getSession().setAttribute("nUsuarios", nUsuarios);
			req.getSession().setAttribute("nRecursos", nRecursos);
			req.getSession().setAttribute("nReservas", nReservas);
			req.getSession().setAttribute("user", user);

			req.getSession().setAttribute("nOcupadas", nOcupadas);

			RequestDispatcher view = req
					.getRequestDispatcher("ShowStats.jsp");
			view.forward(req, resp);
		} else {
			resp.sendRedirect(url);
		}
	}

}