package com.ashraya.customer.domain;

import com.ashraya.customer.constants.AppUsage;

import lombok.Data;

@Data
public class LoginRequestPayload {

    private String loginType;
    private String firstName;
    private String lastName;
    private String displayName;
    private String emailId;
    private String phoneNumber;
    private String googlePictureUrl;
    private String facebookPictureUrl;
    private String gstNumber;
    private int categoryId;
    private boolean emailVerified;
    private AppUsage appUsage;
}
