package es.upm.dit.isst.reserve;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.reserve.dao.ReserveDAO;
import es.upm.dit.isst.reserve.dao.ReserveDAOImpl;

public class RemoveReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter out = resp.getWriter();
		// /////COMPROBACION ADMIN///////////
		if (UserServiceFactory.getUserService().isUserLoggedIn()) {// /LEVEL///
			String id = req.getParameter("reserveId");
			ReserveDAO dao = ReserveDAOImpl.getInstance();
			dao.remove(Long.parseLong(id));
			alertHTML(out, "Reserva eliminada!!");
			out.println("<script>location='/listReserves';</script>");

		} else {
			out.println("<script>location='/main';</script>");
		}
	}

	private void alertHTML(PrintWriter out, String message) throws IOException {
		out.println("<script type=\"text/javascript\">");
		out.println("alert('" + message + "');");
		out.println("</script>");

	}
}
