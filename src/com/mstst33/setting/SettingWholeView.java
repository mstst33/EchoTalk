package com.mstst33.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mstst33.echoproject.BasicInfo;
import com.mstst33.echoproject.GPS;
import com.mstst33.echoproject.MainActivity;
import com.mstst33.echoproject.R;

public class SettingWholeView extends LinearLayout {
	private LinearLayout settingUpView;
	private LinearLayout settingBasic;
	private LinearLayout settingBody;
	private TextView location;
	private TextView num_people;
	
	private Button settingNotice;
	private Button settingAlert;
	private Button settingTalkOfEcho;
	
	private Dialog_Setting_Notice dialog_settingNotice;
	private Dialog_Setting_Alert dialog_alert;
	private Dialog_Setting_Talk dialog_talk;
	
	Context context;
	
	SharedPreferences.Editor edt;
	SharedPreferences prefs;
	
	public SettingWholeView(Context context) {
		super(context);
		
		this.context = context;
		setOrientation(LinearLayout.VERTICAL);
		
		edt = context.getSharedPreferences("Echo", Context.MODE_PRIVATE).edit();
		prefs = context.getSharedPreferences("Echo", Context.MODE_PRIVATE);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		settingUpView = (LinearLayout) inflater.inflate(R.layout.setting_top, null);
		settingBody = (LinearLayout) View.inflate(context, R.layout.setting_main_body, null);
		
		
		location = (TextView) settingUpView.findViewById(R.id.setting_location_text);
		num_people = (TextView) settingUpView.findViewById(R.id.setting_num_people_text);
		
		settingBody.setBackgroundResource(R.drawable.setting_back);
		
		LayoutParams topParam = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.topHeight);
		LayoutParams middleParam = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.viewHeight);
		settingNotice = (Button)settingBody.findViewById(R.id.setting_notice);
		settingAlert = (Button)settingBody.findViewById(R.id.setting_alert);
		settingTalkOfEcho = (Button)settingBody.findViewById(R.id.setting_talk_of_echo);
		
		settingNotice.setOnClickListener(listener);
		settingAlert.setOnClickListener(listener);
		settingTalkOfEcho.setOnClickListener(listener);
		
		addView(settingUpView, topParam);
		addView(settingBody, middleParam);
		
	}
	
	private View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if( v == settingNotice){
				
				dialog_settingNotice = new Dialog_Setting_Notice(context);
				dialog_settingNotice.show();
				
			}
			if( v == settingAlert){
				dialog_alert = new Dialog_Setting_Alert (context);
				dialog_alert.show();
				
			}
			if( v == settingTalkOfEcho){
				dialog_talk = new Dialog_Setting_Talk (context);
				dialog_talk.show();
			}
						
		}
	};
	
	public void updateLocation(boolean isSucceeded){
		if(isSucceeded){
			BasicInfo.LOCATION = String.valueOf(GPS.CUR_LAT) + " " + String.valueOf(GPS.CUR_LNG);
			BasicInfo.ADDRESS = GPS.CUR_ADDRESS;
			location.setText(BasicInfo.ADDRESS);
			
			edt.putString("location", BasicInfo.LOCATION);
			edt.putString("address", BasicInfo.ADDRESS);
			edt.commit();
		}
		else{
			BasicInfo.LOCATION = prefs.getString("location", "");
			BasicInfo.ADDRESS = prefs.getString("address", "");
			location.setText(BasicInfo.ADDRESS);
		}
		
		Log.d("EchoUpdate", "Address: " + GPS.CUR_ADDRESS);
	}
	
	public void updateNumPeople(boolean isSucceeded){
		if(isSucceeded){
			num_people.setText(BasicInfo.NUM_OF_PEOPLE_AROUND_ME);
		
			edt.putString("num_of_people_around_me", BasicInfo.NUM_OF_PEOPLE_AROUND_ME);
			edt.commit();
		}
		else{
			BasicInfo.NUM_OF_PEOPLE_AROUND_ME = prefs.getString("num_of_people_around_me", "0");
			num_people.setText(BasicInfo.NUM_OF_PEOPLE_AROUND_ME);
		}
		
		Log.d("EchoUpdate", "The number of people around me: " + BasicInfo.NUM_OF_PEOPLE_AROUND_ME);
	}
}
