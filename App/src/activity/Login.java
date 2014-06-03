package activity;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import utils.HttpUtils;

import com.example.coursetable02.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
        setContentView(R.layout.activity_login);
        
        et_name = (EditText)findViewById(R.id.et_name);
        et_passwd = (EditText)findViewById(R.id.et_passwd);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);
        
        
        btn_confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String queryStr = "username=" + et_name.getText().toString()
						+"&password=" + et_passwd.getText().toString();
				String urlStr = HttpUtils.BASE_URL+"servlet/tomcat?"+queryStr;
				String result = HttpUtils.queryStringForPost(urlStr);
				if(result != null) {
					showDialog("登录成功！");
				}else {
					showDialog("登录失败！");
					Intent intent = new Intent();
	                intent.setClass(Login.this, CourseTable.class);
	                startActivity(intent);
				}
			}
		});
        
        class LoginBtnListener implements OnClickListener{
    		public LoginBtnListener(){
    			super();
    		}
    		@Override
    		public void onClick(View arg0) {
    			
    			String username= et_name.getText().toString();
				String password= et_passwd.getText().toString();
				NameValuePair nameValuePair1 = new BasicNameValuePair("name",username);
				NameValuePair nameValuePair2 = new BasicNameValuePair("password",password);
				List<NameValuePair> content = new ArrayList<NameValuePair>();
				content.add(nameValuePair1);
				content.add(nameValuePair2);
				//创建请求体
				//UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(content);  
				HttpEntity requestHttpEntity;
				try {
					requestHttpEntity = new UrlEncodedFormEntity(content);
					HttpPost  httpPost = new HttpPost(HttpUtils.BASE_URL);
					httpPost.setEntity(requestHttpEntity); 
					String result = HttpUtils.queryStringForPost(httpPost);
					if(result != null) {
						Intent intent = new Intent();
		                intent.setClass(Login.this, CourseTable.class);
		                /*Bundle bundle = new Bundle();
		                bundle.putSerializable("course", courseInfo);
		                intent.putExtras(bundle);*/
		                startActivity(intent);
					}else {
						showDialog("登录失败！");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				
				
    			
    			
    			
    		}

    	}
        
        
        
    }
    
    private void showDialog(String str) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(str).setPositiveButton("确定", null);
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
}