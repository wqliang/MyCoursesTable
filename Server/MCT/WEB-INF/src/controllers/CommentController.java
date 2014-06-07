package controllers;

import classes.Comment;

public class CommentController {
	public boolean addComment(Comment cmm){
		String sql = "INSERT INTO comment(tid,publisher,time,detail) VALUES(";
		sql = sql + cmm.getTid() +",'";
		sql = sql + cmm.getPublisher() + "','";
		sql = sql + cmm.getTime() +"','";
		sql = sql + cmm.getDetail() + "')";
		SqlPerformer sp = new SqlPerformer();
		if (sp.update(sql)){
			SqlPerformer spu = new SqlPerformer();
			sql = "UPDATE topic SET lastUpdate = '" + cmm.getTime() +"' WHERE tid = " + cmm.getCMid();
			if (spu.update(sql))
				return true;
			else
				return true;
		}
		else
			return false;
	}
}
