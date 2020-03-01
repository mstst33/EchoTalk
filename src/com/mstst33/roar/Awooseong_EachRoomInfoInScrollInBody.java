package com.mstst33.roar;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.mstst33.echoproject.R;
import com.mstst33.echoproject.URLBitmapDownload;

public class Awooseong_EachRoomInfoInScrollInBody implements View.OnClickListener, JoinOrCommentAble {
	
	private LinearLayout linear_roomInScrollInLinear;
	private LinearLayout myInfoLayout;
	private LinearLayout joinLayout;
	private ImageView myInfoMyPic;
	private ImageView myInfoReportPic;
	private TextView myInfoMyName;
	private TextView myInfoDate;
	
	private LinearLayout linear;
	private LinearLayout textLinear;
	private LinearLayout twoButtonLinear;
	private TextView writingContent;
	private ImageView image;
	private Context awooseong;

	private int theme;
	private TextView comment;
	private TextView commentLike;
	private TextView join;
	private TextView joinLike;
		
	private Awooseong_ThemeRoom info;
	private boolean isComment;
	private String userId;
	private String writingNum;
	
	private Awooseong_Dialog_Join dialog_join;
	private Awooseong_Dialog_Comment dialog_reply;
	private Awooseong_Dialog_Image dialog_image;
	private Awooseong_Dialog_StatusOfRoom dialog_commentForRoom;
	private Awooseong_Dialog_EachPersonInfo dialog_eachPerson;
	private Awooseong_Dialog_Report dialog_report;
	private Awooseong_Dialog_ThemeContent dialog_themeContent;
	public Awooseong_EachRoomInfoInScrollInBody(Context context, Awooseong_ThemeRoom info){
		
		Log.i("EachRoom","start");

		theme = info.getThemeNumber();
		this.info = info;
		isComment = false;
		// 1. daily, 2. exercise, 3.hobby
		// 4. job    5. question  6.study
		// 7. travel 8. used      9.check
		Log.i("EachRoom","theme is " + theme);
		awooseong = context;
		
		linear_roomInScrollInLinear = (LinearLayout) View.inflate(awooseong,R.layout.awooseong_body_scroll_room_linear, null);
		
		// my info
		myInfoLayout = (LinearLayout) View.inflate(awooseong,R.layout.awooseong_my_info_in_scrollview, null);
		myInfoMyPic = (ImageView) myInfoLayout.findViewById(R.id.awooseong_scrollMyInfoMainPic);
		myInfoReportPic = (ImageView) myInfoLayout.findViewById(R.id.awooseong_scrollMyInfoReportPic);
		myInfoMyName = (TextView) myInfoLayout.findViewById(R.id.awooseong_scrollMyInfoName);
		myInfoDate = (TextView) myInfoLayout.findViewById(R.id.awooseong_scrollMyInfoDate);

		twoButtonLinear = (LinearLayout) View.inflate(awooseong,R.layout.awooseong_two_button_in_everytheme, null);
		textLinear = (LinearLayout) View.inflate(awooseong,R.layout.awooseong_writing_content_in_everytheme, null);
		image = (ImageView) View.inflate(awooseong,R.layout.awooseong_body_scrollview_image, null);
		writingContent = (TextView)textLinear.findViewById(R.id.awooseong_writing_content_in_everytheme);
		
		commentLike = (TextView)twoButtonLinear.findViewById(R.id.awooseong_twoBtn_CommentLikeView);
		joinLike = (TextView)twoButtonLinear.findViewById(R.id.awooseong_twoBtn_JoinLikeView);
		comment = (TextView)twoButtonLinear.findViewById(R.id.awooseong_twoBtn_CommnetTextView);
		join = (TextView)twoButtonLinear.findViewById(R.id.awooseong_twoBtn_JoinTextView);
		joinLayout = (LinearLayout)twoButtonLinear.findViewById(R.id.daily_join_invisible);
		
		Log.i("EachRoom","my info inflated");
		
		switch(theme){
		
		case Awooseong_Theme.DAILY:
			//daily
			Awooseong_Room_Var_Daily A;
			if (info instanceof Awooseong_Room_Var_Daily) {
				A = (Awooseong_Room_Var_Daily) info;
			}
			else
				return;
			
			Awooseong_Room_Info_Daily dailyInfo = new Awooseong_Room_Info_Daily(awooseong, A);
			linear = dailyInfo.getLinearLayout();
	
			setCommentNum(A.daily_echoNum);
			setRoomComment(A.daily_content);
			setMyInfoName(A.daily_userId);
			setMyInfoDate(A.daily_date);
			setMyInfoMyPic(A.daily_userPic);
			setImage(A.daily_pictureData);

			userId = A.daily_userId;
			writingNum = A.daily_writingNum;
			joinLayout.setVisibility(View.INVISIBLE);
			break;
			
		case Awooseong_Theme.EXERCISE:
			// exercise
			Awooseong_Room_Var_Exercise exer;
			if (info instanceof Awooseong_Room_Var_Exercise) {
				exer = (Awooseong_Room_Var_Exercise) info;
			}
			else
				return;
			
			Awooseong_Room_Info_Exercise exerciseInfo = new Awooseong_Room_Info_Exercise(awooseong, exer);
			linear = exerciseInfo.getLinearLayout();
			
			setCommentNum(exer.exercise_echoNum);
			setJoinNum(exer.exercise_joinNum);
			setRoomComment(exer.exercise_content);
			setMyInfoName(exer.exercise_userId);
			setMyInfoDate(exer.exercise_date);
			setMyInfoMyPic(exer.exercise_userPic);
			setImage(exer.exercise_pictureData);
			
			writingNum = exer.exercise_writingNum;
			userId = exer.exercise_userId;
			break;
			
		case Awooseong_Theme.HOBBY:
			//hobby
			Awooseong_Room_Var_Hobby hob;
			if (info instanceof Awooseong_Room_Var_Hobby) {
				hob = (Awooseong_Room_Var_Hobby)info;
			}
			else
				return;
			
			Awooseong_Room_Info_Hobby hobbyInfo = new Awooseong_Room_Info_Hobby(awooseong, hob);
			linear = hobbyInfo.getLinearLayout();
			
			setCommentNum(hob.hobby_echoNum);
			setJoinNum(hob.hobby_joinNum);
			setRoomComment(hob.hobby_content);
			setMyInfoName(hob.hobby_userId);
			setMyInfoDate(hob.hobby_date);
			setMyInfoMyPic(hob.hobby_userPic);
			setImage(hob.hobby_pictureData);
			
			userId = hob.hobby_userId;
			writingNum = hob.hobby_writingNum;
			break;
			
		case Awooseong_Theme.JOB:
			
			Awooseong_Room_Var_Job jo;
			if (info instanceof Awooseong_Room_Var_Job) {
				jo = (Awooseong_Room_Var_Job)info;
			}
			else
				return;
			
			Awooseong_Room_Info_Job jobInfo = new Awooseong_Room_Info_Job(awooseong, jo);
			linear = jobInfo.getLinearLayout();
			
			setJoinNum(jo.job_joinNum);
			setCommentNum(jo.job_echoNum);
			setRoomComment(jo.job_content);
			setMyInfoName(jo.job_userId);
			setMyInfoDate(jo.job_date);
			setMyInfoMyPic(jo.job_userPic);
			setImage(jo.job_pictureData);
			
			userId = jo.job_userId;
			writingNum = jo.job_writingNum;
			break;
			
		case Awooseong_Theme.QUESTION:
			//question
			Awooseong_Room_Var_Question qe;
			if (info instanceof Awooseong_Room_Var_Question) {
				qe = (Awooseong_Room_Var_Question)info;
			}
			else
				return;
			
			Awooseong_Room_Info_Question questionInfo = new Awooseong_Room_Info_Question(awooseong, qe);
			linear = questionInfo.getLinearLayout();
			
			setCommentNum(qe.question_echoNum);
			setRoomComment(qe.question_content);
			setMyInfoName(qe.question_userId);
			setMyInfoDate(qe.question_date);
			setMyInfoMyPic(qe.question_userPic);
			setImage(qe.question_pictureData);
			
			userId = qe.question_userId;
			writingNum = qe.question_writingNum;
			joinLayout.setVisibility(View.INVISIBLE);
			break;
			
		case Awooseong_Theme.STUDY:
			// study
			Awooseong_Room_Var_Study stu;
			if (info instanceof Awooseong_Room_Var_Study) {
				stu = (Awooseong_Room_Var_Study)info;
			}
			else
				return;
			
			Awooseong_Room_Info_Study studyInfo = new Awooseong_Room_Info_Study(awooseong, stu);
			linear = studyInfo.getLinearLayout();
			
			setCommentNum(stu.study_echoNum);
			setJoinNum(stu.study_joinNum);
			setRoomComment(stu.study_content);
			setMyInfoName(stu.study_userId);
			setMyInfoDate(stu.study_date);
			setMyInfoMyPic(stu.study_userPic);
			setImage(stu.study_pictureData);
			
			userId = stu.study_userId;
			writingNum = stu.study_writingNum;
			break;
			
		case Awooseong_Theme.TRAVEL:
			Awooseong_Room_Var_Travel tr;
			if (info instanceof Awooseong_Room_Var_Travel) {
				tr = (Awooseong_Room_Var_Travel)info;
			}
			else
				return;
			
			Awooseong_Room_Info_Travel travelInfo = new Awooseong_Room_Info_Travel(awooseong, tr);
			linear = travelInfo.getLinearLayout();
			
			setCommentNum(tr.travel_echoNum);
			setJoinNum(tr.travel_joinNum);
			setRoomComment(tr.travel_content);
			setMyInfoName(tr.travel_userId);
			setMyInfoDate(tr.travel_date);
			setMyInfoMyPic(tr.travel_userPic);
			setImage(tr.travel_pictureData);
			
			userId = tr.travel_userId;
			writingNum = tr.travel_writingNum;
			break;
			
		case Awooseong_Theme.USED:
			Awooseong_Room_Var_Used us;
			if (info instanceof Awooseong_Room_Var_Used) {
				us = (Awooseong_Room_Var_Used)info;
			}
			else
				return;
			
			Awooseong_Room_Info_Used usedInfo = new Awooseong_Room_Info_Used(awooseong, us);
			linear = usedInfo.getLinearLayout();
			
			setCommentNum(us.used_echoNum);
			setJoinNum(us.used_joinNum);
			setRoomComment(us.used_content);
			setMyInfoName(us.used_userId);
			setMyInfoDate(us.used_date);
			setMyInfoMyPic(us.used_userPic);
			setImage(us.used_pictureData);
			
			userId = us.used_userId;
			writingNum = us.used_writingNum;
			break;
		}
	
		myInfoReportPic.setOnClickListener(this);
		myInfoMyName.setOnClickListener(this);
		myInfoMyPic.setOnClickListener(this);
		textLinear.setOnClickListener(this);
		comment.setOnClickListener(this);
		linear.setOnClickListener(this);
		join.setOnClickListener(this);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		WindowManager wm = (WindowManager) awooseong.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		
		if(image != null)
			image.setOnClickListener(this);		
		
		linear_roomInScrollInLinear.addView(myInfoLayout);
		
		if( theme != Awooseong_Theme.DAILY)
			linear_roomInScrollInLinear.addView(linear);

		if (image != null)
			linear_roomInScrollInLinear.addView(image);
		
		if( isComment )
			linear_roomInScrollInLinear.addView(textLinear);
		linear_roomInScrollInLinear.addView(twoButtonLinear);
		linear_roomInScrollInLinear.setPadding(5, 5, 5, 5);
	}
	
