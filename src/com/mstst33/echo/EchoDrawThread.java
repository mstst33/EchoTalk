package com.mstst33.echo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import com.mstst33.echoproject.MainActivity;
import com.mstst33.echoproject.R;

public class EchoDrawThread extends Thread{
	private ArrayList<EchoView> eViewList;
	private ArrayList<EchoBall> eBallList;
	private SurfaceHolder sHolder;
	
	public boolean isExit;
	private EchoBall eBall;
	private Canvas canvas;
	private Paint ballPnt;
	private Paint textPnt;
	
	private Bitmap theme;
	private final Bitmap join;
	private final Bitmap echo;
	// private final Bitmap newState;
	private final Bitmap background;
	
	private final Bitmap ballon;
	private final Bitmap ballon2;
	
	private final Bitmap life_ballon;
	private final Bitmap tour_ballon;
	private final Bitmap sports_ballon;
	private final Bitmap hobby_ballon;
	private final Bitmap study_ballon;
	private final Bitmap question_ballon;
	private final Bitmap job_ballon;
	private final Bitmap reuse_ballon;
	
	EchoDrawThread(Context context){
		this.eViewList = new ArrayList<EchoView>();
		this.eBallList = null;
		this.sHolder = null;
		isExit = false;
		
		ballPnt = new Paint();
		ballPnt.setAntiAlias(true);
		
		textPnt = new Paint();
		textPnt.setAntiAlias(true);
		textPnt.setColor(Color.BLUE);
		textPnt.setTextAlign(Align.CENTER);
		
		join = BitmapFactory.decodeResource(context.getResources(), R.drawable.people);
		echo = BitmapFactory.decodeResource(context.getResources(), R.drawable.talk);
		// newState = BitmapFactory.decodeResource(context.getResources(), R.drawable.new_icon);
		background = BitmapFactory.decodeResource(context.getResources(), R.drawable.speak_back);
		
		ballon = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon);
		ballon2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon2);
		
		life_ballon = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_balloon);
		tour_ballon = BitmapFactory.decodeResource(context.getResources(), R.drawable.tour_balloon);
		sports_ballon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sports_balloon);
		hobby_ballon = BitmapFactory.decodeResource(context.getResources(), R.drawable.hobby_balloon);
		study_ballon = BitmapFactory.decodeResource(context.getResources(), R.drawable.study_balloon);
		question_ballon = BitmapFactory.decodeResource(context.getResources(), R.drawable.question_balloon);
		job_ballon = BitmapFactory.decodeResource(context.getResources(), R.drawable.job_balloon);
		reuse_ballon = BitmapFactory.decodeResource(context.getResources(), R.drawable.reuse_balloon);
	}
	
	public void run() {
		while (!isExit) {
			for (int j = 0; j < eViewList.size(); ++j) {
				eBallList = eViewList.get(j).getEBallList();
				sHolder = eViewList.get(j).getHolder();
				
				/*
				// Animate balls
				for (int i = 0; i < eBallList.size(); ++i) {
					eBall = eBallList.get(i);
				}*/
				
				// Draw new balls
				synchronized (sHolder) {
					canvas = sHolder.lockCanvas();
					
					if (canvas == null)
						break;
					
					canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
					canvas.drawBitmap(background , null, new RectF(0, 0, MainActivity.displayWidth, MainActivity.viewHeight), null);
					for (int i = 0; i < eBallList.size(); ++i) {
						eBall = eBallList.get(i);
						Draw();

						if (isExit)
							break;
					}

					sHolder.unlockCanvasAndPost(canvas);
				}
			}

			try {
				Thread.sleep(EchoView.THREAD_DELAY);
			} catch (InterruptedException e) {
			}
		}
		
		if(!join.isRecycled())
			join.recycle();
		
		if(!echo.isRecycled())
			echo.recycle();
		
		// newState.recycle();
		if(!background.isRecycled())
			background.recycle();
		
		if(!ballon.isRecycled())
			ballon.recycle();
		
		if(!ballon2.isRecycled())
			ballon2.recycle();
		
		if(!life_ballon.isRecycled())
			life_ballon.recycle();
		
		if(!tour_ballon.isRecycled())
			tour_ballon.recycle();
		
		if(!sports_ballon.isRecycled())
			sports_ballon.recycle();
		
		if(!hobby_ballon.isRecycled())
			hobby_ballon.recycle();
		
		if(!study_ballon.isRecycled())
			study_ballon.recycle();
		
		if(!question_ballon.isRecycled())
			question_ballon.recycle();
		
		if(!job_ballon.isRecycled())
			job_ballon.recycle();
		
		if(!reuse_ballon.isRecycled())
			reuse_ballon.recycle();
	}
	
	public void addEViewItem(EchoView eView){
		eViewList.add(eView);
	}
	
	public void removeEViewItem(EchoView eView){
		eViewList.remove(eView);
	}
	
	private void recycleBitmap(ImageView iv) {
        Drawable d = iv.getDrawable();
        if (d instanceof BitmapDrawable) {
            Bitmap b = ((BitmapDrawable)d).getBitmap();
            b.recycle();
        }
         
        d.setCallback(null);
    }
	
	public void Draw(){
		Bitmap bal;
		
		if(eBall.info.get(6).equals("true")){
			bal = ballon;
		}
		else{
			bal = ballon2;
		}
		
		canvas.drawBitmap(bal, null, new RectF((float) (eBall.x - eBall.rad * 1.2), (float) (eBall.y - eBall.rad * 1.3),
				(float) (eBall.x + eBall.rad * 1.2), (float) (eBall.y + eBall.rad * 1.3)), null);
		
		textPnt.setTextSize(eBall.rad / 3);
		
		float dp = (float) (eBall.rad / 2.5);
		float margin = eBall.rad / 2;
		String[] date = eBall.info.get(0).substring(5).split("\\.");
		if(date[0].startsWith("0"))
			date[0] = date[0].substring(1);
		
		if(date[1].startsWith("0"))
			date[1] = date[1].substring(1);
		
		switch(Integer.parseInt(eBall.info.get(4))){
		case 0:
			theme = life_ballon;
			break;
		case 1:
			theme = tour_ballon;
			break;
		case 2:
			theme = sports_ballon;
			break;
		case 3:
			theme = hobby_ballon;
			break;
		case 4:
			theme = study_ballon;
			break;
		case 5:
			theme = question_ballon;
			break;
		case 6:
			theme = job_ballon;
			break;
		case 7:
			theme = reuse_ballon;
			break;
		}
		
		canvas.drawBitmap(theme, null, new RectF((float) (eBall.x - eBall.rad / 1.8), (float) (eBall.y - eBall.rad / 1.8 - dp),
				(float)(eBall.x + eBall.rad / 1.8), (float)(eBall.y + eBall.rad / 1.8 - dp)), null);
		canvas.drawText(date[0] + "." + date[1], eBall.x, (float) (eBall.y - margin * 1.8), textPnt);
		canvas.drawBitmap(join, null, new RectF((float) (eBall.x - margin * 1.5), (float) (eBall.y + margin / 2.5),
				(float) (eBall.x - margin * 1.5) + dp, (float) (eBall.y + margin / 2.5) + dp), null);
		canvas.drawText(eBall.info.get(1), eBall.x - margin / 3, eBall.y + margin, textPnt);
		canvas.drawBitmap(echo, null, new RectF(eBall.x, (float) (eBall.y + margin / 2.5),
				eBall.x + dp, (float) (eBall.y + margin / 2.5) + dp), null);
		canvas.drawText(eBall.info.get(2), (float) (eBall.x + margin * 1.2), eBall.y + margin, textPnt);
		
		if(eBall.info.get(3).equals("true")){
			//canvas.drawBitmap(newState, null, new RectF(eBall.x + margin, eBall.y - margin * 2, 
					//eBall.x + margin + dp, eBall.y - margin * 2 + dp), null);
		}
	}
}