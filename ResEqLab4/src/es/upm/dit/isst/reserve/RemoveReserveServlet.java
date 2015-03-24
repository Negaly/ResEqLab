package es.upm.dit.isst.reserve;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.isst.reserve.dao.ReserveDAO;
import es.upm.dit.isst.reserve.dao.ReserveDAOImpl;

public class RemoveReserveServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {
    String id = req.getParameter("id");
    ReserveDAO dao = ReserveDAOImpl.getInstance();
    dao.remove(Long.parseLong(id));
    resp.sendRedirect("/");
  }
} 
