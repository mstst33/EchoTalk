package com.mstst33.echoproject;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateAndTimePicker extends DialogFragment implements View.OnClickListener{
	public static final int DATE_AND_TIME_PICK = 10;
	
	DatePicker datePicker;
	TimePicker timePicker;
	Button set;
	Button cancel;
	
	LinearLayout dateTimePickerContent;
	GregorianCalendar calendar;
	String date;
	String time;
	String timeToShow;
	
	public static DateAndTimePicker newInstance(int num) {
		DateAndTimePicker dateAndTimePicker = new DateAndTimePicker();
		Bundle bundle = new Bundle();
		bundle.putInt("num", num);
		dateAndTimePicker.setArguments(bundle);

		return dateAndTimePicker;
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
		calendar = new GregorianCalendar();
		date = calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DAY_OF_MONTH);
		time = Time.HOUR + ":" + "0";
		timeToShow = calendar.get(Calendar.HOUR) + "시" + " " + "0" + "분";
		dateTimePickerContent = (LinearLayout) View.inflate(getActivity(), R.layout.date_time_picker, null);
		set = (Button) dateTimePickerContent.findViewById(R.id.date_time_btn);
		cancel = (Button) dateTimePickerContent.findViewById(R.id.date_time_cancel);
		set.setOnClickListener(this);
		cancel.setOnClickListener(this);
		
		datePicker = (DatePicker) dateTimePickerContent.findViewById(R.id.date_pick);
		datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				date = String.valueOf(year) + "." + String.valueOf(monthOfYear + 1) + "." + String.valueOf(dayOfMonth);
				Log.d("DateAndTimePicker", date);
			}
			
		});
		
		timePicker = (TimePicker) dateTimePickerContent.findViewById(R.id.time_pick);
		timePicker.setCurrentMinute(0);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener(){

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
				timeToShow = String.valueOf(hourOfDay) + "시" + " " + String.valueOf(minute) + "분";
				Log.d("DateAndTimePicker", time);
			}
			
		});
		
		if (getDialog() != null)
			getDialog().setCanceledOnTouchOutside(true);

		return dateTimePickerContent;
	}

	@Override
	public void onClick(View v) {
		Intent intent = getActivity().getIntent();
		
		if(v == set){
			intent.putExtra("date", date);
			intent.putExtra("time", time);
			intent.putExtra("timeToShow", timeToShow);
			getTargetFragment().onActivityResult(getTargetRequestCode(), FragmentActivity.RESULT_OK, intent);
			getDialog().dismiss();
		}
		else if(v == cancel){
			getTargetFragment().onActivityResult(getTargetRequestCode(), FragmentActivity.RESULT_CANCELED, intent);
			getDialog().dismiss();
		}
	}
}