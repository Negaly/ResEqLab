package es.upm.dit.isst.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.user.dao.UserDAO;
import es.upm.dit.isst.user.dao.UserDAOImpl;
import es.upm.dit.isst.user.model.AppUser;

public class CreateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// System.out.println("Adding atributes for GoogleUser ");
		//
		// String nickname = checkNull(req.getParameter("nickname"));
		// String name = checkNull(req.getParameter("name"));
		// int level = 0;
		// System.out.println("Creating new User " + nickname + name + level);
		//
		// UserDAO dao = UserDAOImpl.getInstance();
		// //dao.add(googleId, name, level);
		//
		// resp.sendRedirect("/createUser");

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

		List<AppUser> users = new ArrayList<AppUser>();

		UserService userService = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User user = userService.getCurrentUser();
		PrintWriter out = resp.getWriter();
		// System.out.println("Creo un usuario nuevo ? "+!dao.appUserExists(user.getUserId()));
		if (!dao.appUserExists(user.getUserId())) {

			String googleId = user.getUserId();
			String email = user.getEmail();
			int level = 0;
			System.out.println("Creating new AppUser " + googleId + email
					+ level);

			UserDAO userdao = UserDAOImpl.getInstance();
			userdao.add(googleId, email, level);

			req.getSession().setAttribute("users",
					new ArrayList<AppUser>(users));
			alertHTML(out, "Bienvenido " + user.getNickname() + "!!");

		}
		out.println("<script>location='/main';</script>");

		// resp.sendRedirect("/main");

	}

	private void alertHTML(PrintWriter out, String message) throws IOException {
		out.println("<script type=\"text/javascript\">");
		out.println("alert('" + message + "');");
		out.println("</script>");

	}
}
