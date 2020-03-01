package com.mstst33.myinfo;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.message.*;
import org.json.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mstst33.echoproject.*;
import com.mstst33.echoproject.MyInfoFragment.*;
import com.mstst33.roar.*;

public class MyInfoWholeView extends LinearLayout implements View.OnClickListener, Awooseong_Categoriable{
	private LinearLayout myinfoUpView;
	private LinearLayout myInfoBasic;
	private TextView location;
	private TextView num_people;
	
	public Button modify_btn;
	public Button decision_btn;
	
	public ImageView my_profile_photo;
	private Button my_theme_btn;
	
	public EditText my_id;
	public EditText my_email_edit;
	public EditText my_name_edit;
	public EditText my_age_edit;
	public RadioGroup gender_group;
	public RadioButton male_radio;
	public RadioButton female_radio;
	public EditText my_status_message;
	
	public Button my_email_btn_true;
	public Button my_email_btn_false;
	public Button my_name_btn_true;
	public Button my_name_btn_false;
	public Button my_age_btn_true;
	public Button my_age_btn_false;
	public Button my_gender_btn_true;
	public Button my_gender_btn_false;
	
	private ImageView myPic;
	
	private Awooseong_Dialog_CategoryOfTheme myFavoriteTheme;
	
	public String gender = "0";
	
	Context context;
	
	SharedPreferences.Editor edt;
	SharedPreferences prefs;
	
	public MyInfoWholeView(Context context) {
		super(context);
		
		this.context = context;
		setOrientation(LinearLayout.VERTICAL);
		
		edt = context.getSharedPreferences("Echo", Context.MODE_PRIVATE).edit();
		prefs = context.getSharedPreferences("Echo", Context.MODE_PRIVATE);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		myinfoUpView = (LinearLayout) inflater.inflate(R.layout.myinfo_top, null);
		
		location = (TextView) myinfoUpView.findViewById(R.id.myinfo_location_text);
		num_people = (TextView) myinfoUpView.findViewById(R.id.myinfo_num_people_text);
		
		modify_btn = (Button) myinfoUpView.findViewById(R.id.top_modify_btn);
		decision_btn = (Button) myinfoUpView.findViewById(R.id.top_decision_btn);
		decision_btn.setVisibility(Button.INVISIBLE);
		
		myInfoBasic = (LinearLayout) inflater.inflate(R.layout.myinfo_frag, null);
		
		my_profile_photo = (ImageView) myInfoBasic.findViewById(R.id.my_profile_photo);		
		
		my_theme_btn = (Button) myInfoBasic.findViewById(R.id.my_theme_btn);
		my_theme_btn.setOnClickListener(this);
		
		my_id = (EditText) myInfoBasic.findViewById(R.id.my_id);
		my_email_edit = (EditText) myInfoBasic.findViewById(R.id.my_email_edit);
		my_name_edit = (EditText) myInfoBasic.findViewById(R.id.my_name_edit);
		my_age_edit = (EditText) myInfoBasic.findViewById(R.id.my_age_edit);
		
		my_id.setText(BasicInfo.USER_ID);
		my_email_edit.setText(BasicInfo.USER_EMAIL);
		my_name_edit.setText(BasicInfo.USER_NAME);
		my_age_edit.setText(BasicInfo.USER_AGE);
		
		gender_group = (RadioGroup) myInfoBasic.findViewById(R.id.gender_group);
		gender_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup rgroup, int id) {
				if(id == R.id.male_radio)
					gender = "1";
				else if(id == R.id.female_radio)
					gender = "2";
			}
		});
		
		male_radio = (RadioButton) myInfoBasic.findViewById(R.id.male_radio);
		female_radio = (RadioButton) myInfoBasic.findViewById(R.id.female_radio);
		
		if(BasicInfo.USER_GENDER.equals("1")){
			male_radio.toggle();
		}
		else if(BasicInfo.USER_GENDER.equals("2")){
			female_radio.toggle();
		}
		
		my_status_message = (EditText) myInfoBasic.findViewById(R.id.my_status_edit);
		my_status_message.setText(BasicInfo.USER_COMMENT);
		my_status_message.setEnabled(false);
		
		LayoutParams topParam = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.topHeight);
		LayoutParams middleParam = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.viewHeight);
		
		addView(myinfoUpView, topParam);
		addView(myInfoBasic, middleParam);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.my_theme_btn:
			setMyTheme();
			break;
		}
	}
	
	public void modify(){
		modify_btn.setVisibility(Button.INVISIBLE);
		decision_btn.setVisibility(Button.VISIBLE);
		my_profile_photo.setEnabled(true);
		my_name_edit.setEnabled(true);
		my_age_edit.setEnabled(true);
		male_radio.setEnabled(true);
		female_radio.setEnabled(true);
		my_status_message.setEnabled(true);
		my_name_edit.setFocusable(true);
		my_age_edit.setFocusable(true);
		male_radio.setFocusable(true);
		female_radio.setFocusable(true);
		my_status_message.setFocusable(true);
	}
	
	public void decision(){
		modify_btn.setVisibility(Button.VISIBLE);
		decision_btn.setVisibility(Button.INVISIBLE);
		my_profile_photo.setEnabled(false);
		my_name_edit.setEnabled(false);
		my_age_edit.setEnabled(false);
		male_radio.setEnabled(false);
		female_radio.setEnabled(false);
		my_status_message.setEnabled(false);
		my_name_edit.setFocusable(false);
		my_age_edit.setFocusable(false);
		male_radio.setFocusable(false);
		female_radio.setFocusable(false);
		my_status_message.setFocusable(false);
	}
	
	public void saveMyProfile(){
		BasicInfo.USER_NAME = my_name_edit.getText().toString();
		BasicInfo.USER_AGE = my_age_edit.getText().toString();
		BasicInfo.USER_GENDER = gender;
		BasicInfo.USER_COMMENT = my_status_message.getText().toString();
	}
	
	public void setMyTheme(){
		
		myFavoriteTheme = new Awooseong_Dialog_CategoryOfTheme(context, this);
		myFavoriteTheme.show();
	}
	
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
		
		Log.d("MyInfoWholeView", "Address: " + GPS.CUR_ADDRESS);
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
		
		Log.d("MyInfoWholeView", "The number of people around me: " + BasicInfo.NUM_OF_PEOPLE_AROUND_ME);
	}
}