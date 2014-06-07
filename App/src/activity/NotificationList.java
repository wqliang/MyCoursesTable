package activity;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.CourseInfo;
import model.NotificationInfo;
import model.UserInfo;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import utils.HttpUtils;
import xml.NotificationContentHandler;
import activity.TopicList.NewTopicBtnListener;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.example.coursetable02.R;

import download.HttpDownloader;

public class NotificationList extends ListActivity{
	private List<NotificationInfo> NotificationInfos = null;
	private CourseInfo courseInfo=null;
	private Button NewNotification;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_list);
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		courseInfo=(CourseInfo)bundle.getSerializable("course");
		NewNotification = (Button)findViewById(R.id.newNotification);
		NewNotification.setOnClickListener(new NewNotificationBtnListener());
		updateListView();
	}
	class NewNotificationBtnListener implements OnClickListener{
		
		@Override
		public void onClick(View arg0) {
			showDialog();
		}

	}
	
	
	public void showDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	final EditText inputServer = new EditText(this);
    	inputServer.setHint("输入通知内容");
        inputServer.setFocusable(true);
    	builder.setView(inputServer);
    	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
            	String inputContent = inputServer.getText().toString();
                //提交到服务器
            	sendToServer(inputContent);
            }

			private void sendToServer(String inputContent) {
				// TODO Auto-generated method stub     
				//operation = addT
				//		uid = 用户id
				//		cid = 所属课程ID
				//		title = ****
				//		detail = *****
				UserInfo user = UserInfo.getInstance(); 
				String uid = user.getId();
				NameValuePair nameValuePair1 = new BasicNameValuePair("operation","addN");
				NameValuePair nameValuePair2 = new BasicNameValuePair("uid",uid);
				NameValuePair nameValuePair3 = new BasicNameValuePair("cid",courseInfo.getId());
				NameValuePair nameValuePair4 = new BasicNameValuePair("detail",inputContent);
				List<NameValuePair> content = new ArrayList<NameValuePair>();
				content.add(nameValuePair1);
				content.add(nameValuePair2);
				content.add(nameValuePair3);
				content.add(nameValuePair4);
				
				System.out.println(content);
				//创建请求体
				//UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(content);  
				HttpEntity requestHttpEntity=null;
				try {
					requestHttpEntity = new UrlEncodedFormEntity(content);
					HttpPost  httpPost = new HttpPost(HttpUtils.BASE_URL);
					httpPost.setEntity(requestHttpEntity); 
					String result = HttpUtils.queryStringForPost(httpPost);
					System.out.println(result);
					if(result == "success") {
						showDialog("发布成功！");
						updateListView();
					}else {
						showDialog("发布失败！");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        });
    	
    	builder.setNeutralButton("取消", null);
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
	
	private void showDialog(String str) {  
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);  
	    builder.setMessage(str).setPositiveButton("确定", null);  
	    AlertDialog dialog = builder.create();  
	    dialog.show();  
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
		//System.out.println(list);
		//创建一个simpleAdapter对象
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.item_notification,new String[]{"publisher","detail","date"},new int[]{R.id.notification_publisher,R.id.notification_name,R.id.notification_time});
		return simpleAdapter;
	}
	private void updateListView(){
		
		//下载包含文件信息的xml文件
		//String xml = downloadXML("http://10.0.2.2/course/notification.xml");
		String xml = getFromServer();
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
	private String getFromServer() {
		// TODO Auto-generated method stub     
		//operation = getT
		//cid = 课程id
		UserInfo user = UserInfo.getInstance(); 
		String uid = user.getId();
		NameValuePair nameValuePair1 = new BasicNameValuePair("operation","getN");
		NameValuePair nameValuePair2 = new BasicNameValuePair("cid",courseInfo.getId());
		List<NameValuePair> content = new ArrayList<NameValuePair>();
		content.add(nameValuePair1);
		content.add(nameValuePair2);
		
		System.out.println(content);
		//创建请求体
		//UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(content);  
		HttpEntity requestHttpEntity=null;
		try {
			requestHttpEntity = new UrlEncodedFormEntity(content);
			HttpPost  httpPost = new HttpPost(HttpUtils.BASE_URL);
			httpPost.setEntity(requestHttpEntity); 
			String result = HttpUtils.queryStringForPost(httpPost);
			System.out.println(result);
			if(result != null) {
				updateListView();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
}
