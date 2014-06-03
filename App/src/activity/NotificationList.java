package activity;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.NotificationInfo;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import xml.NotificationContentHandler;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.example.coursetable02.R;

import download.HttpDownloader;

public class NotificationList extends ListActivity{
	private List<NotificationInfo> NotificationInfos = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_list);
		updateListView();
	}
	private SimpleAdapter buildSimpleAdapter(List<NotificationInfo> NotificationInfos){
		//构建adapter对象
		//生成一个List对象，并按照simpleAdapter的标准，将NotificationInfos添加到List当中去
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for (Iterator<NotificationInfo> iterator = NotificationInfos.iterator(); iterator.hasNext();) {
			NotificationInfo fi = (NotificationInfo) iterator.next();
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("publisher",fi.getPublisher() );
			map.put("detail", fi.getDetail());
			map.put("date", fi.getDate());
			list.add(map);
		}
		System.out.println("???????????????????????????????????????");
		System.out.println(list);
		//创建一个simpleAdapter对象
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.item_notification,new String[]{"publisher","detail","date"},new int[]{R.id.notification_publisher,R.id.notification_name,R.id.notification_time});
		return simpleAdapter;
	}
	private void updateListView(){
		
		//下载包含文件信息的xml文件
		String xml = downloadXML("http://10.0.2.2/course/notification.xml");
		System.out.println("xml----------------" + xml);
		//对xml文件进行解析，并将解析结果放置到NotificationInfos对象当中，并将NotificationInfo对象放置到List对象当中
		NotificationInfos = parse(xml);
		SimpleAdapter simpleAdapter = buildSimpleAdapter(NotificationInfos);
		//将simpleAdapter对象设置到listActivity当中
		setListAdapter(simpleAdapter);
	}
	private String downloadXML(String urlStr){
		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}
	
	
	private List<NotificationInfo> parse(String xmlStr){
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		List<NotificationInfo> infos = new ArrayList<NotificationInfo>();
		try{
			XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
			
			NotificationContentHandler NotificationContentHandler = new NotificationContentHandler(infos);
			xmlReader.setContentHandler(NotificationContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for (Iterator<NotificationInfo> iterator = infos.iterator(); iterator.hasNext();) {
				NotificationInfo notificationInfo = (NotificationInfo) iterator.next();
				System.out.println(notificationInfo);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return infos;
	}
	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//根据用户点击得到对应文件对象
		//获取要下载的文件名称
		NotificationInfo fi = NotificationInfos.get(position);
		System.out.println("file-----"+fi);
		//访问网络，下载对应文件
			//1.需要创建一个service,等级比较高，生命周期长，在activity中会有可能随时被中断
			//2.每次每个文件的下载都需要在一个独立的线程中进行，保证用户的交互
		Intent intent = new Intent();
		//将文件对象存储到intent对象中
		intent.putExtra("NotificationInfo", fi);
		intent.setClass(FileList.this, DownloadService.class);
		startService(intent);
		//通知用户下载的结果
		super.onListItemClick(l, v, position, id);
	}*/
}
