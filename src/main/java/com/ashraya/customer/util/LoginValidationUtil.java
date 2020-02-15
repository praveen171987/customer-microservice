package com.ashraya.customer.util;

import java.util.Optional;

import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.constants.LoginType;
import com.ashraya.customer.domain.LoginRequestPayload;
import com.ashraya.customer.exception.ValidationException;

public class LoginValidationUtil {

    public static void validate(LoginRequestPayload loginRequestPayload) {
        if (loginRequestPayload.getLoginType().equals(LoginType.PHONE.toString())) {
            validatePhone(loginRequestPayload.getPhoneNumber());
        }
        if (loginRequestPayload.getGstNumber() == null) {
            throw new ValidationException(Constants.GST_NOT_FOUND);
        }
    }

    public static String validateLogintype(String loginType) {
        return Optional.ofNullable(loginType).orElseThrow(() -> new ValidationException(Constants.LOGIN_TYPE_NOT_FOUND));
    }

    public static String validateEmail(String emailId) {
        return Optional.ofNullable(emailId).orElseThrow(() -> new ValidationException(Constants.EMAIL_NOT_FOUND));
    }

    public static String validatePhone(String phonenumber) {
        return Optional.ofNullable(phonenumber).orElseThrow(() -> new ValidationException(Constants.PHONE_NUMBER_NOT_FOUND));
    }
}
