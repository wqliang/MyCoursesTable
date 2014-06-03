package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {
private String SDCardRoot;
	
	//得到当前外部存储设备的目录
	public FileUtils() {
		SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	//在SD卡创建文件
	public File createFileInSDCard(String fileName,String dir)throws IOException{
		File file = new File(SDCardRoot+File.separator+dir + File.separator +fileName);
		System.out.println(file);
		file.createNewFile();
		return file;
	}
	//在SD卡上创建目录
	public File createSDDir(String dirName){
		File dirFile = new File(SDCardRoot+File.separator+dirName+File.separator);
		System.out.println(dirFile.mkdir());
		return dirFile;
	}
	//判断SD卡上的文件是否存在
	public boolean isFileExist(String fileName,String path){
		File file= new File(SDCardRoot+File.separator+path+File.separator+fileName);
		return file.exists();
	}
	//将一个InputStream里面的数据写入到SD卡中
	public File write2SDFromInput(String path,String fileName,InputStream input){
		File file = null;
		OutputStream output = null;
		try{
			createSDDir(path);
			file = createFileInSDCard(fileName,path);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4*1024];
			int tmp;
			while((tmp=input.read(buffer))!=-1){
				output.write(buffer,0,tmp);
			}
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				output.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;	
	}
}
