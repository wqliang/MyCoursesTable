package model;

import java.io.Serializable;


public class TopicInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String publisher;
	private String update;
	private String detail;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public TopicInfo(String id, String publisher, String update, String detail) {
		super();
		this.id = id;
		this.publisher = publisher;
		this.update = update;
		this.detail = detail;
	}
	public TopicInfo() {
		super();
	}
	@Override
	public String toString() {
		return "TopicInfo [id=" + id + ", publisher=" + publisher + ", update="
				+ update + ", detail=" + detail + "]";
	}
	

}
