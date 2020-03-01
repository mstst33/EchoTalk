package com.mstst33.roar;

import android.util.*;

public class Awooseong_Theme {
	
	public static final int DAILY = 0;
	public static final int TRAVEL = 1;
	public static final int EXERCISE = 2;
	public static final int HOBBY = 3;
	public static final int STUDY = 4;
	public static final int QUESTION = 5;
	public static final int JOB = 6;
	public static final int USED = 7;
	
	private boolean exercise;
	private boolean daily;
	private boolean hobby;
	private boolean job;
	private boolean question;
	private boolean study;
	private boolean travel;
	private boolean used;
	
	// 1. daily, 2. exercise, 3.hobby
	// 4. job    5. question  6.study
	// 7. travel 8. used      9.shout
	
	Awooseong_Theme(){
		exercise = false;
		daily = false;
		hobby = false;
		job = false;
		question = false;
		study = false;
		travel = false;
		used = false;
	}
	
	public String getThemeChosen(){ //BasicInfo.INTERESTED_THEME의 형식에 맞게 값을 변화하여 반환.
		// set theme as the theme user choose from the theme dialog
		StringBuffer st = new StringBuffer();
		
		if( getDaily() == true )
			st.append("1");
		else
			st.append("0");
		
		if( getTravel() == true )
			st.append("1");
		else
			st.append("0");

		if( getExercise() == true )
			st.append("1");
		else
			st.append("0");
		
		if( getHobby() == true )
			st.append("1");
		else
			st.append("0");
		
		if( getStudy() == true )
			st.append("1");
		else
			st.append("0");
		
		if( getQuestion() == true )
			st.append("1");
		else
			st.append("0");
		
		if( getJob() == true )
			st.append("1");
		else
			st.append("0");
		
		if( getUsed() == true )
			st.append("1");
		else
			st.append("0");
		
		Log.i("Awooseong", "theme was chosen" + st.toString());
		
		return st.toString();
		
	}
	
	public void setExercise(boolean choice){
		exercise = choice;
	}
	public void setDaily(boolean choice){
		daily = choice;
	}
	public void setHobby(boolean choice){
		hobby = choice;
	}
	public void setJob(boolean choice){
		job = choice;
	}
	public void setQuestion(boolean choice){
		question = choice;
	}
	public void setStudy(boolean choice){
		study = choice;
	}
	public void setTravel(boolean choice){
		travel = choice;
	}
	public void setUsed(boolean choice){
		used = choice;
	}
	
	
	public boolean getExercise(){
		return exercise;
	}
	public boolean getDaily(){
		return daily;
	}
	public boolean getHobby(){
		return hobby;
	}
	public boolean getJob(){
		return job;
	}
	public boolean getQuestion(){
		return question;
	}
	public boolean getStudy(){
		return study;
	}
	public boolean getTravel(){
		return travel;
	}
	public boolean getUsed(){
		return used;
	}
	

}
