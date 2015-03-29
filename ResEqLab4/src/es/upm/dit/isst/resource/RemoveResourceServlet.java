package es.upm.dit.isst.resource;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.reserve.dao.ReserveDAO;
import es.upm.dit.isst.reserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.resource.dao.ResourceDAO;
import es.upm.dit.isst.resource.dao.ResourceDAOImpl;

public class RemoveResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void alertHTML(PrintWriter out, String message) throws IOException {
		out.println("<script type=\"text/javascript\">");
		out.println("alert('" + message + "');");
		out.println("</script>");

	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter out = resp.getWriter();
		// /////COMPROBACION ADMIN///////////
		if (UserServiceFactory.getUserService().isUserAdmin()) {// /LEVEL///
			String id = req.getParameter("id");
			ResourceDAO dao = ResourceDAOImpl.getInstance();
			dao.remove(Long.parseLong(id));
			alertHTML(out, "Reserva eliminada!!");

		}
		out.println("<script>location='/main';</script>");

		
	}
}
