package com.mstst33.roar;

public class Awooseong_Room_Var_Daily implements Awooseong_ThemeRoom {

	public String daily_content;
	public String daily_date;
	public String daily_pictureData;
	public String daily_location;
	public String daily_writingNum;
	public String daily_userId;
	public String daily_echoNum;
	public String daily_echo;
	public String daily_userPic;
	
	@Override
	public int getThemeNumber() {
		// TODO Auto-generated method stub
		return Awooseong_Theme.DAILY;
	}
}
