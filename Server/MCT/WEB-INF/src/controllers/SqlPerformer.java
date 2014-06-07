package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SqlPerformer {
	private Connection getConn(){
		Connection db = null;
		
		try{
			String url = "jdbc:mysql://localhost:3306/coursestable";
			Class.forName("com.mysql.jdbc.Driver");
			db = DriverManager.getConnection(url,"root","admin");
		} catch(Exception e){
			MCTLog log = new MCTLog();
	        log.write("Exception in SqlPerformer.getConn: "+e.getMessage());
		} 
		return db;
	}
	
	public ResultSet query(String sql){
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		MCTLog log1 = new MCTLog();/////////////////////
        log1.write(sql);////////////////////////////////
		conn = getConn();
		try {
			pst = (PreparedStatement) conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
		} catch (SQLException e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in SqlPerformer.query:"+e.getMessage());
		}
		return rs;
	}
	
	public boolean update(String sql){
		Connection conn = null;
		Statement stmt = null;
		conn = getConn();
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch(SQLException e){
			MCTLog log = new MCTLog();
	        log.write("Exception in SqlPerformer.update:"+e.getMessage());
	        return false;
		}
	}
}
