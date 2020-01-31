package com.ashraya.customer.domain;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaterSupplierResponse {

	private String name;
	
	private String vechicleNum;
	
	private Integer mobileNum;
	
}
