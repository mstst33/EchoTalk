package com.mstst33.echoproject;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private final String LOGIN_URL = BasicInfo.SERVER_ADDRESS + "login.php";
	
	String deviceID;
	
	EditText id = null;
	EditText pw = null;
	Button lg = null;
	Button join = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Intent intent = this.getIntent();
		deviceID = intent.getStringExtra("deviceID");
		
		id = (EditText) this.findViewById(R.id.edit_id_in_login);
		pw = (EditText) this.findViewById(R.id.edit_pw_in_login);
		lg = (Button) this.findViewById(R.id.login_button_in_login);
		join = (Button) this.findViewById(R.id.join_button_in_login);
	}

	public void mOnClick(View v) {
		// TODO Auto-generated method stub
		if (v == lg) {
			if(!id.getText().toString().equals("")){
				if(!pw.getText().toString().equals("")){
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				
					nameValuePairs.add(new BasicNameValuePair("string_id", id.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("passwd", pw.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("device_id", deviceID));
					nameValuePairs.add(new BasicNameValuePair("date", TodayDate.getDate() + " " + TodayDate.getTime()));
					
					new LoginTask().execute(nameValuePairs);
				}
				else{
					Toast.makeText(LoginActivity.this, "Please, input your Password", Toast.LENGTH_SHORT).show();
				}
			}
			else{
				Toast.makeText(LoginActivity.this, "Please, input your ID", Toast.LENGTH_SHORT).show();
			}
		} else if (v == join) {
			Intent intent = new Intent(this, JoinActivity.class);
			intent.putExtra("deviceID", deviceID);
			this.startActivity(intent);
			this.finish();
		}
	}
	
	class LoginTask extends AsyncTask<ArrayList<NameValuePair>, Void, Boolean> {
		ProgressDialog pDialog = null;
		JSONObject json_result = null;

		LoginTask() {
			pDialog = new ProgressDialog(LoginActivity.this);
		}

		protected void onPreExecute() {
			pDialog.setMessage("Processing request of Log-in...");
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
			Boolean result = null;
			try {
				json_result = RestClient.requestLoginResult(LOGIN_URL, params[0]);
				result = Boolean.parseBoolean(json_result.getString("is_success"));
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return result;
		}

		protected void onPostExecute(Boolean result) {
			pDialog.dismiss();

			if (result == null)
				return;
			
			if (result) {
				Toast.makeText(LoginActivity.this, "Login succeeded", Toast.LENGTH_SHORT).show();
				
				try {
					BasicInfo.USER_ID = json_result.getString("id");
					BasicInfo.USER_EMAIL = json_result.getString("email");
					BasicInfo.USER_DATE = json_result.getString("date");
					BasicInfo.USER_JOIN_DATE = json_result.getString("join_date");
					BasicInfo.USER_NAME = json_result.getString("name");
					BasicInfo.USER_GENDER = json_result.getString("gender");
					BasicInfo.USER_AGE = json_result.getString("age");
					BasicInfo.INTERESTED_THEME = json_result.getString("interested_theme");
					BasicInfo.USER_COMMENT = json_result.getString("comment");
					MainActivity.PICTURE_DATA = json_result.getString("picture_data");
					
					BasicInfo.IS_LOGIN = true;
					BasicInfo.IS_DEVICE_ID = true;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				GCMIntentService.GCMRegistration_id(LoginActivity.this);
				
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				LoginActivity.this.startActivity(intent);
				LoginActivity.this.overridePendingTransition(R.anim.fade, R.anim.hold);
				LoginActivity.this.finish();
			} else
				Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
		}
	}
}