package utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	// ����URL
	//public static final String BASE_URL="http://10.0.2.2/course/";
	public static final String BASE_URL="http://172.18.159.201:8080/MCT/servlet/MCTServer";
	// ���Get�������request
	public static HttpGet getHttpGet(String url){
		//�����������
		HttpGet request = new HttpGet(url);
		 return request;
	}
	// ���Post�������request
	public static HttpPost getHttpPost(String url){
		//�����������
		 HttpPost request = new HttpPost(url);
		 return request;
	}
	// ������������Ӧ����response����������
	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	// ������������Ӧ����response,��������
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	// ����Post���󣬻����Ӧ��ѯ���
	public static String queryStringForPost(String url){
		// ����url���HttpPost����
		HttpPost request = HttpUtils.getHttpPost(url);
		String result = null;
		try {
			// �����Ӧ����
			HttpResponse response = HttpUtils.getHttpResponse(request);
			// �ж��Ƿ�����ɹ�
			if(response.getStatusLine().getStatusCode()==200){
				// ʹ��getEntity()���������Ӧ������,ת����string?EntityUtils�Ǵ�����Inputstream�Ĺ���
				//String result= result = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
			else if(response.getStatusLine().getStatusCode()==404){
				return "404";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣��";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣��";
			return result;
		}
        return null;
    }
	// �����Ӧ��ѯ���
	public static String queryStringForPost(HttpPost request){
		String result = null;
		try {
			// �����Ӧ����
			HttpResponse response = HttpUtils.getHttpResponse(request);
			// �ж��Ƿ�����ɹ�
			if(response.getStatusLine().getStatusCode()==200){
				// �����Ӧ
				result = EntityUtils.toString(response.getEntity());
				System.out.println("result------>"+ result);
				return result;
			}
			else if(response.getStatusLine().getStatusCode()==404){
				System.out.println("yes-----404");
				return "404";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣��";
			System.out.println("result------>"+ result);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣��";
			System.out.println("result------>"+ result);
			return result;
		}
        return null;
    }
	// ����Get���󣬻����Ӧ��ѯ���
	public static  String queryStringForGet(String url){
		// ���HttpGet����
		HttpGet request = HttpUtils.getHttpGet(url);
		String result = null;
		try {
			// �����Ӧ����
			HttpResponse response = HttpUtils.getHttpResponse(request);
			// �ж��Ƿ�����ɹ�
			if(response.getStatusLine().getStatusCode()==200){
				// �����Ӧ
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣��";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣��";
			return result;
		}
        return null;
    }
}