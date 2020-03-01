package com.mstst33.echo;

/*
public class EchoRoom implements View.OnClickListener, JoinOrCommentAble{

	private Context mcontext;
	private boolean isFavorite;
	private Awooseong_ThemeRoom roomInfo;	
	
	private RelativeLayout mainView;
	private LayoutInflater inflater;
	private LinearLayout joinSet;
	private LinearLayout commentSet;
	
	private ImageView deleteImg;
	private ImageView themeImg;
	private ImageView joinImg;
	private ImageView commentImg;
	
	private TextView joinNum;
	private TextView commentNum;
	private TextView roomName;
	private TextView roomDate;
	
	private String subject;
	private String date;
	private String joinNumber;
	private String commentNumber;
	private String writingNum;
	private int theme;
	private Handler handler;

	public EchoRoom(Context context, Awooseong_ThemeRoom roomInfo , boolean isFavorite, Handler handler) {
		
		// TODO Auto-generated constructor stub
		mcontext = context;
		this.isFavorite = isFavorite;
		this.roomInfo = roomInfo;
		this.handler = handler;
		theme = roomInfo.getThemeNumber();
		
		mainView = (RelativeLayout)View.inflate(mcontext, R.layout.echo_list_room_custom, null);
		
		deleteImg = (ImageView)mainView.findViewById(R.id.echo_list_custom_deleteImg);
		themeImg = (ImageView)mainView.findViewById(R.id.echo_list_custom_themeImg);
		roomName = (TextView)mainView.findViewById(R.id.echo_list_custom_room_name);
		roomDate = (TextView)mainView.findViewById(R.id.echo_list_custom_room_date);
		joinNum = (TextView)mainView.findViewById(R.id.echo_list_custom_JoinLikeView);
		commentNum = (TextView)mainView.findViewById(R.id.echo_list_custom_CommentLikeView);
		joinSet = (LinearLayout) mainView.findViewById(R.id.echo_list_custom_join);
		commentSet = (LinearLayout) mainView.findViewById(R.id.echo_list_custom_comment);
		
		deleteImg.setOnClickListener(this);
		themeImg.setOnClickListener(this);
		roomName.setOnClickListener(this);
		joinNum.setOnClickListener(this);
		commentNum.setOnClickListener(this);
		joinSet.setOnClickListener(this);
		commentSet.setOnClickListener(this);
		
		if(isFavorite)
			deleteImg.setVisibility(View.GONE);
		setRoomContent();
	}
	
	public RelativeLayout getWholeView(){
		return mainView;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if( v == deleteImg ){
			
			new AlertDialog.Builder(mcontext)
			.setTitle("삭제요청")
			.setMessage("해당 글을 삭제 하시겠습니까?")
			.setPositiveButton("예", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Message msg = Message.obtain();
					msg.what = 0;
					msg.arg1 = theme; 
					msg.obj = writingNum;
					handler.sendMessage(msg);
				}
			})
			.setNegativeButton("아니오", null)
			.show();
		}
		
		else if( v == themeImg || v == roomName ){
			Log.i("EchoRoom", "room is clicked");
			Echo_Dialog_EachRoom dialogEachRoom = new Echo_Dialog_EachRoom(mcontext, roomInfo);
			dialogEachRoom.show();
		}
	//	
		//else if( v == joinNum || v == joinSet){
		//	Log.i("EchoRoom", "joiNum is Clicked");
		//	Awooseong_Dialog_Join dialogJoin= new Awooseong_Dialog_Join(mcontext, this, roomInfo);
		//	dialogJoin.show();
	//	}
		
		//else if( v == commentNum || v == commentSet ){
		//	Log.i("EchoRoom", "commentNum is Clicked");
	//		Awooseong_Dialog_Comment dialogComment = new Awooseong_Dialog_Comment(mcontext, this, roomInfo);
		//	dialogComment.show();
		//}	
		//
	}
	
	public void recycleTheme(){
		if(themeImg != null)
			themeImg.setBackgroundDrawable(null);
		if(deleteImg != null)
			deleteImg.setBackgroundDrawable(null);
		if(themeImg != null)
			themeImg.setBackgroundDrawable(null);
		if( joinImg != null)
			joinImg.setBackgroundDrawable(null);
		if( commentImg != null)
			commentImg.setBackgroundDrawable(null);
	}
	
	private void setRoomContent(){
		if(!isFavorite){ // roomList you made
			switch (roomInfo.getThemeNumber()) {
	
			case 0:
				themeImg.setImageResource(R.drawable.life_balloon);
				joinSet.setVisibility(View.INVISIBLE);
				break;
			case 1:
				themeImg.setImageResource(R.drawable.tour_balloon);
				break;
			case 2:
				themeImg.setImageResource(R.drawable.sports_balloon);
				break;
			case 3:
				themeImg.setImageResource(R.drawable.hobby_balloon);
				break;
			case 4:
				themeImg.setImageResource(R.drawable.study_balloon);
				break;
			case 5:
				themeImg.setImageResource(R.drawable.question_balloon);
				joinSet.setVisibility(View.INVISIBLE);
				break;
			case 6:
				themeImg.setImageResource(R.drawable.job_balloon);
				break;
			case 7:
				themeImg.setImageResource(R.drawable.reuse_balloon);
				break;
			}
		}
		else{ //favorite room list
			switch (roomInfo.getThemeNumber()) {
			
			case 0:
				themeImg.setImageResource(R.drawable.life_roll);
				joinSet.setVisibility(View.INVISIBLE);
				deleteImg.setVisibility(View.GONE);
				break;
			case 1:
				themeImg.setImageResource(R.drawable.tour_roll);
				deleteImg.setVisibility(View.GONE);
				break;
			case 2:
				themeImg.setImageResource(R.drawable.sports_roll);
				deleteImg.setVisibility(View.GONE);
				break;
			case 3:
				themeImg.setImageResource(R.drawable.hobby_roll);
				deleteImg.setVisibility(View.GONE);
				break;
			case 4:
				themeImg.setImageResource(R.drawable.study_roll);
				deleteImg.setVisibility(View.GONE);
				break;
			case 5:
				themeImg.setImageResource(R.drawable.question_roll);
				joinSet.setVisibility(View.INVISIBLE);
				deleteImg.setVisibility(View.GONE);
				break;
			case 6:
				themeImg.setImageResource(R.drawable.job_roll);
				deleteImg.setVisibility(View.GONE);
				break;
			case 7:
				themeImg.setImageResource(R.drawable.reuse_roll);
				deleteImg.setVisibility(View.GONE);
				break;
			}
		}
		
		switch(roomInfo.getThemeNumber()){
		
		case Awooseong_Theme.DAILY:
			Awooseong_Room_Var_Daily A;
			if (roomInfo instanceof Awooseong_Room_Var_Daily) {
				A = (Awooseong_Room_Var_Daily) roomInfo;
			}
			else
				return;			
			subject = A.daily_content;
			date = A.daily_date;
			commentNumber = A.daily_echoNum;
			writingNum = A.daily_writingNum;
			break;
			
		case Awooseong_Theme.EXERCISE:
			Awooseong_Room_Var_Exercise exer;
			if (roomInfo instanceof Awooseong_Room_Var_Exercise) {
				exer = (Awooseong_Room_Var_Exercise) roomInfo;
			}
			else
				return;
			
			subject = exer.exercise_subject;
			date = exer.exercise_date;
			joinNumber = exer.exercise_joinNum;
			commentNumber = exer.exercise_echoNum;
			writingNum = exer.exercise_writingNum;
			break;
			
		case Awooseong_Theme.HOBBY:
			Awooseong_Room_Var_Hobby hob;
			if (roomInfo instanceof Awooseong_Room_Var_Hobby) {
				hob = (Awooseong_Room_Var_Hobby)roomInfo;
			}
			else
				return;
			
			subject = hob.hobby_subject;
			date = hob.hobby_date;
			joinNumber = hob.hobby_joinNum;
			commentNumber = hob.hobby_echoNum;
			writingNum = hob.hobby_writingNum;
			break;
			
		case Awooseong_Theme.JOB:
			Awooseong_Room_Var_Job jo;
			if (roomInfo instanceof Awooseong_Room_Var_Job) {
				jo = (Awooseong_Room_Var_Job)roomInfo;
			}
			else
				return;
			
			subject = jo.job_subject;
			date = jo.job_date;
			joinNumber = jo.job_joinNum;
			commentNumber = jo.job_echoNum;
			writingNum = jo.job_writingNum;
			break;
			
		case Awooseong_Theme.QUESTION:
			Awooseong_Room_Var_Question qe;
			if (roomInfo instanceof Awooseong_Room_Var_Question) {
				qe = (Awooseong_Room_Var_Question)roomInfo;
			}
			else
				return;
			subject = qe.question_subject;
			date = qe.question_date;
			commentNumber = qe.question_echoNum;
			writingNum = qe.question_writingNum;
			break;
			
		case Awooseong_Theme.STUDY:
			Awooseong_Room_Var_Study stu;
			if (roomInfo instanceof Awooseong_Room_Var_Study) {
				stu = (Awooseong_Room_Var_Study)roomInfo;
			}
			else
				return;
			
			subject = stu.study_subject;
			date = stu.study_date;
			joinNumber = stu.study_joinNum;
			commentNumber = stu.study_echoNum;
			writingNum = stu.study_writingNum;
			break;
			
		case Awooseong_Theme.TRAVEL:
			Awooseong_Room_Var_Travel tr;
			if (roomInfo instanceof Awooseong_Room_Var_Travel) {
				tr = (Awooseong_Room_Var_Travel)roomInfo;
			}
			else
				return;
			
			subject = tr.travel_subject;
			date = tr.travel_date;
			joinNumber = tr.travel_joinNum;
			commentNumber = tr.travel_echoNum;
			writingNum = tr.travel_writingNum;
			break;
			
		case Awooseong_Theme.USED:
			
			Awooseong_Room_Var_Used us;
			if (roomInfo instanceof Awooseong_Room_Var_Used) {
				us = (Awooseong_Room_Var_Used)roomInfo;
			}
			else
				return;
			subject = us.used_subject;
			date = us.used_date;
			joinNumber = us.used_joinNum;
			commentNumber = us.used_echoNum;
			writingNum = us.used_writingNum;
			break;
		}
		roomName.setText(subject);
		roomDate.setText(date);
		joinNum.setText(joinNumber);
		commentNum.setText(commentNumber);
	}
	
	public void setUpgradeJoinNum(String newNum){
		
		joinNum.setVisibility(View.INVISIBLE);
		joinNum.setText(newNum);
		joinNum.setVisibility(View.VISIBLE);
	}
	
	public void setUpgradeCommentNum(String newNum){
		commentNum.setVisibility(View.INVISIBLE);
		commentNum.setText(newNum);
		commentNum.setVisibility(View.VISIBLE);
	}
	
}*/

