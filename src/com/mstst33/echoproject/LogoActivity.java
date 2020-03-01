package com.mstst33.echoproject;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

public class LogoActivity extends FragmentActivity{
	private final int DEVICE_CHECK = 0;
	private final int UPDATE_DATE = 1;
	
	private final int LOGO_DELAY_TIME = 1500;
	private final String CHECK_REGISTERED_ID_URL = BasicInfo.SERVER_ADDRESS + "check_device_id.php";
	private final String UPDATE_DATE_URL = BasicInfo.SERVER_ADDRESS + "update_date.php";
	
	private int logo_state;
	
	private String reg_Id;
	private String deviceID;
	private Intent intent;
	
	boolean is_get_notification;
	String id;
	String msg;
	String selectedInfo;
	String writing_num;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo);
		
		SharedPreferences prefs = getSharedPreferences("Echo", Context.MODE_PRIVATE);
		if(prefs != null){
			Log.d("Initialize", "Load some data");
			BasicInfo.USER_ID = prefs.getString("user_id", "");
			BasicInfo.USER_PHOTO = prefs.getString("user_photo", "0");
			BasicInfo.USER_NAME = prefs.getString("user_name", "");
			BasicInfo.USER_AGE = prefs.getString("user_age", "");
			BasicInfo.USER_GENDER = prefs.getString("user_gender", "");
			BasicInfo.USER_EMAIL = prefs.getString("user_email", "");
			BasicInfo.USER_COMMENT = prefs.getString("user_comment", "");
			BasicInfo.USER_JOIN_DATE = prefs.getString("user_join_date", "");
			BasicInfo.USER_DATE = prefs.getString("user_date", "");
			BasicInfo.IS_LOGIN = prefs.getBoolean("isLogin", false);
			BasicInfo.IS_REGISTERED_ID = prefs.getBoolean("isRegisteredID", false);
			BasicInfo.IS_DEVICE_ID = prefs.getBoolean("isDeviceID", false);
			BasicInfo.REG_ID = prefs.getString("reg_id", "");
			BasicInfo.IS_THERE_SOUND = prefs.getBoolean("isThereSound", true);
			BasicInfo.IS_THERE_VIBRATE = prefs.getBoolean("isThereVibrate", true);
			BasicInfo.IS_GET_MESSAGE = prefs.getBoolean("isGetMessage", true);
			BasicInfo.CURRENT_MESSAGE_NUM = prefs.getInt("currentMessageNum", 0);
			BasicInfo.INTERESTED_THEME = prefs.getString("interested_theme", "00000000");
			BasicInfo.LOCATION = prefs.getString("location", "");
			BasicInfo.ADDRESS = prefs.getString("address", "");
			BasicInfo.NUM_OF_PEOPLE_AROUND_ME = prefs.getString("num_of_people_around_me", "0");
			BasicInfo.DISTANCE_TYPE = prefs.getString("distance_type", "3");
			BasicInfo.RANGE_TYPE = prefs.getString("range_type", "1");
			BasicInfo.IS_FIRST = prefs.getBoolean("isFirst", true);
			EchoFragment.ECHO_PAGE_NUM = prefs.getInt("echoPageNum", 1);
		}
		
		if(BasicInfo.CURRENT_MESSAGE_NUM < BasicInfo.MAX_MESSAGE_NUM){
			
		}	
		
		Intent intents = this.getIntent();
		is_get_notification = intents.getBooleanExtra("gcm", false);
		
		if(is_get_notification){
			Log.d("LogoActivity", "Get notification");
			
			id = intents.getStringExtra("id");
			msg = intents.getStringExtra("message");
			selectedInfo = intents.getStringExtra("selectedInfo");
			writing_num = intents.getStringExtra("writing_num");
		}
		
		if(!BasicInfo.IS_REGISTERED_ID){
			
		}
		
		if(!BasicInfo.IS_DEVICE_ID){
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			deviceID = tm.getDeviceId();
			
			
			if(deviceID == null){
				deviceID = "";
			}
			else{
				Log.d("Device ID", deviceID);
			}
		}
		
		Handler splashHandler = new Handler();
		splashHandler.postDelayed(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(BasicInfo.IS_LOGIN){
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("user_id", BasicInfo.USER_ID));
					nameValuePairs.add(new BasicNameValuePair("date", TodayDate.getDate() + " " + TodayDate.getTime()));
					
					logo_state = UPDATE_DATE;
					new LogoTask().execute(nameValuePairs);
				}
				else{
					
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("device_id", deviceID));
					nameValuePairs.add(new BasicNameValuePair("date", TodayDate.getDate() + " " + TodayDate.getTime()));
					
					logo_state = DEVICE_CHECK;
					new LogoTask().execute(nameValuePairs);
				}
			}
		}, LOGO_DELAY_TIME);
	}
	
	class LogoTask extends AsyncTask<ArrayList<NameValuePair>, Void, Boolean> {
		JSONObject json;

		protected void onPreExecute() {
		}

		@Override
		protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
			Boolean result = null;
			
			switch(logo_state){
			case DEVICE_CHECK:
				try {
					json = RestClient.requestLoginResult(CHECK_REGISTERED_ID_URL, params[0]);
					result = Boolean.parseBoolean(json.getString("is_success"));
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case UPDATE_DATE:
				try {
					json = RestClient.requestLoginResult(UPDATE_DATE_URL, params[0]);
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
	
		protected void onPostExecute(Boolean result) {

			if (result == null)
				return;
			
			switch(logo_state){
			case DEVICE_CHECK:
				if (result){
					try {
						BasicInfo.USER_ID = json.getString("id");
						BasicInfo.USER_EMAIL = json.getString("email");
						BasicInfo.USER_DATE = json.getString("date");
						BasicInfo.USER_JOIN_DATE = json.getString("join_date");
						BasicInfo.USER_NAME = json.getString("name");
						BasicInfo.USER_GENDER = json.getString("gender");
						BasicInfo.USER_AGE = json.getString("age");
						BasicInfo.INTERESTED_THEME = json.getString("interested_theme");
						BasicInfo.USER_COMMENT = json.getString("comment");
						MainActivity.PICTURE_DATA = json.getString("picture_data");
						
						BasicInfo.IS_LOGIN = true;
						BasicInfo.IS_DEVICE_ID = true;
					
						GCMIntentService.GCMRegistration_id(LogoActivity.this);
					
						intent = new Intent(LogoActivity.this, MainActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						LogoActivity.this.finish();
						
						Log.d("Logo", "Found device_id");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				else{
					intent = new Intent(LogoActivity.this, LoginActivity.class);
					intent.putExtra("deviceID", deviceID);
					startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					LogoActivity.this.finish();
					
					Log.d("Logo", "Could not find device_id");
				}
				break;
			case UPDATE_DATE:
				if(result){
					intent = new Intent(LogoActivity.this, MainActivity.class);
					
					if(is_get_notification){
						intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("gcm", is_get_notification);
						intent.putExtra("id", id);
						intent.putExtra("message", msg);
						intent.putExtra("selectedInfo", selectedInfo);
						intent.putExtra("writing_num", writing_num);
					}
					
					startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					LogoActivity.this.finish();
				}
				else{
					Log.d("LogoActivity", "Updated last date to connect to this APP");
				}
				break;
			}
		}
	}
}