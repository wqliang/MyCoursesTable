package activity;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.FileInfo;
import model.UserInfo;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import service.DownloadService;
import utils.HttpUtils;
import xml.FlieListContentHandler;

import com.example.coursetable02.R;


import download.HttpDownloader;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FileList extends ListActivity {
	
	private List<FileInfo> fileInfos = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_list);
		updateListView();
	}
	private SimpleAdapter buildSimpleAdapter(List<FileInfo> fileInfos){
		//����adapter����
		//����һ��List���󣬲�����simpleAdapter�ı�׼����fileInfos��ӵ�List����ȥ
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for (Iterator<FileInfo> iterator = fileInfos.iterator(); iterator.hasNext();) {
			FileInfo fi = (FileInfo) iterator.next();
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("file_name",fi.getFilename() );
			map.put("file_size", fi.getFilesize());
			list.add(map);
		}
		//����һ��simpleAdapter����
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.item_file,new String[]{"file_name","file_size"},new int[]{R.id.file_name,R.id.file_size});
		return simpleAdapter;
	}
	private void updateListView(){
		
		//���ذ����ļ���Ϣ��xml�ļ�
		String xml = downloadXML("http://10.0.2.2/course/file.xml");
		//String xml = getFileFromServer();
		//System.out.println("xml----------------" + xml);
		//��xml�ļ����н�������������������õ�fileInfos�����У�����fileInfo������õ�List������
		fileInfos = parse(xml);
		SimpleAdapter simpleAdapter = buildSimpleAdapter(fileInfos);
		//��simpleAdapter�������õ�listActivity����
		setListAdapter(simpleAdapter);
	}
	private String getFileFromServer() {
		// TODO Auto-generated method stub     
				//operation = getF
				//		uid = �û�id
				//		ps = �����γ�ID
		UserInfo user = UserInfo.getInstance(); 
		String uid = user.getId();
		String ps = user.getPassword();
		NameValuePair nameValuePair1 = new BasicNameValuePair("operation","getF");
		NameValuePair nameValuePair2 = new BasicNameValuePair("name",uid);
		NameValuePair nameValuePair3 = new BasicNameValuePair("password",ps);
		List<NameValuePair> content = new ArrayList<NameValuePair>();
		content.add(nameValuePair1);
		content.add(nameValuePair2);
		content.add(nameValuePair3);
		
		System.out.println(content);
		//����������
		//UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(content);  
		HttpEntity requestHttpEntity=null;
		try {
			requestHttpEntity = new UrlEncodedFormEntity(content);
			HttpPost  httpPost = new HttpPost(HttpUtils.BASE_URL);
			httpPost.setEntity(requestHttpEntity); 
			String result = HttpUtils.queryStringForPost(httpPost);
			System.out.println(result);
			if(result != "404") {
				return result;
			}else {
				showDialog("��ȡʧ�ܣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private String downloadXML(String urlStr){
		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}
	
	
	private List<FileInfo> parse(String xmlStr){
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		List<FileInfo> infos = new ArrayList<FileInfo>();
		try{
			XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
			
			FlieListContentHandler fileListContentHandler = new FlieListContentHandler(infos);
			xmlReader.setContentHandler(fileListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for (Iterator<FileInfo> iterator = infos.iterator(); iterator.hasNext();) {
				FileInfo fileInfo = (FileInfo) iterator.next();
				System.out.println(fileInfo);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return infos;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//�����û�����õ���Ӧ�ļ�����
		//��ȡҪ���ص��ļ�����
		FileInfo fi = fileInfos.get(position);
		System.out.println("file-----"+fi);
		//�������磬���ض�Ӧ�ļ�
			//1.��Ҫ����һ��service,�ȼ��Ƚϸߣ��������ڳ�����activity�л��п�����ʱ���ж�
			//2.ÿ��ÿ���ļ������ض���Ҫ��һ���������߳��н��У���֤�û��Ľ���
		Intent intent = new Intent();
		//���ļ�����洢��intent������
		intent.putExtra("fileInfo", fi);
		intent.setClass(FileList.this, DownloadService.class);
		startService(intent);
		//֪ͨ�û����صĽ��
		super.onListItemClick(l, v, position, id);
	}
	private void showDialog(String str) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(str).setPositiveButton("ȷ��", null);
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
}
