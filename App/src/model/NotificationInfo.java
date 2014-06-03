package model;

import java.io.Serializable;


public class NotificationInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String publisher;
	private String date;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Override
	public String toString() {
		return "NotificationInfo [id=" + id + ", publisher=" + publisher + ", date="
				+ date + ", detail=" + detail + "]";
	}
	public NotificationInfo(String id, String publisher, String date, String detail) {
		super();
		this.id = id;
		this.publisher = publisher;
		this.date = date;
		this.detail = detail;
	}
	public NotificationInfo() {
		super();
	}
}
