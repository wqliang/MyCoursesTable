package model;

import java.io.Serializable;


public class CourseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int date;
	private int start;
	private int duration;
	private String addr;
	private String teacher;
	

	
	@Override
	public String toString() {
		return "CourseInfo [id=" + id + ", name=" + name + ", date=" + date
				+ ", start=" + start + ", duration=" + duration + ", addr="
				+ addr + ", teacher=" + teacher + "]";
	}

	public CourseInfo() {
		super();
	}

	public CourseInfo(String id, String name, int date, int start,
			int duration, String addr, String teacher) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.start = start;
		this.duration = duration;
		this.addr = addr;
		this.teacher = teacher;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
}
