package com.mstst33.echo;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mstst33.echoproject.CameraAlbumDialog;
import com.mstst33.echoproject.DateAndTimePicker;
import com.mstst33.echoproject.R;

public class EchoShoutWindow extends DialogFragment implements View.OnClickListener{
	private LinearLayout echoShoutView;
	private LinearLayout echoExerciseInfo;
	private LinearLayout echoDailyInfo;
	private LinearLayout echoHobbyInfo;
	private LinearLayout echoJobInfo;
	private LinearLayout echoQuestionInfo;
	private LinearLayout echoStudyInfo;
	private LinearLayout echoTravelInfo;
	private LinearLayout echoUsedInfo;
	private LinearLayout echoWritingInfo;
	
	// Button
	private Button echo_daily_btn;
	private Button echo_travel_btn;
	private Button echo_exercise_btn;
	private Button echo_hobby_btn;
	private Button echo_study_btn;
	private Button echo_question_btn;
	private Button echo_job_btn;
	private Button echo_used_btn;
	private Button echo_shout_btn;
	private Button echo_add_photo_btn;
	
	// Daily
	private EditText daily_writing_content;
	
	// Exercise
	private EditText exercise_subject;
	private TextView exercise_start_date;
	private EditText exercise_duration_per_day;
	private EditText exercise_number_addmitted;
	private EditText exercise_place;
	private EditText exercise_writing_content;

	// Hobby
	private EditText hobby_subject;
	private TextView hobby_start_date;
	private EditText hobby_duration_per_day;
	private EditText hobby_number_addmitted;
	private EditText hobby_place;
	private EditText hobby_writing_content;
	
	// Job
	private EditText job_subject;
	private TextView job_start_date;
	private EditText job_duration_per_day;
	private EditText job_number_addmitted;
	private EditText job_pay;
	private EditText job_place;
	private EditText job_writing_content;
	
	private TextView job_mon;
	private TextView job_tue;
	private TextView job_wed;
	private TextView job_thu;
	private TextView job_fri;
	private TextView job_sat;
	private TextView job_sun;
	
	// Question
	private EditText question_subject;
	private TextView question_is_completed;
	private EditText question_writing_content;
	
	// Study
	private EditText study_subject;
	private TextView study_start_date;
	private EditText study_duration_per_day;
	private EditText study_number_addmitted;
	private EditText study_place;
	private EditText study_writing_content;
	
	private TextView study_mon;
	private TextView study_tue;
	private TextView study_wed;
	private TextView study_thu;
	private TextView study_fri;
	private TextView study_sat;
	private TextView study_sun;	
	
	// Travel
	private EditText travel_subject;
	private TextView travel_date;
	private EditText travel_period;
	private EditText travel_number_addmitted;
	private EditText travel_place;
	private EditText travel_writing_content;
	
	// Used
	private EditText used_subject;
	private EditText used_product_classification;
	private TextView used_is_completed;
	private EditText used_asking_price;
	private EditText used_mode_of_dealing;
	private EditText used_writing_content;
	
	private TextView used_buy;
	private TextView used_sell;
	
	String dayOfWeek;
	String date;
	String buyOrSell;
	
	byte[] tempImage;
	Bitmap bitmapImage;
	ArrayList<NameValuePair> nameValuePairs;
	private int selectedInfo;
	
	private String alert_shout;
	private boolean isDialogShownBool;
	private String bitmapStr;
	
	public EchoShoutWindow(){
		
	}

