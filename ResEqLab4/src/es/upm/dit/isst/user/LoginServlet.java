package es.upm.dit.isst.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	       
	            
	            String nickname = request.getParameter("nickname");
	            String pass = request.getParameter("pass");
	            
	            System.out.println("Usuario: "+nickname+" Pass: "+pass);
	            
	            //if(){
	                System.out.println("Let's go");
	                response.sendRedirect("/reservar");
	            //}else{
	               // System.out.println("Error en pass/user");
	                //response.sendRedirect("");
	            }
	    }
