package controllers;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlConnector {
	public Connection getDBconnection(){
		Connection db = null;
	
		try{
			String url = "jdbc:mysql://localhost:3306/coursestable";
			Class.forName("com.mysql.jdbc.Driver");
			db = DriverManager.getConnection(url,"root","admin");
		} catch(Exception e){
			System.out.println("database connect faild!" + e.getMessage());
		} 
		return db;
	}
}
