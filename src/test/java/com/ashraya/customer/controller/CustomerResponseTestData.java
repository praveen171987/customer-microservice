package com.ashraya.customer.controller;

import com.ashraya.customer.domain.CustomerResponse;

public class CustomerResponseTestData {

	public static CustomerResponse get() {
		return CustomerResponse.builder()
				.customerId(1)
				.emailId("JohnDoe@mail.com")
				.status("success")
				.userName("JohnDoe")
				.build();
	}
}
