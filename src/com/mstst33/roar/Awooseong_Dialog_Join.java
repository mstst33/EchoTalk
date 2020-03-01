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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.mstst33.echoproject.BasicInfo;
import com.mstst33.echoproject.R;
import com.mstst33.echoproject.RestClient;
import com.mstst33.echoproject.RoarFragment;
import com.mstst33.echoproject.TodayDate;

public class Awooseong_Dialog_Join extends Dialog implements View.OnClickListener {

	private String REQUEST_DATA_URL = BasicInfo.SERVER_ADDRESS + "request_join_data.php";
	private static final int JOIN_GET_DATA = 0;
	private int joinState;
	private Context awooseong;
	
	private RelativeLayout layoutRelativeBackground;
	private LinearLayout layoutBackground;
	private LinearLayout layoutLinearInScroll;
	private LinearLayout layoutJoinTwoButton;

	private ScrollView scrollView;
	private Button join, close;

	private RelativeLayout.LayoutParams RelativeLp;
	private RelativeLayout.LayoutParams linearTwoButtonLp;

	private Awooseong_Join_EachPersonLayout each;
	private JoinOrCommentAble upgradeJoinNum;
	private Awooseong_ThemeRoom info;
	private int theme;
	
	private String writingNum;
	private String selectedTheme;
	private String date;
	private String userId;
	private String is_update = "false";
	private String is_delete = "false";
	private Boolean isDelete;
	private Boolean isSet;
	private Boolean isMaster;
	private TodayDate time;
	private int joinNum;
	
	private ArrayList<Awooseong_Var_In_Join>joinArray;
	private ArrayList<Awooseong_Join_EachPersonLayout>recycleImage;
	private Awooseong_Var_In_Join joinTemp;
	
	private FrameLayout frameBase;
	private LinearLayout progress;
	private boolean isProgress;
	
