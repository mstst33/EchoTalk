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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mstst33.echoproject.BasicInfo;
import com.mstst33.echoproject.R;
import com.mstst33.echoproject.RestClient;
import com.mstst33.echoproject.RoarFragment;
import com.mstst33.echoproject.TodayDate;

public class Awooseong_Dialog_Comment extends Dialog implements View.OnClickListener{

	private Context awooseong;
	
	private String REQUEST_DATA_URL = BasicInfo.SERVER_ADDRESS + "request_comment_data.php";
	private static final int COMMENT_GET_DATA = 0;
	private int commentState;
	
	private RelativeLayout layoutRelativeBackground;
	private LinearLayout layoutBackground;
	private LinearLayout layoutLinearInScroll;
	private LinearLayout directLayoutLinearInScroll;
	private LinearLayout layoutEditText;
	
	private ScrollView scrollView;
	
	private EditText editTextReplyContent;
	private ImageView imageMainPicInRelative;
	private TextView textNameInRelative;
	private TextView textReplyInRelative;
	
	private Button editTextSendBtn;
	private Button additionalBtn;
	
	private String commentContent = "";
	
	private Awooseong_Comment_EachPersonLayout each;
	private RelativeLayout.LayoutParams RelativeLp;
	private RelativeLayout.LayoutParams linearEditTextLp;
	private RelativeLayout.LayoutParams frameLp;
	private RelativeLayout.LayoutParams btnLp;
	
	private String writingNum;
	private String selectedTheme;
	private String date;
	private String userId;
	private String is_update = "false";
	private String is_delete = "false";
	private String milliDel;//is connected to wirtingNum
	private String deleteCount;
	private String lastOne;
	private String commentNum;
	//private int commentNum;
	private int countCall;
	private TodayDate time;
	
	private ArrayList<Awooseong_Var_In_Comment>commentArray;
	private Awooseong_Var_In_Comment commentTemp;
	
	private Awooseong_ThemeRoom info;
	private JoinOrCommentAble upgradeCommentNum;
	private ArrayList<Awooseong_Comment_EachPersonLayout>recycleImage;
	private int theme;
	private boolean isSet;
	private boolean isFirst;
	private boolean isDelete;
	private boolean isAdditional;
	private boolean isAddShow;
	
	private FrameLayout frameBase;
	private LinearLayout progress;
	//public int comment_num;
	
