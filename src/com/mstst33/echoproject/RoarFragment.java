package com.mstst33.echoproject;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.*;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;

import com.mstst33.roar.*;


public class RoarFragment extends Fragment implements Awooseong_Categoriable{
	private static final int ROAR_GET_DATA = 0;
	
	public static boolean IS_DOWNLOADING_FIRST;
	
	private boolean isClicked = false;
	Context awooseong;
	private Awooseong_Dialog_CategoryOfTheme dialog_category;

	private FrameLayout frameBase;
	private LinearLayout progress;
	private boolean isProgress;
	private Awooseong_Dialog_Temp dialogTemp;
	
	private LinearLayout linear_bodyInScrollIndirectOne;
	private LinearLayout linear_btn_additionalContent;
	private LinearLayout linear_baseForAllLayout;
	private LinearLayout linear_bodyInScrollDirectOne;
	private LinearLayout linear_bodyTop;

	private ScrollView scroll_bodyView;
	private Awooseong_Theme chooseTheme;

	private Awooseong_Room_Var_Daily dailyRoom;
	private Awooseong_Room_Var_Exercise exerciseRoom;
	private Awooseong_Room_Var_Hobby hobbyRoom;
	private Awooseong_Room_Var_Job jobRoom;
	private Awooseong_Room_Var_Question questionRoom;
	private Awooseong_Room_Var_Study studyRoom;
	private Awooseong_Room_Var_Travel travelRoom;
	private Awooseong_Room_Var_Used usedRoom;
	
	
	private String REQUEST_DATA_URL = BasicInfo.SERVER_ADDRESS + "request_data.php";
	private static String writingNum;
	private StringBuffer st;
	
	private int roarState;
	
	
	private int count;
	Awooseong_Theme themeChosen;
	
	public boolean isFirsts;
	public boolean isAdditional;
	public boolean isRenew;
	
	private Button btn_forAdditional;
	private Button btn_categoty;
	private Button btn_renew;
	private Button btn_upward;
	
	
	private TextView location;
	private TextView num_people;
	
	SharedPreferences.Editor edt;
	SharedPreferences prefs;
	
	// var class for getting data from server
	private Awooseong_ThemeRoom themeRoom;
	private Awooseong_EachRoomInfoInScrollInBody info_in_scroll;
	private ArrayList <Awooseong_ThemeRoom> arrayThemeRoom;
	private ArrayList<Awooseong_EachRoomInfoInScrollInBody> recycleImage;