	public Awooseong_Dialog_Join(Context context, JoinOrCommentAble eachRoom, Awooseong_ThemeRoom roomInfo) {

		super(context);
		isDelete = false;
		isSet = false;
		isMaster = false;
		isProgress = true;
		awooseong = context;
		upgradeJoinNum = eachRoom;
		this.info = roomInfo;
		this.theme = roomInfo.getThemeNumber();
		joinArray = new ArrayList<Awooseong_Var_In_Join>();
		recycleImage = new ArrayList<Awooseong_Join_EachPersonLayout>();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.i("Dialog_Join", "reply dialog start");
		
		frameBase = (FrameLayout)View.inflate(awooseong, R.layout.echo_framelayout, null);
		progress = (LinearLayout)View.inflate(awooseong, R.layout.echo_progress, null);		
		
		layoutRelativeBackground = (RelativeLayout) View.inflate(awooseong,R.layout.awooseong_reply_join_relativelayout_whole_background,null);
		layoutLinearInScroll = (LinearLayout) View.inflate(awooseong,R.layout.awooseong_reply_join_linearlayout_in_scrollview, null);
		layoutJoinTwoButton = (LinearLayout) View.inflate(awooseong,R.layout.awooseong_join_dialog_two_button, null);
		layoutBackground = (LinearLayout) View.inflate(awooseong,R.layout.awooseong_reply_join_backgroud_linearlayout, null);
		scrollView = (ScrollView) View.inflate(awooseong,R.layout.awooseong_reply_join_scrollview, null);

		join = (Button) layoutJoinTwoButton.findViewById(R.id.awooseong_join_dialog_joinBtn);
		close = (Button) layoutJoinTwoButton.findViewById(R.id.awooseong_join_dialog_closeBtn);

		join.setOnClickListener(this);
		close.setOnClickListener(this);
		
		switch(theme){
		
		case Awooseong_Theme.TRAVEL:
			Awooseong_Room_Var_Travel tr;
			if (info instanceof Awooseong_Room_Var_Travel) {
				tr = (Awooseong_Room_Var_Travel)info;
			}
			else
				return;
			
			writingNum = tr.travel_writingNum;
			userId = tr.travel_userId;
			selectedTheme = "1";
			break;
			
		case Awooseong_Theme.EXERCISE:
			
			Awooseong_Room_Var_Exercise exer;
			if (info instanceof Awooseong_Room_Var_Exercise) {
				exer = (Awooseong_Room_Var_Exercise) info;
			}
			else
				return;
			
			writingNum = exer.exercise_writingNum;
			userId = exer.exercise_userId;
			selectedTheme = "2";
			break;
			
		case Awooseong_Theme.HOBBY:
			Awooseong_Room_Var_Hobby hob;
			if (info instanceof Awooseong_Room_Var_Hobby) {
				hob = (Awooseong_Room_Var_Hobby)info;
			}
			else
				return;
			
			writingNum = hob.hobby_writingNum;
			userId = hob.hobby_userId;
			selectedTheme = "3";
			break;
		
		case Awooseong_Theme.STUDY:
			Awooseong_Room_Var_Study stu;
			if (info instanceof Awooseong_Room_Var_Study) {
				stu = (Awooseong_Room_Var_Study)info;
			}
			else
				return;
			
			writingNum = stu.study_writingNum;
			userId = stu.study_userId;
			selectedTheme = "4";
			break;
			
		case Awooseong_Theme.JOB:
			Awooseong_Room_Var_Job jo;
			if (info instanceof Awooseong_Room_Var_Job) {
				jo = (Awooseong_Room_Var_Job)info;
			}
			else
				return;
			
			writingNum = jo.job_writingNum;
			userId = jo.job_userId;
			selectedTheme = "6";
			break;	
			
		case Awooseong_Theme.USED:
			Awooseong_Room_Var_Used us;
			if (info instanceof Awooseong_Room_Var_Used) {
				us = (Awooseong_Room_Var_Used)info;
			}
			else
				return;
			writingNum = us.used_writingNum;
			userId = us.used_userId;
			selectedTheme = "7";
			break;
		}

		// layoutLinearInScroll에 Each person Join layout이 매번 등록 될 것이다.
		// 이는 Join Button Click 시 진행
		scrollView.addView(layoutLinearInScroll);

		// 전체적인 틀을 맞춘다.
		WindowManager wm = (WindowManager) awooseong.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		RelativeLp = new RelativeLayout.LayoutParams(display.getWidth(),display.getHeight());
		layoutRelativeBackground.setLayoutParams(RelativeLp);

		linearTwoButtonLp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		linearTwoButtonLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		linearTwoButtonLp.height = (int) (display.getHeight() * 0.08);
		layoutJoinTwoButton.setLayoutParams(linearTwoButtonLp);

		// ScrollView를 LinearLayout에 추가 한후 이를 전체적인 View인 Relative Layout에 추가하여
		// ScrollView가 전체 중 일정부분만 차지 할 수 있도록 설정한다.
		Log.i("Dialog_Join", "background of Scroll is being set");
		RelativeLayout.LayoutParams frameLp = new RelativeLayout.LayoutParams(display.getWidth(),
				display.getHeight());
		frameLp.height = (int) (display.getHeight() * 0.55);
		layoutBackground.setLayoutParams(frameLp);

		layoutBackground.addView(scrollView);

		layoutRelativeBackground.addView(layoutJoinTwoButton);
		layoutRelativeBackground.addView(frameBase);

		frameBase.setLayoutParams(frameLp);
		
		frameBase.addView(layoutBackground);
		frameBase.addView(progress);
		
		setContentView(layoutRelativeBackground);
		
		layoutBackground.setVisibility(View.INVISIBLE);
		
		if(userId.equals(BasicInfo.USER_ID)){
			isMaster = true;
		}
		join.setVisibility(View.INVISIBLE);
		close.setVisibility(View.INVISIBLE);
		getDataFromServer();
		
		// TODO Auto-generated constructor stub
	}

