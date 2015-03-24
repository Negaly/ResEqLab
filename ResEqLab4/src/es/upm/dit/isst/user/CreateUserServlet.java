package es.upm.dit.isst.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.isst.user.dao.UserDAO;
import es.upm.dit.isst.user.dao.UserDAOImpl;
import es.upm.dit.isst.user.model.User;

public class CreateUserServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("Adding atributes for GoogleUser ");
		/*User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
		*/
		String nickname = checkNull(req.getParameter("nickname"));
		String name = checkNull(req.getParameter("name"));
		String pass = checkNull(req.getParameter("pass"));
		System.out.println("Creating new User " +nickname+name+pass);

		UserDAO dao = UserDAOImpl.getInstance();
		dao.add(nickname, name, pass);

		resp.sendRedirect("/createUser");
		
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		UserDAO dao = UserDAOImpl.getInstance();

		
		List<User> users = new ArrayList<User>();
		users = dao.getUsers();
		System.out.println(users);
		
		req.getSession().setAttribute("users", new ArrayList<User>(users));
		
		
		RequestDispatcher view = req.getRequestDispatcher("CreateUserApplication.jsp");
        view.forward(req, resp);
		
	}
		
}
