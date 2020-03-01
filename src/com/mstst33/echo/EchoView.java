package com.mstst33.echo;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mstst33.echoproject.MainActivity;
import com.mstst33.echoproject.R;
import com.mstst33.echoproject.TodayDate;

public class EchoView extends SurfaceView implements SurfaceHolder.Callback{
	public final static int THREAD_DELAY = 10;
	public final static int MAX_NUM_BALL = 9;
	public final int width = MainActivity.displayWidth / 3;
	public final int height = (int) (MainActivity.viewHeight / 3);
	
	public ArrayList<EchoBall> eBallList;
	
	private boolean isDown;
	private int selectedItem;
	private String joinNum;
	private String echoNum;
	private String isNew;
	public int curNumBall;
	Random rand;
	
	private float rad1;
	private float rad2;
	private float rad3;
	
	final String daily_string;
	final String travel_string;
	final String exercise_string;
	final String hobby_string;
	final String study_string;
	final String question_string;
	final String job_string;
	final String used_string;
	
	private SurfaceHolder sHolder;
	private Bitmap background;
	
	private int tapWidth, tapHeight;
	
	public EchoView(Context context) {
		super(context);
		
		// setZOrderOnTop(true);
		
		isDown = false;
		selectedItem = -1;
		joinNum = "0";
		echoNum = "0";
		isNew = "true";
		curNumBall = 0;
		rand = new Random();
		
		rad1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
		rad2 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
		rad3 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());
		
		daily_string = context.getResources().getString(R.string.daily);
		travel_string = context.getResources().getString(R.string.travel);
		exercise_string = context.getResources().getString(R.string.exercise);
		hobby_string = context.getResources().getString(R.string.hobby);
		study_string = context.getResources().getString(R.string.study);
		question_string = context.getResources().getString(R.string.question);
		job_string = context.getResources().getString(R.string.job);
		used_string = context.getResources().getString(R.string.used);
		
		// Designate myself as callback to process event that is happened when there is something changed
		sHolder = this.getHolder();
		sHolder.addCallback(this);
		// sHolder.setFormat(PixelFormat.TRANSLUCENT);
		
