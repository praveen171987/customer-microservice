package com.ashraya.customer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.domain.CustomerResponse;
import com.ashraya.customer.domain.LoginRequestPayload;
import com.ashraya.customer.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {

	 @Autowired
	  private WebApplicationContext webApplicationContext;
	  private MockMvc mockMvc;

	@Mock
	LoginService loginService;

	@InjectMocks
	LoginController loginController;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void whenValidInputForLogin_thenReturns200() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(post(Constants.LOGIN_PATH).contentType("application/json")
				.content(objectMapper.writeValueAsString(LoginControllerTestData.get()))).andExpect(status().isOk());
	}
	
	@Test
	public void whenValidInputForVerifyOtp_thenReturns200() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(post(Constants.OTP_PATH).contentType("application/json")
				.content(objectMapper.writeValueAsString(LoginControllerTestData.getOtpData()))).andExpect(status().isOk());
	}
	
	@Test
	public void whenInValidInput_thenReturns400() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(post(LoginControllerTestData.LOGIN_PATH).contentType("application/json")
				.content(objectMapper.writeValueAsString(LoginControllerTestData.getInvalidData()))).andExpect(status().is4xxClientError());
	}
}
