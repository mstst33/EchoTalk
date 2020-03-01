package com.mstst33.echoproject;

public class BasicInfo {
	/** Flags */
	public static boolean IS_LOGIN = false;
	public static boolean IS_REGISTERED_ID = false;
	public static boolean IS_DEVICE_ID = false;
	public static boolean IS_FIRST = true;
	
	/** Project Info */
	public static final String PROJECT_ID = "1023401221249";
    public static String REG_ID = "";
    
    /** Message Setting */
    public static final int MAX_MESSAGE_NUM = 1;
    public static int CURRENT_MESSAGE_NUM = 0;
    public static boolean IS_THERE_SOUND = true;
	public static boolean IS_THERE_VIBRATE = true;
	public static boolean IS_GET_MESSAGE = true;
    
	/** Distance or Range Setting */
	public static String DISTANCE_TYPE = "3";
	public static String RANGE_TYPE = "1";
	
    /** Person Info */
	public static String USER_PHOTO = "";
    public static String USER_ID = "";
    public static String USER_NAME = "";
    public static String USER_AGE = "";
    public static String USER_GENDER = "0";
    public static String USER_EMAIL = "";
    public static String USER_COMMENT = "";
    public static String USER_JOIN_DATE = "";
    public static String USER_DATE = "";
    public static String INTERESTED_THEME = "00000000";
    public static String LOCATION = "";
    public static String ADDRESS = "";
    public static String NUM_OF_PEOPLE_AROUND_ME = "0";
    
    /** Server Address */
    public static final String SERVER_ADDRESS = "http://mstst.cafe24.com/echo/";
    public static final String SERVER_IMAGE_ADDRESS = "http://mstst.cafe24.com/echo/image/";
}