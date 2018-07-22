package com.github.carlos.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 
* @ClassName: HttpClient4 
* @Description: 
* @date 2015年10月22日 下午1:10:56
 */
public class HttpClientUtils {

	/**
	 * @return httpclient 实例
	 * @Description: 创建一个httpClient
	 */
	private static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		return httpclient;
	}

	/**
	 * @param url
	 *            请求的url
	 * @param params
	 *            请求头信息
	 * @return PostMethod
	 * @throws UnsupportedEncodingException
	 * @Description: 创建一个post实例
	 */
	private static HttpPost createPostMethod(String url, HashMap<String, String> params)
			throws UnsupportedEncodingException {
		HttpPost method = new HttpPost(url);
		if (params != null && params.size() != 0) {
			Set<String> keySet = params.keySet();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			int i = 0;
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			method.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		}
		return method;
	}

	/**
	 * @param url
	 *            请求的url
	 * @param params
	 *            请求头信息
	 * @return GetMethod
	 * @throws UnsupportedEncodingException
	 * @Description: 创建一个get实例
	 */
	private static HttpGet createGetMethod(String url, HashMap<String, String> params)
			throws UnsupportedEncodingException {
		HttpGet method = new HttpGet(url);
		if (params != null && params.size() != 0) {
			Set<String> keySet = params.keySet();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			int i = 0;
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
				i++;
			}
			((HttpResponse) method).setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		}
		return method;
	}

	public static String get(String url) {
		return getContent(url, null, "get", null);
	}

	public static String post(String url) {
		return getContent(url, null, "post", null);
	}

	public static String post(String url, HashMap<String, String> params) {
		return getContent(url, null, "post", params);
	}

	public static String getContent(String url, String srcContentCode, String requestType,
			HashMap<String, String> params) {

		CloseableHttpClient httpClient = getHttpClient();
		HttpResponse response = null;
		try {
			if ("post".equals(requestType)) {
				HttpPost httpPost = createPostMethod(url, params);
				response = httpClient.execute(httpPost);
			} else {
				HttpGet httpGet = createGetMethod(url, params);
				response = httpClient.execute(httpGet);
			}
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				String result = StringUtils.isNotBlank(srcContentCode) ? EntityUtils.toString(entity, srcContentCode)
						: EntityUtils.toString(entity);
				return result;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static String uploadFile(String url, File file) {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httppost = new HttpPost(url);

		FileBody bin = new FileBody(file);
		StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

		HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();

		httppost.setEntity(reqEntity);

		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity);
					EntityUtils.consume(entity);
					return result;
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}


}
