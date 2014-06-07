package activity;

import model.CourseInfo;

import com.example.coursetable02.R;
import com.example.coursetable02.R.layout;

import activity.CourseTable.CourseBtnListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CourseItem extends Activity{

	
	private TextView courseName;
	private TextView courseAddr;
	private TextView courseTime;
	private TextView courseTeacher;
	private Button notification;
	private Button topic;
	private Button file;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_item);
		courseName = (TextView)findViewById(R.id.course_name);
		courseAddr = (TextView)findViewById(R.id.course_addr);
		courseTime = (TextView)findViewById(R.id.course_time);
		courseTeacher = (TextView)findViewById(R.id.course_teacher);
		notification = (Button)findViewById(R.id.getNotification);
		topic = (Button)findViewById(R.id.getTopic);
		file = (Button)findViewById(R.id.getFile);
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		CourseInfo courseInfo=(CourseInfo)bundle.getSerializable("course");
		courseName.setText("课程："+courseInfo.getName());
		courseAddr.setText("地址："+courseInfo.getAddr());
		String time = "星期" + courseInfo.getDate()+"   第"+courseInfo.getStart()+ "~" + (courseInfo.getDuration()+courseInfo.getStart());
		courseTime.setText("时间："+ time + "节");
		courseTeacher.setText("教师："+courseInfo.getTeacher());
		
		notification.setOnClickListener(new NotificationBtnListener(courseInfo));
		topic.setOnClickListener(new TopicBtnListener(courseInfo));
		file.setOnClickListener(new FileBtnListener(courseInfo));
	}
	
	class NotificationBtnListener implements OnClickListener{
		private CourseInfo courseInfo;
		public NotificationBtnListener(CourseInfo courseInfo){
			super();
			this.courseInfo=courseInfo;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			System.out.println(courseInfo);
			Intent intent = new Intent();
            intent.setClass(CourseItem.this, NotificationList.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("course", courseInfo);
            intent.putExtras(bundle);
            startActivity(intent);
            //Work_01.this.finish(); 
		}

	}
	class TopicBtnListener implements OnClickListener{
		private CourseInfo courseInfo;
		public TopicBtnListener(CourseInfo courseInfo){
			super();
			this.courseInfo=courseInfo;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			System.out.println(courseInfo);
			Intent intent = new Intent();
			intent.setClass(CourseItem.this, TopicList.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("course", courseInfo);
            intent.putExtras(bundle);
            startActivity(intent);
            //Work_01.this.finish(); 
		}

	}
	
	class FileBtnListener implements OnClickListener{
		private CourseInfo courseInfo;
		public FileBtnListener(CourseInfo courseInfo){
			super();
			this.courseInfo=courseInfo;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			System.out.println(courseInfo);
			Intent intent = new Intent();
			intent.setClass(CourseItem.this, FileList.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("course", courseInfo);
            intent.putExtras(bundle);
            startActivity(intent);
            //Work_01.this.finish(); 
		}

	}
	
	
}
