package com.mstst33.roar;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mstst33.echoproject.BasicInfo;
import com.mstst33.echoproject.R;
import com.mstst33.echoproject.RestClient;

public class Awooseong_Dialog_Report extends Dialog implements View.OnClickListener{

	private static final int REPORT_GET_DATA = 0;
	private String REQUEST_DATA_URL = BasicInfo.SERVER_ADDRESS + "request_report_data.php";
	private int report_state;
	
	Context mainContext;
	LinearLayout reportDialog_layout;
	Button btnSend;
	Button btnClose;
	EditText reportText;
	String writerId, writingNum;
	
	//Button btn_reportDialog;
	
	public Awooseong_Dialog_Report(Context context, String writerId, String writingNum) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mainContext = context;
		this.writerId = writerId;
		this.writingNum = writingNum;
		// TODO Auto-generated constructor stub
		reportDialog_layout = (LinearLayout)View.inflate(mainContext, R.layout.awooseong_report_layout, null);
		btnSend = (Button)reportDialog_layout.findViewById(R.id.awooseong_reportSendBtn);
		btnClose = (Button)reportDialog_layout.findViewById(R.id.awooseong_reportCloseBtn);
		reportText = (EditText)reportDialog_layout.findViewById(R.id.awooseong_reportText);
		btnSend.setOnClickListener(this);
		btnClose.setOnClickListener(this);
		
		setContentView(reportDialog_layout);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v == btnSend ){
			sendDataToSever();
		}
		else
			dismiss();
	}
	
	public void sendDataToSever() {
		Log.i("Dialog_EachPerson", "getDataFromServer() is working");
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("user_id", BasicInfo.USER_ID));
		nameValuePairs.add(new BasicNameValuePair("writer_id", writerId));
		nameValuePairs.add(new BasicNameValuePair("writing_num", writingNum));
		nameValuePairs.add(new BasicNameValuePair("report_text", reportText.getText().toString()));
		
		report_state = REPORT_GET_DATA;
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
			
			switch (report_state) {
			case REPORT_GET_DATA:
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
			if(result == null || result.equals("null")){
				Toast.makeText(mainContext, "신고 접수를 실패했습니다.", 0).show();
				reportText.setText("");
			}
			
			else if(result){
				Toast.makeText(mainContext, "신고 접수가 완료되었습니다.", 0).show();
				dismiss();
			}
			else{
				Toast.makeText(mainContext, "신고 접수를 실패했습니다.", 0).show();
				reportText.setText("");
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
