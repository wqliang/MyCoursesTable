package classes;

public class User {
	private int uid;
	private String pw;
	
	public User(int uid, String pw){
		this.uid = uid;
		this.pw = pw;
	}
	
	public int getUid(){
		return uid;
	}
	
	public String getPw(){
		return pw;
	}
}
