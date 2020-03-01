package com.mstst33.roar;

public class Awooseong_Room_Var_Hobby implements Awooseong_ThemeRoom {

	public String hobby_subject;
	public String hobby_content;
	public String hobby_date;
	public String hobby_pictureData;
	public String hobby_numPeople;
	public String hobby_startDate;
	public String hobby_duration;
	public String hobby_place;
	public String hobby_location;
	public String hobby_writingNum;
	public String hobby_userId;
	public String hobby_joinNum;
	public String hobby_echoNum;
	public String hobby_join;
	public String hobby_echo;
	public String hobby_userPic;
	
	@Override
	public int getThemeNumber() {
		// TODO Auto-generated method stub
		return Awooseong_Theme.HOBBY;
	}

}
