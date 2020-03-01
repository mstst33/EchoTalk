package com.mstst33.echoproject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class URLBitmapDownload extends Thread{
	private ArrayList<String> fileName;
	private ArrayList<ImageView> view;
	private int waitingTime;
	
	private String fileNames;
	private ImageView views;
	private boolean isArray;
	private boolean isExtended;
	private boolean isUserPic;
	private Handler viewHandler;
	
	// It's easy to use this class for downloading bitmap from server.
	// Just put filename you already got from server and view you're gonna show
	public URLBitmapDownload(ArrayList<String> fileName, ArrayList<ImageView> view){
		this.fileName = fileName;
		this.view = view;
		this.waitingTime = 5000;
		isArray = true;
		
		// When updating, use this. Setting daemon thread in updating thread is better.
		// Looper.prepare();
		// Looper.loop();
	}
	
	public URLBitmapDownload(String fileName, ImageView view, boolean isExtended, boolean isUserPic, Handler handler){
		fileNames = fileName;
		views = view;
		isArray = false;
		this.isExtended = isExtended;
		this.isUserPic = isUserPic;
		viewHandler = handler;
		
	}
	
	@Override
	public void run() {
		if(isArray){
			for (int i = 0; i < fileName.size(); ++i) {
				boolean isSucceeded = false;
				String URLName = BasicInfo.SERVER_IMAGE_ADDRESS + fileName.get(i);

				URL url = null;
				Bitmap bitmap = null;
				try {
					url = new URL(URLName);

					// Sometimes, we can't get downloading even if there is definitely URL.
					// We need this connection to wait more until downloading file.
					URLConnection con = url.openConnection();
					con.setReadTimeout(waitingTime);
					con.setConnectTimeout(waitingTime);
					//BitmapFactory.Options options = new BitmapFactory.Options();
					//options.inSampleSize = 4;
					
					//FileOutputStream fos= new FileOutputStream(getFilePath());
					
					//bitmap = BitmapFactory.decodeStream(con.getInputStream(), null, options);
					int width = bitmap.getWidth();
					int height = bitmap.getHeight();
					Bitmap resized;
					if( isExtended )
						resized = Bitmap.createScaledBitmap(bitmap, (int)(width*0.1), (int)(height*0.1), true);
					else
						resized = Bitmap.createScaledBitmap(bitmap, width, height, true);

					isSucceeded = true;
				} catch (MalformedURLException e) {
					bitmap = null;
				} catch (IOException e) {
					bitmap = null;
				}

				// View can't be changed in Thread. So, we need this way to change view
				Message msg = Message.obtain();
				msg.what = (isSucceeded) ? 1 : 0;
				msg.obj = bitmap;
				msg.arg1 = i;
				handler.sendMessage(msg);
			}
		}
		else{
			boolean isSucceeded = false;
			String URLName = BasicInfo.SERVER_IMAGE_ADDRESS + fileNames;

			URL url = null;
			Bitmap bitmap = null;
			Bitmap resized = null;
			try {
				url = new URL(URLName);
				
				// Sometimes, we can't get downloading even if there is definitely URL.
				// We need this connection to wait more until downloading file.
				URLConnection con = url.openConnection();
				con.setReadTimeout(waitingTime);
				con.setConnectTimeout(waitingTime);
				bitmap = BitmapFactory.decodeStream(con.getInputStream());
			
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				
				if( !isExtended )
					resized = Bitmap.createScaledBitmap(bitmap, (int)(width*0.4), (int)(height*0.4), true);
				else if( isUserPic ){
					resized = Bitmap.createScaledBitmap(bitmap, (int)(width*0.2), (int)(height*0.2), true);
				}
				else
					resized = Bitmap.createScaledBitmap(bitmap, width, height, true);
				
				//bitmap.recycle();
				
				isSucceeded = true;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				bitmap = null;
			} catch (IOException e) {
				e.printStackTrace();
				bitmap = null;
			} catch (Exception e){
				e.printStackTrace();
				bitmap = null;
			}

			// View can't be changed in Thread. So, we need this way to change view
			Message msg = Message.obtain();
			msg.what = (isSucceeded) ? 1 : 0;
			msg.obj = resized;
			handler.sendMessage(msg);
		}
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (isArray) {
				synchronized (view) {
					if (msg.what == 1) {
						Bitmap resized = (Bitmap) msg.obj;
						resized.compress(CompressFormat.JPEG, 0, null);
						view.get(msg.arg1).setImageBitmap(resized);
						//view.get(msg.arg1).setImageBitmap((Bitmap) msg.obj);
					} else {
						Log.i("URLBitmapDownload", "Failed to download Image " + String.valueOf(msg.arg1));
					}
				}
			} else {
				synchronized (views) {
					if (msg.what == 1) {
						views.setImageBitmap((Bitmap) msg.obj);
						
						if(viewHandler != null)
							viewHandler.sendEmptyMessage(9);
					/*	if(views.getVisibility() != View.VISIBLE)
							views.setVisibility(View.VISIBLE);
							*/
					} else {
						Log.i("URLBitmapDownload", "Failed to download Image");
					}
				}
			}
		}
	};
	
	public static void recycleBitmap(ImageView iv) {
        Drawable d = iv.getDrawable();
        if (d instanceof BitmapDrawable) {
            Bitmap b = ((BitmapDrawable)d).getBitmap();
            if(!b.isRecycled())
            	b.recycle();
            
            b = null;
        }
         
        d.setCallback(null);
    }
	
	public static synchronized String getFilePath(){
		
		String sdCard = Environment.getExternalStorageState();
		File file = null;
		
		if( !sdCard.equals(Environment.MEDIA_MOUNTED)){
			file = Environment.getRootDirectory();
		}
		else
		{
			file = Environment.getExternalStorageDirectory();
		}
		
		String dir = file.getAbsolutePath() + String.format("/echoImage");
		
		file = new File(dir);
		if( !file.exists() ){
			file.mkdirs();
		}
		
		return dir;
	}
}
