package activity;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.FileInfo;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import service.DownloadService;
import xml.FlieListContentHandler;

import com.example.coursetable02.R;


import download.HttpDownloader;

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
		//构建adapter对象
		//生成一个List对象，并按照simpleAdapter的标准，将fileInfos添加到List当中去
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for (Iterator<FileInfo> iterator = fileInfos.iterator(); iterator.hasNext();) {
			FileInfo fi = (FileInfo) iterator.next();
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("file_name",fi.getFilename() );
			map.put("file_size", fi.getFilesize());
			list.add(map);
		}
		//创建一个simpleAdapter对象
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.item_file,new String[]{"file_name","file_size"},new int[]{R.id.file_name,R.id.file_size});
		return simpleAdapter;
	}
	private void updateListView(){
		
		//下载包含文件信息的xml文件
		String xml = downloadXML("http://10.0.2.2/course/file.xml");
		//System.out.println("xml----------------" + xml);
		//对xml文件进行解析，并将解析结果放置到fileInfos对象当中，并将fileInfo对象放置到List对象当中
		fileInfos = parse(xml);
		SimpleAdapter simpleAdapter = buildSimpleAdapter(fileInfos);
		//将simpleAdapter对象设置到listActivity当中
		setListAdapter(simpleAdapter);
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
		//根据用户点击得到对应文件对象
		//获取要下载的文件名称
		FileInfo fi = fileInfos.get(position);
		System.out.println("file-----"+fi);
		//访问网络，下载对应文件
			//1.需要创建一个service,等级比较高，生命周期长，在activity中会有可能随时被中断
			//2.每次每个文件的下载都需要在一个独立的线程中进行，保证用户的交互
		Intent intent = new Intent();
		//将文件对象存储到intent对象中
		intent.putExtra("fileInfo", fi);
		intent.setClass(FileList.this, DownloadService.class);
		startService(intent);
		//通知用户下载的结果
		super.onListItemClick(l, v, position, id);
	}
}
