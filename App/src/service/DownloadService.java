package service;

import download.HttpDownloader;
import model.FileInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//System.out.println("service---->");
		super.onCreate();
	}
	//每次用户点击listActivity当中的一个条目时，就调用改方法
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		FileInfo fi = (FileInfo)intent.getSerializableExtra("fileInfo");
		System.out.println("service---->"+fi);
		//生成一个下载线程，并将fileInfo对象作为参数传递到线程对象当中
		DownloadThread downloadThread = new DownloadThread(fi);
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	class DownloadThread implements Runnable{
		private FileInfo fileinfo=null;
		public DownloadThread(FileInfo fi){
			this.fileinfo = fi;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//下载地址为http://10.0.2.2/course/files/filename.txt
			//String fileUrl = "http://10.0.2.2/course/files/"+fileinfo.getFilename();
			String fileUrl = "http://172.18.159.201:8080/MCT/servlet/MCTServer/"+fileinfo.getFilename();
			//生成下载文件所用的对象
			HttpDownloader httpDownloader = new HttpDownloader();
			//将文件下载到sd卡当中，并储存到SDCard当中
			int result = httpDownloader.downFile(fileUrl, "file/", fileinfo.getFilename());
			System.out.println(fileUrl + result);
			//通知用户下载结果信息
			String resultMessage = null;
			if(result==-1){
				resultMessage = "下载失败";
			}
			else if(result == 0){
				resultMessage = "下载成功";
			}
			else if(result == 1){
				resultMessage = "文件已存在";
			}
			System.out.println(resultMessage);
			//Toast.makeText(getApplicationContext(), resultMessage,Toast.LENGTH_SHORT).show();	
		}
		
	}
}
