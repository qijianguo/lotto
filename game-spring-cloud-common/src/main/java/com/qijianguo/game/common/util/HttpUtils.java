package com.qijianguo.game.common.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.io.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http??????????????????
 * 1.??????http????????????(??????httpClient??????http,https???????????????)
 * 2.??????http??????(??????net.sourceforge.htmlunit???????????????html??????,???????????????js???????????????)
 */
public class HttpUtils {
	/**
	 * ??????????????????,??????20000ms
	 */
	private int timeout = 20000;
	/**
	 * ????????????JS????????????,??????20000ms
	 */
	private int waitForBackgroundJavaScript = 20000;
	/**
	 * cookie???
	 */
	private Map<String, String> cookieMap = new HashMap<>();

	/**
	 * ????????????(??????????????????)?????????UTF-8
	 */
	private String charset = "UTF-8";

	private static HttpUtils httpUtils;

	private HttpUtils() {
	}

	/**
	 * ????????????
	 *
	 * @return
	 */
	public static HttpUtils getInstance() {
		if (httpUtils == null) {
			httpUtils = new HttpUtils();
		}
		return httpUtils;
	}

	/**
	 * ??????cookieMap
	 */
	public void invalidCookieMap() {
		cookieMap.clear();
	}

	public int getTimeout() {
		return timeout;
	}

	/**
	 * ????????????????????????
	 *
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getCharset() {
		return charset;
	}

	/**
	 * ???????????????????????????
	 *
	 * @param charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getWaitForBackgroundJavaScript() {
		return waitForBackgroundJavaScript;
	}

	/**
	 * ??????????????????HTML?????????????????????JS???????????????
	 *
	 * @param waitForBackgroundJavaScript
	 */
	public void setWaitForBackgroundJavaScript(int waitForBackgroundJavaScript) {
		this.waitForBackgroundJavaScript = waitForBackgroundJavaScript;
	}

	/**
	 * ??????????????????????????????????????????
	 *
	 * @param html
	 * @return
	 * @throws Exception
	 */
	public static Document parseHtmlToDoc(String html) throws Exception {
		return removeHtmlSpace(html);
	}

	private static Document removeHtmlSpace(String str) {
		Document doc = Jsoup.parse(str);
		String result = doc.html().replace("&nbsp;", "");
		return Jsoup.parse(result);
	}

	/**
	 * ??????get??????,??????doc
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Document executeGetAsDocument(String url) throws Exception {
		return parseHtmlToDoc(executeGet(url));
	}

	/**
	 * ??????get??????
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String executeGet(String url) throws Exception {
		return executeGetWithHeader(url, null);
	}

	public String executeGetWithJsonAndHeaders(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
		List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
		if (params != null) {
			params.keySet().forEach(key -> {
				valuePairs.add(new BasicNameValuePair(key, params.get(key)));
			});
		}
		return executeGetWithHeader(generateGetUrl(url, params), headers);
	}

	/**
	 * ??????get??????
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String executeGetWithJson(String url, Map<String, String> params) throws Exception {
		return executeGet(generateGetUrl(url, params));
	}

	/**
	 * ???https??????get??????,??????doc
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Document executeGetWithSSLAsDocument(String url) throws Exception {
		return parseHtmlToDoc(executeGetWithSSL(url));
	}

	/**
	 * ???https??????get??????
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String executeGetWithSSL(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Cookie", convertCookieMapToString(cookieMap));
		//httpGet.setHeader( "User-Agent" , "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36" );
		httpGet.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
		// CloseableHttpClient httpClient =  createSSLInsecureClient();

		//*********************????????????????????????ssl??????****************
		//?????????????????????????????????https??????
		SSLContext sslcontext = createIgnoreVerifySSL();

		// ????????????http???https???????????????socket?????????????????????
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext))
				.build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);

		//??????????????????httpclient??????
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();


		return sendGet(httpClient, httpGet);
	}

	/**
	 * ????????????
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// ????????????X509TrustManager?????????????????????????????????????????????????????????
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	/**
	 * ??????get??????
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String executeGetWithHeader(String url, Map<String, String> headers) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Cookie", convertCookieMapToString(cookieMap));
		httpGet.setHeader( "User-Agent" , "Mozilla/5.0??(Windows??NT??6.1;??Win64;??x64;??rv:50.0)??Gecko/20100101??Firefox/50.0" );
		if (headers != null) {
			headers.forEach((key, value) -> {
				httpGet.setHeader(key, value);
			});
		}
		httpGet.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
		return sendGet(HttpClientBuilder.create().build(), httpGet);
	}

	private String sendGet(CloseableHttpClient httpClient, HttpGet httpGet) throws IOException {
		String str = "";
		CloseableHttpResponse response = null;
		try {
			HttpClientContext context = HttpClientContext.create();
			response = httpClient.execute(httpGet, context);
			getCookiesFromCookieStore(context.getCookieStore(), cookieMap);
			int state = response.getStatusLine().getStatusCode();
			if (state == 404) {
				str = "";
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				str = EntityUtils.toString(entity, charset);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException ignore) {
					throw ignore;
				}
			}
			if (httpClient != null) {
				httpClient.close();
			}
		}
		return str;
	}

	/**
	 * ??????post??????,??????doc
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Document executePostAsDocument(String url, Map<String, String> params) throws Exception {
		return parseHtmlToDoc(executePost(url, params));
	}

	/**
	 * ??????post??????
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String executePost(String url, Map<String, String> params) throws Exception {
		String reStr = "";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
		httpPost.setHeader("Cookie", convertCookieMapToString(cookieMap));
		List<NameValuePair> paramsRe = new ArrayList<>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			paramsRe.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		CloseableHttpResponse response;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(paramsRe));
			HttpClientContext context = HttpClientContext.create();
			response = httpclient.execute(httpPost, context);
			getCookiesFromCookieStore(context.getCookieStore(), cookieMap);
			HttpEntity entity = response.getEntity();
			reStr = EntityUtils.toString(entity, charset);
		} catch (IOException e) {
			throw e;
		} finally {
			httpPost.releaseConnection();
		}
		return reStr;
	}

	/**
	 * ???https??????post??????,??????doc
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Document executePostWithSSLAsDocument(String url, Map<String, String> params) throws Exception {
		return parseHtmlToDoc(executePostWithSSL(url, params));
	}

	/**
	 * ???https??????post??????
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String executePostWithSSL(String url, Map<String, String> params) throws Exception {
		String re = "";
		HttpPost post = new HttpPost(url);
		List<NameValuePair> paramsRe = new ArrayList<>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			paramsRe.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		post.setHeader("Cookie", convertCookieMapToString(cookieMap));
		post.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
		CloseableHttpResponse response;
		try {
			CloseableHttpClient httpClientRe = createSSLInsecureClient();
			HttpClientContext contextRe = HttpClientContext.create();
			post.setEntity(new UrlEncodedFormEntity(paramsRe));
			response = httpClientRe.execute(post, contextRe);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				re = EntityUtils.toString(entity, charset);
			}
			getCookiesFromCookieStore(contextRe.getCookieStore(), cookieMap);
		} catch (Exception e) {
			throw e;
		}
		return re;
	}

	/**
	 * ??????JSON??????body???POST??????
	 *
	 * @param url      ??????
	 * @param jsonBody json body
	 * @return
	 * @throws Exception
	 */
	public String executePostWithJson(String url, String jsonBody) throws Exception {
		String reStr = "";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
		httpPost.setHeader("Cookie", convertCookieMapToString(cookieMap));
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		CloseableHttpResponse response;
		try {
			httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
			HttpClientContext context = HttpClientContext.create();
			response = httpclient.execute(httpPost, context);
			getCookiesFromCookieStore(context.getCookieStore(), cookieMap);
			HttpEntity entity = response.getEntity();
			reStr = EntityUtils.toString(entity, charset);
		} catch (IOException e) {
			throw e;
		} finally {
			httpPost.releaseConnection();
		}
		return reStr;
	}

