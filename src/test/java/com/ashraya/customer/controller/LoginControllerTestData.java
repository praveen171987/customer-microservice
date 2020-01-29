package com.ashraya.customer.controller;

import com.ashraya.customer.constants.LoginType;
import com.ashraya.customer.domain.LoginRequestPayload;
import com.ashraya.customer.domain.OtpPayload;

public class LoginControllerTestData {
	
	public final static String LOGIN_PATH = "http://localhost:8181/customer/v1/login";
	public final static String OTP_PATH = "http://localhost:8181/customer/v1/verifyOtp";
	
	public final static String DISPLAY_NAME = "JohnDoe";
	public final static String LAST_NAME = "Doe";
	public final static String FIRST_NAME = "John";
	public final static int CATEGORY_ID = 1;
	public final static String PHONE_NUMBER = "1222354354";
	public final static String EMAIL = "JohnDoe@mail.com";
	public final static String GOOGLE_PICTURE_URL = "https://google.com/path/to/picture";
	public final static String GST_NUMBER = "123435";
	
	public static LoginRequestPayload get() {		
		 
		LoginRequestPayload payload = new LoginRequestPayload();
		 payload.setCategoryId(CATEGORY_ID);
		 payload.setDisplayName(DISPLAY_NAME);
		 payload.setEmailId(EMAIL);
		 payload.setEmailVerified(true);
		 payload.setGooglePictureUrl(GOOGLE_PICTURE_URL);
		 payload.setGstNumber(GST_NUMBER);
		 payload.setFirstName(DISPLAY_NAME);
		 payload.setLastName(LAST_NAME);
		 payload.setPhoneNumber(PHONE_NUMBER);
		
		 payload.setLoginType(LoginType.GOOGLE);
		 
		 return payload;
	}
	
	public static OtpPayload getOtpData() {		
		 OtpPayload payload = new OtpPayload();
		  payload.setOtpNumber("1234");
		 return payload;
	}
	
	public static LoginRequestPayload getInvalidData() {
		LoginRequestPayload payload = get();
		 payload.setLoginType(null);
		 return payload;
	}
}
