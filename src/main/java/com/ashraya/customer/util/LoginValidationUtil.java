package com.ashraya.customer.util;

import java.util.Optional;

import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.constants.LoginType;
import com.ashraya.customer.domain.LoginRequestPayload;
import com.ashraya.customer.exception.ValidationException;

public class LoginValidationUtil {

    public static void validate(LoginRequestPayload loginRequestPayload) {
        if (loginRequestPayload.getLoginType() == LoginType.PHONE) {
            validatePhone(loginRequestPayload.getPhoneNumber());
        }
        if (loginRequestPayload.getGstNumber() == null) {
            throw new ValidationException(Constants.GST_NOT_FOUND);
        }
    }

    public static LoginType validateLogintype(LoginType loginType) {
        return Optional.ofNullable(loginType).orElseThrow(() -> new ValidationException(Constants.LOGIN_TYPE_NOT_FOUND));
    }

    public static String validateToken(String emailId) {
        return Optional.ofNullable(emailId).orElseThrow(() -> new ValidationException(Constants.EMAIL_NOT_FOUND));
    }

    public static String validatePhone(String phonenumber) {
        return Optional.ofNullable(phonenumber).orElseThrow(() -> new ValidationException(Constants.PHONE_NUMBER_NOT_FOUND));
    }
}
