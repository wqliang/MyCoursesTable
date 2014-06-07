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
	//ÿ���û����listActivity���е�һ����Ŀʱ���͵��øķ���
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		FileInfo fi = (FileInfo)intent.getSerializableExtra("fileInfo");
		System.out.println("service---->"+fi);
		//����һ�������̣߳�����fileInfo������Ϊ�������ݵ��̶߳�����
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
			//���ص�ַΪhttp://10.0.2.2/course/files/filename.txt
			//String fileUrl = "http://10.0.2.2/course/files/"+fileinfo.getFilename();
			String fileUrl = "http://172.18.159.201:8080/MCT/servlet/MCTServer/"+fileinfo.getFilename();
			//���������ļ����õĶ���
			HttpDownloader httpDownloader = new HttpDownloader();
			//���ļ����ص�sd�����У������浽SDCard����
			int result = httpDownloader.downFile(fileUrl, "file/", fileinfo.getFilename());
			System.out.println(fileUrl + result);
			//֪ͨ�û����ؽ����Ϣ
			String resultMessage = null;
			if(result==-1){
				resultMessage = "����ʧ��";
			}
			else if(result == 0){
				resultMessage = "���سɹ�";
			}
			else if(result == 1){
				resultMessage = "�ļ��Ѵ���";
			}
			System.out.println(resultMessage);
			//Toast.makeText(getApplicationContext(), resultMessage,Toast.LENGTH_SHORT).show();	
		}
		
	}
}
