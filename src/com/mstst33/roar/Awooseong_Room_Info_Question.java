package com.mstst33.roar;

import android.content.*;
import android.view.*;
import android.widget.*;

import com.mstst33.echoproject.*;

public class Awooseong_Room_Info_Question {

	private Context myContext;
	private LinearLayout linear;
	private TextView question_subject;
	private TextView question_isCompleted;
	
	public Awooseong_Room_Info_Question(Context context, Awooseong_Room_Var_Question info){
		
		myContext = context;
		linear = (LinearLayout)View.inflate(myContext, R.layout.awooseong_compact_question_info, null);
		question_subject = (TextView)linear.findViewById(R.id.awooseong_compact_question_subject);
		question_isCompleted = (TextView)linear.findViewById(R.id.awooseong_compact_question_is_completed);
	
		question_subject.setText(info.question_subject);
				
		if(Integer.parseInt(info.question_is_completed) == 1)
			question_isCompleted.setText("completed");
		else
			question_isCompleted.setText("is not completed");		
	}
	
	public LinearLayout getLinearLayout(){
		return linear;
	}
}
