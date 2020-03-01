package com.mstst33.roar;

import android.content.*;
import android.view.*;
import android.widget.*;

import com.mstst33.echoproject.*;

public class Awooseong_Room_Info_Study {

	private Context myContext;
	private LinearLayout linear;
	
	// StudyInfo Variable
	private TextView study_subject;
	private TextView study_startDate;
	private TextView study_numberAddmitted;
	private TextView study_place;;
	
	public Awooseong_Room_Info_Study(Context context, Awooseong_Room_Var_Study info){
		
		myContext = context;
		linear = (LinearLayout)View.inflate(myContext, R.layout.awooseong_compact_study_info, null);
		study_subject = (TextView)linear.findViewById(R.id.awooseong_compact_study_subject);
		study_startDate = (TextView)linear.findViewById(R.id.awooseong_compact_study_start_date);
		study_numberAddmitted = (TextView)linear.findViewById(R.id.awooseong_compact_study_number_addmitted);
		study_place = (TextView)linear.findViewById(R.id.awooseong_compact_study_place);
		
		study_subject.setText(info.study_subject);
		study_startDate.setText(info.study_startDate);	
		study_numberAddmitted.setText(info.study_numPeople);
		study_place.setText(info.study_place);		
	}
	
	public LinearLayout getLinearLayout(){
		return linear;
	}

}
