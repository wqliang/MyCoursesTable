package activity;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.CourseInfo;
import model.UserInfo;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import utils.HttpUtils;
import xml.CourseTableContentHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.coursetable02.R;
import com.example.coursetable02.R.layout;

import db.DatabaseHelper;
import download.HttpDownloader;

public class CourseTable extends Activity{
		
	private static final int pergrid = 28;
	private static final int perclass = 26;
	private static final int UPDATE =1;
	private static final int INSERT =2;
	private List<CourseInfo> courseInfos = null;
	private RelativeLayout monday = null;
	private RelativeLayout tuesday = null;
	private RelativeLayout wednesday = null;
	private RelativeLayout thursday = null;
	private RelativeLayout friday = null;
	private RelativeLayout saturday = null;
	private RelativeLayout sunday = null;
	private int courseId = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_table);
        monday = (RelativeLayout)findViewById(R.id.monday);
        tuesday = (RelativeLayout)findViewById(R.id.tuesday);
    	wednesday = (RelativeLayout)findViewById(R.id.wednesday);
    	thursday = (RelativeLayout)findViewById(R.id.thursday);
    	friday = (RelativeLayout)findViewById(R.id.friday);
    	saturday = (RelativeLayout)findViewById(R.id.saturday);
    	sunday = (RelativeLayout)findViewById(R.id.sunday);   	
    	updateFromDatabase();
	}
	private void updateFromDatabase() {
		// TODO Auto-generated method stub
		courseInfos = new ArrayList<CourseInfo>();
		//��ȡ���ݿ�����ݣ����courseInfos��Ȼ�����courseInfos����̬����btn
		DatabaseHelper dbHelper = new DatabaseHelper(CourseTable.this,"course_table_db");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		//Cursor cursor = db.query("course", new String[]{"name","duration","start",""}, null, null, null, null, null);
		Cursor cursor = db.rawQuery( 
			     "SELECT * FROM course", null);
		while(cursor.moveToNext()){
			//����һ�� courseInfo����
			CourseInfo c = new CourseInfo();
			c.setName(cursor.getString(cursor.getColumnIndex("name")));
			c.setAddr(cursor.getString(cursor.getColumnIndex("addr")));
			c.setDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex("date"))));
			c.setDuration(Integer.parseInt(cursor.getString(cursor.getColumnIndex("duration"))));
			c.setId(cursor.getString(cursor.getColumnIndex("id")));
			c.setStart(Integer.parseInt(cursor.getString(cursor.getColumnIndex("start"))));
			c.setTeacher(cursor.getString(cursor.getColumnIndex("teacher")));
			courseInfos.add(c);
			System.out.println(c);
			addToCourseTable(c);
		}
		cursor.close();
		db.close();
		if(courseInfos.size()==0){
			updateFromServer();
		}
	}
	private void updateFromServer() {
		// TODO Auto-generated method stub
		//ɾ���ɱ�����
		DatabaseHelper dbHelper = new DatabaseHelper(CourseTable.this,"course_table_db");
		//
		String sql = "DELETE FROM course;";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql);
		//�ӷ������㵽�γ����ݣ���һ���б����ߴӱ������ݿ⣿
        //String xml = downloadXML("http://10.0.2.2/course/course.xml");
        String xml = sendToServer();
        //System.out.println(xml);
        
        //��xml�ļ����н�������������������õ�CourseInfos�����У�����CourseInfo������õ�List������
        courseInfos = parse(xml);
        //addToCourseTable(courseInfos.get(1));
        //addToCourseTable(courseInfos.get(2));
        if(courseInfos!=null){
        	//�������б���̬���ɱ������
	        for (Iterator<CourseInfo> iterator = courseInfos.iterator(); iterator.hasNext();) {
				CourseInfo courseInfo = (CourseInfo) iterator.next();
				//System.out.println(courseInfo);
				addToCourseTable(courseInfo);
				updateLocalDatabase(courseInfo);
			}
        }
        db.close();
	}
	
	
	private String sendToServer() {
		// TODO Auto-generated method stub     
		//operation = login
		//		uid = �û�id
		//		ps = �����γ�ID
		UserInfo user = UserInfo.getInstance(); 
		String uid = user.getId();
		String ps = user.getPassword();
		NameValuePair nameValuePair1 = new BasicNameValuePair("operation","getAllC");
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
				showDialog("����ʧ�ܣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void showDialog(String str) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(str).setPositiveButton("ȷ��", null);
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
	
	
	private void updateLocalDatabase(CourseInfo courseInfo) {
		//����ContentValues����
		ContentValues values = new ContentValues();
		//����ֵ"id int," "name varchar(20)," "date int," "start int," 
		//"duration int," "addr varchar(40)," "teacher varchar(10))");
		values.put("id", courseInfo.getId());
		values.put("name", courseInfo.getName());
		values.put("date", courseInfo.getDate());
		values.put("start", courseInfo.getStart());
		values.put("duration", courseInfo.getDuration());
		values.put("addr", courseInfo.getAddr());
		values.put("teacher", courseInfo.getTeacher());
		DatabaseHelper dbHelper = new DatabaseHelper(CourseTable.this,"course_table_db");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		System.out.println(values);
				db.insert("course", null, values);
		System.out.println("insert----"+courseInfo.getId());
	}
	private void addToCourseTable(CourseInfo courseInfo) {
        Button btn = new Button(this);
        //������Ӧ������
    	btn.setText(courseInfo.getName());
    	//System.out.println("courseid---------" + courseId);
    	btn.setId(Integer.parseInt(courseInfo.getId()));
    	btn.setTextSize(9);
    	courseId++;
    	RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, perclass*courseInfo.getDuration());
    	lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    	int start = courseInfo.getStart()-1;
    	//System.out.println("start------" + start);
    	lp1.topMargin=pergrid*start;
    	//System.out.println("topMargin------" + lp1.topMargin);
    	btn.setOnClickListener(new CourseBtnListener(courseInfo));
    	int date = courseInfo.getDate();
    	//System.out.println(date);
    	switch(date){
	    	case(1):
	    		monday.addView(btn, lp1 );
	    		break;
	    	case(2):
	    		tuesday.addView(btn, lp1 );
	    		break;
	    	case(3):
	    		wednesday.addView(btn, lp1 );
	    		break;
	    	case(4):
	    		thursday.addView(btn, lp1 );
	    		break;
	    	case(5):
	    		friday.addView(btn, lp1 );
	    		break;
	    	case(6):
	    		saturday.addView(btn, lp1 );
	    		break;
	    	case(7):
	    		sunday.addView(btn, lp1 );
	    		break;
	    	default:
	    		break;
    	}
	    	
	}
	class CourseBtnListener implements OnClickListener{
		private CourseInfo courseInfo;
		public CourseBtnListener(CourseInfo courseInfo){
			super();
			this.courseInfo=courseInfo;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			System.out.println(courseInfo);
			Intent intent = new Intent();
            intent.setClass(CourseTable.this, CourseItem.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("course", courseInfo);
            intent.putExtras(bundle);
            startActivity(intent);
		}

	}
	private List<CourseInfo> parse(String xmlStr) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		List<CourseInfo> infos = new ArrayList<CourseInfo>();
		try{
			XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
			CourseTableContentHandler courseTableContentHandler = new CourseTableContentHandler(infos);
			xmlReader.setContentHandler(courseTableContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
		}catch(Exception e){
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return infos;
	}
	private String downloadXML(String urlStr) {
		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, UPDATE, 1, R.string.update);
		menu.add(0, INSERT, 2, R.string.insert_course);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==UPDATE){
			//�û���������б�
			updateFromServer();
		}
		else if(item.getItemId()==INSERT){
			insertCourse();
		}
		return super.onOptionsItemSelected(item);
	}
	private void insertCourse() {
		// TODO Auto-generated method stub
		//showDialog("����γ�");
		/*DatabaseHelper dbHelper = new DatabaseHelper(CourseTable.this,"course_table_db");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("date",3 );
		//�������޸ĵ�ֵ��ռλ��
		db.update("course", values, "id=?", new String[]{"0001"});*/
	}
	
	
	
	private void showDialog() {
    	
    	
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	LayoutInflater inflater = getLayoutInflater();
    	View layout = inflater.inflate(R.layout.insert_course,
    	     (ViewGroup) findViewById(R.id.insert_table));
    	builder.setView(layout);
    	builder.setMessage("����γ�").setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
            	final String timeString[] = {null};
            	final CourseInfo insertCourse = new CourseInfo();
            	EditText name = (EditText)findViewById(R.id.course_name);
            	EditText addr = (EditText)findViewById(R.id.course_addr);
            	EditText teacher = (EditText)findViewById(R.id.course_teacher);
            	
                String inputName = name.getText().toString();
                String inputAddr = addr.getText().toString();
                String inputTeacher = teacher.getText().toString();
                insertCourse.setAddr(inputAddr);
                insertCourse.setName(inputName);
                insertCourse.setTeacher(inputTeacher);
                Spinner spinnerDate = (Spinner) findViewById(R.id.course_date);
            	Spinner spinnerDate_from = (Spinner) findViewById(R.id.course_date_from);
            	Spinner spinnerDate_to = (Spinner) findViewById(R.id.course_date_to);
            	spinnerDate.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
                {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                            // TODO Auto-generated method stub
                    	//String InputDate = CourseTable.this.getResources().getStringArray(R.array.date)[arg2];
                            //������ʾ��ǰѡ�����
                        arg0.setVisibility(View.VISIBLE);
                        //insertCourse.setDate(Integer.parseInt(InputDate));
                        insertCourse.setDate(arg2);
                    }

        			@Override
        			public void onNothingSelected(AdapterView<?> arg0) {
        				// TODO Auto-generated method stub
        				
        			}
                        
                });
            	spinnerDate_from.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
                {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                            // TODO Auto-generated method stub
                    	timeString[0] = CourseTable.this.getResources().getStringArray(R.array.date)[arg2];
                            //������ʾ��ǰѡ�����
                        arg0.setVisibility(View.VISIBLE);
                        insertCourse.setDate(Integer.parseInt(timeString[0]));
                    }

        			@Override
        			public void onNothingSelected(AdapterView<?> arg0) {
        				// TODO Auto-generated method stub
        				
        			}
                        
                });
            	spinnerDate_to.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
                {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                            // TODO Auto-generated method stub
                    	timeString[1] = CourseTable.this.getResources().getStringArray(R.array.date)[arg2];
                            //������ʾ��ǰѡ�����
                        arg0.setVisibility(View.VISIBLE);
                    }

        			@Override
        			public void onNothingSelected(AdapterView<?> arg0) {
        				// TODO Auto-generated method stub
        				
        			}
                        
                });
                insertCourse.setDuration(Integer.parseInt(timeString[0])-Integer.parseInt(timeString[1]));
                insertCourse.setId(Math.random() * 10000+"");
                System.out.println(insertCourse);
                //addToCourseTable(insertCourse);
				//updateLocalDatabase(insertCourse);
            }
        });
    	
    	builder.setMessage("����γ�").setNeutralButton("ȡ��", null);
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }

}
