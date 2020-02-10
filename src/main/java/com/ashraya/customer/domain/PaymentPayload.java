package com.ashraya.customer.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPayload {

	private Integer orderId;
	
	private String paymentMode;
	
	private Float amount;
	
	private Float discount;
	
}
