package com.ashraya.customer.constants;

public class Constants {

    public static final String STATUS_LOGIN = "User LoggedIn";
    public static final String STATUS_REGISTER = "User Registered";
    public static final String GST_NOT_FOUND = "GST number can't be null";
    public static final String LOGIN_TYPE_NOT_FOUND = "LoginType can't be null";
    public static final String EMAIL_NOT_FOUND = "Email can't be null";
    public static final String PHONE_NUMBER_NOT_FOUND = "Phone number can't be null";
    public static final String FACEBOOK_ID_NOT_FOUND = "Facebook Id not found";
    public static final String GOOGLE_ID_NOT_FOUND = "Google Id not found";
    public static final String OTP_VERIFY = "SUCCESS";
    public static final String OTP_NOT_VERIED = "FAIL";
    public static final String VERSION = "/v1";
    
    public final static String LOGIN_PATH = "http://localhost:8181/customer/v1/login";
	public final static String OTP_PATH = "http://localhost:8181/customer/v1/verifyOtp";
}
