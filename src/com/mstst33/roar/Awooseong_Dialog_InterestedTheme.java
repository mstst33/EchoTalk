package com.mstst33.roar;

import com.mstst33.echoproject.*;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;

public class Awooseong_Dialog_InterestedTheme extends Dialog{

	private Context context;
	private LinearLayout layout;
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
	private String interestedTheme;
	
	public Awooseong_Dialog_InterestedTheme(Context context, String interestedTheme) {
		super(context);
		
		this.interestedTheme = interestedTheme;
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		layout = (LinearLayout)View.inflate(context, R.layout.awooseong_choose_theme_category_of_nine_theme, null);
		
		btn_daily = (ImageView)layout.findViewById(R.id.category_of_nine_btnTheme1);
		btn_travel = (ImageView)layout.findViewById(R.id.category_of_nine_btnTheme2);
		btn_exercise = (ImageView)layout.findViewById(R.id.category_of_nine_btnTheme3);
		btn_hobby = (ImageView)layout.findViewById(R.id.category_of_nine_btnTheme4);
		btn_study = (ImageView)layout.findViewById(R.id.category_of_nine_btnTheme5);
		btn_question = (ImageView)layout.findViewById(R.id.category_of_nine_btnTheme6);
		btn_job = (ImageView)layout.findViewById(R.id.category_of_nine_btnTheme7);
		btn_used = (ImageView)layout.findViewById(R.id.category_of_nine_btnTheme8);
		btn_all = (ImageView)layout.findViewById(R.id.category_of_nine_btnTheme9);
		btn_check = (Button)layout.findViewById(R.id.category_of_nine_btnCheck);
		
		btn_check.setVisibility(View.GONE);
		btn_all.setVisibility(View.GONE);
		// 전체적인 틀을 맞춘다.
		if (interestedTheme.charAt(0) == '0')
			btn_daily.setVisibility(View.GONE);
		else
			btn_daily.setImageResource(R.drawable.category_daily_btn_select);
		if (interestedTheme.charAt(1) == '0')
			btn_travel.setVisibility(View.GONE);
		else
			btn_travel.setImageResource(R.drawable.category_travel_btn_select);

		if (interestedTheme.charAt(2) == '0')
			btn_exercise.setVisibility(View.GONE);
			
		else
			btn_exercise.setImageResource(R.drawable.category_exercise_btn_select);

		if (interestedTheme.charAt(3) == '0')
			btn_hobby.setVisibility(View.GONE);
			
		else
			btn_hobby.setImageResource(R.drawable.category_hobby_btn_select);

		if (interestedTheme.charAt(4) == '0')
			btn_study.setVisibility(View.GONE);
			
		else
			btn_study.setImageResource(R.drawable.category_study_btn_select);

		if (interestedTheme.charAt(5) == '0')
			btn_question.setVisibility(View.GONE);
			
		else
			btn_question.setImageResource(R.drawable.category_question_btn_select);

		if (interestedTheme.charAt(6) == '0')
			btn_job.setVisibility(View.GONE);
		else
			btn_job.setImageResource(R.drawable.category_job_btn_select);

		if (interestedTheme.charAt(7) == '0')
			btn_used.setVisibility(View.GONE);
		else
			btn_used.setImageResource(R.drawable.category_used_btn_select);

		btn_all.setImageResource(R.drawable.category_all_btn_select);

		// TODO Auto-generated constructor stub
		setContentView(layout);
	}
	
}
