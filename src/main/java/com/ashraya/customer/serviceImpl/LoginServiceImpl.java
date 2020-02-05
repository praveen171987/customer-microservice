package com.ashraya.customer.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashraya.customer.LoggerService;
import static com.ashraya.customer.constants.Constants.*;
import com.ashraya.customer.constants.LoginType;
import com.ashraya.customer.domain.LoginMapper;
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

	@Autowired
	private LoggerService log;

	private static final String className = LoginServiceImpl.class.getName();

	public CustomerResponse loginOrRegister(LoginRequestPayload payload) {
		log.printStart(className, "loginOrRegister");
		LoginType loginType = LoginValidationUtil.validateLogintype(payload.getLoginType());
		if (loginType.equals(LoginType.PHONE)) {
			loginWithMobile(payload);
		} else if (loginType.equals(LoginType.GOOGLE)) {
			loginWithGoogle(payload);
		} else if (loginType.equals(LoginType.FACEBOOK)) {
			loginWithFacebook(payload);
		}

		log.printEnd(className, "loginOrRegister");
		return createResponse(status, payload, waterRecipient);
	}

	private void loginWithMobile(LoginRequestPayload payload) {
		log.printStart(className, "loginWithMobile");
		String phoneNumber = LoginValidationUtil.validatePhone(payload.getPhoneNumber());
		waterRecipient = waterRecpientRepository.findByMobileNumber(phoneNumber);
		if (waterRecipient != null) {
			status = LOGIN_STATUS;
		} else {
			LoginValidationUtil.validate(payload);
			createWaterRecipient(payload, null, null);
		}

		log.printEnd(className, "loginWithMobile");

	}

	private void loginWithFacebook(LoginRequestPayload payload) {

		log.printStart(className, "loginWithFacebook");

		String email = LoginValidationUtil.validateEmail(payload.getEmailId());
		Integer id = facebookAccountRepository.findByEmailId(email);

		if (id != null) {
			waterRecipient = waterRecpientRepository.findByFacebookAccountInfoId(id);
			status = LOGIN_STATUS;
		} else {
			LoginValidationUtil.validate(payload);
			FacebookAccountInfo facebookAccountInfo = facebookAccountRepository
					.save(LoginMapper.mapToFacebookAccountInfo(payload));
			createWaterRecipient(payload, facebookAccountInfo, null);
		}

		log.printEnd(className, "loginWithFacebook");

	}

	private void loginWithGoogle(LoginRequestPayload payload) {

		log.printStart(className, "loginWithGoogle");

		String email = LoginValidationUtil.validateEmail(payload.getEmailId());
		Integer id = googleAccountRepository.findByEmail(email);
		if (id != null) {
			waterRecipient = waterRecpientRepository.findByGoogleAccountInfoId(id);
			status = LOGIN_STATUS;
		} else {
			LoginValidationUtil.validate(payload);
			GoogleAccountInfo googleAccountInfo = googleAccountRepository
					.save(LoginMapper.mapToGoogleAccountInfo(payload));
			createWaterRecipient(payload, null, googleAccountInfo);
		}

		log.printEnd(className, "loginWithGoogle");

	}

	private void createWaterRecipient(LoginRequestPayload payload, FacebookAccountInfo facebookAccountInfo,
			GoogleAccountInfo accountInfo) {

		log.printStart(className, "createWaterRecipient");

		waterRecipient = waterRecpientRepository.save(LoginMapper.mapToWaterRecipient(payload, facebookAccountInfo,
				accountInfo, categoryRepository.findOne(payload.getCategoryId())));
		status = REGISTER_STATUS;

		log.printEnd(className, "createWaterRecipient");

	}

	private CustomerResponse createResponse(String status, LoginRequestPayload payload, WaterRecipient waterRecipient) {
		return CustomerResponse.builder().status(status).customerId(waterRecipient.getId())
				.userName(payload.getFirstName()).emailId(payload.getEmailId()).build();
	}

	@Override
	public OtpResponse verifyOtp(OtpPayload otpPayload) {
		log.printStart(className, "verifyOtp");

		Otp otp = otpRepository.findByWaterRecipientIdAndOtpNumber(otpPayload.getUserId(), otpPayload.getOtpNumber());
		if (otp == null) {
			log.printEnd(className, "verifyOtp");
			return OtpResponse.builder().status(OTP_NOT_VERIED).build();
		}
		otpRepository.delete(otp);
		log.printEnd(className, "verifyOtp");
		return OtpResponse.builder().status(OTP_VERIFY).build();
	}
}