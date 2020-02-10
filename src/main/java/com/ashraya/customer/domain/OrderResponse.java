package com.ashraya.customer.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

	private String orderStatus;
	
	@JsonInclude(Include.NON_NULL)
	private Integer disbutionId;
	
	@JsonInclude(Include.NON_NULL)
	private WaterSupplierResponse supplier;
	
	@JsonInclude(Include.NON_NULL)
	private OrderGeoLocationResponse orderGeoLocation;
	
	private String message;
	
	@JsonInclude(Include.NON_NULL)
	private Double amount;
	
	@JsonInclude(Include.NON_NULL)
	private String currency;
	
	
}
