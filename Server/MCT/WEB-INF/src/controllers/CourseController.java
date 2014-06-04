package controllers;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


//import com.mysql.jdbc.PreparedStatement;

import classes.Course;

public class CourseController {

	static Connection db = null;
	static PreparedStatement pst = null;
	ResultSet rs = null;
	
	public static Connection getDBConnection(){
		Connection dbconn = null;
		try{
			String url = "jdbc:mysql://localhost:3306/coursestable";
			Class.forName("com.mysql.jdbc.Driver");
			dbconn = DriverManager.getConnection(url,"root","admin");
		} catch(Exception e){
			System.out.println("database connect faild!" + e.getMessage());
		}
		return dbconn;
	}
	
	public Course getone(){
		return new Course("geton", "getone", "getone", 1, 2, 3);
	}
	
	public Course getOneCourse(/*Course cour*/){
		Course result = null;
		try{
			db = getDBConnection();
			String sql = "select * from course where cid = 1";
			pst = (PreparedStatement) db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery(sql);
			
			while (rs.next()){
				String name = rs.getString("name");
				String addr = rs.getString("addr");
				String teacher = rs.getString("teacher");
				int weekday = rs.getInt("weekday");
				int start = rs.getInt("start");
				int duration = rs.getInt("duration");
			
				result = new Course(name, addr, teacher, weekday, start, duration);
				//System.out.println(name + " " + addr + " " + time + " " + duration + " " + teacher);
			}
			db.close();
		} catch(SQLException e){
			return new Course("connection faild",e.getMessage(),"qqq",1,1,1);
			//System.out.println("query failed!" + e.getMessage());
		} 
		return result;//new Course("name", "ada","dfaf",1,1,1);
	}
	
}
