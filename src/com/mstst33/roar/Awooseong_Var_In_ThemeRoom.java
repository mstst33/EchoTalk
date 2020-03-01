package com.mstst33.roar;

public class Awooseong_Var_In_ThemeRoom {

	public Awooseong_Var_DailyInfo var_daily = null;
	public Awooseong_Var_ExerciseInfo var_exercise = null;
	public Awooseong_Var_TravelInfo var_travel = null;
	public Awooseong_Var_HobbyInfo var_hobby = null;
	public Awooseong_Var_StudyInfo var_study = null;
	public Awooseong_Var_JobInfo var_job = null;
	public Awooseong_Var_QuestionInfo var_question = null;
	public Awooseong_Var_UsedInfo var_used = null;
	
	private int theme;
	
	public int getThemeNumber(){
		return theme;
	}
	
	public Awooseong_Var_In_ThemeRoom(int theme){
		
		this.theme = theme;
		switch(theme){
		case Awooseong_Theme.DAILY:
			var_daily = new Awooseong_Var_DailyInfo();
			break;
		case Awooseong_Theme.TRAVEL:
			var_travel = new Awooseong_Var_TravelInfo();
			break;
		case Awooseong_Theme.EXERCISE:
			var_exercise = new Awooseong_Var_ExerciseInfo();
			break;
		case Awooseong_Theme.HOBBY:
			var_hobby = new Awooseong_Var_HobbyInfo();
			break;
		case Awooseong_Theme.STUDY:
			var_study = new Awooseong_Var_StudyInfo();
			break;
		case Awooseong_Theme.QUESTION: 
			var_question = new Awooseong_Var_QuestionInfo();
			break;
		case Awooseong_Theme.JOB:
			var_job = new Awooseong_Var_JobInfo();
			break;
		case Awooseong_Theme.USED:
			var_used = new Awooseong_Var_UsedInfo();
			break;
		}
	}
	public class Awooseong_Var_DailyInfo {

		public String daily_content;
		public String daily_date;
		public String daily_pictureData;
		public String daily_location;
		public String daily_writingNum;
		public String daily_userId;
		public String daily_echoNum;
		public String daily_echo;
		public String daily_userPic;

	}

	public class Awooseong_Var_ExerciseInfo {

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
	}

	public class Awooseong_Var_HobbyInfo {

		// got hobby data from server
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

	}

	public class Awooseong_Var_JobInfo {

		// got job data from server
		public String job_subject;
		public String job_content;
		public String job_date;
		public String job_pictureData;
		public String job_numPeople;
		public String job_dayWeek;
		public String job_startDate;
		public String job_duration;
		public String job_place;
		public String job_pay;
		public String job_location;
		public String job_writingNum;
		public String job_userId;
		public String job_joinNum;
		public String job_echoNum;
		public String job_join;
		public String job_echo;
		public String job_userPic;

	}

	public class Awooseong_Var_QuestionInfo {

		// got question data from server
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
	}

	public class Awooseong_Var_StudyInfo {
		// got study data from server
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

	}

	public class Awooseong_Var_TravelInfo {

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

	}

	public class Awooseong_Var_UsedInfo {

		// got used data from server
		public String used_subject;
		public String used_content;
		public String used_date;
		public String used_pictureData;
		public String used_section;
		public String used_isCompleted;
		public String used_isSell;
		public String used_price;
		public String used_howToSell;
		public String used_location;
		public String used_writingNum;
		public String used_userId;
		public String used_joinNum;
		public String used_echoNum;
		public String used_join;
		public String used_echo;
		public String used_userPic;
	}
}
