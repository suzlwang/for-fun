package coo.suzl.jiqiren.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import coo.suzl.jiqiren.bean.CMResult;
import coo.suzl.jiqiren.bean.ChatMessage;
import coo.suzl.jiqiren.bean.ChatMessage.Type;

public class HttpUtils {
 
	
	private static final String URL="http://www.tuling123.com/openapi/api";
	
	private static final String API_KEY="dcdf19be8305fb9b35ff49c83a73275c";
	

	public static ChatMessage sendMessage(String msg){
		
		ChatMessage chatMessage=new ChatMessage();
		
		String jsonRes=doGet(msg);
		Gson gson=new Gson();
		CMResult result=null;
		try {
			result=gson.fromJson(jsonRes, CMResult.class);
			chatMessage.setMsg(result.getText());
		} catch (JsonSyntaxException e) {
			chatMessage.setMsg("服务器忙或网络连接不可用");
			e.printStackTrace();
		}
		chatMessage.setDate(new Date());
		chatMessage.setType(Type.INCOMING);
		return chatMessage;
		
	}
	
	public static String doGet(String msg) {
	
		String result="";
		
		String url =setParams(msg);
		
		InputStream is=null;

		try {
			URL urlStr=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) urlStr.openConnection();
			conn.setReadTimeout(5*1000);
			conn.setConnectTimeout(5*1000);
			conn.setRequestMethod("GET");
			
			is=conn.getInputStream();
			
			
			BufferedReader bf=new BufferedReader(new InputStreamReader(is));
			String line  =null;
			StringBuffer sb=new StringBuffer();
			while ((line=bf.readLine())!=null){
				sb.append(line);
			}
			result=sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}

	private static String setParams(String msg) {
		String url="";
		try {
			url=URL+"?key="+API_KEY+"&info="+URLEncoder.encode(msg,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
		
	}
}
