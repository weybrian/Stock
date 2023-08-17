package com.example.service.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.StockDao;
import com.example.service.StockService;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	StockDao stockDao;

	// 本國上市證券 有價證券代號及名稱 一覽表
	private final String STOCK_CODE_LIST_WEB = "https://isin.twse.com.tw/isin/C_public.jsp?strMode=2";

	@Override
	public Map<String, String> getCompanyMap(ArrayList<String> arrayList) {
		// 本國上市證券有價證券代號及名稱一覽表
		String responseBody = executeGetRequest(STOCK_CODE_LIST_WEB);

		Map<String, String> map = responseBody2CompanyMap(responseBody, arrayList);

		return map;
	}

	/**
	 * 執行get請求
	 * 
	 * @param url
	 * @return
	 */
	public static String executeGetRequest(String url) {

		// 建立HttpClient實例
		HttpClient httpClient;

		HttpResponse<String> response = null;
		try {
			httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1) // http 1.1
					.connectTimeout(Duration.ofSeconds(5)) // timeout after 5 seconds
					.sslContext(disabledSSLContext()) // disable SSL verify
					.build();

			// 建立HttpRequest請求
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

			// 發送請求並接收回應
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 取得回應主體內容
		return response.body();
	}

	/**
	 * 執行get請求2Json
	 * 
	 * @param url
	 * @return
	 */
	public static LinkedHashMap<String, Object> executeGetRequest2Json(String url) {

		LinkedHashMap<String, Object> map = null;

		String responseBody = executeGetRequest(url);

		try {
			// to JsonObject
			map = new JSONParser(responseBody).parseObject();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 取得回應主體內容
		return map;
	}

	/**
	 * 停用SSL Context
	 * https://matthung0807.blogspot.com/2021/11/java-11-httpclient-send-request-example.html
	 * 
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	private static SSLContext disabledSSLContext() throws KeyManagementException, NoSuchAlgorithmException {
		SSLContext sslContext = SSLContext.getInstance("TLS"); // https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html#sslcontext-algorithms
		sslContext.init(null, new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} }, new SecureRandom());
		return sslContext;
	}

	/**
	 * 將responseBody內的 有價證券代號及名稱 放入map
	 * 
	 * @param responseBody
	 */
	private static Map<String, String> responseBody2CompanyMap(String responseBody, ArrayList<String> arrayList) {
		int typeStart = 0, typeEnd = 0, nextTypeStart = 0, companyStart = 0, companyEnd = 0, nextCompanyStart = 0;
		Boolean hasNextType = true;
		Map<String, String> companies = new LinkedHashMap<String, String>();

		while (hasNextType) {
			// 股票種類
			typeStart = responseBody.indexOf("<B> ", typeEnd + 5);
			typeEnd = responseBody.indexOf(" <B> ", typeStart + 4);
			String type = responseBody.substring(typeStart + 4, typeEnd);

			companyStart = typeStart;
			companyEnd = typeEnd;

			// 下一個股票種類位置
			nextTypeStart = responseBody.indexOf("<B> ", typeEnd + 5);
			if (-1 == nextTypeStart) {
				hasNextType = false;
			}

			while (arrayList.contains(type) && -1 != nextCompanyStart
					&& (nextCompanyStart < nextTypeStart || -1 == nextTypeStart)) {
				companyStart = responseBody.indexOf("<tr><td bgcolor=#FAFAD2>", companyEnd + 1);
				int t = responseBody.indexOf("</td><td bgcolor=#FAFAD2>T", companyStart + 1);
				int k = responseBody.indexOf("</td><td bgcolor=#FAFAD2>K", companyStart + 1);
				if (-1 == t || -1 == k) {
					companyEnd = Math.max(t, k);
				} else if (t > k) {
					companyEnd = k;
				} else if (t < k) {
					companyEnd = t;
				}
				String[] array = responseBody.substring(companyStart + 24, companyEnd).split("　");
				companies.put(array[0], array[1]);

				nextCompanyStart = responseBody.indexOf("<tr><td bgcolor=#FAFAD2>", companyEnd + 1);
			}
		}
		return companies;
	}

	@Override
	public ArrayList<Map<String, Object>> getStockInfo(Map<String, String> map) {
		ArrayList<Map<String, Object>> stockInfo = new ArrayList<>();

		for (String key : map.keySet()) {
			stockInfo.add(executeGetRequest2Json(
					"https://www.twse.com.tw/exchangeReport/STOCK_DAY?response=json&date=20230801&stockNo=" + key));
		}
		return stockInfo;
	}

	@Override
	public void save(String key, String name, Object data) {
		// TODO 拆解資料

	}
}