	public LinearLayout getWholeRoom(){
		return linear_roomInScrollInLinear;
	}
	
	public void setJoinNum(String joinNum){
		
		joinLike.setText(joinNum);
	
	}
	
	public void setCommentNum(String commentNum){
		commentLike.setText(commentNum);
	}
		
	// myInfo set
	public void setMyInfoName(String newName){
		myInfoMyName.setText(newName);
	}
	public void setMyInfoDate(String newDate){
		myInfoDate.setText(newDate);
	}
	public void setMyInfoMyPic(String newImage){
		
		if (newImage.length() < 2 || newImage.toString() == "null") {
			Log.i("eachRoomInfo", "image is null");
		
		} else {
			new URLBitmapDownload(newImage, myInfoMyPic, false, true, null).start();
			Log.i("eachRoomInfo", "myInfoMyPic is downloading");
		}
	}

	void setRoomComment(String newComment){
		// 이전에 서버로부터 값을 받아 string으로 보낸다
		if( newComment == "null" || newComment.length() == 0)
			{
				isComment = false;
				return;
			}
		writingContent.setText(newComment);
		isComment = true;
		Log.i("setRoomComment", newComment);
	}
	
	void setImage(String newImage){
		// uri 를 받아 이미지를 준다
		
		if(newImage.length() < 2 || newImage.toString() == "null")
			{
				image = null;
				Log.i("eachRoomInfo", "image is null");
				return;
			}
		else{
				new URLBitmapDownload(newImage, image, false, false, null).start();
				Log.i("eachRoomInfo_Image", newImage);
				Log.i("eachRoomInfo", "image is downloading");
		}
	}
	
