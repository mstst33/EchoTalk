package com.mstst33.echoproject;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mstst33.setting.SettingWholeView;

public class SettingFragment extends Fragment implements GPS.NoticeGPSListener{
	private static final int MY_INFO_UPDATE = 0;
	private static final int MY_INFO_LOCATION = 1;
	
	private ArrayList<NameValuePair> nameValuePairs;
	private SettingWholeView settingWholeView;
	
	private int setting_State;
	
	SharedPreferences.Editor edt;
	SharedPreferences prefs;
	
	public SettingFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		edt = getActivity().getSharedPreferences("Echo", Context.MODE_PRIVATE).edit();
		prefs = getActivity().getSharedPreferences("Echo", Context.MODE_PRIVATE);
		
		settingWholeView = new SettingWholeView(getActivity());
		
		update();
		
		return settingWholeView;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		if(MainActivity.gps != null)
			MainActivity.gps.setOnNoticeGPSListener(this);
		
		updateWithGPS();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if(MainActivity.gps != null)
			MainActivity.gps.detachOnNoticeGPSListener();
	}
	
	@Override
	public void onNoticeGPS(boolean isSucceeded) {
		update();
	}
	
	public void update(){
		if (GPS.IS_GET_DATA) {
			Log.d("MyInfoFragment", "Updating succeeded");

			if (nameValuePairs != null) {
				if (!nameValuePairs.isEmpty()) {
					nameValuePairs.clear();
				}

				nameValuePairs = null;
			}

			nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("userID", BasicInfo.USER_ID));
			nameValuePairs.add(new BasicNameValuePair("location", BasicInfo.LOCATION));
			nameValuePairs.add(new BasicNameValuePair("setting_distance", BasicInfo.DISTANCE_TYPE));
			nameValuePairs.add(new BasicNameValuePair("setting_range", BasicInfo.RANGE_TYPE));

			Log.d("MyInfoFragment", BasicInfo.LOCATION);
			
			setting_State = MY_INFO_LOCATION;
			new SettingUpdateInfo().execute(nameValuePairs);
			
			GPS.IS_GET_DATA = false;
		} else {
			Log.d("MyInfoFragment", "Updating succeeded with before data");
			settingWholeView.updateLocation(false);
			settingWholeView.updateNumPeople(false);
		}
	}
	
	public void updateWithGPS(){
		settingWholeView.updateLocation(false);
		settingWholeView.updateNumPeople(false);
		
		if(!GPS.IS_RUNNING){
			Log.d("MyInfoFragment", "Update with gps");
			MainActivity.gps.startUpdate();
		}
	}
	
	public class SettingUpdateInfo extends AsyncTask<ArrayList<NameValuePair>, String, Boolean> {
		JSONObject json;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
			Boolean result = null;
			
			switch(setting_State){
			case MY_INFO_LOCATION:
				try {
					json = RestClient.requestLoginResult(MainActivity.LOCATION_SETUP_URL, params[0]);
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
			if (result == null)
				return;
			
			switch(setting_State){
			case MY_INFO_LOCATION:
				if (result){
					try {
						BasicInfo.NUM_OF_PEOPLE_AROUND_ME = json.getString("num_of_people_around_me");
						
						settingWholeView.updateLocation(true);
						settingWholeView.updateNumPeople(true);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Log.d("MyInfoFragment", "Updating location to server succeeded");
					
					if(BasicInfo.IS_FIRST){
						BasicInfo.IS_FIRST = false;
						
						edt.putBoolean("isFirst", BasicInfo.IS_FIRST);
						edt.commit();
						
						Log.d("EchoFragment", "Get location again at first");
						if(nameValuePairs != null){
							if(!nameValuePairs.isEmpty()){
								nameValuePairs.clear();
							}
							
							nameValuePairs = null;
						}
						
						nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("userID", BasicInfo.USER_ID));
						nameValuePairs.add(new BasicNameValuePair("location", BasicInfo.LOCATION));
						nameValuePairs.add(new BasicNameValuePair("setting_distance", BasicInfo.DISTANCE_TYPE));
						nameValuePairs.add(new BasicNameValuePair("setting_range", BasicInfo.RANGE_TYPE));
						
						new SettingUpdateInfo().execute(nameValuePairs);
					}
				}
				else{
					settingWholeView.updateLocation(false);
					settingWholeView.updateNumPeople(false);
					
					Log.d("MyInfoFragment", "Updating location to server failed");
				}
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}
}