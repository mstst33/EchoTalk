package com.mstst33.roar;

import android.content.*;
import android.view.*;
import android.widget.*;

import com.mstst33.echoproject.*;

public class Awooseong_Room_Info_Travel {
	
	private Context myContext;
	private LinearLayout linear;
	
	//TravelInfo Variable
	private TextView travel_subject;
	private TextView travel_date;
	private TextView travel_place;
	
	public Awooseong_Room_Info_Travel(Context context, Awooseong_Room_Var_Travel info){
		
		myContext = context;
		linear = (LinearLayout)View.inflate(myContext, R.layout.awooseong_compact_travel_info, null);
		travel_subject = (TextView)linear.findViewById(R.id.awooseong_compact_travel_subject);
		travel_date = (TextView)linear.findViewById(R.id.awooseong_compact_travel_date);
		travel_place = (TextView)linear.findViewById(R.id.awooseong_compact_travel_place);

		travel_subject.setText(info.travel_subject);
		travel_date.setText(info.travel_date);
		travel_place.setText(info.travel_place);
	}
	
	public LinearLayout getLinearLayout(){
		return linear;
	}
}