	public static EchoShoutWindow newInstance(int num){	
		EchoShoutWindow echoShoutFragment = new EchoShoutWindow();
	    Bundle bundle = new Bundle();
	    bundle.putInt("num", num);
	    echoShoutFragment.setArguments(bundle);

	    return echoShoutFragment;

	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int num = getArguments().getInt("num");

        int style = DialogFragment.STYLE_NO_FRAME, theme = android.R.style.Theme_Dialog;
        
        setStyle(style, theme);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	  Bundle savedInsntaceState) {
		// Remove the title
		// getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		 
		// LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		echoShoutView = (LinearLayout) inflater.inflate(R.layout.echo_shout, null);
		echoDailyInfo = (LinearLayout) inflater.inflate(R.layout.echo_daily_info, null); // 0
		echoTravelInfo = (LinearLayout) inflater.inflate(R.layout.echo_travel_info, null); // 1
		echoExerciseInfo = (LinearLayout) inflater.inflate(R.layout.echo_exercise_info, null); // 2
		echoHobbyInfo = (LinearLayout) inflater.inflate(R.layout.echo_hobby_info, null); // 3
		echoStudyInfo = (LinearLayout) inflater.inflate(R.layout.echo_study_info, null); // 4
		echoQuestionInfo = (LinearLayout) inflater.inflate(R.layout.echo_question_info, null); // 5
		echoJobInfo = (LinearLayout) inflater.inflate(R.layout.echo_job_info, null); // 6
		echoUsedInfo = (LinearLayout) inflater.inflate(R.layout.echo_used_info, null); // 7
		echoWritingInfo = (LinearLayout) echoShoutView.findViewById(R.id.writing_info);
		
		// Buttons
		echo_daily_btn = (Button) echoShoutView.findViewById(R.id.echo_daily_btn);
		echo_travel_btn = (Button) echoShoutView.findViewById(R.id.echo_travel_btn);
		echo_exercise_btn = (Button) echoShoutView.findViewById(R.id.echo_exercise_btn);
		echo_hobby_btn = (Button) echoShoutView.findViewById(R.id.echo_hobby_btn);
		echo_study_btn = (Button) echoShoutView.findViewById(R.id.echo_study_btn);
		echo_question_btn = (Button) echoShoutView.findViewById(R.id.echo_question_btn);
		echo_job_btn = (Button) echoShoutView.findViewById(R.id.echo_job_btn);
		echo_used_btn = (Button) echoShoutView.findViewById(R.id.echo_used_btn);
		echo_shout_btn = (Button) echoShoutView.findViewById(R.id.echo_shout_btn);
		echo_add_photo_btn = (Button) echoShoutView.findViewById(R.id.echo_add_photo_btn);
		
		echo_daily_btn.setOnClickListener(this);
		echo_travel_btn.setOnClickListener(this);
		echo_exercise_btn.setOnClickListener(this);
		echo_hobby_btn.setOnClickListener(this);
		echo_study_btn.setOnClickListener(this);
		echo_question_btn.setOnClickListener(this);
		echo_job_btn.setOnClickListener(this);
		echo_used_btn.setOnClickListener(this);
		echo_shout_btn.setOnClickListener(this);
		echo_add_photo_btn.setOnClickListener(this);
		
		// Daily
		daily_writing_content = (EditText) echoDailyInfo.findViewById(R.id.daily_writing_content);
		
		// Exercise
		exercise_subject = (EditText) echoExerciseInfo.findViewById(R.id.exercise_subject);
		exercise_start_date = (TextView) echoExerciseInfo.findViewById(R.id.exercise_start_date);
		exercise_duration_per_day = (EditText) echoExerciseInfo.findViewById(R.id.exercise_duration_per_day);
		exercise_number_addmitted = (EditText) echoExerciseInfo.findViewById(R.id.exercise_number_addmitted);
		exercise_place = (EditText) echoExerciseInfo.findViewById(R.id.exercise_place);
		exercise_writing_content = (EditText) echoExerciseInfo.findViewById(R.id.exercise_writing_content);
		
		exercise_start_date.setOnClickListener(this);
		
		// Hobby
		hobby_subject = (EditText) echoHobbyInfo.findViewById(R.id.hobby_subject);
		hobby_start_date = (TextView) echoHobbyInfo.findViewById(R.id.hobby_start_date);
		hobby_duration_per_day = (EditText) echoHobbyInfo.findViewById(R.id.hobby_duration_per_day);
		hobby_number_addmitted = (EditText) echoHobbyInfo.findViewById(R.id.hobby_number_addmitted);
		hobby_place = (EditText) echoHobbyInfo.findViewById(R.id.hobby_place);
		hobby_writing_content = (EditText) echoHobbyInfo.findViewById(R.id.hobby_writing_content);
		
		hobby_start_date.setOnClickListener(this);
		
		// Job
		job_subject = (EditText) echoJobInfo.findViewById(R.id.job_subject);
		job_start_date = (TextView) echoJobInfo.findViewById(R.id.job_start_date);
		job_duration_per_day = (EditText) echoJobInfo.findViewById(R.id.job_duration_per_day);
		job_number_addmitted = (EditText) echoJobInfo.findViewById(R.id.job_number_addmitted);
		job_pay = (EditText) echoJobInfo.findViewById(R.id.job_pay);
		job_place = (EditText) echoJobInfo.findViewById(R.id.job_place);
		job_writing_content = (EditText) echoJobInfo.findViewById(R.id.job_writing_content);
		
		job_start_date.setOnClickListener(this);
		
		job_mon = (TextView) echoJobInfo.findViewById(R.id.job_mon);
		job_tue = (TextView) echoJobInfo.findViewById(R.id.job_tue);
		job_wed = (TextView) echoJobInfo.findViewById(R.id.job_wed);
		job_thu = (TextView) echoJobInfo.findViewById(R.id.job_thu);
		job_fri = (TextView) echoJobInfo.findViewById(R.id.job_fri);
		job_sat = (TextView) echoJobInfo.findViewById(R.id.job_sat);
		job_sun = (TextView) echoJobInfo.findViewById(R.id.job_sun);
		
		job_mon.setOnClickListener(this);
		job_tue.setOnClickListener(this);
		job_wed.setOnClickListener(this);
		job_thu.setOnClickListener(this);
		job_fri.setOnClickListener(this);
		job_sat.setOnClickListener(this);
		job_sun.setOnClickListener(this);
		
		// Question
		question_subject = (EditText) echoQuestionInfo.findViewById(R.id.question_subject);
		question_writing_content = (EditText) echoQuestionInfo.findViewById(R.id.question_writing_content);
		
		// Study
		study_subject = (EditText) echoStudyInfo.findViewById(R.id.study_subject);
		study_start_date = (TextView) echoStudyInfo.findViewById(R.id.study_start_date);
		study_duration_per_day = (EditText) echoStudyInfo.findViewById(R.id.study_duration_per_day);
		study_number_addmitted = (EditText) echoStudyInfo.findViewById(R.id.study_number_addmitted);
		study_place = (EditText) echoStudyInfo.findViewById(R.id.study_place);
		study_writing_content = (EditText) echoStudyInfo.findViewById(R.id.study_writing_content);
		
		study_start_date.setOnClickListener(this);
		
		study_mon = (TextView) echoStudyInfo.findViewById(R.id.study_mon);
		study_tue = (TextView) echoStudyInfo.findViewById(R.id.study_tue);
		study_wed = (TextView) echoStudyInfo.findViewById(R.id.study_wed);
		study_thu = (TextView) echoStudyInfo.findViewById(R.id.study_thu);
		study_fri = (TextView) echoStudyInfo.findViewById(R.id.study_fri);
		study_sat = (TextView) echoStudyInfo.findViewById(R.id.study_sat);
		study_sun = (TextView) echoStudyInfo.findViewById(R.id.study_sun);
		
		study_mon.setOnClickListener(this);
		study_tue.setOnClickListener(this);
		study_wed.setOnClickListener(this);
		study_thu.setOnClickListener(this);
		study_fri.setOnClickListener(this);
		study_sat.setOnClickListener(this);
		study_sun.setOnClickListener(this);
		
		// Travel
		travel_subject = (EditText) echoTravelInfo.findViewById(R.id.travel_subject);
		travel_date = (TextView) echoTravelInfo.findViewById(R.id.travel_date);
		travel_period = (EditText) echoTravelInfo.findViewById(R.id.travel_period);
		travel_number_addmitted = (EditText) echoTravelInfo.findViewById(R.id.travel_number_addmitted);
		travel_place = (EditText) echoTravelInfo.findViewById(R.id.travel_place);
		travel_writing_content = (EditText) echoTravelInfo.findViewById(R.id.travel_writing_content);
		
		travel_date.setOnClickListener(this);
		
		// Used
		used_subject = (EditText) echoUsedInfo.findViewById(R.id.used_subject);
		used_product_classification = (EditText) echoUsedInfo.findViewById(R.id.used_product_classification);
		used_asking_price = (EditText) echoUsedInfo.findViewById(R.id.used_asking_price);
		used_mode_of_dealing = (EditText) echoUsedInfo.findViewById(R.id.used_mode_of_dealing);
		used_writing_content = (EditText) echoUsedInfo.findViewById(R.id.used_writing_content);
		
		used_buy = (TextView) echoUsedInfo.findViewById(R.id.used_buy);
		used_sell = (TextView) echoUsedInfo.findViewById(R.id.used_sell);
		
		used_buy.setOnClickListener(this);
		used_sell.setOnClickListener(this);
		used_sell.setTextColor(getActivity().getResources().getColor(R.color.used_color));
		
		dayOfWeek = "0000000";
		date = "0";
		buyOrSell = "2";
		bitmapImage = null;
		nameValuePairs = new ArrayList<NameValuePair>();
		selectedInfo = 0;
		alert_shout = getString(R.string.alert_shout);
		isDialogShownBool = false;
		bitmapStr = "";
		
		echoWritingInfo.addView(echoDailyInfo);
		
		/*
		WindowManager.LayoutParams wl = new WindowManager.LayoutParams();
        wl.width = (2 * MainActivity.displayWidth) / 10;
        wl.height = (8 * MainActivity.displayWidth) / 10;
        getDialog().getWindow().setAttributes(wl);*/
		
     	if (getDialog() != null) 
     		getDialog().setCanceledOnTouchOutside(false);
		
	    return echoShoutView;
	}
	
