package com.mstst33.roar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mstst33.echoproject.BasicInfo;
import com.mstst33.echoproject.R;
import com.mstst33.echoproject.RoarFragment;

public class Awooseong_Dialog_CategoryOfTheme extends Dialog implements View.OnClickListener {

	private Context awooseong;
	private Awooseong_Theme theme;
	
	private boolean[] btn;
	
	private ImageView btn_daily;
	private ImageView btn_travel;
	private ImageView btn_exercise;
	private ImageView btn_hobby;
	private ImageView btn_study;
	private ImageView btn_question;
	private ImageView btn_job;
	private ImageView btn_used;
	private ImageView btn_all;
	private Button btn_check;
	private StringBuffer st;
	private LinearLayout linear;
	private LinearLayout.LayoutParams linearLp;
	private Awooseong_Categoriable mainView;
	private boolean isClicked;
	
	public Awooseong_Dialog_CategoryOfTheme(Context context, Awooseong_Categoriable mainView) {
		super(context);
		this.mainView = mainView;
		// TODO Auto-generated constructor stub
		awooseong = context;
		isClicked = false;
		btn = new boolean[9];
		for(int i = 0; i < btn.length; ++i)
			btn[i] = true;
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // 바탕을 투명하게 해준다.
		theme = new Awooseong_Theme();

		linear = (LinearLayout)View.inflate(context, R.layout.awooseong_choose_theme_category_of_nine_theme, null);
		setContentView(linear);
		
		btn_daily = (ImageView) linear.findViewById(R.id.category_of_nine_btnTheme1);
		btn_travel = (ImageView) linear.findViewById(R.id.category_of_nine_btnTheme2);
		btn_exercise = (ImageView) linear.findViewById(R.id.category_of_nine_btnTheme3);
		btn_hobby = (ImageView) linear.findViewById(R.id.category_of_nine_btnTheme4);
		btn_study = (ImageView) linear.findViewById(R.id.category_of_nine_btnTheme5);
		btn_question = (ImageView) linear.findViewById(R.id.category_of_nine_btnTheme6);
		btn_job = (ImageView) linear.findViewById(R.id.category_of_nine_btnTheme7);
		btn_used = (ImageView) linear.findViewById(R.id.category_of_nine_btnTheme8);
		btn_all = (ImageView) linear.findViewById(R.id.category_of_nine_btnTheme9);
		btn_check = (Button) linear.findViewById(R.id.category_of_nine_btnCheck);

		btn_daily.setOnClickListener(this);
		btn_travel.setOnClickListener(this);
		btn_exercise.setOnClickListener(this);
		btn_hobby.setOnClickListener(this);
		btn_study.setOnClickListener(this);
		btn_question.setOnClickListener(this);
		btn_job.setOnClickListener(this);
		btn_used.setOnClickListener(this);
		btn_all.setOnClickListener(this);
		btn_check.setOnClickListener(this);
		
		checkInterestedTheme();
		
		if( mainView instanceof Awooseong_Dialog_EachPersonInfo){
			checkIsClickedMyInfo();
		}
				
	}
	
	private void checkIsClickedMyInfo() {
		
		btn_daily.setEnabled(false);
		btn_travel.setEnabled(false);
		btn_exercise.setEnabled(false);
		btn_hobby.setEnabled(false);
		btn_study.setEnabled(false);
		btn_question.setEnabled(false);
		btn_job.setEnabled(false);
		btn_used.setEnabled(false);
		btn_all.setVisibility(View.GONE);
		btn_check.setVisibility(View.GONE);
		
	}
	
