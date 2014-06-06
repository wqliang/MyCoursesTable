package classes;

//implemented by JiaLi

public class Comment {
	private int tid;
	private int cmid;
	private String publisher;
	private String time;
	private String detail;
	
	public Comment(int t, int cm, String pub, String tim, String det){
		this.tid = t;
		this.cmid = cm;
		this.publisher = pub;
		this.time = tim;
		this.detail = det;
	}
	
	public int getTid(){
		return tid;
	}
	
	public void setTid(int t){
		this.tid = t;
	}
	
	public int getCMid(){
		return cmid;
	}
	
	public void setCMid(int cm){
		this.cmid = cm;
	}
	
	public String getPublisher(){
		return publisher;
	}
	
	public void setPublisher(String pub){
		this.publisher = pub;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String nt){
		this.time = nt;
	}
	
	public String getDetail(){
		return detail;
	}
	
	public void setDetail(String de){
		this.detail = de;
	}
}