		eBallList = new ArrayList<EchoBall>();
	}
	
	private NoticeEchoViewListener m_Listener;
	
	public interface NoticeEchoViewListener {
        public void onNoticeEchoView(boolean isAddPage, int selectedInfo, boolean isEcho, String writing_num);
    }
	
	public void setOnNoticeEchoListener(NoticeEchoViewListener m_Listener){
		this.m_Listener = m_Listener;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		tapWidth  = this.getWidth();
		tapHeight = this.getHeight();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	
	public boolean onTouchEvent(MotionEvent event){
		final int action = event.getAction();
		
		if(action == MotionEvent.ACTION_DOWN){
			isDown = true;
			
			return true;
		}
		else if(action == MotionEvent.ACTION_UP){
			if(isDown){
				isDown = false;
				
				float fX = event.getX();
				float fY = event.getY();
				
				selectedItem = checkImageClicked(fX, fY);
				
				if(selectedItem > -1){
					Log.d("EchoView", "Image Clicked " + selectedItem);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void createEchoBall(int selectedInfo, boolean isEcho, String writing_num){
		int x = 0, y = 0;
		float rad = 0;
		boolean isPageCreated = false;
		
		if(Integer.valueOf(joinNum) + Integer.valueOf(echoNum) < 5)
			rad = rad1;
		else if(Integer.valueOf(joinNum) + Integer.valueOf(echoNum) < 10)
			rad = rad2;
		else if(Integer.valueOf(joinNum) + Integer.valueOf(echoNum) < 15)
			rad = rad3;
		
		++curNumBall;
		
		switch(curNumBall){
		case 1:
			x = width / 2;
			y = height / 2;
			break;
		case 2:
			x = width + width / 2;
			y = height / 2;
			break;
		case 3:
			x = 2 * width + width / 2;
			y = height / 2;
			break;
		case 4:
			x = width / 2;
			y = height + height / 2;
			break;
		case 5:
			x = width + width / 2;
			y = height + height / 2;
			break;
		case 6:
			x = 2 * width + width / 2;
			y = height + height / 2;
			break;
		case 7:
			x = width / 2;
			y = 2 * height + height / 2;
			break;
		case 8:
			x = width + width / 2;
			y = 2 * height + height / 2;
			break;
		case 9:
			x = 2 * width + width / 2;
			y = 2 * height + height / 2;
			break;
		default:
			curNumBall = 9;
			isPageCreated = true;
			
			if(m_Listener != null){
				Log.d("EchoView", "Add new page");
				this.m_Listener.onNoticeEchoView(true, selectedInfo, isEcho, writing_num);
			}
			break;
		}
		
		/*
		switch(curNumBall){
		case 1:
			x = (int) (rad) + rand.nextInt(width - (int) (rad) * 2) + 1  + width * 0;
			y = (int) (rad) + rand.nextInt(height - (int) (rad) * 2) + 1 + height * 0;
			break;
		case 2:
			x = (int) (rad) + rand.nextInt(width - (int) (rad) * 2) + 1  + width * 1;
			y = (int) (rad) + rand.nextInt(height - (int) (rad) * 2) + 1 + height * 0;
			break;
		case 3:
			x = (int) (rad) + rand.nextInt(width - (int) (rad) * 2) + 1  + width * 2;
			y = (int) (rad) + rand.nextInt(height - (int) (rad) * 2) + 1 + height * 0;
			break;
		case 4:
			x = (int) (rad) + rand.nextInt(width - (int) (rad) * 2) + 1  + width * 0;
			y = (int) (rad) + rand.nextInt(height - (int) (rad) * 2) + 1 + height * 1;
			break;
		case 5:
			x = (int) (rad) + rand.nextInt(width - (int) (rad) * 2) + 1  + width * 1;
			y = (int) (rad) + rand.nextInt(height - (int) (rad) * 2) + 1 + height * 1;
			break;
		case 6:
			x = (int) (rad) + rand.nextInt(width - (int) (rad) * 2) + 1  + width * 2;
			y = (int) (rad) + rand.nextInt(height - (int) (rad) * 2) + 1 + height * 1;
			break;
		case 7:
			x = (int) (rad) + rand.nextInt(width - (int) (rad) * 2) + 1  + width * 0;
			y = (int) (rad) + rand.nextInt(height - (int) (rad) * 2) + 1 + height * 2;
			break;
		case 8:
			x = (int) (rad) + rand.nextInt(width - (int) (rad) * 2) + 1  + width * 1;
			y = (int) (rad) + rand.nextInt(height - (int) (rad) * 2) + 1 + height * 2;
			break;
		case 9:
			x = (int) (rad) + rand.nextInt(width - (int) (rad) * 2) + 1  + width * 2;
			y = (int) (rad) + rand.nextInt(height - (int) (rad) * 2) + 1 + height * 2;
			break;
		default:
			curNumBall = 9;
			isPageCreated = true;
			
			if(m_Listener != null){
				Log.d("EchoView", "Add new page");
				this.m_Listener.onNoticeEchoView(true, selectedInfo, isEcho, writing_num);
			}
			break;
		}*/
		
		if (!isPageCreated) {
			ArrayList<String> info = new ArrayList<String>();
			info.add(TodayDate.getDate());
			info.add(joinNum);
			info.add(echoNum);
			info.add(isNew);
			info.add(String.valueOf(selectedInfo));
			info.add(writing_num);
			info.add(String.valueOf(isEcho));

			if (isEcho)
				eBallList.add(new EchoBall(x, y, Color.BLUE, rad, info));
			else
				eBallList.add(new EchoBall(x, y, Color.RED, rad, info));
			
			if(m_Listener != null)
				this.m_Listener.onNoticeEchoView(false, selectedInfo, isEcho, writing_num);
		}
	}
	
	public ArrayList<EchoBall> getEBallList(){
		return eBallList;
	}
	
	public int checkImageClicked(float x, float y){
		for(int i = 0; i < eBallList.size(); ++i){
			if(eBallList.get(i).contain(x, y)){
				return i;
			}
		}
		
		return -1;
	}
	
	public void setCurNumBall(int num){
		curNumBall = num;
	}
}