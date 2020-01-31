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

    @GetMapping(value = "/getAllWaterTankerCategory")
    public List<WaterTanker> getAllWaterTankerCategory() {
        return orderService.getAllWaterTankerCategory();
    }
    
    @PostMapping(path = Constants.VERSION + "/placeOrder", consumes = "application/json", produces = "application/json")
    public OrderResponse placeOrder(@RequestBody WaterDistributionPayload waterDistributionDto) throws ParseException {
        return orderService.placeOrder(waterDistributionDto);
    }

    @GetMapping(value = Constants.VERSION + "/cancelOrder/{distributionId}/{userId}")
    public OrderResponse cancelOrder(@PathVariable("distributionId") Integer distributionId, @PathVariable("userId") Integer userId) {
        return orderService.cancelOrder(distributionId, userId);
    }
}