	public Awooseong_Dialog_Comment(Context context, JoinOrCommentAble eachRoom, Awooseong_ThemeRoom roomInfo) {
		
		super(context);
		isSet = false;
		isAddShow = false;
		isFirst = true;
		isDelete = false;
		isAdditional = false;
		
		countCall = 0;
		awooseong = context;
		upgradeCommentNum = eachRoom;
		is_update = "false";
		this.info = roomInfo;
		this.theme = roomInfo.getThemeNumber();
		commentArray = new ArrayList<Awooseong_Var_In_Comment>();
		recycleImage = new ArrayList<Awooseong_Comment_EachPersonLayout>();
		
		Log.i("Dialog_Comment", "reply dialog start");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		frameBase = (FrameLayout)View.inflate(awooseong, R.layout.echo_framelayout, null);
		progress = (LinearLayout)View.inflate(awooseong, R.layout.echo_progress, null);		
		
		layoutRelativeBackground = (RelativeLayout)View.inflate(awooseong, R.layout.awooseong_reply_join_relativelayout_whole_background, null);
		layoutBackground = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_reply_join_backgroud_linearlayout, null);
		layoutLinearInScroll = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_reply_join_linearlayout_in_scrollview, null);
		directLayoutLinearInScroll = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_reply_join_linearlayout_in_scrollview, null);
		layoutEditText = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_reply_edittext_layout, null);
		scrollView = (ScrollView)View.inflate(awooseong, R.layout.awooseong_reply_join_scrollview, null);
		additionalBtn = (Button)View.inflate(context, R.layout.awooseong_comment_additional_button, null);
		
		// reply send Button을 등록한다.
		editTextSendBtn = (Button)layoutEditText.findViewById(R.id.awooseong_reply_eidt_sendBtn);
		editTextReplyContent = (EditText)layoutEditText.findViewById(R.id.awooseong_reply_editText);
		
		editTextSendBtn.setOnClickListener(this);
		
		switch(theme){
		case Awooseong_Theme.DAILY:
			Awooseong_Room_Var_Daily dr;
			if( info instanceof Awooseong_Room_Var_Daily){
				dr = (Awooseong_Room_Var_Daily)info;
			}else
				return;
			writingNum = dr.daily_writingNum;
			userId = dr.daily_userId;
			selectedTheme = "0";
			break;
			
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
		
		// layout_linearInScroll에 Each person reply layout이 매번 등록 될 것이다.
		// 이는 send Button Click 시 진행
		scrollView.addView(layoutLinearInScroll);
		layoutLinearInScroll.addView(directLayoutLinearInScroll);
				
		// 전체적인 틀을 맞춘다.
		WindowManager wm = (WindowManager) awooseong.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		RelativeLp = new RelativeLayout.LayoutParams(display.getWidth(), (int)(display.getHeight()));		
		layoutRelativeBackground.setLayoutParams(RelativeLp);
		
		linearEditTextLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		linearEditTextLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		linearEditTextLp.height=(int)(display.getHeight()*0.08);
		layoutEditText.setLayoutParams(linearEditTextLp);

		// ScrollView를 LinearLayout에 추가 한후 이를 전체적인 View인 Relative Layout에 추가하여
		// ScrollView가 전체 중 일정부분만 차지 할 수 있도록 설정한다.
		frameLp = new RelativeLayout.LayoutParams(display.getWidth(), display.getHeight());
		frameLp.height=(int)(display.getHeight()*0.55);
		frameBase.setLayoutParams(frameLp);
		
		frameBase.addView(progress);
		frameBase.addView(scrollView);
		layoutBackground.addView(frameBase);
			
		layoutRelativeBackground.addView(layoutEditText);
		layoutRelativeBackground.addView(layoutBackground);
		
		scrollView.setVisibility(View.INVISIBLE);
		
		setContentView(layoutRelativeBackground);
		
		editTextReplyContent.setVisibility(View.INVISIBLE);
		editTextSendBtn.setVisibility(View.INVISIBLE);
		
		getDataFromServer();
		// TODO Auto-generated constructor stub
	}

	public Handler DialogCommentHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0:
				progress.setVisibility(View.INVISIBLE);
				scrollView.setVisibility(View.VISIBLE);
				break;
			case 1: //delete
				commentTemp = (Awooseong_Var_In_Comment)msg.obj;
				milliDel = commentTemp.onlyNum;
				deleteCount = commentTemp.additionalCount;
				//isAdditional = true;
				isDelete = true;
				is_update = "false";
				is_delete = "true";
				
				if(lastOne.equals("1")){
					if( upgradeCommentNum instanceof Awooseong_EachRoomInfoInScrollInBody){
						Awooseong_EachRoomInfoInScrollInBody A = (Awooseong_EachRoomInfoInScrollInBody)upgradeCommentNum;
						A.setUpgradeCommentNum("0");
					}
					/*
					else if( upgradeCommentNum instanceof EchoRoom){
						EchoRoom A = (EchoRoom)upgradeCommentNum;
						A.setUpgradeCommentNum("0");
					}*/
					directLayoutLinearInScroll.removeAllViews();
					directLayoutLinearInScroll.invalidate();
				}
				getDataFromServer();
				break;
			
			case 2:
				countCall++;
				if(!isAddShow)
				{	isAddShow = true; // 더보기가 보여지고 있다.
					layoutLinearInScroll.addView(additionalBtn, 0);
					layoutLinearInScroll.invalidate();
				}
				break;
			case 3:
				if(isAddShow){
					isAddShow = false;
					layoutLinearInScroll.removeView(additionalBtn);
					layoutLinearInScroll.invalidate();
					break;
				}
			}
		}
	};
	
	private void getDataFromServer(){
		
		Log.i("Dialog_Comment", "getDataFromServer() is working");		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("theme", selectedTheme));
		nameValuePairs.add(new BasicNameValuePair("user_id", BasicInfo.USER_ID));
		nameValuePairs.add(new BasicNameValuePair("writing_num", writingNum));
		nameValuePairs.add(new BasicNameValuePair("date", String.valueOf(time.getDate() + " " + time.getTime())));
		
		
		nameValuePairs.add(new BasicNameValuePair("time", String.valueOf(time.getMilliSecond())));
		nameValuePairs.add(new BasicNameValuePair("is_update", is_update));
		nameValuePairs.add(new BasicNameValuePair("is_delete", is_delete));
		nameValuePairs.add(new BasicNameValuePair("comment", commentContent));
		
		if(!isDelete){
			if(!isAdditional){
				nameValuePairs.add(new BasicNameValuePair("milli_second", "0"));
			}
			else{ // isAdditonal
				nameValuePairs.add(new BasicNameValuePair("milli_second", Integer.toString(countCall)));
			}
			nameValuePairs.add(new BasicNameValuePair("milli_del", "0"));
		}
		else{ // isDelete
			nameValuePairs.add(new BasicNameValuePair("milli_del", milliDel));
			nameValuePairs.add(new BasicNameValuePair("milli_second", deleteCount));
		}
		
		Log.i("Dialog_Comment", selectedTheme +" "+ BasicInfo.USER_ID +" "+ writingNum +" "+ is_update +" "+ is_delete +" "+ commentContent+" "+ BasicInfo.USER_PHOTO);
		Log.d("Dialog_Comment", date + is_update + selectedTheme + writingNum);
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
		
			switch (commentState) {
			case COMMENT_GET_DATA:
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
			
			if(isDelete){
				is_delete="false";
			}
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			Log.i("Dialog_Comment", "onPostExecute is woring");

			if (result == null)
				{
					Log.i("Dialog_Comment","result from server is null");
					return;
				}
			
			switch (commentState) {
			case COMMENT_GET_DATA:
				
				Log.i("Dialog_Comment", result.toString());
				
				if (result){

					String results;
					try {
						
						Log.i("Dialog_Comment", "Join get data is trying");
						results =  json.getString("result");
						commentNum = json.getString("num");
						
						
						Log.i("Dialog_Comment_commentNum", commentNum);
						Log.i("Dialog_Comment", "JSonArray size :" + results.length());
						
						if(results.equals("") || results == null || results.equals("null")){
							Log.i("Dialog_Comment", "Theme info has nothing from server");
						}
						else{
							String temp = RoarFragment.replaceAll(results,"%#$3%",";");
							Log.i("Dialog_Comment ; replace", temp);
							String[] value = temp.split(";");
						
							for( int j = 0; j < value.length; j++){
								String temp2 = RoarFragment.replaceAll(value[j], "%3#$%", "/");
								Log.i("Dialog_Comment / replace", temp2);
								String[] realValue = temp2.split("/");
								
								commentTemp = new Awooseong_Var_In_Comment();
								
								commentTemp.onlyNum = realValue[0];
								commentTemp.id = realValue[1];
								commentTemp.pictureData = realValue[2];
								commentTemp.comment=realValue[3];
								commentTemp.date = realValue[4];
								
								commentTemp.additionalCount = Integer.toString(countCall);
								commentTemp.commentNum = commentNum;
								
								lastOne = commentTemp.commentNum;
								commentArray.add(commentTemp);
								
								if( upgradeCommentNum instanceof Awooseong_EachRoomInfoInScrollInBody){
									Awooseong_EachRoomInfoInScrollInBody A = (Awooseong_EachRoomInfoInScrollInBody)upgradeCommentNum;
									A.setUpgradeCommentNum(commentNum);
								}
								/*
								else if( upgradeCommentNum instanceof EchoRoom){
									EchoRoom A = (EchoRoom)upgradeCommentNum;
									A.setUpgradeCommentNum(commentNum);
								}*/
							}							
							Log.i("Dialog_Comment", "just finished getting data from server");
							
						//upgradeCommentNum.setUpgradeCommentNum(commentTemp.commentNum)
						
						addCommentViewInScroll();
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					Log.d("Dialog_Comment", "Getting writing succeeded");
				}
				else{
					Log.d("Dialog_Comment", "Getting writing failed");
				}
				break;
			}
			Log.i("Dialog_Comment","call putInfoIntoScroll");
			
			if(!isSet){
				editTextReplyContent.setVisibility(View.VISIBLE);
				editTextSendBtn.setVisibility(View.VISIBLE);
				isSet = true;
				DialogCommentHandler.sendEmptyMessage(0);
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
	
	private void addCommentViewInScroll(){
		
		if(isDelete)
		{
			Log.i("Dialog_Comment","layoutLInearInScroll is clear");
			isDelete = false;
			directLayoutLinearInScroll.removeAllViews();
		}
		
		for( int i = 0; i < commentArray.size(); i++){
			
			each = new Awooseong_Comment_EachPersonLayout(awooseong, commentArray.get(i), DialogCommentHandler);
			
			recycleImage.add(each);
			directLayoutLinearInScroll.addView(each.getLayout());	
		}
		
		if(!isAdditional){
			new Handler().postDelayed(new Runnable() {
				public void run() {
					Log.i("Dialog_Comment", "scroll run");
					scrollView.fullScroll(View.FOCUS_DOWN);
					scrollView.invalidate(); // repaint redraw
					/*
					if( Integer.parseInt(commentNum) > (10*countCall + 9) ){
						DialogCommentHandler.sendEmptyMessage(2);
					}
					else
					{
						DialogCommentHandler.sendEmptyMessage(3);
					}
					*/
					commentArray.clear();
				}
			}, 100);
		}
	}
	
	private void recycleBitmapImage(){
		
		directLayoutLinearInScroll.removeAllViews();
		
		for( int i = 0; i < recycleImage.size(); i ++ )
			recycleImage.get(i).recycleImage();
		recycleImage.clear();
	}

	public void onBackPressed(){
		recycleBitmapImage();
		Log.i("Dialog_Comment", "Images are recycled");
		super.onBackPressed();
	}
	
	@Override
	public void onClick(View v) {
		// 서버로 값을 보낸다
		// 객체에 content 값에 값을 넣는다.
		// 서버에서 갑을 받아온다.
		if( v == editTextSendBtn){
			if(editTextReplyContent.getText().toString().equals("")){
				Log.i("Dialog_Comment","nothing with Comment");
				Toast.makeText(awooseong, "댓글을 달아주세요", 0).show();
			}

			else {
				Log.i("Dialog_Comment","give a comment");
				is_update = "true";
				isSet = true;
				commentContent = editTextReplyContent.getText().toString();
				editTextReplyContent.setText("");
				getDataFromServer();
				recycleBitmapImage();
			}
		}
		else if( v == additionalBtn ){
			isAdditional = true;
			getDataFromServer();
		}
		else{
			
			recycleBitmapImage();
			dismiss();
		}
	}
}
