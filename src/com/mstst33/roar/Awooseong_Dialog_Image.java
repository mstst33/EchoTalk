package com.mstst33.roar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.*;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;

import com.mstst33.echoproject.R;
import com.mstst33.echoproject.URLBitmapDownload;

public class Awooseong_Dialog_Image extends Dialog implements View.OnClickListener {
	
	private ImageView image;
	private Context awooseong;
	private int theme;
	private Awooseong_ThemeRoom info;
	
	private FrameLayout frameBase;
	private LinearLayout progress;
	
	public Awooseong_Dialog_Image(Context context, int theme, Awooseong_ThemeRoom info) {
		super(context);
		this.theme = theme;
		this.info = info;
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		awooseong = context;// TODO Auto-generated constructor stub
		
		image = (ImageView)View.inflate(awooseong, R.layout.awooseong_body_image_layout, null);
		
		frameBase = (FrameLayout)View.inflate(awooseong, R.layout.echo_framelayout, null);
		progress = (LinearLayout)View.inflate(awooseong, R.layout.echo_progress, null);		
		
		frameBase.setBackgroundColor(Color.WHITE);
		
		switch(theme){
		case Awooseong_Theme.DAILY:
			Awooseong_Room_Var_Daily A;
			if (info instanceof Awooseong_Room_Var_Daily) {
				A = (Awooseong_Room_Var_Daily) info;
			}
			else
				return;
			setImage(A.daily_pictureData);
			break;
		case Awooseong_Theme.TRAVEL:
			Awooseong_Room_Var_Travel tr;
			if (info instanceof Awooseong_Room_Var_Travel) {
				tr = (Awooseong_Room_Var_Travel)info;
			}
			else
				return;
			setImage(tr.travel_pictureData);
			break;
		case Awooseong_Theme.EXERCISE:
			Awooseong_Room_Var_Exercise exer;
			if (info instanceof Awooseong_Room_Var_Exercise) {
				exer = (Awooseong_Room_Var_Exercise) info;
			}
			else
				return;
			setImage(exer.exercise_pictureData);
			break;
		case Awooseong_Theme.HOBBY:
			Awooseong_Room_Var_Hobby hob;
			if (info instanceof Awooseong_Room_Var_Hobby) {
				hob = (Awooseong_Room_Var_Hobby)info;
			}
			else
				return;
			setImage(hob.hobby_pictureData);
			break;
		case Awooseong_Theme.STUDY:
			Awooseong_Room_Var_Study stu;
			if (info instanceof Awooseong_Room_Var_Study) {
				stu = (Awooseong_Room_Var_Study)info;
			}
			else
				return;
			setImage(stu.study_pictureData);
			break;
			
		case Awooseong_Theme.QUESTION:
			Awooseong_Room_Var_Question qe;
			if (info instanceof Awooseong_Room_Var_Question) {
				qe = (Awooseong_Room_Var_Question)info;
			}
			else
				return;
			setImage(qe.question_pictureData);
			break;
		case Awooseong_Theme.JOB:
			Awooseong_Room_Var_Job jo;
			if (info instanceof Awooseong_Room_Var_Job) {
				jo = (Awooseong_Room_Var_Job)info;
			}
			else
				return;
			setImage(jo.job_pictureData);
			break;
		case Awooseong_Theme.USED:
			Awooseong_Room_Var_Used us;
			if (info instanceof Awooseong_Room_Var_Used) {
				us = (Awooseong_Room_Var_Used)info;
			}
			else
				return;
			setImage(us.used_pictureData);
			break;
		}
		
		image.setOnClickListener(this);
		
		frameBase.addView(image);
		frameBase.addView(progress);
		
		//image.setVisibility(View.INVISIBLE);
		
		setContentView(frameBase);
	}

	public Handler DialogImageHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 9: 
				progress.setVisibility(View.INVISIBLE);
			}
		}
	};
	
	public void onBackPressed(){
		URLBitmapDownload.recycleBitmap(image);
		Log.i("ImageDialog", "Image is recycled");
		super.onBackPressed();
	}
	public void setImage(String uri) {
		
		if (uri.length() < 2 && Integer.parseInt(uri) == 0) {
			Log.i("Dialog_Image", "image is null");
			dismiss();

		} else {
			new URLBitmapDownload(uri, image, true, false, DialogImageHandler).start();
			Log.i("Dialog_Image", "body Image is being downloaded");
			//new CheckThread().start();
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		URLBitmapDownload.recycleBitmap(image);
		image = null;
		dismiss();
	}
}