	public Handler DialogJoinHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0:
				isProgress = false;
				progress.setVisibility(View.INVISIBLE);
				layoutBackground.setVisibility(View.VISIBLE);
				break;
			}
		}
	};
	
	private void getDataFromServer(){
		
		if(!isProgress)
		{
			isProgress = true;
			layoutBackground.setVisibility(View.INVISIBLE);
			progress.setVisibility(View.VISIBLE);
		}
		
		Log.i("Dialog_join", "getDataFromServerAtTheFirst() is working");		
		if(!isDelete)
		{	Log.i("joinDialog_isDelete", isDelete.toString());
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("theme", selectedTheme));
			nameValuePairs.add(new BasicNameValuePair("user_id", BasicInfo.USER_ID));
			nameValuePairs.add(new BasicNameValuePair("writing_num", writingNum));
			nameValuePairs.add(new BasicNameValuePair("date", String.valueOf(time.getDate()+" "+time.getTime())));
			nameValuePairs.add(new BasicNameValuePair("milli_second", String.valueOf(time.getMilliSecond())));
			nameValuePairs.add(new BasicNameValuePair("is_update", is_update));
			nameValuePairs.add(new BasicNameValuePair("is_delete", is_delete));
			nameValuePairs.add(new BasicNameValuePair("my_photo", BasicInfo.USER_PHOTO));
			Log.i("Dialog_join", is_delete.toString());
			Log.i("Dialog_Comment", selectedTheme +" "+ BasicInfo.USER_ID +" "+ writingNum +" "+ is_update +" "+ is_delete +" "+ " " + BasicInfo.USER_PHOTO);
			new RoarUpdateInfo().execute(nameValuePairs);
		}
		else
		{
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("theme", selectedTheme));
			nameValuePairs.add(new BasicNameValuePair("user_id", BasicInfo.USER_ID));
			nameValuePairs.add(new BasicNameValuePair("writing_num", writingNum));
			nameValuePairs.add(new BasicNameValuePair("date", String.valueOf(time.getDate()+" "+time.getTime())));
			nameValuePairs.add(new BasicNameValuePair("milli_second", date));
			nameValuePairs.add(new BasicNameValuePair("is_update", is_update));
			nameValuePairs.add(new BasicNameValuePair("is_delete", is_delete));
			nameValuePairs.add(new BasicNameValuePair("my_photo", BasicInfo.USER_PHOTO));
			Log.i("Dialog_joinWr", writingNum.toString());
			Log.i("Dialog_join", is_delete.toString());
			Log.i("Dialog_Comment", selectedTheme +" "+ BasicInfo.USER_ID +" "+ writingNum +" "+ is_update +" "+ is_delete +" "+ BasicInfo.USER_PHOTO);
			Log.i("Dialog_join77", date.toString());
			new RoarUpdateInfo().execute(nameValuePairs);
		}

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
			
			switch (joinState) {
			case JOIN_GET_DATA:
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
			
			Log.i("Awooseong", "onPostExecute is working");

			if (result == null)
				{
					Log.i("Dialog_join","result from server is null");
					return;
				}
			
			switch (joinState) {
			case JOIN_GET_DATA:
				
				Log.i("Dialog_Join", result.toString());
				
				if (result){
					String results;
					try {
						Log.i("Dialog_join", "Join get data is trying");
						results =  json.getString("result");
						joinNum = json.getInt("num");
						
						Log.i("Awooseong", "JSonArray size :" + results.length());
						
						if(results.equals("") || results == null || results.equals("null")){
							Log.i("Awooseong", "Theme info has nothing from server");
							
						}else{
						
							String[] value = RoarFragment.replaceAll(results,"%#$3%",";").split(";");
							
							for( int j = 0; j < value.length; j++){
								String temp = RoarFragment.replaceAll(value[j],"%3#$%","/");
								String[] realValue = temp.split("/");
								
								joinTemp = new Awooseong_Var_In_Join();
								
								joinTemp.onlyNum = realValue[0];
								joinTemp.id = realValue[1];
								joinTemp.pictureData = realValue[2];
								joinTemp.date = realValue[3];
								joinTemp.joinNum = Integer.toString(joinNum);
								Log.i("Dialog_join, private", joinTemp.pictureData);
								joinArray.add(joinTemp);
								// 휴대폰 사용자가 이미 join을 했는지 안했는지를 check
								if(joinTemp.id.equals(BasicInfo.USER_ID) && !isMaster){
									isDelete = true;
									date = joinTemp.onlyNum;
									join.setText("CANCEL");
									
								}
							}
							Log.i("Dialog_Join", "just finished getting data from server");
						
						// 글의 joinNum를 변경
						if( upgradeJoinNum instanceof Awooseong_EachRoomInfoInScrollInBody){
							Awooseong_EachRoomInfoInScrollInBody A = (Awooseong_EachRoomInfoInScrollInBody)upgradeJoinNum;
							A.setUpgradeJoinNum(Integer.toString(joinNum));
						}
						/*
						else if( upgradeJoinNum instanceof EchoRoom){
							EchoRoom A = (EchoRoom)upgradeJoinNum;
							A.setUpgradeJoinNum(Integer.toString(joinNum));
						}*/
						
						//upgradeJoinNum.setUpgradeJoinNum(joinTemp.joinNum);
						
						addJoinViewInScroll();
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
			if (!isSet && !isMaster) // join, close 버튼을 동시에 보여준다. join 인지
				// cancel인지 확인 후
				{
					join.setVisibility(View.VISIBLE);
					close.setVisibility(View.VISIBLE);
					isSet = true;
					DialogJoinHandler.sendEmptyMessage(0);
				}
			else if(!isSet && isMaster){
				close.setVisibility(View.VISIBLE);
				isSet = true;
				DialogJoinHandler.sendEmptyMessage(0);
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
	
	private void addJoinViewInScroll(){
		
		for( int i = 0; i < joinArray.size(); i++){
			
			each = new Awooseong_Join_EachPersonLayout(awooseong, joinArray.get(i));
			
			recycleImage.add(each);
			layoutLinearInScroll.addView(each.getLayout());		
		}
		joinArray.clear();
	}
	
	private void recycleBitmapImage(){
		
		layoutLinearInScroll.removeAllViews();
		
		for( int i = 0; i < recycleImage.size(); i ++ )
			recycleImage.get(i).recycleImage();
		recycleImage.clear();
	}
	
	public void onBackPressed(){
		recycleBitmapImage();
		Log.i("Dialog_Join", "Pics are recycled");
		super.onBackPressed();
	}
	
	@Override
	public void onClick(View v) {

		if (v == join) {
			Log.i("Dialog_Join", "join btn in Join btn clicked");
			// upload data to server
			// receive new data from server
			if(join.getText().equals("JOIN"))
			{
				is_update="true";
				is_delete="false";
				join.setVisibility(View.INVISIBLE);
				close.setVisibility(View.INVISIBLE);
				join.setText("CANCEL");
				isSet = false;
				recycleBitmapImage();
				getDataFromServer();
				
			}
			else
			{
				is_update="true";
				is_delete="true";
				join.setVisibility(View.INVISIBLE);
				close.setVisibility(View.INVISIBLE);
				join.setText("JOIN");
				isSet = false;
				
				if(joinTemp.joinNum.equals("1")){
					if( upgradeJoinNum instanceof Awooseong_EachRoomInfoInScrollInBody){
						Awooseong_EachRoomInfoInScrollInBody A = (Awooseong_EachRoomInfoInScrollInBody)upgradeJoinNum;
						A.setUpgradeJoinNum("0");
					}
					/*
					else if( upgradeJoinNum instanceof EchoRoom){
						EchoRoom A = (EchoRoom)upgradeJoinNum;
						A.setUpgradeJoinNum("0");
					}*/
				}
				getDataFromServer();
				recycleBitmapImage();
			}
		}
		// recycle Bitmap
		else{
			
			recycleBitmapImage();
			dismiss();
		}	
	}
}
