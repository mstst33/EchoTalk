package com.mstst33.roar;

public class Awooseong_Room_Var_Question implements Awooseong_ThemeRoom {

	public String question_subject;
	public String question_content;
	public String question_date;
	public String question_pictureData;
	public String question_is_completed;
	public String question_location;
	public String question_writingNum;
	public String question_userId;
	public String question_echoNum;
	public String question_echo;
	public String question_userPic;
	
	@Override
	public int getThemeNumber() {
		// TODO Auto-generated method stub
		return Awooseong_Theme.QUESTION;
	}
}
