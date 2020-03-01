package com.mstst33.roar;

import com.mstst33.echoproject.*;

import android.content.*;
import android.view.*;
import android.widget.*;

public class Awooseong_Room_Info_Exercise {
	
	private Context myContext;
	private LinearLayout linear;
	private TextView exercise_subject;
	private TextView exercise_startDate;
	private TextView exercise_place;
	
	public Awooseong_Room_Info_Exercise(Context context, Awooseong_Room_Var_Exercise info){
		
		myContext = context;
		linear = (LinearLayout)View.inflate(myContext, R.layout.awooseong_compact_exercise_info, null);
		exercise_subject = (TextView)linear.findViewById(R.id.awooseong_compact_exercise_subject);
		exercise_startDate = (TextView)linear.findViewById(R.id.awooseong_compact_exercise_start_date);
		exercise_place = (TextView)linear.findViewById(R.id.awooseong_compact_exercise_place);
		 
		exercise_subject.setText(info.exercise_subject);
		exercise_startDate.setText(info.exercise_startDate);
		exercise_place.setText(info.exercise_place);
	}
	
	public LinearLayout getLinearLayout(){
		return linear;
	}
}
