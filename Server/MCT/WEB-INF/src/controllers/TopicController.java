package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import classes.Topic;

public class TopicController {
	public boolean addTopic(Topic topic){
		String sql = "INSERT INTO topic(cid,publisher,lastUpdate,detail,title) VALUES(";
		sql = sql + topic.getCid() +",'";
		sql = sql + topic.getPublisher() + "','";
		sql = sql + topic.getTime() +"','";
		sql = sql + topic.getDetail() +"','";
		sql = sql + topic.getTitle() + "')";
		SqlPerformer sp = new SqlPerformer();
		if (sp.update(sql))
			return true;
		else
			return false;
	}
	
	public ArrayList<Topic> getTopic(int cid){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		String sql = "SELECT * FROM topic WHERE cid =" + cid + " ORDER BY lastUpdate DESC";
		SqlPerformer sp = new SqlPerformer();
		ResultSet rs = null;
		rs = sp.query(sql);
		try {
			while (rs.next()){
				int tid = rs.getInt("tid");
				int ccid = rs.getInt("cid");
				String publisher = rs.getString("publisher");
				String time = rs.getString("lastUpdate");
				String detail = rs.getString("detail");
				String title = rs.getString("title");
				topics.add(new Topic(tid, ccid, publisher, time, title, detail));
			}
		} catch (SQLException e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in CourseController.getAllCourses"+e.getMessage());
		}
		return topics;
	}
}
