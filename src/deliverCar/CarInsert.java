package deliverCar;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BL.ClientCar;

/**
 * Servlet implementation class CarInsert
 */
@WebServlet(name = "/CarInsert", urlPatterns = { "/register" })
public class CarInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SendCar client = new SendCar();
	private int pumps = client.getPumpsNumber();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarInsert() {
		super();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String encodedURL = response.encodeRedirectURL("registerCar.html");
         response.sendRedirect(encodedURL);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextSite = "carSent.html";
		int liters = 0;
		boolean wash = false;
		int pump = pumps+1;
		try {
			liters = Integer.parseInt(request.getParameter("fuel"));
			wash = Boolean.parseBoolean(request.getParameter("wash"));
			pump = Integer.parseInt(request.getParameter("pump"));
		}catch (IllegalStateException | NumberFormatException e) {
			nextSite = "webError.html";
		}
		if(pump <= pumps && pump >= 1) {
			ClientCar car = new ClientCar(liters, wash, pump);
			nextSite = (client.sendACar(car) ? nextSite : "webError.html"); //if the the car sent to the server.
			String encodedURL = response.encodeRedirectURL(nextSite);
	        response.sendRedirect(encodedURL);
		}
		else
			pumpOutOfBound(response);
	}
	
	private void pumpOutOfBound(HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			out.println("<html>");
			out.println("<head>");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			out.println("<title>Gas Station error!</title>");
			out.println("</head>");
			out.println("<body style=\"background-color:#e5eecc\">");
			out.println("<br>");
			out.println("<h1><i>Error: There is only " + pumps + " Pumps!</i></h1>");
			out.println("<blockquote>");
			out.println("<form method=\"get\" action=\"register\">");
			out.println("<input type=\"submit\" value=\"BACK TO REGISTERATION\" />");
			out.println("</form>");
			out.println("</blockquote>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
		}
	}

}
