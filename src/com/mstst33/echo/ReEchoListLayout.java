package com.mstst33.echo;

/*
public class ReEchoListLayout implements View.OnClickListener{

	private static final int ECHO_ROOM_GET_DATA = 0;
	public static boolean IS_DOWNLOADING_FIRST;
	private String REQUEST_DATA_URL = BasicInfo.SERVER_ADDRESS + "request_echo_room_data.php";
	private int echoRoomState;
	private String writingNum;
	private String deleteNum;
	private int theme;
	
	private Context mcontext;
	private boolean isFavorite;
	private LayoutInflater inflater;
	
	private LinearLayout wholeView;
	private ScrollView scroll;
	private LinearLayout scrollDirectLinear;
	private LinearLayout scrollInDirectLinear;
	
	private LinearLayout linear_btn_additionalContent;
	private Button btn_upward;
	private Button btn_forAdditional;
	private boolean isAdditional;
	private boolean isDelete;
	
	private EchoRoom temp;
	private Awooseong_Room_Var_Daily dailyRoom;
	private Awooseong_Room_Var_Exercise exerciseRoom;
	private Awooseong_Room_Var_Hobby hobbyRoom;
	private Awooseong_Room_Var_Job jobRoom;
	private Awooseong_Room_Var_Question questionRoom;
	private Awooseong_Room_Var_Study studyRoom;
	private Awooseong_Room_Var_Travel travelRoom;
	private Awooseong_Room_Var_Used usedRoom;
	private ArrayList <Awooseong_ThemeRoom> arrayThemeRoom;
	private ArrayList <EchoRoom>recycle;
	
	private TranslateAnimation mShowAnimation;
	private TranslateAnimation mHideAnimation;
	
	private FrameLayout frameBase;
	private LinearLayout progress;
	private boolean isProgress;
	private int count;

	
	public EchoListLayout(Context context, boolean isFavorite) {
	
		// TODO Auto-generated constructor stub
		mcontext = context;
		this.isFavorite = isFavorite;
		arrayThemeRoom = new ArrayList <Awooseong_ThemeRoom>(); 
		recycle = new ArrayList <EchoRoom>();
		isAdditional = false;
		isDelete = false;
		isProgress = true;
		count = 0;
		writingNum = "0";
		theme = -1;
		
		wholeView =(LinearLayout)View.inflate(mcontext, R.layout.awooseong_body_linear, null);
		scroll = (ScrollView)View.inflate(context, R.layout.awooseong_body_scrollview, null);
		
		frameBase = (FrameLayout)View.inflate(context, R.layout.echo_framelayout, null);
		progress = (LinearLayout)View.inflate(context, R.layout.echo_progress, null);
		
		
		scrollDirectLinear = (LinearLayout)View.inflate(context, R.layout.awooseong_body_scroll_direct_linearlayout, null);
		scrollInDirectLinear = (LinearLayout)View.inflate(context, R.layout.awooseong_body_scroll_indirect_linear, null);
		
		linear_btn_additionalContent = (LinearLayout) View.inflate(context,R.layout.awooseong_btn_layout_additional_content_in_scrollview, null);
		btn_forAdditional = (Button) linear_btn_additionalContent.findViewById(R.id.awooseong_btn_additional_content_in_scrollview);
		btn_upward = (Button) linear_btn_additionalContent.findViewById(R.id.awooseong_btn_upward_in_scrollview);
		
		btn_forAdditional.setOnClickListener(this);
		btn_upward.setOnClickListener(this);
		
		scrollDirectLinear.addView(scrollInDirectLinear);
		scrollDirectLinear.addView(linear_btn_additionalContent);
		
		scroll.addView(scrollDirectLinear);
		scrollInDirectLinear.setPadding(5, 5, 5, 5);
		wholeView.addView(scroll);
		
		frameBase.addView(wholeView);
		frameBase.addView(progress);
		scroll.setVisibility(View.INVISIBLE);
		
		doAnimation();
		getDataFromServer();
		
	}
	
	public FrameLayout getWholeView(){
		return frameBase;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v == btn_forAdditional){
			Log.i("Awooseong", "btnForAdditional is working");
			isAdditional = true;
			count++;
			getDataFromServer();
		}
		
		if( v == btn_upward ){
			
			new Handler().postDelayed(new Runnable() {
				public void run(){
					Log.i("awooseong","scroll run");
					scroll.fullScroll(View.FOCUS_UP);
					scroll.invalidate();
				}
				
			}, 100);
		}
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0:
				isDelete = true;
				theme = msg.arg1;
				deleteNum = (String)msg.obj;
				getDataFromServer();
				break;
			case 1:
				isProgress = false;
				Log.i("EchoListLayout", "handle message 1");
				progress.setVisibility(View.INVISIBLE);
				scroll.setVisibility(View.VISIBLE);
				break;
			case 2:
				isProgress = false;
				Log.i("EchoListLayout", "handle message 2");
				progress.setVisibility(View.INVISIBLE);
				break;
			}
		}
	};
	
	public void openList(){
		frameBase.setVisibility(View.VISIBLE);
		frameBase.startAnimation(mShowAnimation);
	}
	
	public void closeList(){
		frameBase.setVisibility(View.INVISIBLE);
		frameBase.startAnimation(mHideAnimation);
	}
	
	private void doAnimation(){
		mShowAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mHideAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAnimation.setDuration(1000);
		mHideAnimation.setDuration(1000);
	}
	
	private void recycleImage(){
		for(int i = 0; i < recycle.size(); i++){
			recycle.get(i).recycleTheme();
		}
		
	}
	
	private void addRoomInScroll() {

		Log.i("EchoListLayout", "addRoomInScroll is working");
		if(arrayThemeRoom == null )
			return;
		if(!isAdditional)
			scrollInDirectLinear.removeAllViews();
		
		for (int i = 0; i < arrayThemeRoom.size(); i++) {
			temp = new EchoRoom(mcontext, arrayThemeRoom.get(i), isFavorite, handler);
			scrollInDirectLinear.addView(temp.getWholeView());
			recycle.add(temp);
			
			Log.i("EchoListLayout", "addRoomInScroll is added");
		}
		
		if(isAdditional)
			handler.sendEmptyMessage(2);
		else
			handler.sendEmptyMessage(1);
		
		arrayThemeRoom.clear();
		
		if (!isAdditional) {
			new Handler().postDelayed(new Runnable() {
				public void run() {
					Log.i("awooseong", "scroll run");
					scroll.fullScroll(View.FOCUS_UP);
					scroll.invalidate();
				}
			}, 100);
		}
	}
	
	private void getWritingNum(Awooseong_ThemeRoom theme) {
		Log.i("Awooseong", "getWritingNum is working");
		
		switch (theme.getThemeNumber()) {
		case Awooseong_Theme.DAILY:

			if (theme instanceof Awooseong_Room_Var_Daily) {
				Awooseong_Room_Var_Daily A = (Awooseong_Room_Var_Daily) theme;
				writingNum = A.daily_writingNum;
			}
			break;
			
		case Awooseong_Theme.TRAVEL:
			if (theme instanceof Awooseong_Room_Var_Travel) {
				Awooseong_Room_Var_Travel A = (Awooseong_Room_Var_Travel) theme;
				writingNum = A.travel_writingNum;
			}
			break;
			
		case Awooseong_Theme.EXERCISE:
			if (theme instanceof Awooseong_Room_Var_Exercise) {
				Awooseong_Room_Var_Exercise A = (Awooseong_Room_Var_Exercise) theme;
				writingNum = A.exercise_writingNum;
			}
			break;
			
		case Awooseong_Theme.HOBBY:
			if (theme instanceof Awooseong_Room_Var_Hobby) {
				Awooseong_Room_Var_Hobby A = (Awooseong_Room_Var_Hobby) theme;
				writingNum = A.hobby_writingNum;
			}
			break;
			
		case Awooseong_Theme.STUDY:
			if (theme instanceof Awooseong_Room_Var_Study) {
				Awooseong_Room_Var_Study A = (Awooseong_Room_Var_Study) theme;
				writingNum = A.study_writingNum;
			}
			break;
			
		case Awooseong_Theme.QUESTION:
			if (theme instanceof Awooseong_Room_Var_Question) {
				Awooseong_Room_Var_Question A = (Awooseong_Room_Var_Question) theme;
				writingNum = A.question_writingNum;
			}
			break;
			
		case Awooseong_Theme.JOB:
			if (theme instanceof Awooseong_Room_Var_Job) {
				Awooseong_Room_Var_Job A = (Awooseong_Room_Var_Job) theme;
				writingNum = A.job_writingNum;
			}
			break;
			
		case Awooseong_Theme.USED:
			if (theme instanceof Awooseong_Room_Var_Used) {
				Awooseong_Room_Var_Used A = (Awooseong_Room_Var_Used) theme;
				writingNum = A.used_writingNum;
			}
			break;
		}
	}
	
	public void getDataFromServer() {
		Log.i("EchoListLayout", "getDataFromServer() is working");
		
		if(!isProgress){
			isProgress = true;
			progress.setVisibility(View.VISIBLE);
			
			if(!isAdditional)
				scroll.setVisibility(View.INVISIBLE);
			recycleImage();
		}
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("user_id", BasicInfo.USER_ID));
		// writing_num and * are needed when you delete the room you made
		
		if(isFavorite){
			nameValuePairs.add(new BasicNameValuePair("is_favorite", "true"));
			nameValuePairs.add(new BasicNameValuePair("writing_num", Integer.toString(count)));
			nameValuePairs.add(new BasicNameValuePair("is_delete", "false"));
		}
		else{
			nameValuePairs.add(new BasicNameValuePair("is_favorite", "false"));
			if(!isDelete){
				nameValuePairs.add(new BasicNameValuePair("writing_num", writingNum));
				nameValuePairs.add(new BasicNameValuePair("is_delete", "false"));
			}
			else{
				nameValuePairs.add(new BasicNameValuePair("writing_num", deleteNum));
				nameValuePairs.add(new BasicNameValuePair("is_delete", "true"));
				//isDelete = false;
			}
		}
		nameValuePairs.add(new BasicNameValuePair("theme", Integer.toString(theme)));
		
		Log.i("EchoListLayout", "sendData: " + BasicInfo.USER_ID + writingNum);
		echoRoomState = ECHO_ROOM_GET_DATA;
		new RoarUpdateInfo().execute(nameValuePairs);
	}
	
	public class RoarUpdateInfo extends AsyncTask<ArrayList<NameValuePair>, String, Boolean> {
		JSONObject json;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
			Boolean result = null;
			
			switch (echoRoomState) {
			case ECHO_ROOM_GET_DATA:
				try {
					json = RestClient.requestLoginResult(REQUEST_DATA_URL, params[0]);
					result = Boolean.parseBoolean(json.getString("is_success"));
					Log.i("EchoListLayout_jsonResult", result.toString());
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			}			
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			Log.i("EchoListLayout", "onPostExecute is woring");
			if (result == null)
				return;
			Log.i("EchoListLayout", result.toString());
			
			switch (echoRoomState) {
			case ECHO_ROOM_GET_DATA:
				if (result){
					int return_num;
					JSONArray results;
					try {
						return_num = json.getInt("return_num");
						results =  json.getJSONArray("result");
						Log.i("EchoListLayout", "JSonArray size :" + results.length());
						if(results == null){
							Log.i("EchoListLayout", "Theme info has nothing from server");
							
						}
						for(int i = 0; i < return_num; ++i){
							//String[] value = results.getString(i).split("/");
							String temp = RoarFragment.replaceAll(results.getString(i), "%$3#%", "/");
							Log.i("RoarFragment_split_/",temp);
							String[] value = temp.split("/");
							
							switch(Integer.parseInt(value[0])){
							case 0:
								dailyRoom = new Awooseong_Room_Var_Daily();
								dailyRoom.daily_content = value[1];
								dailyRoom.daily_date = value[2];
								dailyRoom.daily_pictureData = value[3];
								dailyRoom.daily_location = value[4];
								dailyRoom.daily_writingNum = value[5];
								dailyRoom.daily_userId = value[6];
								dailyRoom.daily_echoNum = value[7];
								dailyRoom.daily_echo = value[8];
								dailyRoom.daily_userPic = value[9];
								arrayThemeRoom.add(dailyRoom);
								
								break;
								
							case 1:
								travelRoom = new Awooseong_Room_Var_Travel();
								travelRoom.travel_subject = value[1];
								travelRoom.travel_content = value[2];
								travelRoom.travel_date = value[3];
								travelRoom.travel_pictureData = value[4];
								travelRoom.travel_numPeople = value[5];
								travelRoom.travel_startDate = value[6];
								travelRoom.travel_duration = value[7];
								travelRoom.travel_place = value[8];
								travelRoom.travel_location = value[9];
								travelRoom.travel_writingNum = value[10];
								travelRoom.travel_userId = value[11];
								travelRoom.travel_joinNum = value[12];
								travelRoom.travel_echoNum = value[13];
								travelRoom.travel_join = value[14];
								travelRoom.travel_echo = value[15];
								travelRoom.travel_userPic = value[16];
								
								arrayThemeRoom.add(travelRoom);
								break;
								
							case 2:
								exerciseRoom = new Awooseong_Room_Var_Exercise();
								exerciseRoom.exercise_subject = value[1];
								exerciseRoom.exercise_content = value[2];
								exerciseRoom.exercise_date = value[3];
								exerciseRoom.exercise_pictureData = value[4];
								exerciseRoom.exercise_numPeople = value[5];
								exerciseRoom.exercise_startDate = value[6];
								exerciseRoom.exercise_duration = value[7];
								exerciseRoom.exercise_place = value[8];
								exerciseRoom.exercise_location = value[9];
								exerciseRoom.exercise_writingNum = value[10];
								exerciseRoom.exercise_userId = value[11];
								exerciseRoom.exercise_joinNum = value[12];
								exerciseRoom.exercise_echoNum = value[13];
								exerciseRoom.exercise_join = value[14];
								exerciseRoom.exercise_echo = value[15];
								exerciseRoom.exercise_userPic = value[16];
								arrayThemeRoom.add(exerciseRoom);
								break;
								
							case 3:
								hobbyRoom = new Awooseong_Room_Var_Hobby();
								hobbyRoom.hobby_subject = value[1];
								hobbyRoom.hobby_content = value[2];
								hobbyRoom.hobby_date = value[3];
								hobbyRoom.hobby_pictureData = value[4];
								hobbyRoom.hobby_numPeople = value[5];
								hobbyRoom.hobby_startDate = value[6];
								hobbyRoom.hobby_duration = value[7];
								hobbyRoom.hobby_place = value[8];
								hobbyRoom.hobby_location = value[9];
								hobbyRoom.hobby_writingNum = value[10];
								hobbyRoom.hobby_userId = value[11];
								hobbyRoom.hobby_joinNum = value[12];
								hobbyRoom.hobby_echoNum = value[13];
								hobbyRoom.hobby_join = value[14];
								hobbyRoom.hobby_echo = value[15];
								hobbyRoom.hobby_userPic = value[16];
								arrayThemeRoom.add(hobbyRoom);
								break;
								
							case 4:
								studyRoom = new Awooseong_Room_Var_Study();
								studyRoom.study_subject = value[1];
								studyRoom.study_content = value[2];
								studyRoom.study_date = value[3];
								studyRoom.study_pictureData = value[4];
								studyRoom.study_numPeople = value[5];
								studyRoom.study_startDate = value[6];
								studyRoom.study_duration = value[7];
								studyRoom.study_place = value[8];
								studyRoom.study_day_week = value[9];
								studyRoom.study_location = value[10];
								studyRoom.study_writingNum = value[11];
								studyRoom.study_userId = value[12];
								studyRoom.study_joinNum = value[13];
								studyRoom.study_echoNum = value[14];
								studyRoom.study_join = value[15];
								studyRoom.study_echo = value[16];
								studyRoom.study_userPic = value[17];
								arrayThemeRoom.add(studyRoom);
								break;
								
							case 5:
								questionRoom = new Awooseong_Room_Var_Question();
								questionRoom.question_subject = value[1];
								questionRoom.question_content = value[2];
								questionRoom.question_date = value[3];
								questionRoom.question_pictureData = value[4];
								questionRoom.question_is_completed = value[5];
								questionRoom.question_location = value[6];
								questionRoom.question_writingNum = value[7];
								questionRoom.question_userId = value[8];
								questionRoom.question_echoNum = value[9];
								questionRoom.question_echo = value[10];
								questionRoom.question_userPic = value[11];
								arrayThemeRoom.add(questionRoom);
								break;
								
							case 6:
								jobRoom = new Awooseong_Room_Var_Job();
								jobRoom.job_subject = value[1];
								jobRoom.job_content = value[2];
								jobRoom.job_date = value[3];
								jobRoom.job_pictureData = value[4];
								jobRoom.job_numPeople = value[5];
								jobRoom.job_dayWeek = value[6];
								jobRoom.job_startDate = value[7];
								jobRoom.job_duration = value[8];
								jobRoom.job_place = value[9];
								jobRoom.job_pay = value[10];
								jobRoom.job_location = value[11];
								jobRoom.job_writingNum = value[12];
								jobRoom.job_userId = value[13];
								jobRoom.job_joinNum = value[14];
								jobRoom.job_echoNum = value[15];
								jobRoom.job_join = value[16];
								jobRoom.job_echo = value[17];
								jobRoom.job_userPic = value[18];
								arrayThemeRoom.add(jobRoom);
								break;
								
							case 7:
								usedRoom = new Awooseong_Room_Var_Used();
								usedRoom.used_subject = value[1];
								usedRoom.used_content = value[2];
								usedRoom.used_date = value[3];
								usedRoom.used_pictureData = value[4];
								usedRoom.used_section = value[5];
								usedRoom.used_isCompleted = value[6];
								usedRoom.used_isSell = value[7];
								usedRoom.used_price = value[8];
								usedRoom.used_howToSell = value[9];
								usedRoom.used_location = value[10];
								usedRoom.used_writingNum = value[11];
								usedRoom.used_userId = value[12];
								usedRoom.used_joinNum = value[13];
								usedRoom.used_echoNum = value[14];
								usedRoom.used_join = value[15];
								usedRoom.used_echo = value[16];
								usedRoom.used_userPic = value[17];
								arrayThemeRoom.add(usedRoom);
								break;
								
							}
							// writingNum is for additional
							getWritingNum(arrayThemeRoom.get(i));
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					Log.d("EchoListLayout", "Getting writing succeeded");
				}
				else{
					Log.d("EchoListLayout", "Getting writing failed");
				}
				break;
			}
			if(isAdditional && arrayThemeRoom.size() == 0){
				Log.i("EchoListLayout", "isAdditional has nothing");
				handler.sendEmptyMessage(1);
				Toast.makeText(mcontext, "더 이상의 정보가 없습니다", 0).show();
				return;
			}
			addRoomInScroll();
			Log.i("EchoListLayout","call putInfoIntoScroll");
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}
}*/

