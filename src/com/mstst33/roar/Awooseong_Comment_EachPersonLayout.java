package com.mstst33.roar;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mstst33.echoproject.*;

public class Awooseong_Comment_EachPersonLayout implements View.OnClickListener{
	
	private Context awooseong;
	private RelativeLayout layout;
	private ImageView mainPic;
	private ImageView imageDelete;
	private TextView name;
	private TextView comment;
	private Awooseong_Dialog_EachPersonInfo dialog_eachPerson;
	private Awooseong_Var_In_Comment userData;
	private Handler handler;
	private boolean isMaster;
	
	public Awooseong_Comment_EachPersonLayout(Context context, Awooseong_Var_In_Comment userData, Handler handler){
		
		awooseong = context;
		this.userData = userData;
		this.handler = handler;
		if(userData.id.equals(BasicInfo.USER_ID))
			isMaster = true;
		else
			isMaster = false;
		
		layout = (RelativeLayout)View.inflate(awooseong, R.layout.awooseong_reply_relativelayout_in_scrollview, null);
		mainPic = (ImageView)layout.findViewById(R.id.awooseong_reply_relative_mainPic);
		name = (TextView)layout.findViewById(R.id.awooseong_reply_relative_name);
		comment = (TextView)layout.findViewById(R.id.awooseong_reply_relative_replyContent);
		imageDelete = (ImageView)layout.findViewById(R.id.awooseong_reply_relative_deleteImg);
		
		if(!isMaster)
			imageDelete.setVisibility(View.GONE);
		
		setUserData();
	
		imageDelete.setOnClickListener(this);
		mainPic.setOnClickListener(this);
		name.setOnClickListener(this);
	}
	
	Handler eachPersonHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 9:
				Log.i("EachPerson in Comment", "Image is completed");
			}
		}
	};
	
	private void setUserData(){
		
		if( userData.pictureData.length() > 2)
			new URLBitmapDownload(userData.pictureData, mainPic, false, true, eachPersonHandler).start();
		
		name.setText(userData.id);
		comment.setText(userData.comment);
	}

	public RelativeLayout getLayout(){
		Log.i("awooseong","Reply_each_layout going to push one layout");
		return layout;
	}
	
	public void recycleImage(){
		URLBitmapDownload.recycleBitmap(mainPic);
	}

	@Override
	public void onClick(View v) {
		//server로부터 받아온 해당사용자의 정보를 Dialog에 같이 보내주고, 
		// dialog 안에서 다시 server로 부터 값을 받아온다.
		if( v == mainPic || v == name ){
			dialog_eachPerson = new Awooseong_Dialog_EachPersonInfo(awooseong, userData.id);
			dialog_eachPerson.show();			
		}
		else if( v == imageDelete ){
			
			new AlertDialog.Builder(awooseong)
			.setTitle("댓글")
			.setMessage("댓글을 지우실건가요?")
			.setPositiveButton("예", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Message msg = Message.obtain();
					msg.what = 1;
					msg.obj = userData;
					handler.sendMessage(msg);
				}
			})
			.setNegativeButton("아니요", null)
			.show();
		}
	}
}
