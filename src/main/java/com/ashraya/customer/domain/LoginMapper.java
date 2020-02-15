package com.ashraya.customer.domain;

import com.ashraya.customer.constants.LoginType;
import com.ashraya.customer.model.Category;
import com.ashraya.customer.model.FacebookAccountInfo;
import com.ashraya.customer.model.GoogleAccountInfo;
import com.ashraya.customer.model.WaterRecipient;

public class LoginMapper {

    public static GoogleAccountInfo mapToGoogleAccountInfo(LoginRequestPayload payload) {
        return GoogleAccountInfo.builder().firstName(payload.getFirstName()).lastName(payload.getLastName()).displayName(payload.getFirstName()).emailId(payload.getEmailId())
                        .emailVerified(payload.isEmailVerified()).mobileNumber(payload.getPhoneNumber()).googlePictureUrl(payload.getGooglePictureUrl()).build();
    }

    public static FacebookAccountInfo mapToFacebookAccountInfo(LoginRequestPayload payload) {
        return FacebookAccountInfo.builder().displayName(payload.getFirstName()).firstName(payload.getFirstName()).lastName(payload.getLastName()).emailId(payload.getEmailId())
                        .emailVerified(payload.isEmailVerified()).facebookPictureUrl(payload.getFacebookPictureUrl()).mobileNumber(payload.getPhoneNumber()).build();
    }

    public static WaterRecipient mapToWaterRecipient(LoginRequestPayload payload, FacebookAccountInfo facebookAccountInfo, GoogleAccountInfo googleAccountInfo, Category category) {
        return WaterRecipient.builder().facebookAccountInfo(facebookAccountInfo).googleAccountInfo(googleAccountInfo).gstNumber(payload.getGstNumber())
                        .mobileNumber(payload.getPhoneNumber()).name(payload.getDisplayName()).appUsage(payload.getAppUsage()).category(category)
                        .loginAccount(Enum.valueOf(LoginType.class, payload.getLoginType())).build();
    }
}
