package com.mstst33.roar;

public class Awooseong_Room_Var_Exercise implements Awooseong_ThemeRoom {
	
	public String exercise_subject;
	public String exercise_content;
	public String exercise_date;
	public String exercise_pictureData;
	public String exercise_numPeople;
	public String exercise_startDate;
	public String exercise_duration;
	public String exercise_place;
	public String exercise_location;
	public String exercise_writingNum;
	public String exercise_userId;
	public String exercise_joinNum;
	public String exercise_echoNum;
	public String exercise_join;
	public String exercise_echo;
	public String exercise_userPic;
	
	@Override
	public int getThemeNumber() {
		// TODO Auto-generated method stub
		return Awooseong_Theme.EXERCISE;
	}
}
