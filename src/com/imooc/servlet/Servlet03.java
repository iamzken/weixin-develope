package com.imooc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Servlet implementation class Servlet03
 */
@WebServlet(name = "servlet03", urlPatterns = { "/servlet03" })
public class Servlet03 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	String appId="wx5d271f57da3824f9";
	String appSecret = "76a81949cdbb88f62372d1a45f1b5578";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet03() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
		
		
		HttpClient client = new DefaultHttpClient();
		try {
			accessTokenUrl = accessTokenUrl.replace("APPID", appId).replace(
					"APPSECRET", appSecret);
			HttpGet get = new HttpGet(accessTokenUrl);
			System.out.println(accessTokenUrl);
			HttpEntity entity = (HttpEntity) client.execute(get);
			if (entity != null) {
				JSONObject json = JSONObject.fromObject(entity.toString());
				String token = json.getString("access_token");
				int expiresIn = json.getInt("expires_in");
				System.out.println("access_token:::" + token + ",expires_in:::"
						+ expiresIn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
