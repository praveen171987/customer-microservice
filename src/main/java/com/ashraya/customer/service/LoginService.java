package com.ashraya.customer.service;

import com.ashraya.customer.domain.CustomerResponse;
import com.ashraya.customer.domain.LoginRequestPayload;
import com.ashraya.customer.domain.OtpPayload;
import com.ashraya.customer.domain.OtpResponse;

public interface LoginService {

    public CustomerResponse loginOrRegister(LoginRequestPayload payload);

    public OtpResponse verifyOtp(OtpPayload otpPayload);

}
