package com.mstst33.roar;

import android.content.*;
import android.view.*;
import android.widget.*;

import com.mstst33.echoproject.*;

public class Awooseong_Room_Info_Job {

	private Context myContext;
	private LinearLayout linear;
	private TextView job_subject;
	private TextView job_startDate;
	private TextView job_pay;
	private TextView job_place;
	
	public Awooseong_Room_Info_Job(Context context, Awooseong_Room_Var_Job info){
		
		myContext = context;
		linear = (LinearLayout)View.inflate(myContext, R.layout.awooseong_compact_job_info, null);
		job_subject = (TextView)linear.findViewById(R.id.awooseong_compact_job_subject);
		job_startDate = (TextView)linear.findViewById(R.id.awooseong_compact_job_start_date);
		job_pay = (TextView)linear.findViewById(R.id.awooseong_compact_job_pay);
		job_place = (TextView)linear.findViewById(R.id.awooseong_compact_job_place);
		 
		job_subject.setText(info.job_subject);
		job_startDate.setText(info.job_startDate);
		job_pay.setText(info.job_pay);
		job_place.setText(info.job_place);		
	}
	
	public LinearLayout getLinearLayout(){
		return linear;
	}
}
