package model;

import java.io.Serializable;

public class FileInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String filename;
	private String filesize;
	public FileInfo(String id, String filename, String filesize) {
		super();
		this.id = id;
		this.filename = filename;
		this.filesize = filesize;
	}
	
	public FileInfo() {
		super();
	}
	
	@Override
	public String toString() {
		return "fileInfo [id=" + id + ", filename=" + filename + ", filesize="
				+ filesize + "]";
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	
}
