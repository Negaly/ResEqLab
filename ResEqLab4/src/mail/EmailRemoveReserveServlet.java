package mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.reserve.dao.ReserveDAO;
import es.upm.dit.isst.reserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.reserve.model.Reserve;
import es.upm.dit.isst.resource.dao.ResourceDAO;
import es.upm.dit.isst.resource.dao.ResourceDAOImpl;
import es.upm.dit.isst.resource.model.Resource;

public class EmailRemoveReserveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//System.out.println("llego mail serv");
		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getEmail();

		String resourceId = req.getParameter("id");
		String startdate = req.getParameter("date");
		String starthour = req.getParameter("mishoras");
		String title = req.getParameter("title");

		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"noreply@testing-isst.appspotmail.com",
					"ResEqLab Team"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user, user));
			msg.setSubject("You have removed a reserve im ResEqLab");
			String msgBody = "Your reservation of "
					+ title + "\n - Hour : " + starthour
					+ "\n - Date: " + startdate+"\n Has been removed. If you didn't make this log in into "
							+ "http://reseqlab.appspot.com";

			msgBody += System.getProperty("line.separator") + "Kind Regards,"
					+ System.getProperty("line.separator") + "ResEqLab Team";
			msg.setText(msgBody);
			Transport.send(msg);
			//System.out.println(msgBody);
			resp.sendRedirect("/main");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
	}

}
