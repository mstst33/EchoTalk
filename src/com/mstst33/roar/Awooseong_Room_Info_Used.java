package com.mstst33.roar;

import android.content.*;
import android.view.*;
import android.widget.*;

import com.mstst33.echoproject.*;

public class Awooseong_Room_Info_Used {

	private Context myContext;
	private LinearLayout linear;
	
	//UsedInfo Variable
	private TextView used_subject;
	private TextView used_buy;
	private TextView used_sell;
	private TextView used_askingPrice;	
	
	public Awooseong_Room_Info_Used(Context context, Awooseong_Room_Var_Used info){
		
		myContext = context;
		linear = (LinearLayout)View.inflate(myContext, R.layout.awooseong_compact_used_info, null);
		used_subject = (TextView)linear.findViewById(R.id.awooseong_compact_used_subject);
		used_buy = (TextView)linear.findViewById(R.id.awooseong_compact_used_buy);
		used_sell = (TextView)linear.findViewById(R.id.awooseong_compact_used_sell);
		used_askingPrice = (TextView)linear.findViewById(R.id.awooseong_compact_used_asking_price);
		
		used_subject.setText(info.used_subject);
		used_askingPrice.setText(info.used_price);
		
		if(Integer.parseInt(info.used_isSell) == 1)
			{
				used_buy.setText(null);
			}
		
		else{
				used_sell.setText(null);			
			}
			
	}
	
	public LinearLayout getLinearLayout(){
		return linear;
	}

}
