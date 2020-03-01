package com.mstst33.roar;

import android.content.*;
import android.view.*;
import android.widget.*;

import com.mstst33.echoproject.*;

public class Awooseong_Room_Info_Hobby {

	private Context myContext;
	private LinearLayout linear;
	private TextView hobby_subject;
	private TextView hobby_startDate;
	private TextView hobby_place;
	
	public Awooseong_Room_Info_Hobby(Context context, Awooseong_Room_Var_Hobby info){
		
		myContext = context;
		linear = (LinearLayout)View.inflate(myContext, R.layout.awooseong_compact_hobby_info, null);
		hobby_subject = (TextView)linear.findViewById(R.id.awooseong_compact_hobby_subject);
		hobby_startDate = (TextView)linear.findViewById(R.id.awooseong_compact_hobby_start_date);
		hobby_place = (TextView)linear.findViewById(R.id.awooseong_compact_hobby_place);
		 
		hobby_subject.setText(info.hobby_subject);
		hobby_startDate.setText(info.hobby_startDate);
		hobby_place.setText(info.hobby_place);
	}
	
	public LinearLayout getLinearLayout(){
		return linear;
	}
}
