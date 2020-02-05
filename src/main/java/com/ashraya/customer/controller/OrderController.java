package com.ashraya.customer.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashraya.customer.LoggerService;
import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.domain.OrderResponse;
import com.ashraya.customer.domain.WaterDistributionPayload;
import com.ashraya.customer.model.WaterTanker;
import com.ashraya.customer.service.OrderService;

@RestController
@RequestMapping(Constants.CUSTOMER)
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private LoggerService log;
	
	 private static final String className = OrderController.class.getName();

	@GetMapping(value = "/getAllWaterTankerCategory")
	public List<WaterTanker> getAllWaterTankerCategory() {
		log.printStart(className,"getAllWaterTankerCategory");
		List<WaterTanker> response = orderService.getAllWaterTankerCategory();
		log.printEnd(className,"getAllWaterTankerCategory");
		return response;
	}

	@PostMapping(path = Constants.VERSION + "/placeOrder", consumes = "application/json", produces = "application/json")
	public OrderResponse placeOrder(@RequestBody WaterDistributionPayload waterDistributionDto) throws ParseException {
		log.printStart(className,waterDistributionDto.toString(),"placeOrder");	
		OrderResponse response = orderService.placeOrder(waterDistributionDto);
		log.printEnd(className,"placeOrder");
		return response;
	}

	@GetMapping(value = Constants.VERSION + "/cancelOrder/{distributionId}")
	public OrderResponse cancelOrder(@PathVariable("distributionId") Integer distributionId) {
		log.printStart(className,"cancelOrder");
		OrderResponse response = orderService.cancelOrder(distributionId);
		log.printEnd(className,"cancelOrder");
		return response;
	}

}
