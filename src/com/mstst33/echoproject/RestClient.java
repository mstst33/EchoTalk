package com.mstst33.echoproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RestClient {

	public static String convertStreamToString(InputStream is)
			throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}

		return sb.toString();
	}

	// JSON is data format
	public static JSONObject requestLoginResult(String baseUri, ArrayList<NameValuePair> nameValuePairs)
			throws ClientProtocolException, IOException,JSONException {

		HttpClient httpClient = new DefaultHttpClient();

		// baseUri += URLEncoder.encode(join_result, "utf-8");
		
		Log.d("base_uri",baseUri);
		UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
		HttpPost httpPost = new HttpPost(baseUri);
		httpPost.setEntity(ent);
		HttpResponse response = null;

		InputStream is = null;

		JSONObject resultJsonObject = null;

		try {
			response = httpClient.execute(httpPost); //Using uri, try to connect to httpclient and get response

			Log.d("RestClient", response.getStatusLine().toString());

			HttpEntity entity = response.getEntity(); //response from server

			if (entity != null) {
				is = entity.getContent(); //return resultContent as form of InputStream
				String result = convertStreamToString(is);
				
				Log.e("sReturn", result);
				resultJsonObject = new JSONObject(result);
				Log.d("RestClient", resultJsonObject.toString());
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

		return resultJsonObject;
	}
	
	// JSON is data format
	public static void requestLoginResultVoid(String baseUri, ArrayList<NameValuePair> nameValuePairs)
			throws ClientProtocolException, IOException, JSONException {

		HttpClient httpClient = new DefaultHttpClient();

		// baseUri += URLEncoder.encode(join_result, "utf-8");

		Log.d("base_uri", baseUri);
		UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
		HttpPost httpPost = new HttpPost(baseUri);
		httpPost.setEntity(ent);
		HttpResponse response = null;

		InputStream is = null;

		try {
			response = httpClient.execute(httpPost); // Using uri, try to
														// connect to httpclient
														// and get response

			Log.d("RestClient", response.getStatusLine().toString());

			HttpEntity entity = response.getEntity(); // response from server

			if (entity != null) {
				is = entity.getContent(); // return resultContent as form of
											// InputStream
				String result = convertStreamToString(is);
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}
}
