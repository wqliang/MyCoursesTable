package utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	// 基础URL
	//public static final String BASE_URL="http://10.0.2.2/course/";
	public static final String BASE_URL="http://172.18.159.201:8080/MCT/servlet/MCTServer";
	// 获得Get请求对象request
	public static HttpGet getHttpGet(String url){
		//创建请求对象
		HttpGet request = new HttpGet(url);
		 return request;
	}
	// 获得Post请求对象request
	public static HttpPost getHttpPost(String url){
		//创建请求对象
		 HttpPost request = new HttpPost(url);
		 return request;
	}
	// 根据请求获得响应对象response，发送请求
	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	// 根据请求获得响应对象response,发送请求
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	// 发送Post请求，获得响应查询结果
	public static String queryStringForPost(String url){
		// 根据url获得HttpPost对象
		HttpPost request = HttpUtils.getHttpPost(url);
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtils.getHttpResponse(request);
			// 判断是否请求成功
			if(response.getStatusLine().getStatusCode()==200){
				// 使用getEntity()方法获得响应的内容,转换成string?EntityUtils是代替了Inputstream的功能
				//String result= result = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
			else if(response.getStatusLine().getStatusCode()==404){
				return "404";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
        return null;
    }
	// 获得响应查询结果
	public static String queryStringForPost(HttpPost request){
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtils.getHttpResponse(request);
			// 判断是否请求成功
			if(response.getStatusLine().getStatusCode()==200){
				// 获得响应
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
			result = "网络异常！";
			System.out.println("result------>"+ result);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			System.out.println("result------>"+ result);
			return result;
		}
        return null;
    }
	// 发送Get请求，获得响应查询结果
	public static  String queryStringForGet(String url){
		// 获得HttpGet对象
		HttpGet request = HttpUtils.getHttpGet(url);
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtils.getHttpResponse(request);
			// 判断是否请求成功
			if(response.getStatusLine().getStatusCode()==200){
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
        return null;
    }
}