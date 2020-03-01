package com.mstst33.roar;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mstst33.echoproject.R;

public class Awooseong_Dialog_CommentForRoom extends Dialog implements View.OnClickListener{

	private Context awooseong;
	private Awooseong_ThemeRoom info;
	private LinearLayout writingLinear;
	private TextView writingBigger;
	private String newData;
	private int theme;
	
	public Awooseong_Dialog_CommentForRoom(Context context, int theme, Awooseong_ThemeRoom info) {
		
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		awooseong = context;
		this.info = info;
		this.theme = theme;
		
		// TODO Auto-generated constructor stub
		writingLinear = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_writing_content_bigger, null);
		writingBigger = (TextView)writingLinear.findViewById(R.id.awooseong_writing_content_bigger);
		setData();
		
		writingLinear.setOnClickListener(this);
		setContentView(writingLinear);
	}
	
	
	// 관련 내용 얻기
	public void setData(){
		
		switch(theme){
		
		case Awooseong_Theme.DAILY:
			Awooseong_Room_Var_Daily A;
			if (info instanceof Awooseong_Room_Var_Daily) {
				A = (Awooseong_Room_Var_Daily) info;
			}
			else
				return;
			writingBigger.setText(A.daily_content);
			break;
			
		case Awooseong_Theme.TRAVEL:
			Awooseong_Room_Var_Travel tr;
			if (info instanceof Awooseong_Room_Var_Travel) {
				tr = (Awooseong_Room_Var_Travel)info;
			}
			else
				return;
			writingBigger.setText(tr.travel_content);
			break;
			
		case Awooseong_Theme.EXERCISE:
			Awooseong_Room_Var_Exercise exer;
			if (info instanceof Awooseong_Room_Var_Exercise) {
				exer = (Awooseong_Room_Var_Exercise) info;
			}
			else
				return;
			writingBigger.setText(exer.exercise_content);
			break;
			
		case Awooseong_Theme.HOBBY:
			Awooseong_Room_Var_Hobby hob;
			if (info instanceof Awooseong_Room_Var_Hobby) {
				hob = (Awooseong_Room_Var_Hobby)info;
			}
			else
				return;
			writingBigger.setText(hob.hobby_content);
			break;
			
		case Awooseong_Theme.STUDY:
			Awooseong_Room_Var_Study stu;
			if (info instanceof Awooseong_Room_Var_Study) {
				stu = (Awooseong_Room_Var_Study)info;
			}
			else
				return;
			writingBigger.setText(stu.study_content);
			break;
			
		case Awooseong_Theme.QUESTION:
			Awooseong_Room_Var_Question qe;
			if (info instanceof Awooseong_Room_Var_Question) {
				qe = (Awooseong_Room_Var_Question)info;
			}
			else
				return;
			writingBigger.setText(qe.question_content);
			break;
			
		case Awooseong_Theme.JOB:
			Awooseong_Room_Var_Job jo;
			if (info instanceof Awooseong_Room_Var_Job) {
				jo = (Awooseong_Room_Var_Job)info;
			}
			else
				return;
			writingBigger.setText(jo.job_content);
			break;
			
		case Awooseong_Theme.USED:
			Awooseong_Room_Var_Used us;
			if (info instanceof Awooseong_Room_Var_Used) {
				us = (Awooseong_Room_Var_Used)info;
			}
			else
				return;
			writingBigger.setText(us.used_content);
			break;
			
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dismiss();
		
	}

}
