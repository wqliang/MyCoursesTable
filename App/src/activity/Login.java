package activity;
import java.util.ArrayList;
import java.util.List;

import model.UserInfo;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import utils.HttpUtils;

import com.example.coursetable02.R;

import db.DatabaseHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
    /** Called when the activity is first created. */
	private EditText et_name, et_passwd;
	private Button btn_confirm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.font_page);
        
        if(LoginFromDatabase()){
			Intent intent = new Intent();
            intent.setClass(Login.this, CourseTable.class);
            startActivity(intent);
		}
        else{
            setContentView(R.layout.activity_login);
            et_name = (EditText)findViewById(R.id.et_name);
            et_passwd = (EditText)findViewById(R.id.et_passwd);
            btn_confirm = (Button)findViewById(R.id.btn_confirm);
            btn_confirm.setOnClickListener(new LoginBtnListener());
        }
        
    }
        public class LoginBtnListener implements OnClickListener{
    		@Override
    		public void onClick(View arg0) {
    			if(LoginFromDatabase()){
    				Intent intent = new Intent();
                    intent.setClass(Login.this, CourseTable.class);
                    startActivity(intent);
    			}else{
    				FirstLogin();
    				LoginFromDatabase();
    			}
    		}

    	}
        private boolean LoginFromDatabase() {
			// TODO Auto-generated method stub
        	UserInfo user = UserInfo.getInstance();  
			DatabaseHelper dbHelper = new DatabaseHelper(Login.this,"course_table_db");
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			//Cursor cursor = db.query("course", new String[]{"name","duration","start",""}, null, null, null, null, null);
			Cursor cursor = db.rawQuery( 
				     "SELECT * FROM user", null);
			while(cursor.moveToNext()){
				//创建一个 userInfo对象
				user.setId(cursor.getString(cursor.getColumnIndex("id")));
				user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
				System.out.println(user);
			}
			cursor.close();
			db.close();
			if(user.getId()!=null){
				return true;
			}
			else
				return false;
		}
    
	private void writeIntoLocalDatabase(String username, String password) {
		ContentValues values = new ContentValues();
		values.put("id", username);
		values.put("password", password);
		DatabaseHelper dbHelper = new DatabaseHelper(Login.this,"course_table_db");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		System.out.println(values);
		db.insert("user", null, values);
		db.close();
	}
    private void showDialog(String str) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(str).setPositiveButton("确定", null);
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
    
    private void FirstLogin() {
		// TODO Auto-generated method stub
		String username= et_name.getText().toString();
		String password= et_passwd.getText().toString();
		NameValuePair nameValuePair0 = new BasicNameValuePair("operation","login");
		NameValuePair nameValuePair1 = new BasicNameValuePair("name",username);
		NameValuePair nameValuePair2 = new BasicNameValuePair("password",password);
		List<NameValuePair> content = new ArrayList<NameValuePair>();
		content.add(nameValuePair0);
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
			if(result == "success") {
				writeIntoLocalDatabase(username,password);
				LoginFromDatabase();
			}else {
				showDialog("登录失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}