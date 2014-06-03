package activity;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import model.CourseInfo;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import xml.CourseTableContentHandler;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.coursetable02.R;

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
		//获取数据库的数据，存进courseInfos，然后遍历courseInfos，动态加入btn
		DatabaseHelper dbHelper = new DatabaseHelper(CourseTable.this,"course_table_db");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		//Cursor cursor = db.query("course", new String[]{"name","duration","start",""}, null, null, null, null, null);
		Cursor cursor = db.rawQuery( 
			     "SELECT * FROM course", null);
		while(cursor.moveToNext()){
			//创建一个 courseInfo对象
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
		db.close();
	}
	//这个应该放在首页，登陆界面，创建我们的本地数据库
	private void CreateDatabase() {
		DatabaseHelper dbHelper = new DatabaseHelper(CourseTable.this,"course_table_db");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
	}
	private void updateFromServer() {
		// TODO Auto-generated method stub
		//删除旧表内容
		DatabaseHelper dbHelper = new DatabaseHelper(CourseTable.this,"course_table_db");
		//
		String sql = "DELETE FROM course;";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql);
		//从服务器搞到课程数据，是一个列表？或者从本地数据库？
        String xml = downloadXML("http://10.0.2.2/course/course.xml");
        //System.out.println(xml);
        
        //对xml文件进行解析，并将解析结果放置到CourseInfos对象当中，并将CourseInfo对象放置到List对象当中
        courseInfos = parse(xml);
        //addToCourseTable(courseInfos.get(1));
        //addToCourseTable(courseInfos.get(2));
        if(courseInfos!=null){
        	//遍历改列表，动态生成表格数据
	        for (Iterator<CourseInfo> iterator = courseInfos.iterator(); iterator.hasNext();) {
				CourseInfo courseInfo = (CourseInfo) iterator.next();
				//System.out.println(courseInfo);
				addToCourseTable(courseInfo);
				updateLocalDatabase(courseInfo);
			}
        }
        db.close();
	}
	private void updateLocalDatabase(CourseInfo courseInfo) {
		//生成ContentValues对象
		ContentValues values = new ContentValues();
		//放入值"id int," "name varchar(20)," "date int," "start int," 
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
		//db.insert("course", getNullColumnHack(), values);
		System.out.println("insert----"+courseInfo.getId());
	}
	private void addToCourseTable(CourseInfo courseInfo) {
        Button btn = new Button(this);
        //设置响应的属性
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
            //Work_01.this.finish(); 
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
			for (Iterator<CourseInfo> iterator = infos.iterator(); iterator.hasNext();) {
				CourseInfo courseInfo = (CourseInfo) iterator.next();
				//System.out.println(courseInfo);
			}
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
			//用户点击更新列表
			updateFromServer();
		}
		else if(item.getItemId()==INSERT){
			//updateCourse();
		}
		return super.onOptionsItemSelected(item);
	}
	private void updateCourse() {
		// TODO Auto-generated method stub
		DatabaseHelper dbHelper = new DatabaseHelper(CourseTable.this,"course_table_db");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("date",3 );
		//表名，修改的值，占位符
		db.update("course", values, "id=?", new String[]{"0001"});
	}
	
}
