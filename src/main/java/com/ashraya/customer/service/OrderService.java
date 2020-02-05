package com.ashraya.customer.service;

import java.text.ParseException;
import java.util.List;


import com.ashraya.customer.domain.OrderResponse;
import com.ashraya.customer.domain.WaterDistributionPayload;
import com.ashraya.customer.model.WaterTanker;

public interface OrderService {
	

	public List<WaterTanker> getAllWaterTankerCategory();
	
	public OrderResponse placeOrder(WaterDistributionPayload waterDistributionDto) throws ParseException;
	
	public OrderResponse cancelOrder(Integer distributionId);
}
