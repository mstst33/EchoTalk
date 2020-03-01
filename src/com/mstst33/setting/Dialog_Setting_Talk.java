package com.mstst33.setting;

import com.mstst33.echoproject.*;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;

public class Dialog_Setting_Talk extends Dialog implements View.OnClickListener{

	LinearLayout settingTalk;
	Context context;
	
	Button version;
	Button removeId;
	
	public Dialog_Setting_Talk(Context context) {
		super(context);
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated constructor stub
		
		settingTalk = (LinearLayout) View.inflate(context, R.layout.setting_talk_layout, null);
		
		version = (Button)settingTalk.findViewById(R.id.setting_talk_version);
		removeId = (Button)settingTalk.findViewById(R.id.setting_talk_removeId);
		setContentView(settingTalk);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v == version){
			
		}
		if( v == removeId){
			
		}
	}

}
