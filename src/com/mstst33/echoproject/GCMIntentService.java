package com.mstst33.echoproject;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

public class GCMIntentService extends GCMBaseIntentService {
	private final String GCM_REG_URL = BasicInfo.SERVER_ADDRESS + "GCMRegistration_Id.php";
	public static final int GCM_NOTIFICATION_NUM = 0;
	private boolean isAppTop;
	
	String gcm_msg = null;
	String gcm_sender_id = null;
	String selectedInfo = null;
	String writing_num = null;
	
	Context context;
	public GCMIntentService() {
		super(BasicInfo.PROJECT_ID);
	}

	private static final String TAG = "GCMIntentService";

	public void onReceive(Context context, Intent intent) {
	}

	@Override
	protected void onRegistered(Context arg0, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		
		GCM3rdPartyRequest registration_red_id = new GCM3rdPartyRequest();
		registration_red_id.Setting(GCM_REG_URL, registrationId, BasicInfo.USER_ID, null, null, null);
		
		BasicInfo.REG_ID = registrationId;
		BasicInfo.IS_REGISTERED_ID = true;
		
		registration_red_id.start();
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		if (GCMRegistrar.isRegistered(arg0)) {
			Log.i(TAG, "unregistered = " + arg1);
        	GCMRegistrar.unregister(arg0);
        }
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		Log.i(TAG, "CONTEXT : = " + arg0);
		Log.i(TAG, "New Message!");
		Log.i(TAG, String.valueOf(BasicInfo.CURRENT_MESSAGE_NUM));
		if (arg1.getAction().equals("com.google.android.c2dm.intent.RECEIVE") && BasicInfo.IS_GET_MESSAGE
				&& BasicInfo.CURRENT_MESSAGE_NUM < BasicInfo.MAX_MESSAGE_NUM) {
			SharedPreferences.Editor edt = getSharedPreferences("Echo", Context.MODE_PRIVATE).edit();
			edt.putInt("currentMessageNum", ++BasicInfo.CURRENT_MESSAGE_NUM);
			edt.commit();
			
			Log.i("GCM", "Add current message num: " + BasicInfo.CURRENT_MESSAGE_NUM);
			
			gcm_sender_id = arg1.getExtras().getString("id");
			selectedInfo = arg1.getExtras().getString("selectedInfo");
			writing_num = arg1.getExtras().getString("writing_num");
			gcm_msg = arg1.getExtras().getString("msg");
			context = arg0;
			Log.d(TAG, gcm_sender_id + ": " + gcm_msg);
			
			/*
			try {
				Vibrator vibrator = (Vibrator) arg0.getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(1000);
			} catch (Exception e) {
				Log.e(TAG, "[onMessage] Exception : " + e.getMessage());
			}*/
			
			GET_GCM();
		}
	}

	public void GET_GCM() {

		Thread thread = new Thread(new Runnable() {
			public void run() {
				getIsAppTopOrNot();
				
				handler.sendEmptyMessage(0);
			}
		});
		thread.start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			//Context context = getApplicationContext();
			setNotification();
		}
	};
	
	private void getIsAppTopOrNot(){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runList = am.getRunningTasks(10);
		ComponentName name = runList.get(0).topActivity;
		String className = name.getClassName();

		if(className.contains("com.mstst33.echoproject")) {
			isAppTop = true;
			Log.d(TAG, "is APP top");
		}
		else{
			isAppTop = false;
			Log.d(TAG, "is APP not top");
		}
	}
	
	private void setNotification() {
		NotificationManager notificationManager = null;
		Notification notification = null;
		
		try {
			notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			notification = new Notification(R.drawable.icon, gcm_sender_id + ": " + gcm_msg, System.currentTimeMillis());
			
			if(BasicInfo.IS_THERE_SOUND)
				notification.defaults |= Notification.DEFAULT_SOUND;
			
			if(BasicInfo.IS_THERE_VIBRATE)
				notification.defaults |= Notification.DEFAULT_VIBRATE;
			
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			
			Intent intent = new Intent(context, LogoActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("gcm", true);
			intent.putExtra("id", gcm_sender_id);
			intent.putExtra("message", gcm_msg);
			intent.putExtra("selectedInfo", selectedInfo);
			intent.putExtra("writing_num", writing_num);
			PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
			
			notification.deleteIntent = PendingIntent.getBroadcast(context, 0, new Intent("DeleteIntent"), 0);
			notification.setLatestEventInfo(context, gcm_sender_id, gcm_msg, pi);
			notificationManager.notify(GCM_NOTIFICATION_NUM, notification);
		} catch (Exception e) {
			Log.e(TAG, "[setNotification] Exception : " + e.getMessage());
		}
	}
	
	public void showMsg(String msg, int option) {
		Toast.makeText(this, msg, option).show();
	}

	@Override
	protected void onError(Context arg0, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		return super.onRecoverableError(context, errorId);
	}
	
	// Register device id in google server
	public static void GCMRegistration_id(Context context) {
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);

		String regId = GCMRegistrar.getRegistrationId(context);
		Log.i("GCMRegistrar", "registration id =====&nbsp; " + regId);
		
		if (regId.equals("")) {
			GCMRegistrar.register(context, BasicInfo.PROJECT_ID);
		} else {
			Log.v("GCMRegistrar", "Already registered");
		}
	}
}
