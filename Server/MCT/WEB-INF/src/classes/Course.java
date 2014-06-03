package classes;

public class Course {
	private String name;
	private String addr;
	private int start;
	private int duration;
	private int weekday;
	private String teacher;
	
	public Course(String name, String addr, String teacher, int weekday, int start, int duration){
		this.name = name;
		this.addr = addr;
		this.teacher = teacher;
		this.weekday = weekday;
		this.start = start;
		this.duration = duration;
	}
	
	public void setName(String new_name){
		name = new_name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setAddr(String new_addr){
		addr = new_addr;
	}
	
	public String getAddr(){
		return addr;
	}
	
	public void setStart(int s){
		start = s;
	}
	
	public int getStart(){
		return start;
	}
	
	public void setDuration(int d){
		duration = d;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public void setWeekday(int w){
		weekday = w;
	}
	
	public int getWeekday(){
		return weekday;
	}
	
	public void setTeacher(String teac){
		teacher = teac;
	}
	
	public String getTeacher(){
		return teacher;
	}
}
