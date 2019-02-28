package com.taotao.httpclient;

import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {
	@Test
	public void doGet() throws Exception {
		//创建一个HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		//创建一个Get请求
		HttpGet get = new HttpGet("https://baidu.com");
		
		//执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		
		//返回响应内容
		int code = response.getStatusLine().getStatusCode();
		System.out.println(code);
		
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity,"utf-8");
		System.out.println(content);
		
		//关闭httpClient
		response.close();
		httpClient.close();
	}
	
	@Test
	public void doGetWithParams() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		URIBuilder uriBuilder = new URIBuilder("https://www.sogou.com/web");
		uriBuilder.addParameter("query", "花千骨");
		
		HttpGet get = new HttpGet(uriBuilder.build());
		
		HttpResponse response = httpClient.execute(get);
		
		int code = response.getStatusLine().getStatusCode();
		System.out.println(code);
		
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity,"utf-8");
		System.out.println(content);
	}
	
	@Test
	public void doPostWithParams() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一个post对象
		HttpPost post = new HttpPost("http://localhost:8081/rest/test/list");

		//创建一个表单
		List<NameValuePair> kvList = new ArrayList<NameValuePair>();
		kvList.add(new BasicNameValuePair("username", "dfy"));
		kvList.add(new BasicNameValuePair("password", "feiyang123"));
		
		//包装成entity对象
		StringEntity entity = new UrlEncodedFormEntity(kvList);
		
		post.setEntity(entity);
		
		HttpResponse response = httpClient.execute(post);
		
		int code = response.getStatusLine().getStatusCode();
		System.out.println(code);
		
		String content = EntityUtils.toString(response.getEntity());
		System.out.println(content);
		
	}
}
