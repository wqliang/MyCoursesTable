package controllers;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MCTLog {
	public void write(String msg){
		String fileName="D:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\MCT log.txt";  
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        RandomAccessFile randomFile = null;  
        try {        
        	randomFile = new RandomAccessFile(fileName, "rw");      
        	long fileLength = randomFile.length();       
        	randomFile.seek(fileLength);     
        	randomFile.writeBytes("\n"+date+"\t"+msg+"\n");
        	randomFile.close();
        } catch (IOException e) {     
        	e.printStackTrace();     
        }
	}
}
