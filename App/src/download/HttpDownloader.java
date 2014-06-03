package download;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import utils.FileUtils;


public class HttpDownloader {
	//only can down txt file
		public String download(String urlStr){
			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
			InputStream inputStream = null;
			try{
				inputStream = getInputSteamFromUrl(urlStr);
				//使用IO流读取数据
				buffer = new BufferedReader(new InputStreamReader(inputStream));
				while((line = buffer.readLine())!=null){
					sb.append(line);
				}
			}catch(Exception e){
					e.printStackTrace();
			}finally{
				try{
					buffer.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
		
		//下载任意文件
		//返回值整形 -1：下载文件出错；0：成功；1：已存在
		public int downFile(String urlStr,String path,String fileName){
			InputStream inputStream = null;
			try{
				FileUtils fileUtils = new FileUtils();
				if(fileUtils.isFileExist(fileName, path)){
					return 1;
				} else{
					inputStream = getInputSteamFromUrl(urlStr);
					File resultFile = fileUtils.write2SDFromInput(path, fileName, inputStream);
					if(resultFile==null)
						return -1;
				}
			}catch(Exception e){
				e.printStackTrace();
				return -1;
			}finally{
					try{
						inputStream.close();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			return 0;
		}

		private InputStream getInputSteamFromUrl(String urlStr) 
				throws MalformedURLException,IOException{
			// TODO Auto-generated method stub
			URL url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			InputStream inputStream = urlConn.getInputStream();
			return inputStream;
		}
		
		
		
}
