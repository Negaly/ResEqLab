package es.upm.dit.isst.resource;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.isst.resource.dao.ResourceDAO;
import es.upm.dit.isst.resource.dao.ResourceDAOImpl;

public class RemoveResourceServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {
    String id = req.getParameter("id");
    ResourceDAO dao = ResourceDAOImpl.getInstance();
    dao.remove(Long.parseLong(id));
    resp.sendRedirect("/");
  }
} 
