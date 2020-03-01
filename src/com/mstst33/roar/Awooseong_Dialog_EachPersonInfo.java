package com.mstst33.roar;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.mstst33.echoproject.BasicInfo;
import com.mstst33.echoproject.R;
import com.mstst33.echoproject.RestClient;
import com.mstst33.echoproject.URLBitmapDownload;

public class Awooseong_Dialog_EachPersonInfo extends Dialog implements View.OnClickListener, Awooseong_Categoriable {

	private Context awooseong;
	private LinearLayout layout;
	private ImageView mainPic;
	private TextView id;
	private TextView eMail;
	private TextView name;
	private TextView age;
	private TextView gender;
	private TextView comment;
	private Button interestedTheme;
	
	private static final int PERSON_GET_DATA = 0;
	private String REQUEST_DATA_URL = BasicInfo.SERVER_ADDRESS + "request_person_data.php";
	private int person_state;
	private Awooseong_Var_In_EachPerson person;
	private String userId;
	private Awooseong_Dialog_CategoryOfTheme dialog_interestedTheme;
	
	private FrameLayout frameBase;
	private LinearLayout progress;
	
	public Awooseong_Dialog_EachPersonInfo(Context context, String userId) {
		super(context);
		this.userId = userId;
		awooseong = context;
		person = new Awooseong_Var_In_EachPerson();
		
		frameBase = (FrameLayout)View.inflate(awooseong, R.layout.echo_framelayout, null);
		progress = (LinearLayout)View.inflate(awooseong, R.layout.echo_progress, null);		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		layout = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_profile, null);
		
		mainPic = (ImageView)layout.findViewById(R.id.my_profile_photo);
		id = (TextView)layout.findViewById(R.id.my_id);
		eMail = (TextView)layout.findViewById(R.id.my_email);
		name = (TextView)layout.findViewById(R.id.my_name);
		age = (TextView)layout.findViewById(R.id.my_age);
		gender = (TextView)layout.findViewById(R.id.my_gender);
		comment = (TextView)layout.findViewById(R.id.my_status);
		interestedTheme = (Button)layout.findViewById(R.id.my_theme_btn);
		
		// server로부터 Name, mainPic, Info를 받아와 적용한다.
		getDataFromServer();
		
		frameBase.addView(layout);
		frameBase.addView(progress);
		interestedTheme.setOnClickListener(this);
		setContentView(frameBase);
	}
	
	public Handler DialogEachPersonHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 9: 
				progress.setVisibility(View.INVISIBLE);
				mainPic.setVisibility(View.VISIBLE);
				break;
			}
		}
	};
	
	private void setPersonData(){
		Log.i("Dialog_EachPerson", "setPersonData() is working");
		
		name.setText(person.name);
		id.setText(person.id);
		eMail.setText(person.email);
		name.setText(person.name);
		
		age.setText(person.age);
		
		if(gender.getText().toString().equals("1"))
			gender.setText("여자");
		else
			gender.setText("남자");
		comment.setText(person.comment);
		
		if(person.pictureData == null){
			Log.i("Dialog_EachPerson", "MyPic is null");
		}
		else{
			if(person.pictureData.length() > 2)
			{ 	Log.i("Dialog_EachPerson", "Mypic is downloding");
				Log.i("Dialog_EachPerson",person.pictureData);
				mainPic.setVisibility(View.INVISIBLE);
				new URLBitmapDownload(person.pictureData, mainPic, false, true, DialogEachPersonHandler).start();
				//new CheckThread().start();
			}
		}
	}
	
	public String getInteretedTheme(){
		
		return person.interestedTheme;
	}
	
	public void onBackPressed(){
		URLBitmapDownload.recycleBitmap(mainPic);
		Log.i("Dialog_EachPersonInfo", "mainPic is recycled");
		super.onBackPressed();
	}
	
	@Override
	public void onClick(View v) {
		Log.i("Dialog_eachPersoN","intereseted Theme clicked");
		if(person.interestedTheme == null)
		{
			Log.i("EachInfo", "interestedTheme is null");
			return;
		}

		dialog_interestedTheme = new Awooseong_Dialog_CategoryOfTheme(awooseong, this);
		dialog_interestedTheme.show();
	}
	
	
	public void getDataFromServer() {
		Log.i("Dialog_EachPerson", "getDataFromServer() is working");
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("user_id", userId));
		
		person_state = PERSON_GET_DATA;
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
			
			switch (person_state) {
			case PERSON_GET_DATA:
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
			
			Log.i("Dialog_EachPerson", "onPostExecute is woring");
			
			switch (person_state) {
			case PERSON_GET_DATA:
				Log.i("Dialog_EachPerson", "ROAR_GET_DATA in Switch starting");
				if( result == null )
					return;
				if (result){
					
					JSONArray results;
					
					try {
						results = json.getJSONArray("result");
						if(results == null){
							Log.i("Dialog_EachPerson", "info has nothing from server");
						}
						
						Log.i("Dialog_EachPerson", "The String from server will be splited");
						//String[] value = results.getString(0).split("/")
						person.id = results.get(0).toString();
						person.password = results.get(1).toString();
						person.name = results.get(2).toString();
						person.gender = results.get(3).toString();
						person.age = results.get(4).toString();
						person.interestedTheme = results.get(5).toString();
						person.pictureData = results.get(6).toString();
						person.email = results.get(7).toString();
						person.regId = results.get(8).toString();
						person.location = results.get(9).toString();
						person.isLogin = results.get(10).toString();
						person.deviceId = results.get(11).toString();
						person.comment = results.get(12).toString();
						person.eNum = results.get(13).toString();
						person.rNum = results.get(14).toString();
						person.echoRoom = results.get(15).toString();
						person.roarRoom = results.get(16).toString();
						person.date = results.get(17).toString();
						person.joinDate = results.get(18).toString();
						
						Log.i("personal_result", results.toString());
						Log.i("personal", person.id + " " + person.password);
						Log.i("Dialog_EachPerson", person.name);
						Log.i("Dialog_EachPerson", person.deviceId);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					Log.i("Dialog_EachPersont", "Getting writing succeeded");
				}
				else{
					Log.i("Dialog_EachPerson", "Getting writing failed");
				}
				break;
			}
			setPersonData();
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
