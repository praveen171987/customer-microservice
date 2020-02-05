package com.ashraya.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashraya.customer.LoggerService;
import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.domain.CustomerResponse;
import com.ashraya.customer.domain.LoginRequestPayload;
import com.ashraya.customer.domain.OtpPayload;
import com.ashraya.customer.domain.OtpResponse;
import com.ashraya.customer.service.LoginService;

@RestController
@RequestMapping("customer")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private LoggerService log;

    private static final String className = LoginController.class.getName();
    
    @PostMapping(path = Constants.VERSION + "/login", consumes = "application/json", produces = "application/json")
    public CustomerResponse login(@RequestBody LoginRequestPayload payload) {
    	log.printStart(className, payload.toString(), "login");
    	CustomerResponse response =  loginService.loginOrRegister(payload);
    	log.printEnd(className,"login");
    	return response;
    }

    @PostMapping(path = Constants.VERSION + "/verifyOtp", consumes = "application/json", produces = "application/json")
    public OtpResponse verifyOtp(@RequestBody OtpPayload otpPayload) {
    	log.printStart(className,otpPayload.toString(),"verifyOtp");
    	OtpResponse response = loginService.verifyOtp(otpPayload);
    	log.printEnd(className,"verifyOtp");
    	return response;
    }
}
