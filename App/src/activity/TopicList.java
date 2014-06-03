package activity;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.TopicInfo;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import xml.NotificationContentHandler;
import xml.TopicContentHandler;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.example.coursetable02.R;

import download.HttpDownloader;

public class TopicList extends ListActivity{
	private List<TopicInfo> TopicInfos = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_list);
		updateListView();
	}
	private SimpleAdapter buildSimpleAdapter(List<TopicInfo> TopicInfos){
		//构建adapter对象
		//生成一个List对象，并按照simpleAdapter的标准，将TopicInfos添加到List当中去
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for (Iterator<TopicInfo> iterator = TopicInfos.iterator(); iterator.hasNext();) {
			TopicInfo fi = (TopicInfo) iterator.next();
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("publisher",fi.getPublisher() );
			map.put("detail", fi.getDetail());
			map.put("update", fi.getUpdate());
			list.add(map);
		}
		System.out.println("???????????????????????????????????????");
		System.out.println(list);
		//创建一个simpleAdapter对象
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.item_topic,new String[]{"publisher","detail","update"},new int[]{R.id.topic_publisher,R.id.topic_name,R.id.topic_update});
		return simpleAdapter;
	}
	private void updateListView(){
		
		//下载包含文件信息的xml文件
		String xml = downloadXML("http://10.0.2.2/course/topic.xml");
		System.out.println("xml----------------" + xml);
		//对xml文件进行解析，并将解析结果放置到TopicInfos对象当中，并将TopicInfo对象放置到List对象当中
		TopicInfos = parse(xml);
		SimpleAdapter simpleAdapter = buildSimpleAdapter(TopicInfos);
		//将simpleAdapter对象设置到listActivity当中
		setListAdapter(simpleAdapter);
	}
	private String downloadXML(String urlStr){
		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}
	
	
	private List<TopicInfo> parse(String xmlStr){
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		List<TopicInfo> infos = new ArrayList<TopicInfo>();
		try{
			XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
			
			TopicContentHandler NotificationContentHandler = new TopicContentHandler(infos);
			xmlReader.setContentHandler(NotificationContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for (Iterator<TopicInfo> iterator = infos.iterator(); iterator.hasNext();) {
				TopicInfo TopicInfo = (TopicInfo) iterator.next();
				System.out.println(TopicInfo);
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
		TopicInfo fi = TopicInfos.get(position);
		System.out.println("file-----"+fi);
		//访问网络，下载对应文件
			//1.需要创建一个service,等级比较高，生命周期长，在activity中会有可能随时被中断
			//2.每次每个文件的下载都需要在一个独立的线程中进行，保证用户的交互
		Intent intent = new Intent();
		//将文件对象存储到intent对象中
		intent.putExtra("TopicInfo", fi);
		intent.setClass(FileList.this, DownloadService.class);
		startService(intent);
		//通知用户下载的结果
		super.onListItemClick(l, v, position, id);
	}*/
}
