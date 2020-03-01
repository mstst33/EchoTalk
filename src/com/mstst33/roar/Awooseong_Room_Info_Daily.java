package com.mstst33.roar;

import com.mstst33.echoproject.*;

import android.content.*;
import android.view.*;
import android.widget.*;

public class Awooseong_Room_Info_Daily {

	private TextView roomCommentOfDaily;
	private LinearLayout linear;
	private Context myContext;
	
	
	Awooseong_Room_Info_Daily(Context context, Awooseong_Room_Var_Daily info){
		
		myContext = context;
		
		linear = (LinearLayout)View.inflate(myContext, R.layout.awooseong_compact_daily_info, null);
		roomCommentOfDaily = (TextView)linear.findViewById(R.id.awooseong_compact_daily_writing_content);
		roomCommentOfDaily.setText(info.daily_content);
	}
	
	public LinearLayout getLinearLayout(){
		return linear;
	}
}
