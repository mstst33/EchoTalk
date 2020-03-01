package com.mstst33.echo;

/*
public class Echo_Dialog_EachRoom extends Dialog{

	LinearLayout baseLayout;
	Context context;
	Awooseong_EachRoomInfoInScrollInBody room;
	Awooseong_ThemeRoom roomInfo;
	Display display;
	WindowManager wm;
	int width, height;
	LinearLayout.LayoutParams param;
	
	public Echo_Dialog_EachRoom(Context context, Awooseong_ThemeRoom roomInfo) {
		super(context);
		this.context = context;
		this.roomInfo = roomInfo;
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		width = (int)(metrics.widthPixels);
		height = (int)(metrics.heightPixels);
		
		param = new LinearLayout.LayoutParams(width, height);
		// TODO Auto-generated constructor stub
		
		baseLayout = (LinearLayout)View.inflate(context, R.layout.awooseong_body_linear, null);
		baseLayout.setLayoutParams(param);
		room = new Awooseong_EachRoomInfoInScrollInBody(context, roomInfo);
		baseLayout.addView(room.getWholeRoom());
		
		setContentView(baseLayout);
	}
}
*/
