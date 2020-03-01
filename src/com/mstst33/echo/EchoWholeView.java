package com.mstst33.echo;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mstst33.echoproject.BasicInfo;
import com.mstst33.echoproject.EchoFragment;
import com.mstst33.echoproject.GPS;
import com.mstst33.echoproject.MainActivity;
import com.mstst33.echoproject.R;

public class EchoWholeView extends FrameLayout implements View.OnClickListener, AnimationListener{
	private View sliderView;
	public EchoBasic basicView;
	public EchoDrawThread eDThread;
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
	
	public EchoWholeView(Context context) {
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
		
		addViewNum = 2;
		
		eDThread = new EchoDrawThread(context);
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
	
	public ArrayList<EchoView> getEchoViewList(){
		return basicView.eViewList;
	}
	
	public void setEchoViewList(ArrayList<EchoView> eViewList){
		basicView.eViewList = eViewList;
	}
	
	public void addMarkView(ImageView iv){
		this.addView(iv);
	}
	
	public void onChangedMark(int curNum, int orginalNum){
		((ImageView) getChildAt(curNum + addViewNum - 1)).setImageResource(R.drawable.page_alpha);
		((ImageView) getChildAt(orginalNum + addViewNum - 1)).setImageResource(R.drawable.page_now);
	}
	
	public class EchoBasic extends LinearLayout implements View.OnClickListener, EchoView.NoticeEchoViewListener {
		private boolean isFirst;
		public ViewPager vPager;
		private PagerAdapterClass pac;
		public LinearLayout echoUpView;
		public int pageCurrentPos;
		public int pageNum;
		private Context context;
		
		private TextView location;
		private TextView num_people;
		public Button shout;
		public Button setting_distance;
		
		public ArrayList<EchoView> eViewList;
		public ArrayList<EchoBall> eBallWholeList;
		public EchoBasic(Context context) {
			super(context);

			this.context = context;
			isFirst = true;
			eViewList = new ArrayList<EchoView>();
			eBallWholeList = null;
			
			pageCurrentPos = -1;
			pageNum = 0;
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			echoUpView = (LinearLayout) inflater.inflate(R.layout.echo_top, null);
			
			location = (TextView) echoUpView.findViewById(R.id.echo_location_text);
			num_people = (TextView) echoUpView.findViewById(R.id.echo_num_people_text);
			shout = (Button) echoUpView.findViewById(R.id.top_shout_btn);
			setting_distance = (Button) echoUpView.findViewById(R.id.top_setting_distance_btn);
			
			shout.setOnClickListener(this);
			setting_distance.setOnClickListener(this);
			
			for(int i = 0; i < EchoFragment.ECHO_PAGE_NUM; ++i)
				addEchoPage();
			
			pac = new PagerAdapterClass(context);
			
			vPager = new ViewPager(context);
			vPager.setAdapter(pac);
			vPager.setBackgroundResource(R.drawable.index);
			vPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					onChangedMark(pageCurrentPos, position);

					pageCurrentPos = position;
				}

				@Override
				public void onPageScrolled(int position, float positionOffest,
						int positionOffsetPixels) {
				}

				@Override
				public void onPageScrollStateChanged(int state) {
				}
			});
			
			setOrientation(LinearLayout.VERTICAL);

