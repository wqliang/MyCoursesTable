package activity;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.CourseInfo;
import model.TopicInfo;
import model.UserInfo;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import utils.HttpUtils;
import xml.TopicContentHandler;

import com.example.coursetable02.R;

import download.HttpDownloader;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CommentList extends ListActivity{
	private List<TopicInfo> TopicInfos = null;
	private TextView TopicContent;
	private TextView TopicPublisher;
	private TextView TopicUpdate;
	private Button NewComment;
	private TopicInfo topicInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_list);
		
		TopicContent = (TextView)findViewById(R.id.current_comment_name);
		TopicPublisher = (TextView)findViewById(R.id.current_comment_publisher);
		TopicUpdate = (TextView)findViewById(R.id.current_comment_update);
		NewComment = (Button)findViewById(R.id.newComment);
		
		
		NewComment.setOnClickListener(new NewCommentBtnListener());
		
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		topicInfo=(TopicInfo)bundle.getSerializable("TopicInfo");
		String content = topicInfo.getDetail();
		TopicContent.setText(content);
		String publisher = topicInfo.getPublisher();
		TopicPublisher.setText(publisher);
		String update = topicInfo.getUpdate();
		TopicUpdate.setText(update);
		updateListView();
	}
	class NewCommentBtnListener implements OnClickListener{
		
		@Override
		public void onClick(View arg0) {
			showDialog();
		}

	}
private void showDialog() {
    	
    	
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);
    	builder.setView(inputServer);
    	builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
            	String inputContent = inputServer.getText().toString();
                //�ύ��������
            	sendToServer(inputContent);
            }

			private void sendToServer(String inputContent) {
				// TODO Auto-generated method stub     
				//operation = addCM
				//		tid = topicID
				//		uid = �û�id
				//		detail = ��������
				UserInfo user = UserInfo.getInstance(); 
				String uid = user.getId();
				NameValuePair nameValuePair1 = new BasicNameValuePair("operation","addCM");
				NameValuePair nameValuePair2 = new BasicNameValuePair("tid",topicInfo.getId());
				NameValuePair nameValuePair3 = new BasicNameValuePair("uid",uid);
				NameValuePair nameValuePair4 = new BasicNameValuePair("detail",inputContent);
				List<NameValuePair> content = new ArrayList<NameValuePair>();
				content.add(nameValuePair1);
				content.add(nameValuePair2);
				content.add(nameValuePair3);
				content.add(nameValuePair4);
				
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
						showDialog("�����ɹ���");
						updateListView();
					}else {
						showDialog("����ʧ�ܣ�");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        });
    	
    	builder.setNeutralButton("ȡ��", null);
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
	
	private void showDialog(String str) {  
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);  
	    builder.setMessage(str).setPositiveButton("ȷ��", null);  
	    AlertDialog dialog = builder.create();  
	    dialog.show();  
	} 
	private SimpleAdapter buildSimpleAdapter(List<TopicInfo> TopicInfos){
		//����adapter����
		//����һ��List���󣬲�����simpleAdapter�ı�׼����TopicInfos��ӵ�List����ȥ
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for (Iterator<TopicInfo> iterator = TopicInfos.iterator(); iterator.hasNext();) {
			TopicInfo fi = (TopicInfo) iterator.next();
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("publisher",fi.getPublisher() );
			map.put("detail", fi.getDetail());
			map.put("update", fi.getUpdate());
			list.add(map);
		}
		System.out.println(list);
		//����һ��simpleAdapter����
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.item_comment,new String[]{"publisher","detail"},new int[]{R.id.comment_author,R.id.comment_content});
		System.out.println(simpleAdapter);
		return simpleAdapter;
	}
	private void updateListView(){
		
		//���ذ����ļ���Ϣ��xml�ļ�
		//String xml = downloadXML("http://10.0.2.2/course/comments.xml");
		String xml = getFromServer();
		System.out.println("xml----------------" + xml);
		//��xml�ļ����н�������������������õ�TopicInfos�����У�����TopicInfo������õ�List������
		TopicInfos = parse(xml);
		SimpleAdapter simpleAdapter = buildSimpleAdapter(TopicInfos);
		//��simpleAdapter�������õ�listActivity����
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
	
	private String getFromServer() {
		// TODO Auto-generated method stub     
		//operation = getT
		//cid = �γ�id
		UserInfo user = UserInfo.getInstance(); 
		String uid = user.getId();
		NameValuePair nameValuePair1 = new BasicNameValuePair("operation","getC");
		NameValuePair nameValuePair2 = new BasicNameValuePair("tid",topicInfo.getId());
		List<NameValuePair> content = new ArrayList<NameValuePair>();
		content.add(nameValuePair1);
		content.add(nameValuePair2);
		
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
