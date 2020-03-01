package com.mstst33.roar;

public class Awooseong_Room_Var_Study implements Awooseong_ThemeRoom {

	public String study_subject;
	public String study_content;
	public String study_date;
	public String study_pictureData;
	public String study_numPeople;
	public String study_startDate;
	public String study_duration;
	public String study_place;
	public String study_day_week;
	public String study_location;
	public String study_writingNum;
	public String study_userId;
	public String study_joinNum;
	public String study_echoNum;
	public String study_join;
	public String study_echo;
	public String study_userPic;
	
	@Override
	public int getThemeNumber() {
		// TODO Auto-generated method stub
		return Awooseong_Theme.STUDY;
	}

}
