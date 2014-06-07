package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import classes.User;

public class UserController {
	
	public boolean login(User user){
		SqlPerformer sp = new SqlPerformer();
		String pw = "";
		String sql = "select password from user where uid = " + Integer.toString(user.getUid());
		ResultSet rs = sp.query(sql);
		try {
			if (rs.next()){
				pw = rs.getString("password");
			}
		} catch (SQLException e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in login");
		}
		
		if (pw.equals(user.getPw()))
			return true;
		else
			return false;
	}
	
}