	/**
	 * ??????JSON??????body???SSL POST??????
	 *
	 * @param url      ??????
	 * @param jsonBody json body
	 * @return
	 * @throws Exception
	 */
	public String executePostWithJsonAndSSL(String url, String jsonBody) throws Exception {
		String re = "";
		HttpPost post = new HttpPost(url);
		post.setHeader("Cookie", convertCookieMapToString(cookieMap));
		post.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
		CloseableHttpResponse response;
		try {
			CloseableHttpClient httpClientRe = createSSLInsecureClient();
			HttpClientContext contextRe = HttpClientContext.create();
			post.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
			response = httpClientRe.execute(post, contextRe);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				re = EntityUtils.toString(entity, charset);
			}
			getCookiesFromCookieStore(contextRe.getCookieStore(), cookieMap);
		} catch (Exception e) {
			throw e;
		}
		return re;
	}

	/**
	 * ????????????????????????(????????????JS??????)
	 *
	 * @param url ??????URL
	 * @return
	 * @throws Exception
	 */
	public String getHtmlPageResponse(String url) throws Exception {
		String result = "";

		final WebClient webClient = new WebClient(BrowserVersion.CHROME);

		webClient.getOptions().setThrowExceptionOnScriptError(false);//???JS???????????????????????????????????????
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//???HTTP????????????200?????????????????????
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setCssEnabled(false);//????????????CSS
		webClient.getOptions().setJavaScriptEnabled(true); //??????????????????JS
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());//????????????????????????AJAX

		webClient.getOptions().setTimeout(timeout);//??????????????????????????????????????????
		webClient.setJavaScriptTimeout(timeout);//??????JS?????????????????????

		HtmlPage page;
		try {
			page = webClient.getPage(url);
		} catch (Exception e) {
			webClient.close();
			throw e;
		}
		webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);//?????????????????????

		result = page.asXml();
		webClient.close();

		return result;
	}

	/**
	 * ??????????????????Document??????(????????????JS??????)
	 *
	 * @param url ??????URL
	 * @return
	 * @throws Exception
	 */
	public Document getHtmlPageResponseAsDocument(String url) throws Exception {
		return parseHtmlToDoc(getHtmlPageResponse(url));
	}

	private void getCookiesFromCookieStore(CookieStore cookieStore, Map<String, String> cookieMap) {
		List<Cookie> cookies = cookieStore.getCookies();
		for (Cookie cookie : cookies) {
			cookieMap.put(cookie.getName(), cookie.getValue());
		}
	}

	private String convertCookieMapToString(Map<String, String> map) {
		String cookie = "";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			cookie += (entry.getKey() + "=" + entry.getValue() + "; ");
		}
		if (map.size() > 0) {
			cookie = cookie.substring(0, cookie.length() - 2);
		}
		return cookie;
	}

	/**
	 * ?????? SSL??????
	 *
	 * @return
	 * @throws GeneralSecurityException
	 */
	private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
		try {
			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
					(s, sslContextL) -> true);
			return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
		} catch (GeneralSecurityException e) {
			throw e;
		}
	}

	private String generateGetUrl(String url, Map<String, String> params) {
		List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
		if (params != null) {
			params.keySet().forEach(key -> {
				valuePairs.add(new BasicNameValuePair(key, params.get(key)));
			});
		}
		return url + "?" + URLEncodedUtils.format(valuePairs, Charsets.UTF_8);
	}

}