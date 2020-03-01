package com.mstst33.echo;

/*
public class ReEchoWholeView extends FrameLayout implements View.OnClickListener, AnimationListener{
	private View sliderView;
	
	private ArrayList<ImageView>recycle;
	
	public EchoBasic basicView;
	private Context context;
	
	private boolean isPageOpen;
	private Animation translateTopAnim;
	private Animation translateBotAnim;
	private Animation fadeInAnim;
	private Animation fadeOutAnim;
	
	private LayoutParams pageOpened;
	private LayoutParams pageClosed;
	private LinearLayout slidingPage;
	
	private Button nearBtn;
	private Button farBtn;
	private Button veryFarBtn;
	private Button wholeBtn;
	private Button localBtn;
	
	public int addViewNum;
	
	
	SharedPreferences.Editor edt;
	SharedPreferences prefs;
	
	public ReEchoWholeView(Context context) {
		super(context);
		
		this.context = context;
		isPageOpen = false;

		edt = context.getSharedPreferences("Echo", Context.MODE_PRIVATE).edit();
		prefs = context.getSharedPreferences("Echo", Context.MODE_PRIVATE);
		
		basicView = new EchoBasic(context);
		
		translateTopAnim = AnimationUtils.loadAnimation(context, R.anim.slide_in_top);
		translateTopAnim.setAnimationListener(this);
		
		translateBotAnim = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
		translateBotAnim.setAnimationListener(this);
		
		fadeInAnim = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
		fadeOutAnim = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
		
		// setBackgroundResource(R.drawable.echo_bg);
		// setBackgroundColor(Color.rgb(246, 246, 246));
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		sliderView = inflater.inflate(R.layout.echo_slider, null);
		sliderView.setVisibility(View.INVISIBLE);
		sliderView.setOnClickListener(this);
		
		nearBtn = (Button) sliderView.findViewById(R.id.echo_near_btn);
		farBtn = (Button) sliderView.findViewById(R.id.echo_far_btn);
		veryFarBtn = (Button) sliderView.findViewById(R.id.echo_very_far_btn);
		wholeBtn = (Button) sliderView.findViewById(R.id.echo_whole_btn);
		localBtn = (Button) sliderView.findViewById(R.id.echo_local_btn);
		
		nearBtn.setOnClickListener(this);
		farBtn.setOnClickListener(this);
		veryFarBtn.setOnClickListener(this);
		wholeBtn.setOnClickListener(this);
		localBtn.setOnClickListener(this);
		
		if(Integer.parseInt(BasicInfo.RANGE_TYPE) == 1){
			localBtn.setBackgroundResource(R.drawable.setting_area01_reverse);
		}
		else{
			wholeBtn.setBackgroundResource(R.drawable.setting_area02_reverse);
			
			nearBtn.setEnabled(false);
			farBtn.setEnabled(false);
			veryFarBtn.setEnabled(false);
		}
		
		switch(Integer.parseInt(BasicInfo.DISTANCE_TYPE)){
		case 1:
			nearBtn.setBackgroundResource(R.drawable.setting_range01_reverse);
			break;
		case 2:
			farBtn.setBackgroundResource(R.drawable.setting_range02_reverse);
			break;
		case 3:
			veryFarBtn.setBackgroundResource(R.drawable.setting_range03_reverse);
			break;
		}
		// Bitmap tempBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.shout);
		// Drawable drawBitmap = new BitmapDrawable(Bitmap.createScaledBitmap(tempBitmap, MainActivity.displayWdith / 4, MainActivity.displayWdith / 4, true));
		
		pageClosed = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.contentHeight / 4, Gravity.BOTTOM | Gravity.CENTER);
		pageClosed.rightMargin = MainActivity.displayWidth;
		pageOpened = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.contentHeight / 4);
		
		LayoutParams basicParam = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		LayoutParams shoutParam = new LayoutParams(MainActivity.displayWidth / 6, MainActivity.displayWidth / 6, Gravity.TOP | Gravity.CENTER);
		LayoutParams distanceParam = new LayoutParams(MainActivity.displayWidth / 6, MainActivity.displayWidth / 6, Gravity.TOP | Gravity.RIGHT);
		distanceParam.rightMargin = MainActivity.displayWidth / 12;
		
		addView(sliderView, pageClosed);
		addView(basicView, basicParam);
	}
	
	public void recycleImage(){
		basicView.recycleImage();
		nearBtn.setBackgroundDrawable(null);
		farBtn.setBackgroundDrawable(null);
		veryFarBtn.setBackgroundDrawable(null);
		wholeBtn.setBackgroundDrawable(null);
		localBtn.setBackgroundDrawable(null);
	}
	
	@Override
	public void onClick(View v) {
		if(v == sliderView){
			closeSlide();
		}
		else if(v == nearBtn){
			nearBtn.setBackgroundResource(R.drawable.setting_range01_reverse);
			farBtn.setBackgroundResource(R.drawable.setting_range02_select);
			veryFarBtn.setBackgroundResource(R.drawable.setting_range03_select);
			
			BasicInfo.DISTANCE_TYPE = "1";
			edt.putString("distance_type", BasicInfo.DISTANCE_TYPE);
			edt.commit();
		}
		else if(v == farBtn){
			nearBtn.setBackgroundResource(R.drawable.setting_range01_select);
			farBtn.setBackgroundResource(R.drawable.setting_range02_reverse);
			veryFarBtn.setBackgroundResource(R.drawable.setting_range03_select);
			
			
			BasicInfo.DISTANCE_TYPE = "2";
			edt.putString("distance_type", BasicInfo.DISTANCE_TYPE);
			edt.commit();
		}
		else if(v == veryFarBtn){
			nearBtn.setBackgroundResource(R.drawable.setting_range01_select);
			farBtn.setBackgroundResource(R.drawable.setting_range02_select);
			veryFarBtn.setBackgroundResource(R.drawable.setting_range03_reverse);
			
			BasicInfo.DISTANCE_TYPE = "3";
			edt.putString("distance_type", BasicInfo.DISTANCE_TYPE);
			edt.commit();
		}
		else if(v == localBtn){
			localBtn.setBackgroundResource(R.drawable.setting_area01_reverse);
			wholeBtn.setBackgroundResource(R.drawable.setting_area02_select);
			
			nearBtn.setEnabled(true);
			farBtn.setEnabled(true);
			veryFarBtn.setEnabled(true);
			
			BasicInfo.RANGE_TYPE = "1";
			edt.putString("range_type", BasicInfo.RANGE_TYPE);
			edt.commit();
		}
		else if(v == wholeBtn){
			localBtn.setBackgroundResource(R.drawable.setting_area01_select);
			wholeBtn.setBackgroundResource(R.drawable.setting_area02_reverse);
			
			nearBtn.setEnabled(false);
			farBtn.setEnabled(false);
			veryFarBtn.setEnabled(false);
			
			BasicInfo.RANGE_TYPE = "2";
			edt.putString("range_type", BasicInfo.RANGE_TYPE);
			edt.commit();
		}
	}
	
	
	
	
	@Override
	public void onAnimationEnd(Animation animation) {
		if(isPageOpen){
			isPageOpen = false;
			sliderView.setLayoutParams(pageClosed);
		}else{
			isPageOpen = true;
		}
	}
	
	@Override
	public void onAnimationRepeat(Animation animation) {
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}
	
	public void openSlide(){
		if(!isPageOpen){
			bringChildToFront(sliderView);
			sliderView.setLayoutParams(pageOpened);
			sliderView.setVisibility(View.VISIBLE);
			sliderView.startAnimation(translateBotAnim);
		
		}
	}
	
	public void closeSlide(){
		if(isPageOpen){
			sliderView.startAnimation(translateTopAnim);
			sliderView.setVisibility(View.INVISIBLE);
		}
	}
	
	public void addMarkView(ImageView iv){
		this.addView(iv);
	}
	
	public void onChangedMark(int curNum, int orginalNum){
		((ImageView) getChildAt(curNum + addViewNum - 1)).setImageResource(R.drawable.page_alpha);
		((ImageView) getChildAt(orginalNum + addViewNum - 1)).setImageResource(R.drawable.page_now);
	}
	
	public class EchoBasic extends LinearLayout implements View.OnClickListener {
		private boolean isFirst;
		private boolean isShow;
		private final boolean FAVORITE = true;
		private final boolean ROOM_YOU_MADE = false;
	
		public LinearLayout echoUpView;
		
		private EchoListLayout roomListYouMade;
		private EchoListLayout roomListFavorite;
		private FrameLayout echoFrameLayout;
		private RelativeLayout uniqueView;
		private ImageView uniqueBtn;
		
		public int pageCurrentPos;
		public int pageNum;
		private Context context;

		private TextView location;
		private TextView num_people;
		public Button shout;
		public Button setting_distance;
		
		public EchoBasic(Context context) {
			super(context);

			this.context = context;
			isFirst = true;
			isShow = false;
	
			pageCurrentPos = -1;
			pageNum = 0;
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			echoUpView = (LinearLayout) inflater.inflate(R.layout.echo_top, null);
			roomListYouMade = new EchoListLayout(context, ROOM_YOU_MADE);
			roomListFavorite = new EchoListLayout(context, FAVORITE);
			echoFrameLayout = (FrameLayout)View.inflate(context, R.layout.echo_framelayout, null);
			
			location = (TextView) echoUpView.findViewById(R.id.echo_location_text);
			num_people = (TextView) echoUpView.findViewById(R.id.echo_num_people_text);
			shout = (Button) echoUpView.findViewById(R.id.top_shout_btn);
			setting_distance = (Button) echoUpView.findViewById(R.id.top_setting_distance_btn);
			
			shout.setOnClickListener(this);
			setting_distance.setOnClickListener(this);
			
			recycle = new ArrayList<ImageView>();
			
			uniqueView = (RelativeLayout)View.inflate(context, R.layout.echo_unique_button, null);
			uniqueBtn = (ImageView)uniqueView.findViewById(R.id.uniqueBtn);
			uniqueBtn.setOnClickListener(this);

			setOrientation(LinearLayout.VERTICAL);

			LayoutParams middleParam = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.viewHeight);
			LayoutParams topParam = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.topHeight);
			
			echoFrameLayout.setBackgroundResource(R.drawable.speak_back);
			roomListFavorite.getWholeView().setVisibility(View.INVISIBLE);
			
			echoFrameLayout.addView(roomListYouMade.getWholeView());
			echoFrameLayout.addView(roomListFavorite.getWholeView());
			echoFrameLayout.addView(uniqueView);
			
			addView(echoUpView, topParam);
			addView(echoFrameLayout, middleParam);
		}
		
		public void recycleImage(){
			echoUpView.setBackgroundDrawable(null);
			
			roomListYouMade.getWholeView().setBackgroundDrawable(null);
			roomListFavorite.getWholeView().setBackgroundDrawable(null);
			echoFrameLayout.setBackgroundDrawable(null);
			uniqueView.setBackgroundDrawable(null);
			uniqueBtn.setBackgroundDrawable(null);
			location.setBackgroundDrawable(null);
			num_people.setBackgroundDrawable(null);
			shout.setBackgroundDrawable(null);
			setting_distance.setBackgroundDrawable(null);
		}
		
		public void updateLocation(boolean isSucceeded){
			if(isSucceeded){
				BasicInfo.LOCATION = String.valueOf(GPS.CUR_LAT) + " " + String.valueOf(GPS.CUR_LNG);
				BasicInfo.ADDRESS = GPS.CUR_ADDRESS;
				location.setText(BasicInfo.ADDRESS);
				
				edt.putString("location", BasicInfo.LOCATION);
				edt.putString("address", BasicInfo.ADDRESS);
				edt.commit();
			}
			else{
				BasicInfo.LOCATION = prefs.getString("location", "");
				BasicInfo.ADDRESS = prefs.getString("address", "");
				location.setText(BasicInfo.ADDRESS);
			}
			
			Log.d("EchoUpdate", "Address: " + GPS.CUR_ADDRESS);
		}
		
		public void updateNumPeople(boolean isSucceeded){
			if(isSucceeded){
				num_people.setText(BasicInfo.NUM_OF_PEOPLE_AROUND_ME);
			
				edt.putString("num_of_people_around_me", BasicInfo.NUM_OF_PEOPLE_AROUND_ME);
				edt.commit();
			}
			else{
				BasicInfo.NUM_OF_PEOPLE_AROUND_ME = prefs.getString("num_of_people_around_me", "0");
				num_people.setText(BasicInfo.NUM_OF_PEOPLE_AROUND_ME);
			}
			
			Log.d("EchoUpdate", "The number of people around me: " + BasicInfo.NUM_OF_PEOPLE_AROUND_ME);
		}
		
		@Override
		public void onClick(View v) {
			if(v == shout){
				
			}
			else if(v == setting_distance){
				openSlide();
			}
			else if(v == uniqueBtn){
				if(!isShow){
					Log.i("EchoWholeView", "uniqueBtn is Clicked for favoriteLayout showing");
					isShow = true;
					roomListYouMade.closeList();
					roomListFavorite.openList();
					
				}
				else{
					Log.i("EchoWholeView", "uniqueBtn is Clicked for favoriteLayout closing");
					isShow = false;
					roomListFavorite.closeList();
					roomListYouMade.openList();
				}
			}
			
		}
	}
}*/
