package com.mstst33.roar;

public class Awooseong_Room_Var_Travel implements Awooseong_ThemeRoom {

	public String travel_subject;
	public String travel_content;
	public String travel_date;
	public String travel_pictureData;
	public String travel_numPeople;
	public String travel_startDate;
	public String travel_duration;
	public String travel_place;
	public String travel_location;
	public String travel_writingNum;
	public String travel_userId;
	public String travel_joinNum;
	public String travel_echoNum;
	public String travel_join;
	public String travel_echo;
	public String travel_userPic;
	
	@Override
	public int getThemeNumber() {
		// TODO Auto-generated method stub
		return Awooseong_Theme.TRAVEL;
	}
}
