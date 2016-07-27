package com.imooc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.imooc.test.ClientWrapper;

/**
 * Servlet implementation class Servlet04
 */
@WebServlet(name = "servlet04", urlPatterns = { "/servlet04" })
public class Servlet04 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String accessToken = "lvozM7mbKLOZgktzwuNL_kHWpGMrjOG2BJ42AaXZ4aV-bMGVxEB1TA6zTDj-nKrXQeGIJCKULklQIQFa8mHacK_2PQ_4HwgUcAYuhRQvOMwRQScABAFEG";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet04() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpClient client = new DefaultHttpClient();
		client = ClientWrapper.wrapClient(client);
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
		HttpPost post = new HttpPost(url.replace("ACCESS_TOKEN", accessToken));
		post.setEntity(new StringEntity("{"+
     "\"button\":["+
     "{"+	
          "\"type\":\"click\","+
          "\"name\":\"今日歌曲\","+
          "\"key\":\"V1001_TODAY_MUSIC\""+
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
               "\"name\":\"赞一下我们\","+
               "\"key\":\"V1001_GOOD\""+
            "}]"+
       "}]"+
 "}","UTF-8"));
		HttpResponse response1 = client.execute(post);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
		String s = br.readLine();
		while(s != null){
			sb.append(s);
			s = br.readLine();
		}
		System.out.println(sb);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
