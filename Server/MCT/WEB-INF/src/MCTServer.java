import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;

public class MCTServer extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private XMLHandler xmlhandle = new XMLHandler();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
		response.setContentType("text/xml");
		PrintWriter writer = response.getWriter();
		writer.println("hello testing! I'm Server!");
		//doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
		
		
		
		String resp = "this is a response from server!";
		resp = xmlhandle.getResponse("dddd");
		
		response.setContentType("text/xml; charset=UTF-8");
		response.getOutputStream().write(resp.getBytes("UTF-8"));
	}
}
