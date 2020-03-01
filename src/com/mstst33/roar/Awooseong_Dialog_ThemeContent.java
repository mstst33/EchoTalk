package com.mstst33.roar;

import android.app.Dialog;
import android.content.Context;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.mstst33.echoproject.R;

public class Awooseong_Dialog_ThemeContent extends Dialog implements View.OnClickListener{

	private Context awooseong;
	private LinearLayout linear;
	private RelativeLayout background;
	// DailyRoomInfo Variable
	private TextView writing_content;

	// ExerciseRoomInfo Variable
	private TextView exercise_subject;
	private TextView exercise_startDate;
	private TextView exercise_durationPerDay;
	private TextView exercise_numberAddmitted;
	private TextView exercise_place;

	// HobbyRoomInfo Variable
	private TextView hobby_subject;
	private TextView hobby_startDate;
	private TextView hobby_durationPerDay;
	private TextView hobby_numberAddmitted;
	private TextView hobby_place;

	// JobRoomInfo Variable
	private TextView job_subject;
	private TextView job_startDate;
	private TextView job_durationPerDay;
	private TextView job_numberAddmitted;
	private TextView job_pay;
	private TextView job_place;
	private TextView job_mon;
	private TextView job_tue;
	private TextView job_wed;
	private TextView job_thu;
	private TextView job_fri;
	private TextView job_sat;
	private TextView job_sun;

	// QuestionInfo Variable
	private TextView question_subject;
	private TextView question_isCompleted;

	// StudyInfo Variable
	private TextView study_subject;
	private TextView study_startDate;
	private TextView study_durationPerDay;
	private TextView study_numberAddmitted;
	private TextView study_place;
	private TextView study_mon;
	private TextView study_tue;
	private TextView study_wed;
	private TextView study_thu;
	private TextView study_fri;
	private TextView study_sat;
	private TextView study_sun;

	// TravelInfo Variable
	private TextView travel_subject;
	private TextView travel_date;
	private TextView travel_period;
	private TextView travel_numberAddmitted;
	private TextView travel_place;

	// UsedInfo Variable
	private TextView used_subject;
	private TextView used_productClassification;
	private TextView used_buy;
	private TextView used_sell;
	private TextView used_askingPrice;
	private TextView used_madeOfDealing;
	private Awooseong_ThemeRoom info;
	private int theme;
	private RelativeLayout.LayoutParams backgroundLp;
	