			LayoutParams middleParam = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.viewHeight);
			LayoutParams topParam = new LayoutParams(LayoutParams.FILL_PARENT, MainActivity.topHeight);
			
			addView(echoUpView, topParam);
			addView(vPager, middleParam);
		}
		
		public void addEchoPage() {
			// Get dip resource
			Resources r = getResources();
			float padding = TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());

			ImageView iv = new ImageView(context);
			iv.setLayoutParams(new LayoutParams(MainActivity.statusBarHeight, MainActivity.statusBarHeight));
			iv.setPadding(0, 0, (int) padding, 0);
			iv.setTag(pageNum++);
			iv.setOnClickListener(this);

			if (pageCurrentPos == -1) {
				iv.setImageResource(R.drawable.page_now);
				pageCurrentPos = 0;
			} else
				iv.setImageResource(R.drawable.page_alpha);

			addMarkView(iv);
			EchoView eView = new EchoView(context);
			eView.setOnNoticeEchoListener(this);
			eViewList.add(eView);
		}
		
		public void deleteEchoPage() {
			eViewList.remove(pageNum);
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
			else{
				vPager.setCurrentItem((Integer) v.getTag(), true);
			}
			
			// new URLBitmapDownload("welcome.png", iv).start();
			/*
			String sender_address = "";
			String sender_pw = "";
			String reciver_address = "";
			String subject = "Test";
			String content = "Sending.. OK?";
			String fileName1 = "";
			String fileName2 = "";
			String fileName3 = "";
			
			GMailSender sender = new GMailSender(sender_address, sender_pw);
			try {
				sender.sendMail(subject, content, sender_address, reciver_address, fileName1, fileName2, fileName3);
			} catch (Exception e) {
				Log.e("SendMail", e.getMessage(), e);
			}*/
		}
		
		public void arrangeEchoBallList(ArrayList<EchoBall> eBallList){
			quickSort(eBallList, eBallList.size(), 0);
			quickSort(eBallList, eBallList.size(), 1);
		}
		
		public void quickSort(ArrayList<EchoBall> numbers, int array_size, int option){
			switch(option){
			case 0:
				q_sort_by_date(numbers, 0, array_size -1);
				break;
			case 1:
				q_sort_by_isNew(numbers, 0, array_size -1);
			}
		}

		public void q_sort_by_date(ArrayList<EchoBall> numbers, int left, int right){
		    int pivot, l_hold, r_hold;
		    l_hold = left;
		    r_hold = right;
		    pivot = numbers.get(left).date;
		    while (left < right){
		        while ((numbers.get(right).date >= pivot) && (left < right))
		            right --;

		        if (left != right){
		        	numbers.get(left).date = numbers.get(right).date;
		        }
		        
		        while ((numbers.get(left).date <= pivot) && (left < right))
		            left ++;
		        if (left != right){
		        	numbers.get(right).date = numbers.get(left).date;
		             right --;
		        }
		    }
		    
		    numbers.get(left).date = pivot;
		    pivot = left;
		    left = l_hold;
		    right = r_hold;

		    if (left < pivot)
		        q_sort_by_date(numbers, left, pivot - 1);
		    if (right > pivot)
		        q_sort_by_date(numbers, pivot+1, right);
		}
		
		public void q_sort_by_isNew(ArrayList<EchoBall> numbers, int left, int right){
		    int pivot, l_hold, r_hold;
		    l_hold = left;
		    r_hold = right;
		    pivot = numbers.get(left).isNew;
		    while (left < right){
		        while ((numbers.get(right).isNew >= pivot) && (left < right))
		            right --;

		        if (left != right){
		        	numbers.get(left).isNew = numbers.get(right).isNew;
		        }
		        while ((numbers.get(left).isNew <= pivot) && (left < right))
		            left ++;
		        if (left != right){
		        	numbers.get(right).isNew = numbers.get(left).isNew;
		             right --;
		        }
		    }
		    
		    numbers.get(left).isNew = pivot;
		    pivot = left;
		    left = l_hold;
		    right = r_hold;

		    if (left < pivot)
		        q_sort_by_isNew(numbers, left, pivot - 1);
		    if (right > pivot)
		        q_sort_by_isNew(numbers, pivot+1, right);
		}
		
		private class PagerAdapterClass extends PagerAdapter {
			public PagerAdapterClass(Context context) {
				super();
			}

			@Override
			public int getCount() {
				return pageNum;
			}

			@Override
			public Object instantiateItem(View pager, int position) {
				EchoView eView = eViewList.get(position);
				((ViewPager) pager).addView(eView);
				
				eDThread.addEViewItem(eView);
				Log.d("ViewPager", "Add Page " + position);
				
				if(isFirst){
					eDThread.start();
					isFirst = false;
					Log.d("ViewPager", "EchoDrawThread Start");
				}
				
				return eView;
			}

			@Override
			public void destroyItem(View pager, int position, Object view) {
				eDThread.removeEViewItem((EchoView) view);
				Log.d("ViewPager", "Remove Page " + position);
				((ViewPager) pager).removeView((EchoView) view);
			}

			@Override
			public boolean isViewFromObject(View pager, Object obj) {
				return pager == obj;
			}

			@Override
			public void restoreState(Parcelable arg0, ClassLoader arg1) {
			}

			@Override
			public Parcelable saveState() {
				return null;
			}

			@Override
			public void startUpdate(View arg0) {
			}

			@Override
			public void finishUpdate(View arg0) {
			}
		}

		@Override
		public void onNoticeEchoView(boolean isAddPage, int selectedInfo, boolean isEcho, String writing_num) {
			if(isAddPage){
				addEchoPage();
				pac.notifyDataSetChanged();
				
				++EchoFragment.ECHO_PAGE_NUM;
				edt.putInt("echoPageNum", EchoFragment.ECHO_PAGE_NUM);
				edt.commit();
				
				Log.d("EchoBasic", String.valueOf(eViewList.size()));
				eViewList.get(eViewList.size() - 1).createEchoBall(selectedInfo, isEcho, writing_num);
			}
			
			if(eBallWholeList != null){
				if(!eBallWholeList.isEmpty())
					eBallWholeList.clear();
			}
			else{
				eBallWholeList = new ArrayList<EchoBall>();
			}
			
			for(int i = 0; i < eViewList.size(); ++i)
				for(int j = 0; j < eViewList.get(i).eBallList.size(); ++j)
					eBallWholeList.add(eViewList.get(i).eBallList.get(j));
			
			arrangeEchoBallList(eBallWholeList);
			
			for(int t = 0; t < eViewList.size(); ++t)
				eViewList.get(t).eBallList.clear();
			
			int size = eBallWholeList.size();
			for (int i = 0; i < eViewList.size(); ++i) {
				if (size >= EchoView.MAX_NUM_BALL) {
					for (int j = 0; j < EchoView.MAX_NUM_BALL; ++j)
						eViewList.get(i).eBallList.add(eBallWholeList.get(j + EchoView.MAX_NUM_BALL * i));

					size -= EchoView.MAX_NUM_BALL;
				} else {
					for (int j = 0; j < size; ++j)
						eViewList.get(i).eBallList.add(eBallWholeList.get(j + EchoView.MAX_NUM_BALL * i));
				}
			}
		}
	}
}