	public RoarFragment(){
		isFirsts = true;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		IS_DOWNLOADING_FIRST = true;
		isAdditional = false;
		isProgress = true;
		isRenew = false;
		awooseong = getActivity();
		arrayThemeRoom = new ArrayList <Awooseong_ThemeRoom>();
		recycleImage = new ArrayList<Awooseong_EachRoomInfoInScrollInBody>();
		
		
		edt = getActivity().getSharedPreferences("Echo", Context.MODE_PRIVATE).edit();
		
		frameBase = (FrameLayout)View.inflate(getActivity(), R.layout.echo_framelayout, null);
		progress = (LinearLayout)View.inflate(getActivity(), R.layout.echo_progress, null);		
		
		linear_baseForAllLayout = (LinearLayout) View.inflate(awooseong,R.layout.awooseong_main, null);
		linear_bodyTop = (LinearLayout) View.inflate(awooseong, R.layout.roar_top, null);		
		
		Log.i("Awooseong", "main view created");

		// body start
		scroll_bodyView = (ScrollView) View.inflate(awooseong,R.layout.awooseong_body_scrollview, null);
		linear_bodyInScrollIndirectOne = (LinearLayout) View.inflate(awooseong, R.layout.awooseong_body_scroll_indirect_linear, null);
		linear_bodyInScrollDirectOne = (LinearLayout) View.inflate(awooseong, R.layout.awooseong_body_scroll_direct_linearlayout,null);
		linear_btn_additionalContent = (LinearLayout) View.inflate(awooseong,R.layout.awooseong_btn_layout_additional_content_in_scrollview, null);
		frameBase.setBackgroundResource(R.drawable.speak_back);
		Log.i("Awooseong", "body view created");
				
		btn_forAdditional = (Button) linear_btn_additionalContent.findViewById(R.id.awooseong_btn_additional_content_in_scrollview);
		btn_upward = (Button) linear_btn_additionalContent.findViewById(R.id.awooseong_btn_upward_in_scrollview);
		btn_categoty = (Button) linear_bodyTop.findViewById(R.id.top_category_btn);
		btn_renew = (Button) linear_bodyTop.findViewById(R.id.top_renew_btn);
		
		btn_forAdditional.setOnClickListener(mainListener);
		btn_categoty.setOnClickListener(mainListener);
		btn_upward.setOnClickListener(mainListener);
		btn_renew.setOnClickListener(mainListener);
		

		edt = getActivity().getSharedPreferences("Echo", Context.MODE_PRIVATE).edit();
		prefs = getActivity().getSharedPreferences("Echo", Context.MODE_PRIVATE);
		
		location = (TextView) linear_bodyTop.findViewById(R.id.roar_location_text);
		num_people = (TextView) linear_bodyTop.findViewById(R.id.roar_num_people_text);
		
		LayoutParams topParam = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.topHeight);
		LayoutParams middleParam = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.viewHeight);
		
		linear_baseForAllLayout.addView(linear_bodyTop, topParam); 
		linear_baseForAllLayout.addView(frameBase, middleParam);
		scroll_bodyView.addView(linear_bodyInScrollDirectOne);
		
		linear_bodyInScrollDirectOne.addView(linear_bodyInScrollIndirectOne);
		linear_bodyInScrollDirectOne.addView(linear_btn_additionalContent);
		
		if(IS_DOWNLOADING_FIRST){
			if(!BasicInfo.INTERESTED_THEME.equals("00000000"))
				getDataFromServer();
			else
				progress.setVisibility(View.INVISIBLE);
		}
		frameBase.addView(scroll_bodyView);
		frameBase.addView(progress);
		scroll_bodyView.setVisibility(View.INVISIBLE);
		
		//TempThread a = new TempThread();
		//a.setDaemon(true);
		//a.start();
		
		return linear_baseForAllLayout;
	}
	
	Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0: // normal
				isProgress = false;
				progress.setVisibility(View.INVISIBLE);
				scroll_bodyView.setVisibility(View.VISIBLE);
				dialogTemp = new Awooseong_Dialog_Temp(getActivity());
				dialogTemp.show();
				dialogTemp.dismiss();
			case 1: //Additional
				isProgress = false;
				progress.setVisibility(View.INVISIBLE);
				dialogTemp = new Awooseong_Dialog_Temp(getActivity());
				dialogTemp.show();
				dialogTemp.dismiss();
			}
		}
	};
	
	
	
	View.OnClickListener mainListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btn_categoty){
				
				Log.i("Awooseong", "ChooseTheme Btn is working");
				isAdditional = false;
				IS_DOWNLOADING_FIRST = true;
				dialog_category = new Awooseong_Dialog_CategoryOfTheme(awooseong, RoarFragment.this);
				dialog_category.show();
				
				// dialog_category에서 원하는 주제를 선택한 후에 BasicInfo에 접근하여 해당 주제에 맞는 값을 보여준다.
				edt.putString("interested_theme", BasicInfo.INTERESTED_THEME);
				edt.commit();
				
				IS_DOWNLOADING_FIRST = true;
				Log.i("onclick in Awossoeng", "clicekd");
				Log.i("Awooseong", "call getDataFromServer()");
			}
			
			if( v == btn_forAdditional){
				Log.i("Awooseong", "btnForAdditional is working");
				isAdditional = true;
				IS_DOWNLOADING_FIRST = false;
				getDataFromServer();
			}
			
			if( v == btn_renew ){
				Log.i("Awooseong", "refresh scroll view Btn is working");
				isAdditional = false;
				isRenew = true;
				IS_DOWNLOADING_FIRST = true;
				
				if(!BasicInfo.INTERESTED_THEME.equals("00000000"))
					getDataFromServer();
			}
			
			if( v == btn_upward ){
				
				new Handler().postDelayed(new Runnable() {
					public void run(){
						Log.i("awooseong","scroll run");
						scroll_bodyView.fullScroll(View.FOCUS_UP);
						scroll_bodyView.invalidate();
					}
					
				}, 100);
			}
		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
		updateLocation(false);
		updateNumPeople(false);		
	}
	
	public void onPause(){
		super.onPause();
	}
	public void onStop(){
		super.onStop();
	}
	public void onDestroyView(){
		
		Log.i("RoarFragment","onDestoryView and recycleImage");
		if(recycleImage.size() != 0){
		
			for( int i  = recycleImage.size() -1; i >=0; i--){
				recycleImage.get(i).recycleBitmapAndEverything();
			}
			recycleImage.clear();
		}
		frameBase.setBackgroundDrawable(null);
		progress.setBackgroundDrawable(null);
		btn_forAdditional.setBackgroundDrawable(null);
		btn_categoty.setBackgroundDrawable(null);
		btn_upward.setBackgroundDrawable(null);
		btn_renew.setBackgroundDrawable(null);
		linear_bodyTop.setBackgroundDrawable(null);
		super.onDestroyView();
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
	
	
	public static String replaceAll(String original, String from, String to) {
		int startPos;
		String replacedString = original;
		while (replacedString.indexOf(from) != -1) {
			startPos = replacedString.indexOf(from);
			replacedString = replacedString.substring(0, startPos) + to
					+ replacedString.substring(startPos + from.length());
		}
		return replacedString;
	}
	
	public void putInfoIntoScroll() {

		Log.i("Awooseong","putInfoIntoScroll is wroking");

		if (!isAdditional)
			linear_bodyInScrollIndirectOne.removeAllViews();
		
		ArrayList<LinearLayout> arrayLinear = new ArrayList<LinearLayout>();
		
		// 받아온 방의 정보를 받는다. 
		for(int i = 0; i < arrayThemeRoom.size() ; ++i){
			
			info_in_scroll = new Awooseong_EachRoomInfoInScrollInBody(
					awooseong , arrayThemeRoom.get(i));
			arrayLinear.add(info_in_scroll.getWholeRoom());
			//recycleImage.add(info_in_scroll);
		}

		Log.i("Awooseong", "body info going to push into linearlayout");

		linear_bodyInScrollIndirectOne.setGravity(Gravity.CENTER);

		for(int i = 0 ; i < arrayLinear.size(); i ++){
			linear_bodyInScrollIndirectOne.addView(arrayLinear.get(i));
		}
		
		if(linear_btn_additionalContent.getVisibility() == View.GONE && arrayLinear.size() > 0){
			linear_btn_additionalContent.setVisibility(View.VISIBLE);
			linear_btn_additionalContent.invalidate();
		}
		
		// send message to notify view is made up.
		if(isAdditional)
			mHandler.sendEmptyMessage(1);
		else
			mHandler.sendEmptyMessage(0);
		
		Log.i("Awooseong","arrayThemeRoom is being cleared");		
		arrayThemeRoom.clear();
		
		if (!isAdditional) {
			new Handler().postDelayed(new Runnable() {
				public void run() {
					Log.i("awooseong", "scroll run");
					scroll_bodyView.fullScroll(View.FOCUS_UP);
					scroll_bodyView.invalidate();
				}
			}, 100);
		}
	}
	
	private void getWritingNum(Awooseong_ThemeRoom theme) {
		Log.i("Awooseong", "getWritingNum is working");
		
		switch (theme.getThemeNumber()) {
		case Awooseong_Theme.DAILY:

			if (theme instanceof Awooseong_Room_Var_Daily) {
				Awooseong_Room_Var_Daily A = (Awooseong_Room_Var_Daily) theme;
				writingNum = A.daily_writingNum;
			}
			break;
			
		case Awooseong_Theme.TRAVEL:
			if (theme instanceof Awooseong_Room_Var_Travel) {
				Awooseong_Room_Var_Travel A = (Awooseong_Room_Var_Travel) theme;
				writingNum = A.travel_writingNum;
			}
			break;
			
		case Awooseong_Theme.EXERCISE:
			if (theme instanceof Awooseong_Room_Var_Exercise) {
				Awooseong_Room_Var_Exercise A = (Awooseong_Room_Var_Exercise) theme;
				writingNum = A.exercise_writingNum;
			}
			break;
			
		case Awooseong_Theme.HOBBY:
			if (theme instanceof Awooseong_Room_Var_Hobby) {
				Awooseong_Room_Var_Hobby A = (Awooseong_Room_Var_Hobby) theme;
				writingNum = A.hobby_writingNum;
			}
			break;
			
		case Awooseong_Theme.STUDY:
			if (theme instanceof Awooseong_Room_Var_Study) {
				Awooseong_Room_Var_Study A = (Awooseong_Room_Var_Study) theme;
				writingNum = A.study_writingNum;
			}
			break;
			
		case Awooseong_Theme.QUESTION:
			if (theme instanceof Awooseong_Room_Var_Question) {
				Awooseong_Room_Var_Question A = (Awooseong_Room_Var_Question) theme;
				writingNum = A.question_writingNum;
			}
			break;
			
		case Awooseong_Theme.JOB:
			if (theme instanceof Awooseong_Room_Var_Job) {
				Awooseong_Room_Var_Job A = (Awooseong_Room_Var_Job) theme;
				writingNum = A.job_writingNum;
			}
			break;
			
		case Awooseong_Theme.USED:
			if (theme instanceof Awooseong_Room_Var_Used) {
				Awooseong_Room_Var_Used A = (Awooseong_Room_Var_Used) theme;
				writingNum = A.used_writingNum;
			}
			break;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void getDataFromServer() {
		Log.i("Awooseong", "getDataFromServer() is working");
		
		if(!isProgress){
			isProgress = true;
			progress.setVisibility(View.VISIBLE);
			
			if(!isAdditional && !isRenew){
				scroll_bodyView.setVisibility(View.INVISIBLE);
			}
				
		}
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("theme", BasicInfo.INTERESTED_THEME));
		nameValuePairs.add(new BasicNameValuePair("user_id", BasicInfo.USER_ID));
		nameValuePairs.add(new BasicNameValuePair("setting_distance", BasicInfo.DISTANCE_TYPE));
		nameValuePairs.add(new BasicNameValuePair("setting_range", BasicInfo.RANGE_TYPE));
		nameValuePairs.add(new BasicNameValuePair("is_first", String.valueOf(IS_DOWNLOADING_FIRST)));
		if(!IS_DOWNLOADING_FIRST)
			{
			Log.i("writingNum1", writingNum);
			nameValuePairs.add(new BasicNameValuePair("last_writing_num", writingNum));
			}
			
		// call the function of which tell us that user choose the theme own want 
		
		roarState = ROAR_GET_DATA;
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
			
			switch (roarState) {
			case ROAR_GET_DATA:
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

		@SuppressWarnings("unused")
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			Log.i("Awooseong", "onPostExecute is woring");

			if (result == null)
				return;
			
			switch (roarState) {
			case ROAR_GET_DATA:
				if (result){
					int return_num;
					JSONArray results;
					try {
						return_num = json.getInt("return_num");
						
						Log.i("RoarFragment", Integer.toString(return_num));
						
						results =  json.getJSONArray("result");
						
						Log.i("RoarFragment", results.toString());
						
						Log.i("Awooseong", "JSonArray size :" + results.length());
						if(results == null){
							Log.i("Awooseong", "Theme info has nothing from server");
							
						}
						for(int i = 0; i < return_num; ++i){
							String temp = replaceAll(results.getString(i), "%$3#%", "/");
							Log.i("RoarFragment_split_/",temp);
							String[] value = temp.split("/");
							
							switch(Integer.parseInt(value[0])){
							case 0:
								dailyRoom = new Awooseong_Room_Var_Daily();
								dailyRoom.daily_content = value[1];
								dailyRoom.daily_date = value[2];
								dailyRoom.daily_pictureData = value[3];
								dailyRoom.daily_location = value[4];
								dailyRoom.daily_writingNum = value[5];
								dailyRoom.daily_userId = value[6];
								dailyRoom.daily_echoNum = value[7];
								dailyRoom.daily_echo = value[8];
								dailyRoom.daily_userPic = value[9];
								arrayThemeRoom.add(dailyRoom);
								Log.i("dailyRoom_picture", dailyRoom.daily_pictureData);
								
								break;
								
							case 1:
								travelRoom = new Awooseong_Room_Var_Travel();
								travelRoom.travel_subject = value[1];
								travelRoom.travel_content = value[2];
								travelRoom.travel_date = value[3];
								travelRoom.travel_pictureData = value[4];
								travelRoom.travel_numPeople = value[5];
								travelRoom.travel_startDate = value[6];
								travelRoom.travel_duration = value[7];
								travelRoom.travel_place = value[8];
								travelRoom.travel_location = value[9];
								travelRoom.travel_writingNum = value[10];
								travelRoom.travel_userId = value[11];
								travelRoom.travel_joinNum = value[12];
								travelRoom.travel_echoNum = value[13];
								travelRoom.travel_join = value[14];
								travelRoom.travel_echo = value[15];
								travelRoom.travel_userPic = value[16];
								
								Log.i("travelRoom_picture", travelRoom.travel_pictureData);
								Log.i("travelRoom_user", travelRoom.travel_userPic);
								arrayThemeRoom.add(travelRoom);
								break;
								
							case 2:
								exerciseRoom = new Awooseong_Room_Var_Exercise();
								exerciseRoom.exercise_subject = value[1];
								exerciseRoom.exercise_content = value[2];
								exerciseRoom.exercise_date = value[3];
								exerciseRoom.exercise_pictureData = value[4];
								exerciseRoom.exercise_numPeople = value[5];
								exerciseRoom.exercise_startDate = value[6];
								exerciseRoom.exercise_duration = value[7];
								exerciseRoom.exercise_place = value[8];
								exerciseRoom.exercise_location = value[9];
								exerciseRoom.exercise_writingNum = value[10];
								exerciseRoom.exercise_userId = value[11];
								exerciseRoom.exercise_joinNum = value[12];
								exerciseRoom.exercise_echoNum = value[13];
								exerciseRoom.exercise_join = value[14];
								exerciseRoom.exercise_echo = value[15];
								exerciseRoom.exercise_userPic = value[16];
								arrayThemeRoom.add(exerciseRoom);
								Log.i("exerciseRoom_picture", exerciseRoom.exercise_pictureData);
								Log.i("exerciseRoom_user", exerciseRoom.exercise_userPic);
								break;
								
							case 3:
								hobbyRoom = new Awooseong_Room_Var_Hobby();
								hobbyRoom.hobby_subject = value[1];
								hobbyRoom.hobby_content = value[2];
								hobbyRoom.hobby_date = value[3];
								hobbyRoom.hobby_pictureData = value[4];
								hobbyRoom.hobby_numPeople = value[5];
								hobbyRoom.hobby_startDate = value[6];
								hobbyRoom.hobby_duration = value[7];
								hobbyRoom.hobby_place = value[8];
								hobbyRoom.hobby_location = value[9];
								hobbyRoom.hobby_writingNum = value[10];
								hobbyRoom.hobby_userId = value[11];
								hobbyRoom.hobby_joinNum = value[12];
								hobbyRoom.hobby_echoNum = value[13];
								hobbyRoom.hobby_join = value[14];
								hobbyRoom.hobby_echo = value[15];
								hobbyRoom.hobby_userPic = value[16];
								arrayThemeRoom.add(hobbyRoom);
								Log.i("hobbyRoom_picture", hobbyRoom.hobby_pictureData);
								Log.i("hobbyRoom_user", hobbyRoom.hobby_userPic);
								break;
								
							case 4:
								studyRoom = new Awooseong_Room_Var_Study();
								studyRoom.study_subject = value[1];
								studyRoom.study_content = value[2];
								studyRoom.study_date = value[3];
								studyRoom.study_pictureData = value[4];
								studyRoom.study_numPeople = value[5];
								studyRoom.study_startDate = value[6];
								studyRoom.study_duration = value[7];
								studyRoom.study_place = value[8];
								studyRoom.study_day_week = value[9];
								studyRoom.study_location = value[10];
								studyRoom.study_writingNum = value[11];
								studyRoom.study_userId = value[12];
								studyRoom.study_joinNum = value[13];
								studyRoom.study_echoNum = value[14];
								studyRoom.study_join = value[15];
								studyRoom.study_echo = value[16];
								studyRoom.study_userPic = value[17];
								arrayThemeRoom.add(studyRoom);
								Log.i("studyRoom_picture", studyRoom.study_pictureData);
								Log.i("studyRoom_user", studyRoom.study_userPic);
								break;
								
							case 5:
								questionRoom = new Awooseong_Room_Var_Question();
								questionRoom.question_subject = value[1];
								questionRoom.question_content = value[2];
								questionRoom.question_date = value[3];
								questionRoom.question_pictureData = value[4];
								questionRoom.question_is_completed = value[5];
								questionRoom.question_location = value[6];
								questionRoom.question_writingNum = value[7];
								questionRoom.question_userId = value[8];
								questionRoom.question_echoNum = value[9];
								questionRoom.question_echo = value[10];
								questionRoom.question_userPic = value[11];
								arrayThemeRoom.add(questionRoom);
								Log.i("questionRoom_picture", questionRoom.question_pictureData);
								Log.i("questionRoom_user", questionRoom.question_userPic);
								break;
								
							case 6:
								jobRoom = new Awooseong_Room_Var_Job();
								jobRoom.job_subject = value[1];
								jobRoom.job_content = value[2];
								jobRoom.job_date = value[3];
								jobRoom.job_pictureData = value[4];
								jobRoom.job_numPeople = value[5];
								jobRoom.job_dayWeek = value[6];
								jobRoom.job_startDate = value[7];
								jobRoom.job_duration = value[8];
								jobRoom.job_place = value[9];
								jobRoom.job_pay = value[10];
								jobRoom.job_location = value[11];
								jobRoom.job_writingNum = value[12];
								jobRoom.job_userId = value[13];
								jobRoom.job_joinNum = value[14];
								jobRoom.job_echoNum = value[15];
								jobRoom.job_join = value[16];
								jobRoom.job_echo = value[17];
								jobRoom.job_userPic = value[18];
								arrayThemeRoom.add(jobRoom);
								Log.i("jobRoom_picture", jobRoom.job_pictureData);
								Log.i("jobRoom_user", jobRoom.job_userPic);
								break;
								
							case 7:
								usedRoom = new Awooseong_Room_Var_Used();
								usedRoom.used_subject = value[1];
								usedRoom.used_content = value[2];
								usedRoom.used_date = value[3];
								usedRoom.used_pictureData = value[4];
								usedRoom.used_section = value[5];
								usedRoom.used_isCompleted = value[6];
								usedRoom.used_isSell = value[7];
								usedRoom.used_price = value[8];
								usedRoom.used_howToSell = value[9];
								usedRoom.used_location = value[10];
								usedRoom.used_writingNum = value[11];
								usedRoom.used_userId = value[12];
								usedRoom.used_joinNum = value[13];
								usedRoom.used_echoNum = value[14];
								usedRoom.used_join = value[15];
								usedRoom.used_echo = value[16];
								usedRoom.used_userPic = value[17];
								arrayThemeRoom.add(usedRoom);
								Log.i("usedRoom_picture", usedRoom.used_pictureData);
								Log.i("usedRoom_user", usedRoom.used_userPic);
								break;
								
							}
							// writingNum is for additional
							getWritingNum(arrayThemeRoom.get(i));
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
			Log.i("Awooseong","call putInfoIntoScroll");
			
			if(isAdditional && arrayThemeRoom.size() == 0)
			{	
				Log.i("Awooseong","isAdditional has nothing");
				mHandler.sendEmptyMessage(0);
				Toast.makeText(awooseong, "더 이상 울려진 메아리가 없습니다", Toast.LENGTH_SHORT).show();
				return;
			}
			putInfoIntoScroll();
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