package com.imooc.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class Test {
	
	
	String accessUrl = "http://99bill.nat123.net/Weixin/wx.do?sinature=1&timestamp=2&nonce=3&echostr=4";
	String testUrl = "http://99bill.nat123.net/Weixin/Servlet02";
	String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	String appId="wx5d271f57da3824f9";
	String appSecret = "76a81949cdbb88f62372d1a45f1b5578"; 
	
	private static String accessToken = "_mKjJdjVJiCfYfYlq-XejGTRy-Z-kbxTsh7eU39YJdbp3eCn4WzfjRUn7aWpQKO58syjrp7FM8qOM0tacdmmBMo2eySO5W7IZElk_8-kAKG24RG_qwxGRNw8Hd-qx0BvMSMcAHASYS";
	@org.junit.Test
	public void test001() throws ClientProtocolException, IOException{
		
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(accessUrl);
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		System.out.println(entity);
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
		String s = reader.readLine(); 
		StringBuffer sb = new StringBuffer();
		while(s != null){
			sb.append(s);
			s = reader.readLine();
		}
		System.out.println("xxx"+sb+"xxx");
		/*EntityUtils.toString(entity);
		EntityUtils.consume(entity);*/
	}
	
	@org.junit.Test
	public void test002() throws ClientProtocolException, IOException{
		
		
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(testUrl);
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
//		EntityUtils.consume(entity);
//		EntityUtils.toString(entity);
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
		String s = reader.readLine(); 
		StringBuffer sb = new StringBuffer();
		while(s != null){
			sb.append(s);
			s = reader.readLine();
		}
		System.out.println("xxx"+sb+"xxx");
	}
	
	@org.junit.Test
	public void test003(){
		
//		{"access_token":"lvozM7mbKLOZgktzwuNL_kHWpGMrjOG2BJ42AaXZ4aV-bMGVxEB1TA6zTDj-nKrXQeGIJCKULklQIQFa8mHacK_2PQ_4HwgUcAYuhRQvOMwRQScABAFEG","expires_in":7200}
//		lvozM7mbKLOZgktzwuNL_kHWpGMrjOG2BJ42AaXZ4aV-bMGVxEB1TA6zTDj-nKrXQeGIJCKULklQIQFa8mHacK_2PQ_4HwgUcAYuhRQvOMwRQScABAFEG
		
		if(accessToken.equals("")){
			System.out.println("缓存中的access_token值："+accessToken);
		}
		else{
			
			HttpClient client = new DefaultHttpClient();
			client = ClientWrapper.wrapClient(client);
			try {
				accessTokenUrl = accessTokenUrl.replace("APPID", appId).replace(
						"APPSECRET", appSecret);
				HttpGet get = new HttpGet(accessTokenUrl);
				System.out.println("accessToken::"+Test.accessToken);
				HttpResponse response = client.execute(get);
				HttpEntity entity = response.getEntity();
				StringBuffer sb = new StringBuffer();
				BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
				String s = br.readLine();
				while(s != null){
					sb.append(s);
					s = br.readLine();
				}
				System.out.println(sb);
				JSONObject json = JSONObject.fromObject(sb.toString());
				accessToken = json.getString("access_token");
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	@org.junit.Test
	public void test004() throws Exception{
		
		HttpClient client = new DefaultHttpClient();
		client = ClientWrapper.wrapClient(client);
		String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
		HttpGet get = new HttpGet(url.replace("ACCESS_TOKEN", accessToken));
		HttpResponse response = client.execute(get);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String s = br.readLine();
		while(s != null){
			sb.append(s);
			s = br.readLine();
		}
		System.out.println(sb);
	}
	
	
	
	@org.junit.Test
	public void test005() throws Exception{
		
		HttpClient client = new DefaultHttpClient();
		client = ClientWrapper.wrapClient(client);
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
		HttpPost post = new HttpPost(url.replace("ACCESS_TOKEN", accessToken));
		post.setEntity(new StringEntity("{"+
     "\"button\":["+
     "{"+	
          "\"type\":\"view\","+
          "\"name\":\"我的博客\","+
          "\"url\":\"http://blog.csdn.net/zkn_cs_dn_2013\""+
     "},"+
      "{"+
           "\"name\":\"菜单\","+
           "\"sub_button\":["+
           "{"+	
               "\"type\":\"view\","+
               "\"name\":\"搜索\","+
               "\"url\":\"http://www.soso.com/\""+
            "},"+
            "{"+
               "\"type\":\"view\","+
               "\"name\":\"视频\","+
               "\"url\":\"http://v.qq.com/\""+
            "},"+
            "{"+
               "\"type\":\"click\","+
               "\"name\":\"赞一下我\","+
               "\"key\":\"V1001_GOOD\""+
            "}]"+
       "}]"+
 "}","UTF-8"));
		HttpResponse response = client.execute(post);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String s = br.readLine();
		while(s != null){
			sb.append(s);
			s = br.readLine();
		}
		System.out.println(sb);
	}
	
	@org.junit.Test
	public void test006() throws Exception{
		
		HttpClient client = new DefaultHttpClient();
		client = ClientWrapper.wrapClient(client);
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
		HttpGet get = new HttpGet(url.replace("ACCESS_TOKEN", accessToken));
		HttpResponse response = client.execute(get);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
		String s = br.readLine();
		while(s != null){
			sb.append(s);
			s = br.readLine();
		}
		System.out.println(sb);
	}
	
	@org.junit.Test
	public void test007() throws Exception{
		
		HttpClient client = new DefaultHttpClient();
		client = ClientWrapper.wrapClient(client);
		String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?q=apple&from=en&to=zh&appid=2015063000000001&salt=1435660288&sign=f89f9594663708c1605f3d736d01d2d4";
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
		String s = br.readLine();
		while(s != null){
			sb.append(s);
			s = br.readLine();
		}
		System.out.println(sb);
		JSONObject json = JSONObject.fromObject(sb.toString());
		
		System.out.println("翻译结果:::"+json.getJSONArray("trans_result").getJSONObject(0).getString("dst"));
	}

}