	public ImageView getBodyImage(){
		return image;
	}
	
	public ImageView getMainPicImage(){
		return myInfoMyPic;
	}
	
	public void recycleBitmapAndEverything(){
		if( image != null )
			{
				URLBitmapDownload.recycleBitmap(image);
			}
		
		if( myInfoMyPic != null)
			{
				URLBitmapDownload.recycleBitmap(myInfoMyPic);
			}
		
		if( myInfoReportPic != null)
			{
				URLBitmapDownload.recycleBitmap(myInfoReportPic);
			}
	}

	public void setUpgradeJoinNum(String newNum){
		
		joinLike.setVisibility(View.INVISIBLE);
		joinLike.setText(newNum);
		joinLike.setVisibility(View.VISIBLE);
	}
	
	public void setUpgradeCommentNum(String newNum){
		commentLike.setVisibility(View.INVISIBLE);
		commentLike.setText(newNum);
		commentLike.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v == linear){
			Log.i("eachRoomInfo","dialog_themeContent");
			
			dialog_themeContent = new Awooseong_Dialog_ThemeContent(awooseong, info);
			dialog_themeContent.show();
			
		}
		else if( v == myInfoReportPic ){
			Log.i("eachRoomInfo","myinfo reportdialog");
			
			dialog_report = new Awooseong_Dialog_Report(awooseong, userId, writingNum);
			dialog_report.show();
		}
		else if( v == myInfoMyName || v == myInfoMyPic){
			Log.i("eachRoomInfo","myinfo dialog");
			
			dialog_eachPerson = new Awooseong_Dialog_EachPersonInfo(awooseong, userId);
			dialog_eachPerson.show();
			
		}
		else if( v == textLinear){
			Log.i("eachRoomInfo","dialog_commentForRoom");
			if( writingContent.getWidth() >= textLinear.getWidth()*0.9 ){
				dialog_commentForRoom = new Awooseong_Dialog_StatusOfRoom(awooseong, theme, info);
				dialog_commentForRoom.show();
			}
			
		}
		else if( v == image){
			//throw fileName of the image you wanna get 
			
			dialog_image = new Awooseong_Dialog_Image(awooseong, theme, info);
			dialog_image.show();
		}
		else if( v == comment){
			// Server에서 우선 현재까지 올라온 reply를 업데이트를 해야한다.
			
			dialog_reply = new Awooseong_Dialog_Comment(awooseong, this, info);
			
			WindowManager wm = (WindowManager) awooseong.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			int height = display.getHeight();
			
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			lp.copyFrom(dialog_reply.getWindow().getAttributes());
			lp.width = WindowManager.LayoutParams.FILL_PARENT;
			lp.height =(int)(height * 0.7);
			dialog_reply.show();
			dialog_reply.getWindow().setAttributes(lp);
		}
		
		else if( v == join){
			Log.i("eachRoomInfo", "dialog_join");
			
			dialog_join = new Awooseong_Dialog_Join(awooseong, this, info);
			
			WindowManager wm = (WindowManager) awooseong.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			int height = (int)(display.getHeight() * 0.7);
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			lp.copyFrom(dialog_join.getWindow().getAttributes());
			lp.width = WindowManager.LayoutParams.FILL_PARENT;
			lp.height = height;
			
			dialog_join.show();
			dialog_join.getWindow().setAttributes(lp);
		
		}
		else{
			Log.i("eachRoomInfo","dialog_image");		
		}
	}
}