	public Awooseong_Dialog_ThemeContent(Context context, Awooseong_ThemeRoom info) {
		super(context);
		this.info = info;
		theme = info.getThemeNumber();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		awooseong = context;
		
		background = (RelativeLayout)View.inflate(awooseong, R.layout.awooseong_reply_join_relativelayout_whole_background, null);
		
		switch(theme){
		case Awooseong_Theme.DAILY:
			//daily
			Log.i("ThemeContent", "Daily");
			linear = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_extended_daily_info, null);
			setDailyRoomInfo();
			break;
			
		case Awooseong_Theme.EXERCISE:
			Log.i("ThemeContent", "Exercise");
			linear = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_extended_exercise_info, null);
			// exercise
			setExerciseRoomInfo();
			break;
		case Awooseong_Theme.HOBBY:
			//hobby
			Log.i("ThemeContent", "Hobby");
			linear = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_extended_hobby_info, null);
			setHobbyRoomInfo();
			break;
		case Awooseong_Theme.JOB:
			//job
			Log.i("ThemeContent", "Job");
			linear = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_extended_job_info, null);
			setJobRoomInfo();
			break;
		case Awooseong_Theme.QUESTION:
			//question
			Log.i("ThemeContent", "Question");
			linear = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_extended_question_info, null);
			setQuestionRoomInfo();
			break;
		case Awooseong_Theme.STUDY:
			Log.i("ThemeContent", "Study");
			// study
			linear = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_extended_study_info, null);
			setStudyRoomInfo();
			break;
		case Awooseong_Theme.TRAVEL:
			// travel
			Log.i("ThemeContent", "Travel");
			linear = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_extended_travel_info, null);
			setTravelRoomInfo();
			break;
		case Awooseong_Theme.USED:
			// used
			Log.i("ThemeContent", "Used");
			linear = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_extended_used_info, null);
			setUsedRoomInfo();
			break;			
		}
				
		WindowManager wm = (WindowManager) awooseong.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		backgroundLp = new RelativeLayout.LayoutParams(display.getWidth(), display.getHeight());
		background.setLayoutParams(backgroundLp);
		
		LinearLayout.LayoutParams linearLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		linear.setOnClickListener(this);
		linear.setGravity(Gravity.CENTER);
		linear.setLayoutParams(linearLp);
		background.addView(linear);
		
		setContentView(background);
	}

	
	private void setDailyRoomInfo(){
		writing_content = (TextView)linear.findViewById(R.id.awooseong_extended_daily_writing_content);
		
		Awooseong_Room_Var_Daily A;
		if (info instanceof Awooseong_Room_Var_Daily) {
			A = (Awooseong_Room_Var_Daily) info;
		}
		else
			return;
		// --> get data from server
		writing_content.setText(A.daily_content);
	}
	
	private void setExerciseRoomInfo(){
		
		Awooseong_Room_Var_Exercise exer;
		if (info instanceof Awooseong_Room_Var_Exercise) {
			exer = (Awooseong_Room_Var_Exercise) info;
		}
		else
			return;
		
		 exercise_subject = (TextView)linear.findViewById(R.id.awooseong_extended_exercise_subject);
		 exercise_startDate = (TextView)linear.findViewById(R.id.awooseong_extended_exercise_start_date);
		 exercise_durationPerDay = (TextView)linear.findViewById(R.id.awooseong_extended_exercise_duration_per_day);
		 exercise_numberAddmitted = (TextView)linear.findViewById(R.id.awooseong_extended_exercise_number_addmitted);
		 exercise_place = (TextView)linear.findViewById(R.id.awooseong_extended_exercise_place);
		 
		// --> get data from server
		 exercise_subject.setText(exer.exercise_subject);
		 exercise_startDate.setText(exer.exercise_startDate);
		 exercise_durationPerDay.setText(exer.exercise_duration);
		 exercise_numberAddmitted.setText(exer.exercise_numPeople);
		 exercise_place.setText(exer.exercise_place);
	}
	
	private void setHobbyRoomInfo(){
		
		Awooseong_Room_Var_Hobby hob;
		if (info instanceof Awooseong_Room_Var_Hobby) {
			hob = (Awooseong_Room_Var_Hobby)info;
		}
		else
			return;
		
		hobby_subject = (TextView)linear.findViewById(R.id.awooseong_extended_hobby_subject);
		hobby_startDate = (TextView)linear.findViewById(R.id.awooseong_extended_hobby_start_date);
		hobby_durationPerDay = (TextView)linear.findViewById(R.id.awooseong_extended_hobby_duration_per_day);
		hobby_numberAddmitted = (TextView)linear.findViewById(R.id.awooseong_extended_hobby_number_addmitted);
		hobby_place = (TextView)linear.findViewById(R.id.awooseong_extended_hobby_place);
		
		// --> get data from server
		hobby_subject.setText(hob.hobby_subject);
		hobby_startDate.setText(hob.hobby_startDate);
		hobby_durationPerDay.setText(hob.hobby_duration);
		hobby_numberAddmitted.setText(hob.hobby_numPeople);
		hobby_place.setText(hob.hobby_numPeople);
	}
	
	private void setJobRoomInfo(){
		 
		Awooseong_Room_Var_Job jo;
		if (info instanceof Awooseong_Room_Var_Job) {
			jo = (Awooseong_Room_Var_Job)info;
		}
		else
			return;
		
		job_subject = (TextView)linear.findViewById(R.id.awooseong_extended_job_subject);
		job_startDate = (TextView)linear.findViewById(R.id.awooseong_extended_job_start_date);
		job_durationPerDay = (TextView)linear.findViewById(R.id.awooseong_extended_job_duration_per_day);
		job_numberAddmitted = (TextView)linear.findViewById(R.id.awooseong_extended_job_number_addmitted);
		job_pay = (TextView)linear.findViewById(R.id.awooseong_extended_job_pay);
		job_place = (TextView)linear.findViewById(R.id.awooseong_extended_job_place);
		job_mon = (TextView)linear.findViewById(R.id.awooseong_extended_job_mon);
		job_tue = (TextView)linear.findViewById(R.id.awooseong_extended_job_tue);
		job_wed = (TextView)linear.findViewById(R.id.awooseong_extended_job_wed);
		job_thu = (TextView)linear.findViewById(R.id.awooseong_extended_job_thu);
		job_fri = (TextView)linear.findViewById(R.id.awooseong_extended_job_fri);
		job_sat = (TextView)linear.findViewById(R.id.awooseong_extended_job_sat);
		job_sun = (TextView)linear.findViewById(R.id.awooseong_extended_job_sun);
		 
		// --> get data from server
		job_subject.setText(jo.job_subject);
		job_startDate.setText(jo.job_startDate);
		job_durationPerDay.setText(jo.job_duration);
		job_numberAddmitted.setText(jo.job_numPeople);
		job_pay.setText(jo.job_pay);
		job_place.setText(jo.job_place);
		 
		String dayOfWeek = jo.job_dayWeek;
		 
		if(dayOfWeek.charAt(0) == 0 ) job_mon.setText(null);
		if(dayOfWeek.charAt(1) == 0 ) job_tue.setText(null);
		if(dayOfWeek.charAt(2) == 0 ) job_wed.setText(null);
		if(dayOfWeek.charAt(3) == 0 ) job_thu.setText(null);
		if(dayOfWeek.charAt(4) == 0 ) job_fri.setText(null);
		if(dayOfWeek.charAt(5) == 0 ) job_sat.setText(null);
		if(dayOfWeek.charAt(6) == 0 ) job_sun.setText(null);
	}	
	
	private void setQuestionRoomInfo(){
		
		Awooseong_Room_Var_Question qe;
		if (info instanceof Awooseong_Room_Var_Question) {
			qe = (Awooseong_Room_Var_Question)info;
		}
		else
			return;
		
		question_subject = (TextView)linear.findViewById(R.id.awooseong_extended_question_subject);
		question_isCompleted = (TextView)linear.findViewById(R.id.awooseong_extended_question_is_completed);
		//question_writingContent = (TextView)linear.findViewById(R.id.awooseong_extended_question_writing_content);
	
		question_subject.setText(qe.question_subject);
		
		Log.i("ThemeContent", "question iscompleted parse to int");
		if(Integer.parseInt(qe.question_is_completed) == 0)
			question_isCompleted.setText(null);;
			
	}
	
	private void setStudyRoomInfo(){
		
		Awooseong_Room_Var_Study stu;
		if (info instanceof Awooseong_Room_Var_Study) {
			stu = (Awooseong_Room_Var_Study)info;
		}
		else
			return;
		
		study_subject = (TextView)linear.findViewById(R.id.awooseong_extended_study_subject);
		study_startDate = (TextView)linear.findViewById(R.id.awooseong_extended_study_start_date);
	    study_durationPerDay = (TextView)linear.findViewById(R.id.awooseong_extended_study_duration_per_day);
		study_numberAddmitted = (TextView)linear.findViewById(R.id.awooseong_extended_study_number_addmitted);
		study_place = (TextView)linear.findViewById(R.id.awooseong_extended_study_place);
		study_mon = (TextView)linear.findViewById(R.id.awooseong_extended_study_mon);
		study_tue = (TextView)linear.findViewById(R.id.awooseong_extended_study_tue);
		study_wed = (TextView)linear.findViewById(R.id.awooseong_extended_study_wed);
		study_thu = (TextView)linear.findViewById(R.id.awooseong_extended_study_thu);
		study_fri = (TextView)linear.findViewById(R.id.awooseong_extended_study_fri);
		study_sat = (TextView)linear.findViewById(R.id.awooseong_extended_study_sat);
		study_sun = (TextView)linear.findViewById(R.id.awooseong_extended_study_sun);
		
		// --> get data from server
		study_subject.setText(stu.study_subject);
		study_startDate.setText(stu.study_startDate);
		study_durationPerDay.setText(stu.study_duration);
		study_numberAddmitted.setText(stu.study_numPeople);
		study_place.setText(stu.study_place);
		
		String dayOfWeek = stu.study_day_week;
		Log.i("ThemeContent","analyze dayOfWeek");
		if(dayOfWeek.charAt(0) == '0' ) study_mon.setText(null);
		if(dayOfWeek.charAt(1) == '0' ) study_tue.setText(null);
		if(dayOfWeek.charAt(2) == '0' ) study_wed.setText(null);
		if(dayOfWeek.charAt(3) == '0' ) study_thu.setText(null);
		if(dayOfWeek.charAt(4) == '0' ) study_fri.setText(null);
		if(dayOfWeek.charAt(5) == '0' ) study_sat.setText(null);
		if(dayOfWeek.charAt(6) == '0' ) study_sun.setText(null);
	}
	private void setTravelRoomInfo(){
		
		Awooseong_Room_Var_Travel tr;
		if (info instanceof Awooseong_Room_Var_Travel) {
			tr = (Awooseong_Room_Var_Travel)info;
		}
		else
			return;
		travel_subject = (TextView)linear.findViewById(R.id.awooseong_extended_travel_subject);
		travel_date = (TextView)linear.findViewById(R.id.awooseong_extended_travel_date);
		travel_period = (TextView)linear.findViewById(R.id.awooseong_extended_travel_period);
		travel_numberAddmitted = (TextView)linear.findViewById(R.id.awooseong_extended_travel_number_addmitted);
		travel_place = (TextView)linear.findViewById(R.id.awooseong_extended_travel_place);

		// --> get data from server
		travel_subject.setText(tr.travel_subject);
		travel_date.setText(tr.travel_startDate);
		travel_period.setText(tr.travel_duration);
		travel_numberAddmitted.setText(tr.travel_numPeople);
		travel_place.setText(tr.travel_place);
		
	}
	
	private void setUsedRoomInfo(){

		Awooseong_Room_Var_Used us;
		if (info instanceof Awooseong_Room_Var_Used) {
			us = (Awooseong_Room_Var_Used)info;
		}
		else
			return;
		
		used_subject = (TextView)linear.findViewById(R.id.awooseong_extended_used_subject);
		used_productClassification = (TextView)linear.findViewById(R.id.awooseong_extended_used_product_classification);
		used_buy = (TextView)linear.findViewById(R.id.awooseong_extended_used_buy);
		used_sell = (TextView)linear.findViewById(R.id.awooseong_extended_used_sell);
		used_askingPrice = (TextView)linear.findViewById(R.id.awooseong_extended_used_asking_price);
		used_madeOfDealing = (TextView)linear.findViewById(R.id.awooseong_extended_used_mode_of_dealing);
		
		// --> get data from server
		used_subject.setText(us.used_subject);
		used_productClassification.setText(us.used_section);
		used_askingPrice.setText(us.used_price);
		
		if(Integer.parseInt(us.used_isCompleted) == 1)
			used_madeOfDealing.setText("is completed");
		
		if (Integer.parseInt(us.used_isSell) == 1) {
			used_buy.setText(null);
		}

		else {
			used_sell.setText(null);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dismiss();
		
	}
	
}
