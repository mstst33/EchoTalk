package com.mstst33.roar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mstst33.echoproject.R;
import com.mstst33.echoproject.URLBitmapDownload;

public class Awooseong_Join_EachPersonLayout implements View.OnClickListener {
	
	private Context awooseong;
	private RelativeLayout layout;
	private ImageView mainPic;
	private TextView name;
	private TextView date;
	private Awooseong_Dialog_EachPersonInfo dialog_eachPerson;
	private Awooseong_Var_In_Join userData;
	
	public Awooseong_Join_EachPersonLayout(Context context, Awooseong_Var_In_Join userData){
		
		awooseong = context;
		this.userData = userData;
		
		Log.i("Join_EachPersonLayout","has been created");
		
		layout = (RelativeLayout)View.inflate(awooseong, R.layout.awooseong_join_relativelayout_in_scrollview, null);
		mainPic = (ImageView)layout.findViewById(R.id.awooseong_join_relative_mainPic);
		name = (TextView)layout.findViewById(R.id.awooseong_join_relative_name);
		date =(TextView)layout.findViewById(R.id.awooseong_join_relative_comment);
		
		setUserData();
		
		mainPic.setOnClickListener(this);
		name.setOnClickListener(this);
		
		Log.i("Join_EachPersonLayout","Join_object find Id");
	}
	
	private void setUserData(){
		
		if( userData.pictureData.length() > 2)
			new URLBitmapDownload(userData.pictureData, mainPic, false, true, null).start();
		
		name.setText(userData.id);
		date.setText(userData.date);
	}
	
	public RelativeLayout getLayout(){
		Log.i("Join_EachPersonLayout","Join_object going to push one layout");
		return layout;
	}

	public void recycleImage(){
		URLBitmapDownload.recycleBitmap(mainPic);
	}
	
	@Override
	public void onClick(View v) {
		//Server로부터 MainPicture와 Name 그리고 Text를 받아온다.
		
		// TODO Auto-generated method stub
	//	mainPic.setDrawingCacheEnabled(true);
	//	bmp = mainPic.getDrawingCache();
		
		//server로부터 받아온 해당사용자의 정보를 Dialog에 같이 보내주고, 
		// dialog 안에서 다시 server로 부터 값을 받아온다.
		dialog_eachPerson = new Awooseong_Dialog_EachPersonInfo(awooseong, userData.id);
		dialog_eachPerson.show();

	}
}
