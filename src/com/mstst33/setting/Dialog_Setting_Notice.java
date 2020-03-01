package com.mstst33.setting;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.message.*;
import org.json.*;

import com.mstst33.echoproject.*;
import com.mstst33.echoproject.RoarFragment.*;
import com.mstst33.roar.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class Dialog_Setting_Notice extends Dialog {

	ArrayList<String>noticeFromServer;
	LinearLayout settingList;
	Context context;
	
	public Dialog_Setting_Notice(Context context) {
		super(context);
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated constructor stub
		
		settingList = (LinearLayout) View.inflate(context, R.layout.setting_notice_linear_listview, null);
		//getNoticeDataFromServer();
		noticeFromServer = new ArrayList<String>();
		
		ArrayAdapter<String>Adapter;
		Adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, noticeFromServer);
		
		ListView list =(ListView)settingList.findViewById(R.id.setting_notice_listview);
		list.setAdapter(Adapter);
		
		setContentView(settingList);
	}
	
	/*
	private void getNoticeDataFromSercer(){
		Log.i("Awooseong", "getDataFromServer() is working");
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			
		// call the function of which tell us that user choose the theme own want 
		
		settingState = SETTING_GET_DATA;
		new RoarUpdateInfo().execute(nameValuePairs);
	}

	public class RoarUpdateInfo extends AsyncTask<ArrayList<NameValuePair>, String, Boolean> {
		JSONObject json;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
			Boolean result = null;
			
			switch (settingState) {
			case SETTING_GET_DATA:
				try {
					json = RestClient.requestLoginResult(REQUEST_DATA_URL, params[0]);
					result = Boolean.parseBoolean(json.getString("is_success"));
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			Log.i("Awooseong", "onPostExecute is woring");

			if (result == null)
				return;
			
			switch (settingState) {
			case SETTING_GET_DATA:
				if (result){
			
					try {
		
					

						}
				
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					Log.d("Echo Shout", "Getting writing succeeded");
				}
				else{
					Log.d("Echo Shout", "Getting writing failed");
				}
				break;
			}
		}
	}*/
}
