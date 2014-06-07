package controllers;

import java.sql.*;
import java.util.ArrayList;

import classes.Course;

public class CourseController {

	public ArrayList<Course> getAllCourse(int uid){
		ArrayList<Course> courses = new ArrayList<Course>();
		SqlPerformer sp = new SqlPerformer();
		String sql = "select * from course where uid = " + uid;
		ResultSet rs = null;
		rs = sp.query(sql);
		try {
			while (rs.next()){
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				String addr = rs.getString("addr");
				String teacher = rs.getString("teacher");
				int weekday = rs.getInt("weekday");
				int start = rs.getInt("start");
				int duration = rs.getInt("duration");
				courses.add(new Course(cid, name, addr, teacher, weekday, start, duration));
			}
		} catch (SQLException e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in CourseController.getAllCourses"+e.getMessage());
		}
		return courses;
	}
	
	public Course getone(){
		return new Course(1,"geton", "getone", "getone", 1, 2, 3);
	}
	
	public Course getOneCourse(/*Course cour*/){
		Course result = null;
		//try{
		//	db = getDBConnection();
		//	String sql = "select * from course where cid = 1";
		//	pst = (PreparedStatement) db.prepareStatement(sql);
		//	ResultSet rs = pst.executeQuery(sql);
			
		//	while (rs.next()){
		//		String name = rs.getString("name");
		//		String addr = rs.getString("addr");
		//		String teacher = rs.getString("teacher");
		//		int weekday = rs.getInt("weekday");
		//		int start = rs.getInt("start");
		//		int duration = rs.getInt("duration");
			
		//		result = new Course(1,name, addr, teacher, weekday, start, duration);
				//System.out.println(name + " " + addr + " " + time + " " + duration + " " + teacher);
		//	}
		//	db.close();
		//} catch(SQLException e){
		//	return new Course(1,"connection faild",e.getMessage(),"qqq",1,1,1);
			//System.out.println("query failed!" + e.getMessage());
		//} 
		return result;//new Course("name", "ada","dfaf",1,1,1);
	}
	
}