	private void checkInterestedTheme(){
		
		String interestedTheme = BasicInfo.INTERESTED_THEME;
		if( mainView instanceof Awooseong_Dialog_EachPersonInfo){
			Awooseong_Dialog_EachPersonInfo A = (Awooseong_Dialog_EachPersonInfo)mainView;
			interestedTheme = A.getInteretedTheme();
		}
		
		
		if(interestedTheme.equals("00000000"))
			return;
		else
			{
				if( interestedTheme.charAt(0) == '1' )
					{
						btn_daily.setImageResource(R.drawable.category_daily_btn_reverse);
						btn[0] = false;
						theme.setDaily(true);
					}
				
				if( interestedTheme.charAt(1) == '1' )
				{
					btn_travel.setImageResource(R.drawable.category_travel_btn_reverse);
					btn[1] = false;
					theme.setTravel(true);
				}
				
				if( interestedTheme.charAt(2) == '1' )
				{
					btn_exercise.setImageResource(R.drawable.category_exercise_btn_reverse);
					btn[2] = false;
					theme.setExercise(true);
				}
				
				if( interestedTheme.charAt(3) == '1' )
				{
					btn_hobby.setImageResource(R.drawable.category_hobby_btn_reverse);
					btn[3] = false;
					theme.setHobby(true);
				}
				
				if( interestedTheme.charAt(4) == '1' )
				{
					btn_study.setImageResource(R.drawable.category_study_btn_reverse);
					btn[4] = false;
					theme.setStudy(true);
				}
				
				if( interestedTheme.charAt(5) == '1' )
				{
					btn_question.setImageResource(R.drawable.category_question_btn_reverse);
					btn[5] = false;
					theme.setQuestion(true);
				}
				
				if( interestedTheme.charAt(6) == '1' )
				{
					btn_job.setImageResource(R.drawable.category_job_btn_reverse);
					btn[6] = false;
					theme.setJob(true);
				}
				
				if( interestedTheme.charAt(7) == '1' )
				{
					btn_used.setImageResource(R.drawable.category_used_btn_reverse);
					btn[7] = false;
					theme.setUsed(true);
				}

			}
		
	}
	@Override
	public void onClick(View v) {
		
		if (v == btn_daily) {
			isClicked = true;
			if(btn[0]){
				btn_daily.setImageResource(R.drawable.category_daily_btn_reverse);
				theme.setDaily(true);
				btn[0] = false;
			}
			else{
				btn_daily.setImageResource(R.drawable.category_daily_btn_select);
				theme.setDaily(false);
				btn[0] = true;
				
			}	
		}
		
		if (v == btn_travel) {
			isClicked = true;
			if(btn[1]){
				btn_travel.setImageResource(R.drawable.category_travel_btn_reverse);
				theme.setTravel(true);
				btn[1] = false;
			}
			else{
				btn_travel.setImageResource(R.drawable.category_travel_btn_select);
				theme.setTravel(false);
				btn[1] = true;
				}	
		}
		
		if (v == btn_exercise) {
			isClicked = true;
			if(btn[2]){
				btn_exercise.setImageResource(R.drawable.category_exercise_btn_reverse);
				theme.setExercise(true);
				btn[2] = false;
			}
			else{
				btn_exercise.setImageResource(R.drawable.category_exercise_btn_select);
				theme.setExercise(false);
				btn[2] = true;
				}	
		}
		
		if (v == btn_hobby) {
			isClicked = true;
			if(btn[3]){
				btn_hobby.setImageResource(R.drawable.category_hobby_btn_reverse);
				theme.setHobby(true);
				btn[3] = false;
			}
			else{
				btn_hobby.setImageResource(R.drawable.category_hobby_btn_select);
				theme.setHobby(false);
				btn[3] = true;
				}	
		}
		
		if (v == btn_study) {
			isClicked = true;
			if(btn[4]){
				btn_study.setImageResource(R.drawable.category_study_btn_reverse);
				theme.setStudy(true);
				btn[4] = false;
			}
			else{
				btn_study.setImageResource(R.drawable.category_study_btn_select);
				theme.setStudy(false);
				btn[4] = true;
				}	
		}
		
		if (v == btn_question) {
			isClicked = true;
			if(btn[5]){
				btn_question.setImageResource(R.drawable.category_question_btn_reverse);
				theme.setQuestion(true);
				btn[5] = false;
			}
			else{
				btn_question.setImageResource(R.drawable.category_question_btn_select);
				theme.setQuestion(false);
				btn[5] = true;
				}	
		}
		
		if (v == btn_job) {
			isClicked = true;
			if(btn[6]){
				btn_job.setImageResource(R.drawable.category_job_btn_reverse);
				theme.setJob(true);
				btn[6] = false;
			}
			else{
				btn_job.setImageResource(R.drawable.category_job_btn_select);
				theme.setJob(false);
				btn[6] = true;
				}	
		}
		
		if (v == btn_used) {
			isClicked = true;
			if(btn[7]){
				btn_used.setImageResource(R.drawable.category_used_btn_reverse);
				theme.setUsed(true);
				btn[7] = false;
			}
			else{
				btn_used.setImageResource(R.drawable.category_used_btn_select);
				theme.setUsed(false);
				btn[7] = true;
				}	
		}
		
		if (v == btn_all) {
			isClicked = true;
			if (btn[8]) {
				btn_all.setImageResource(R.drawable.category_all_btn_reverse);
				theme.setDaily(true);
				theme.setExercise(true);
				theme.setHobby(true);
				theme.setJob(true);
				theme.setQuestion(true);
				theme.setStudy(true);
				theme.setTravel(true);
				theme.setUsed(true);
				btn[8] = false;
			}
			else{
				btn_all.setImageResource(R.drawable.category_all_btn_select);
				theme.setDaily(false);
				theme.setExercise(false);
				theme.setHobby(false);
				theme.setJob(false);
				theme.setQuestion(false);
				theme.setStudy(false);
				theme.setTravel(false);
				theme.setUsed(false);
				btn[8] = true;
			}
		}
		
		if (v == btn_check) {
			Log.i("Dialog_CategoryOfTheme", "push the theme to MainActivity");
			if(isClicked)
				BasicInfo.INTERESTED_THEME = theme.getThemeChosen();
			
			if( mainView instanceof RoarFragment){
				RoarFragment A = (RoarFragment)mainView;
				A.getDataFromServer();
			}
			
			dismiss();
		}
	}
}
