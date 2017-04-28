package com.hrp.utils.net;

import java.io.IOException;
import java.util.List;

import com.hrp.utils.Constant;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * HttpClientUtil
 */
public class HttpClientUtil {
	private HttpClientUtil() {
		throw new Error("工具类不能实例化");
	}

	/**
	 * httpclient发送post请求
	 */
	public static String requestPost(String url, List<NameValuePair> params) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new UrlEncodedFormEntity(params));
		CloseableHttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		String jsonStr = EntityUtils.toString(entity, Constant.DEFAULT_ENCODING);
		httppost.releaseConnection();
		return jsonStr;
	}
}