	@Override
	public int show(FragmentTransaction transaction, String tag) {
		int result = -1;
		if (!isDialogShownBool) {
			result = super.show(transaction, tag);
			isDialogShownBool = true;
		}
		
		return result;
	}

	@Override
	public void show(FragmentManager manager, String tag) {
		if (!isDialogShownBool) {
			super.show(manager, tag);
			isDialogShownBool = true;
		}
	}

	@Override
	public void dismiss() {
		if (isDialogShownBool) {
			super.dismiss();
		}
	}
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.echo_daily_btn:
			selectedInfo = 0;
			echoWritingInfo.removeAllViews();
			echoWritingInfo.addView(echoDailyInfo);
			break;
		case R.id.echo_travel_btn:
			selectedInfo = 1;
			echoWritingInfo.removeAllViews();
			echoWritingInfo.addView(echoTravelInfo);
			break;
		case R.id.echo_exercise_btn:
			Log.d("EchoShout", "Exercise");
			selectedInfo = 2;
			echoWritingInfo.removeAllViews();
			echoWritingInfo.addView(echoExerciseInfo);
			break;
		case R.id.echo_hobby_btn:
			Log.d("EchoShout", "Hobby");
			selectedInfo = 3;
			echoWritingInfo.removeAllViews();
			echoWritingInfo.addView(echoHobbyInfo);
			break;
		case R.id.echo_study_btn:
			selectedInfo = 4;
			echoWritingInfo.removeAllViews();
			echoWritingInfo.addView(echoStudyInfo);
			break;
		case R.id.echo_question_btn:
			selectedInfo = 5;
			echoWritingInfo.removeAllViews();
			echoWritingInfo.addView(echoQuestionInfo);
			break;
		case R.id.echo_job_btn:
			selectedInfo = 6;
			echoWritingInfo.removeAllViews();
			echoWritingInfo.addView(echoJobInfo);
			break;
		case R.id.echo_used_btn:
			selectedInfo = 7;
			echoWritingInfo.removeAllViews();
			echoWritingInfo.addView(echoUsedInfo);
			break;
		case R.id.echo_shout_btn:
			clickShout();
			break;
		case R.id.echo_add_photo_btn:
			addPhoto(CameraAlbumDialog.REQUEST_SELECT_PHOTO);
			break;
		case R.id.job_mon:
			clickDayOfWeek(0);
			break;
		case R.id.job_tue:
			clickDayOfWeek(1);
			break;
		case R.id.job_wed:
			clickDayOfWeek(2);
			break;
		case R.id.job_thu:
			clickDayOfWeek(3);
			break;
		case R.id.job_fri:
			clickDayOfWeek(4);
			break;
		case R.id.job_sat:
			clickDayOfWeek(5);
			break;
		case R.id.job_sun:
			clickDayOfWeek(6);
			break;
		case R.id.study_mon:
			clickDayOfWeek(0);
			break;
		case R.id.study_tue:
			clickDayOfWeek(1);
			break;
		case R.id.study_wed:
			clickDayOfWeek(2);
			break;
		case R.id.study_thu:
			clickDayOfWeek(3);
			break;
		case R.id.study_fri:
			clickDayOfWeek(4);
			break;
		case R.id.study_sat:
			clickDayOfWeek(5);
			break;
		case R.id.study_sun:
			clickDayOfWeek(6);
			break;
		case R.id.exercise_start_date:
			clickDate();
			break;
		case R.id.study_start_date:
			clickDate();
			break;
		case R.id.travel_date:
			clickDate();
			break;
		case R.id.job_start_date:
			clickDate();
			break;
		case R.id.hobby_start_date:
			clickDate();
			break;
		case R.id.used_buy:
			buyOrSell = "1";
			used_buy.setTextColor(getActivity().getResources().getColor(R.color.used_color));
			used_sell.setTextColor(Color.LTGRAY);
			break;
		case R.id.used_sell:
			buyOrSell = "2";
			used_sell.setTextColor(getActivity().getResources().getColor(R.color.used_color));
			used_buy.setTextColor(Color.LTGRAY);
			break;
		}
	}
	
	private void clickDayOfWeek(int num){
		char[] temp = dayOfWeek.toCharArray();
		
		if(dayOfWeek.charAt(num) == '0'){
			temp[num] = '1';
			
			if(selectedInfo == 4){
				switch(num){
				case 0:
					study_mon.setTextColor(getActivity().getResources().getColor(R.color.study_color));
					break;
				case 1:
					study_tue.setTextColor(getActivity().getResources().getColor(R.color.study_color));
					break;
				case 2:
					study_wed.setTextColor(getActivity().getResources().getColor(R.color.study_color));
					break;
				case 3:
					study_thu.setTextColor(getActivity().getResources().getColor(R.color.study_color));
					break;
				case 4:
					study_fri.setTextColor(getActivity().getResources().getColor(R.color.study_color));
					break;
				case 5:
					study_sat.setTextColor(getActivity().getResources().getColor(R.color.study_color));
					break;
				case 6:
					study_sun.setTextColor(getActivity().getResources().getColor(R.color.study_color));
					break;
				}
			}
			else if(selectedInfo == 6){
				switch(num){
				case 0:
					job_mon.setTextColor(getActivity().getResources().getColor(R.color.job_color));
					break;
				case 1:
					job_tue.setTextColor(getActivity().getResources().getColor(R.color.job_color));
					break;
				case 2:
					job_wed.setTextColor(getActivity().getResources().getColor(R.color.job_color));
					break;
				case 3:
					job_thu.setTextColor(getActivity().getResources().getColor(R.color.job_color));
					break;
				case 4:
					job_fri.setTextColor(getActivity().getResources().getColor(R.color.job_color));
					break;
				case 5:
					job_sat.setTextColor(getActivity().getResources().getColor(R.color.job_color));
					break;
				case 6:
					job_sun.setTextColor(getActivity().getResources().getColor(R.color.job_color));
					break;
				}
			}
		}
		else{
			temp[num] = '0';
			
			if(selectedInfo == 4){
				switch(num){
				case 0:
					study_mon.setTextColor(Color.LTGRAY);
					break;
				case 1:
					study_tue.setTextColor(Color.LTGRAY);
					break;
				case 2:
					study_wed.setTextColor(Color.LTGRAY);
					break;
				case 3:
					study_thu.setTextColor(Color.LTGRAY);
					break;
				case 4:
					study_fri.setTextColor(Color.LTGRAY);
					break;
				case 5:
					study_sat.setTextColor(Color.LTGRAY);
					break;
				case 6:
					study_sun.setTextColor(Color.LTGRAY);
					break;
				}
			}
			else if(selectedInfo == 6){
				switch(num){
				case 0:
					job_mon.setTextColor(Color.LTGRAY);
					break;
				case 1:
					job_tue.setTextColor(Color.LTGRAY);
					break;
				case 2:
					job_wed.setTextColor(Color.LTGRAY);
					break;
				case 3:
					job_thu.setTextColor(Color.LTGRAY);
					break;
				case 4:
					job_fri.setTextColor(Color.LTGRAY);
					break;
				case 5:
					job_sat.setTextColor(Color.LTGRAY);
					break;
				case 6:
					job_sun.setTextColor(Color.LTGRAY);
					break;
				}
			}
		}
		
		dayOfWeek = String.valueOf(temp);
		Log.d("ShoutDayOfWeek", dayOfWeek);
	}
	
	private void clickDate(){
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
	    Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("select_date_time_dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);

	    DialogFragment dialogFrag = DateAndTimePicker.newInstance(0);
	    dialogFrag.setTargetFragment(this, DateAndTimePicker.DATE_AND_TIME_PICK);
	    dialogFrag.show(getFragmentManager().beginTransaction(), "select_date_time_dialog");
	}
	
	private void clickShout(){
		switch(selectedInfo){
		case 0:
			nameValuePairs.add(new BasicNameValuePair("daily_writing_content", daily_writing_content.getText().toString()));
			break;
		case 1:
			nameValuePairs.add(new BasicNameValuePair("travel_subject", travel_subject.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("travel_date", date));
			nameValuePairs.add(new BasicNameValuePair("travel_period", travel_period.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("travel_number_addmitted", travel_number_addmitted.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("travel_place", travel_place.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("travel_writing_content", travel_writing_content.getText().toString()));
			break;
		case 2:
			nameValuePairs.add(new BasicNameValuePair("exercise_subject", exercise_subject.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("exercise_start_date", date));
			nameValuePairs.add(new BasicNameValuePair("exercise_duration_per_day", exercise_duration_per_day.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("exercise_number_addmitted", exercise_number_addmitted.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("exercise_place", exercise_place.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("exercise_writing_content", exercise_writing_content.getText().toString()));
			break;
		case 3:
			nameValuePairs.add(new BasicNameValuePair("hobby_subject", hobby_subject.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("hobby_start_date", date));
			nameValuePairs.add(new BasicNameValuePair("hobby_duration_per_day", hobby_duration_per_day.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("hobby_number_addmitted", hobby_number_addmitted.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("hobby_place", hobby_place.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("hobby_writing_content", hobby_writing_content.getText().toString()));
			break;
		case 4:
			nameValuePairs.add(new BasicNameValuePair("study_subject", study_subject.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("study_start_date", date));
			nameValuePairs.add(new BasicNameValuePair("study_duration_per_day", study_duration_per_day.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("study_day_of_week", dayOfWeek));
			nameValuePairs.add(new BasicNameValuePair("study_number_addmitted", study_number_addmitted.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("study_place", study_place.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("study_writing_content", study_writing_content.getText().toString()));
			break;
		case 5:
			nameValuePairs.add(new BasicNameValuePair("question_subject", question_subject.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("question_is_completed", "0"));
			nameValuePairs.add(new BasicNameValuePair("question_writing_content", question_writing_content.getText().toString()));
			break;
		case 6:
			nameValuePairs.add(new BasicNameValuePair("job_subject", job_subject.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("job_start_date", date));
			nameValuePairs.add(new BasicNameValuePair("job_duration_per_day", job_duration_per_day.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("job_day_of_week", dayOfWeek));
			nameValuePairs.add(new BasicNameValuePair("job_number_addmitted", job_number_addmitted.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("job_pay", job_pay.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("job_place", job_place.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("job_writing_content", job_writing_content.getText().toString()));
			break;
		case 7:
			nameValuePairs.add(new BasicNameValuePair("used_subject", used_subject.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("used_product_classification", used_product_classification.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("used_buy_or_sell", buyOrSell));
			nameValuePairs.add(new BasicNameValuePair("used_is_completed", "0"));
			nameValuePairs.add(new BasicNameValuePair("used_asking_price", used_asking_price.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("used_mode_of_dealing", used_mode_of_dealing.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("used_writing_content", used_writing_content.getText().toString()));
			break;
		}
		
		Intent intent = getActivity().getIntent();
		intent.putExtra("selectedInfo", selectedInfo);
		intent.putExtra("nameValuePairs_data", nameValuePairs);
		if (bitmapStr != null && !bitmapStr.isEmpty()) {
			intent.putExtra("bitmapStr", bitmapStr);
		}
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), FragmentActivity.RESULT_OK, intent);
		getDialog().dismiss();
	}
	
	private void addPhoto(int type){
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
	    Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("select_photo_dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);
	    
	    CameraAlbumDialog.REQUEST_REAL_PHOTO = true;
	    
	    switch (type) {
	        case CameraAlbumDialog.REQUEST_SELECT_PHOTO:
	            DialogFragment dialogFrag = CameraAlbumDialog.newInstance(0);
	            dialogFrag.setTargetFragment(this, CameraAlbumDialog.REQUEST_SELECT_PHOTO);
	            dialogFrag.show(getFragmentManager().beginTransaction(), "select_photo_dialog");
	            break;
	    }
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CameraAlbumDialog.REQUEST_SELECT_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				Intent intent = getActivity().getIntent();
				
				bitmapStr = intent.getStringExtra("bitmapStr");
				
				Log.d("Select Photo", "Completed to get photo");
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.d("Select Photo", "Failed to get photo");
			}
			break;
		case DateAndTimePicker.DATE_AND_TIME_PICK:
			if (resultCode == Activity.RESULT_OK) {
				Intent intent = getActivity().getIntent();
				date = intent.getStringExtra("date") + " " + intent.getStringExtra("time");
				String datas = intent.getStringExtra("date") + " " + intent.getStringExtra("timeToShow");
				
				if(selectedInfo == 1){
					travel_date.setText(datas);
				}
				else if(selectedInfo == 2){
					exercise_start_date.setText(datas);
				}
				else if(selectedInfo == 3){
					hobby_start_date.setText(datas);
				}
				else if(selectedInfo == 4){
					study_start_date.setText(datas);
				}
				else if(selectedInfo == 6){
					job_start_date.setText(datas);
				}
				Log.d("Time And Date", "Completed to get date and time");
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.d("Time And Date", "Failed to get date and time");
			}

			break;
		}
	}
}