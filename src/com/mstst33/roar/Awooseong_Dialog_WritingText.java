package com.mstst33.roar;

import android.app.Dialog;
import android.content.Context;
import android.util.*;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mstst33.echoproject.R;

public class Awooseong_Dialog_WritingText extends Dialog implements View.OnClickListener{

	private Context awooseong;
	private LinearLayout writingLinear;
	private TextView writingBigger;
	private String newData;
	
	public Awooseong_Dialog_WritingText(Context context) {
		
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		awooseong = context;
		
		// TODO Auto-generated constructor stub
		writingLinear = (LinearLayout)View.inflate(awooseong, R.layout.awooseong_writing_content_bigger, null);
		writingBigger = (TextView)writingLinear.findViewById(R.id.awooseong_writing_content_bigger);
		
		writingLinear.setOnClickListener(this);
		setContentView(writingLinear);
	}

	// 관련 내용 얻기
	public void setData(String data){
		newData = data;
		writingBigger.setText(newData);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dismiss();
		
	}

}
