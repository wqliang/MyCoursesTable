import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import controllers.MCTLog;
import controllers.OperationHandler;
import classes.Comment;
import classes.Course;
import classes.Topic;
import classes.User;

public class MCTServer extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
		response.setContentType("text/xml");
		PrintWriter writer = response.getWriter();
		writer.println("hello testing! I'm Server!");
		//doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
		String resp = "";
		MCTLog log = new MCTLog();
        log.write("MCTServer get request");
        
        String operation = request.getParameter("operation");
        //operation = "addCM";
 
        if ("login".equals(operation) || "getAllC".equals(operation)){
        	String name = request.getParameter("name");
        	String pw = request.getParameter("password");
        	User user = new User(Integer.parseInt(name), pw);
        	//User user = new User(12345678, "12345678");
        	OperationHandler oh = new OperationHandler();
        	resp = oh.getResponse(user, operation);
        }
        else if ("addT".equals(operation)){
        	String uid = request.getParameter("uid");
        	String cid = request.getParameter("cid");
        	String title = request.getParameter("title");
        	String detail = request.getParameter("detail");
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(new Date());
        	Topic topic = new Topic(0,Integer.parseInt(cid),uid,time,title,detail);
        	//Topic topic = new Topic(0,1,"12345678",time,"hello testing±ÍÃ‚","hello detailœÍœ∏");
        	OperationHandler oh = new OperationHandler();
        	resp = oh.getResponse(topic);
        }
        else if ("getT".equals(operation)){
        	String cid = request.getParameter("cid");
        	int ccid = Integer.parseInt(cid);
        	//int ccid = 1;
        	OperationHandler oh = new OperationHandler();
        	resp = oh.getResponse(ccid);
        }
        else if ("addCM".equals(operation)){
        	String t = request.getParameter("tid");
        	int tid = Integer.parseInt(t);
        	String uid = request.getParameter("uid");
        	String detail = request.getParameter("detail");
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(new Date());
            Comment cmm = new Comment(tid,0,uid,time,detail);
            //Comment cmm = new Comment(1,2,"uid",time,"detailœÍ«È");
            OperationHandler oh = new OperationHandler();
        	resp = oh.getResponse(cmm);
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////
        log = new MCTLog();
        log.write("MCTServer response:\n"+ resp);
/////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
		response.setCharacterEncoding("gbk");
		response.setContentType("text/xml; charset=gbk");
		response.getOutputStream().write(resp.getBytes("gbk"));
	}
}
