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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends Activity {
	private final String JOIN_URL = BasicInfo.SERVER_ADDRESS + "join.php";
	String deviceID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		
		Intent intent = this.getIntent();
		deviceID = intent.getStringExtra("deviceID");
	}

	public void mOnClick(View v) {
		switch(v.getId()){
		case R.id.add_member_submit:
			// TODO Auto-generated method stub
			EditText id_textbox = (EditText) findViewById(R.id.edit_id);
			EditText password_textbox = (EditText) findViewById(R.id.edit_password);
			EditText password_textbox2 = (EditText) findViewById(R.id.edit_password2);
			EditText email_textbox = (EditText) findViewById(R.id.edit_email);

			if (id_textbox.getText().toString().equals("")) {
				Toast.makeText(this, "Please, input your ID", Toast.LENGTH_SHORT).show();
			} else if (password_textbox.getText().toString().equals("")) {
				Toast.makeText(this, "Please, input your Password", Toast.LENGTH_SHORT).show();
			} else if (password_textbox2.getText().toString().equals("")) {
				Toast.makeText(this, "Please, input your Password to confirm", Toast.LENGTH_SHORT).show();
			} else if (email_textbox.getText().toString().equals("")) {
				Toast.makeText(this, "Please, input your email", Toast.LENGTH_SHORT).show();
			}
			
			if (password_textbox.getText().toString()
					.equals(password_textbox2.getText().toString())) {

				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

				nameValuePairs.add(new BasicNameValuePair("id", id_textbox.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("passwd", password_textbox.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("mail_address", email_textbox.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("device_id", deviceID));
				nameValuePairs.add(new BasicNameValuePair("date", TodayDate.getDate() + " " + TodayDate.getTime()));
				
				new JoinTask().execute(nameValuePairs);
			} else {
				String wrongPW = String.format(getResources().getString(
						R.string.pw_not_correct));
				Toast.makeText(this, wrongPW, Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	class JoinTask extends AsyncTask<ArrayList<NameValuePair>, Void, Boolean> {
		ProgressDialog pDialog = null;
		JSONObject json;
		
		JoinTask() {
			pDialog = new ProgressDialog(JoinActivity.this);
		}

		protected void onPreExecute() {
			pDialog.setMessage("Processing request of Join...");
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
			Boolean result = null;
			try {
				json = RestClient.requestLoginResult(JOIN_URL, params[0]);
				result = Boolean.parseBoolean(json.getString("is_success"));
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

			if (result){
				Log.d("Join", "Join successed");
				
				try {
					BasicInfo.USER_ID = json.getString("id");
					BasicInfo.USER_EMAIL = json.getString("email");
					BasicInfo.USER_DATE = json.getString("date");
					BasicInfo.USER_JOIN_DATE = json.getString("join_date");
					
					BasicInfo.IS_LOGIN = true;
					BasicInfo.IS_DEVICE_ID = true;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				GCMIntentService.GCMRegistration_id(JoinActivity.this);
				
				Intent intent = new Intent(JoinActivity.this, MainActivity.class);
				JoinActivity.this.startActivity(intent);
				JoinActivity.this.overridePendingTransition(R.anim.fade, R.anim.hold);
				JoinActivity.this.finish();
			}
			else{
				Toast.makeText(JoinActivity.this, "같은 아이디가 존재합니다", Toast.LENGTH_SHORT).show();
				Log.d("Join", "Join failed");
			}
		}
	}
}
