package com.ashraya.customer.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.constants.LoginType;
import com.ashraya.customer.domain.CustomerMapper;
import com.ashraya.customer.domain.CustomerResponse;
import com.ashraya.customer.domain.LoginRequestPayload;
import com.ashraya.customer.domain.OtpPayload;
import com.ashraya.customer.domain.OtpResponse;
import com.ashraya.customer.model.FacebookAccountInfo;
import com.ashraya.customer.model.GoogleAccountInfo;
import com.ashraya.customer.model.Otp;
import com.ashraya.customer.model.WaterRecipient;
import com.ashraya.customer.repository.CategoryRepository;
import com.ashraya.customer.repository.FacebookAccountRepository;
import com.ashraya.customer.repository.GoogleAccountRepository;
import com.ashraya.customer.repository.OtpRepository;
import com.ashraya.customer.repository.WaterRecpientRepository;
import com.ashraya.customer.service.LoginService;
import com.ashraya.customer.util.LoginValidationUtil;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private WaterRecpientRepository waterRecpientRepository;

    @Autowired
    private GoogleAccountRepository googleAccountRepository;

    @Autowired
    private FacebookAccountRepository facebookAccountRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private String status;
    private WaterRecipient waterRecipient;

    public CustomerResponse loginOrRegister(LoginRequestPayload payload) {
        LoginType loginType = LoginValidationUtil.validateLogintype(payload.getLoginType());
        if (loginType.equals(LoginType.PHONE)) {
            loginWithMobile(payload);
        } else if (loginType.equals(LoginType.GOOGLE)) {
            loginWithGoogle(payload);
        } else if (loginType.equals(LoginType.FACEBOOK)) {
            loginWithFacebook(payload);
        }
        return createResponse(status, payload, waterRecipient);
    }

    private void loginWithMobile(LoginRequestPayload payload) {
        String phoneNumber = LoginValidationUtil.validatePhone(payload.getPhoneNumber());
        waterRecipient = waterRecpientRepository.findByMobileNumber(phoneNumber);
        if (waterRecipient != null) {
            status = Constants.STATUS_LOGIN;
        } else {
            LoginValidationUtil.validate(payload);
            createWaterRecipient(payload, null, null);
        }
    }

    private void loginWithFacebook(LoginRequestPayload payload) {
        String token = LoginValidationUtil.validateToken(payload.getEmailId());
        Integer id = facebookAccountRepository.findByEmailId(token);
        if (id != null) {
            waterRecipient = waterRecpientRepository.findByFacebookAccountInfoId(id);
            status = Constants.STATUS_LOGIN;
        } else {
            LoginValidationUtil.validate(payload);
            FacebookAccountInfo facebookAccountInfo = facebookAccountRepository.save(CustomerMapper.mapToFacebookAccountInfo(payload));
            createWaterRecipient(payload, facebookAccountInfo, null);
        }
    }

    private void loginWithGoogle(LoginRequestPayload payload) {
        String token = LoginValidationUtil.validateToken(payload.getEmailId());
        Integer id = googleAccountRepository.findByEmail(token);
        if (id != null) {
            waterRecipient = waterRecpientRepository.findByGoogleAccountInfoId(id);
            status = Constants.STATUS_LOGIN;
        } else {
            LoginValidationUtil.validate(payload);
            GoogleAccountInfo googleAccountInfo = googleAccountRepository.save(CustomerMapper.mapToGoogleAccountInfo(payload));
            createWaterRecipient(payload, null, googleAccountInfo);
        }
    }

    private void createWaterRecipient(LoginRequestPayload payload, FacebookAccountInfo facebookAccountInfo, GoogleAccountInfo accountInfo) {
        waterRecipient = waterRecpientRepository.save(CustomerMapper.mapToWaterRecipient(payload, facebookAccountInfo, accountInfo,
                        categoryRepository.findOne(payload.getCategoryId())));
        status = Constants.STATUS_REGISTER;
    }

    private CustomerResponse createResponse(String status, LoginRequestPayload payload, WaterRecipient waterRecipient) {
        return CustomerResponse.builder().status(status).customerId(waterRecipient.getId()).userName(payload.getFirstName()).emailId(payload.getEmailId()).build();
    }

    @Override
    public OtpResponse verifyOtp(OtpPayload otpPayload) {
        OtpResponse otpResponse = new OtpResponse();
        Otp otp = otpRepository.findByWaterRecipientIdAndOtpNumber(otpPayload.getUserId(), otpPayload.getOtpNumber());
        if (otp != null) {
            otpRepository.delete(otp);
            otpResponse.setStatus(Constants.OTP_VERIFY);
        } else {
            otpResponse.setStatus(Constants.OTP_NOT_VERIED);
        }
        return otpResponse;
    }
}