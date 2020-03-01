package com.mstst33.echoproject;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity{
	public final boolean DEBUG_MODE = false;
	public static String PICTURE_DATA = "0";
	
	private boolean exitAble = false;
	private int nowID;
	private int beforeID;
	
	private EchoFragment echoFragment;
	private RoarFragment roarFragment;
	private MyInfoFragment infoFragment;
	private SettingFragment setFragment;
	
	private LinearLayout tap_content;
	private Button echo_btn;
	private Button roar_btn;
	private Button myInfo_btn;
	private Button setting_btn;
	
	private TextView titleTxt;
		
	public static int displayWidth;
	public static int displayHeight;
	public static int statusBarHeight;
	public static int titleBarHeight;
	public static int tapsHeight;
	public static int contentHeight;
	
	public static GPS gps;
	public static final String LOCATION_SETUP_URL = BasicInfo.SERVER_ADDRESS + "location_setup.php";
	public static final String GCM_SEND_MESSAGE_URL = BasicInfo.SERVER_ADDRESS + "GCM_Send_Message.php";
	
	SharedPreferences.Editor edt;
	Context context;
	public static int topHeight;
	public static int viewHeight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("MainActivity", "OnCreate");
		
		gps = new GPS(this);
		
		edt = getSharedPreferences("Echo", Context.MODE_PRIVATE).edit();
		edt.clear();
		edt.putString("user_photo", BasicInfo.USER_PHOTO);
		edt.putString("user_id", BasicInfo.USER_ID);
		edt.putString("user_name", BasicInfo.USER_NAME);
		edt.putString("user_age", BasicInfo.USER_AGE);
		edt.putString("user_gender", BasicInfo.USER_GENDER);
		edt.putString("user_email", BasicInfo.USER_EMAIL);
		edt.putString("user_comment", BasicInfo.USER_COMMENT);
		edt.putString("user_join_date", BasicInfo.USER_JOIN_DATE);
		edt.putString("user_date", BasicInfo.USER_DATE);
		edt.putBoolean("isLogin", BasicInfo.IS_LOGIN);
		edt.putBoolean("isRegisteredID", BasicInfo.IS_REGISTERED_ID);
		edt.putBoolean("isDeviceID", BasicInfo.IS_DEVICE_ID);
		edt.putString("reg_id", BasicInfo.REG_ID);
		edt.putBoolean("isThereSound", BasicInfo.IS_THERE_SOUND);
		edt.putBoolean("isThereVibrate", BasicInfo.IS_THERE_VIBRATE);
		edt.putBoolean("isGetMessage", BasicInfo.IS_GET_MESSAGE);
		edt.putInt("currentMessageNum", BasicInfo.CURRENT_MESSAGE_NUM);
		edt.putString("interested_theme", BasicInfo.INTERESTED_THEME);
		edt.putString("location", BasicInfo.LOCATION);
		edt.putString("address", BasicInfo.ADDRESS);
		edt.putString("num_of_people_around_me", BasicInfo.NUM_OF_PEOPLE_AROUND_ME);
		edt.putString("distance_type", BasicInfo.DISTANCE_TYPE);
		edt.putString("range_type", BasicInfo.RANGE_TYPE);
		edt.putBoolean("isFirst", BasicInfo.IS_FIRST);
		edt.putInt("echoPageNum", EchoFragment.ECHO_PAGE_NUM);
		edt.commit();
		
		echoFragment = new EchoFragment();
		roarFragment = new RoarFragment();
		infoFragment = new MyInfoFragment();
		setFragment = new SettingFragment();
		
		/*
		TestFragment fragment = new TestFragment(); 
		Bundle bundle = new Bundle();  
		bundle.getInt("id", 1);
		frament.setArguments(bundle);  */
		
		if(DEBUG_MODE){
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
		}
		else
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
		
		// Galaxy S4: 1080 * 1920
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		displayWidth = display.getWidth();
		displayHeight = display.getHeight();
		
		// When Changing mode to portrait mode or landscape mode
		if (getWindowManager().getDefaultDisplay().getOrientation() == Configuration.ORIENTATION_PORTRAIT) {
		} else {
		}
		
		// Title bar
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.activity_main, null);
		setContentView(contentView);
		
		tap_content = (LinearLayout) contentView.findViewById(R.id.tap_content);
		echo_btn = (Button) tap_content.findViewById(R.id.echoBtn);
		roar_btn = (Button) tap_content.findViewById(R.id.roarBtn);
		myInfo_btn = (Button) tap_content.findViewById(R.id.myinfoBtn);
		setting_btn = (Button) tap_content.findViewById(R.id.settingBtn);
		
		// Calculate each height
		contentView.post(new Runnable(){
			@Override
			public void run() {
				Rect CheckRect = new Rect();
				getWindow().getDecorView().getWindowVisibleDisplayFrame(CheckRect);
				int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
				
				statusBarHeight = CheckRect.top;
				titleBarHeight = contentTop - statusBarHeight;
				tapsHeight = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight() / 11;
				contentHeight = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight() - tapsHeight;
				topHeight =  3 * contentHeight / 22;
				viewHeight = 19 * contentHeight / 22;
				
				if(BasicInfo.IS_FIRST){
					getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_fragment, infoFragment).commitAllowingStateLoss();
				
					nowID = R.id.myinfoBtn;
					beforeID = R.id.myinfoBtn;
					myInfo_btn.setBackgroundResource(R.drawable.echo_my_info_btn_reverse);
				}
				else{
					// First content to show
					getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_fragment, roarFragment).commitAllowingStateLoss();
				
					nowID = R.id.roarBtn;
					beforeID = R.id.roarBtn;
					roar_btn.setBackgroundResource(R.drawable.echo_roar_btn_reverse);
				}
			}
		});
		
		getApplicationContext().registerReceiver(gcm_delete_br, new IntentFilter("DeleteIntent"));
	}
	
	private final BroadcastReceiver gcm_delete_br = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			nm.cancel(GCMIntentService.GCM_NOTIFICATION_NUM);
			
			edt.putInt("currentMessageNum", --BasicInfo.CURRENT_MESSAGE_NUM);
			edt.commit();
			
			Log.i("GCM", "Minus current message num: " + BasicInfo.CURRENT_MESSAGE_NUM);
		}
	};
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		gps.startUpdate();
		
		Log.d("MainActivity", "On new intent");
		boolean is_get_notification = intent.getBooleanExtra("gcm", false);
		
		if(is_get_notification){
			Log.d("MainActivity", "Get notification");
			
			edt.putInt("currentMessageNum", --BasicInfo.CURRENT_MESSAGE_NUM);
			edt.commit();
			
			Log.i("GCM", "Minus current message num: " + BasicInfo.CURRENT_MESSAGE_NUM);
			
			String id = intent.getStringExtra("id");
			String msg = intent.getStringExtra("message");
			String selectedInfo = intent.getStringExtra("selectedInfo");
			String writing_num = intent.getStringExtra("writing_num");
			Log.d("MainActivity", BasicInfo.CURRENT_MESSAGE_NUM + " " + id + " " + msg + " " + selectedInfo + " " + writing_num);
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.d("MainActivity", "OnResume");
	}
	
	// Use this function to save important info because that guarantees before being finished
	@Override
	public void onPause(){
		super.onPause();
		Log.d("MainActivity", "OnActivityPause");
	}
	
	// Use this function to save temporary info because not guarantee before being finished
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		Log.d("MainActivity", "onSaveInstanceState");
		
		Bundle map = new Bundle();
		map.putString("Save", "good");
		outState.putBundle("SaveBundle", map);
	}
	
	// Use this function to restore temporary info because not guarantee before being finished
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		Log.d("MainActivity", "onRestoreInstanceState");
		
		if(savedInstanceState != null){
			Bundle map = savedInstanceState.getBundle("SaveBundle");
			
			if(map != null){
				String value = map.getString("Save");
			}
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		//unregisterReceiver(gcm_delete_br);
		if(gps.gpsOn || gps.networkOn)
			gps.endUpdate();
		
		gps = null;
	}
	 
    @Override
    public void onBackPressed() {
		if (!exitAble) {
			Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료 됩니다", Toast.LENGTH_SHORT).show();

			exitAble = true;
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			this.finish();
		}
		// super.onBackPressed();
    }
	
    Handler mHandler = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		if(msg.what == 0){
    			exitAble = false;
    		}
    	}
    };
	
	public void mOnClick(View v) {
		String str;
		
		switch (v.getId()) {
		case R.id.echoBtn:
			if(nowID != R.id.echoBtn){
				str = String.format(getResources().getString(R.string.tap_echo));
				getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_fragment, echoFragment).commitAllowingStateLoss();
				
				changeFocus(beforeID);
				echo_btn.setBackgroundResource(R.drawable.echo_echo_btn_reverse);
				nowID = R.id.echoBtn;
				beforeID = nowID;
			}
			break;
		case R.id.roarBtn:
			if(nowID != R.id.roarBtn){
				str = String.format(getResources().getString(R.string.tap_roar));
				getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_fragment, roarFragment).commitAllowingStateLoss();
				
				changeFocus(beforeID);
				roar_btn.setBackgroundResource(R.drawable.echo_roar_btn_reverse);
				nowID = R.id.roarBtn;
				beforeID = nowID;
			}
			break;
		case R.id.myinfoBtn:
			if(nowID != R.id.myinfoBtn){
				str = String.format(getResources().getString(R.string.tap_myinfo));
				getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_fragment, infoFragment).commitAllowingStateLoss();
				
				changeFocus(beforeID);
				myInfo_btn.setBackgroundResource(R.drawable.echo_my_info_btn_reverse);
				nowID = R.id.myinfoBtn;
				beforeID = nowID;
			}
			break;
		case R.id.settingBtn:
			if(nowID != R.id.settingBtn){
				str = String.format(getResources().getString(R.string.tap_setting));
				getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_fragment, setFragment).commitAllowingStateLoss();
				
				changeFocus(beforeID);
				setting_btn.setBackgroundResource(R.drawable.echo_setting_btn_reverse);
				nowID = R.id.settingBtn;
				beforeID = nowID;
			}
			break;
		}
	}
	
	private void changeFocus(int beforeID){
		switch (beforeID) {
		case R.id.echoBtn:
			echo_btn.setBackgroundResource(R.drawable.echo_echo_btn_select);
			break;
		case R.id.roarBtn:
			roar_btn.setBackgroundResource(R.drawable.echo_roar_btn_select);
			break;
		case R.id.myinfoBtn:
			myInfo_btn.setBackgroundResource(R.drawable.echo_my_info_btn_select);
			break;
		case R.id.settingBtn:
			setting_btn.setBackgroundResource(R.drawable.echo_setting_btn_select);
			break;
		}
	}
}