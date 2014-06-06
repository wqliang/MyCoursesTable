package classes;

public class Topic {
	private int tid;
	private int cid;
	private String publisher;
	private String time;
	private String title;
	private String detail;
	
	public Topic(int t, int c, String p, String time, String titl, String de){
		this.tid = t;
		this.cid = c;
		this.publisher = p;
		this.time = time;
		this.title = titl;
		this.detail = de;
	}
	
	public int getTid(){
		return tid;
	}
	
	public void setTid(int tid){
		this.tid = tid;
	}
	
	public int getCid(){
		return cid;
	}
	
	public void setCid(int cid){
		this.cid = cid;
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
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String titl){
		this.title = titl;
	}
	
	public String getDetail(){
		return detail;
	}
	
	public void setDetail(String de){
		this.detail = de;
	}
}
