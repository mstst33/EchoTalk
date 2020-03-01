package com.mstst33.echoproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class GCM3rdPartyRequest extends Thread {
	String mAddr;
	String id;
	String mResult;
	String selectedInfo;
	String writing_num;
	String sender_id;
	String msg;

	public GCM3rdPartyRequest() {

	}

	public void Setting(String addr, String id, String sender_id,
			String msg, String selectedInfo, String writing_num) {
		mAddr = addr;
		this.id = id;
		mResult = "";
		this.sender_id = sender_id;
		this.msg = msg;
		this.selectedInfo = selectedInfo;
		this.writing_num = writing_num;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		StringBuilder html = new StringBuilder();
		try {
			URL url = new URL(mAddr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDefaultUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			StringBuffer buffer = new StringBuffer();
			buffer.append("id").append("=").append(id).append("&");
			buffer.append("sender_id").append("=").append(sender_id).append("&");
			buffer.append("msg").append("=").append(msg).append("&");
			buffer.append("selectedInfo").append("=").append(selectedInfo).append("&");
			buffer.append("writing_num").append("=").append(writing_num);

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8"));
			pw.write(buffer.toString());
			pw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				html.append(line);
			}
			br.close();
			mResult = html.toString();
			if(mResult != null)
				Log.d("After 3rdParty", mResult);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// run

	public String getPort() {
		return mResult;

	}
}