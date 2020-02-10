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
import com.ashraya.customer.domain.PaymentPayload;
import com.ashraya.customer.domain.WaterDistributionPayload;
import com.ashraya.customer.model.WaterTanker;
import com.ashraya.customer.service.OrderService;

@RestController
@RequestMapping(Constants.CUSTOMER)
public class OrderController {

    @Autowired
    private OrderService orderService;

    private LoggerService log = LoggerService.createLogger(OrderController.class.getName());

    @GetMapping(value = "/getAllWaterTankerCategory")
    public List<WaterTanker> getAllWaterTankerCategory() {
        log.printStart("getAllWaterTankerCategory");
        List<WaterTanker> response = orderService.getAllWaterTankerCategory();
        log.printEnd("getAllWaterTankerCategory");
        return response;
    }

    @PostMapping(path = Constants.VERSION + "/placeOrder", consumes = "application/json", produces = "application/json")
    public OrderResponse placeOrder(@RequestBody WaterDistributionPayload waterDistributionDto) throws ParseException {
        log.printStart(waterDistributionDto.toString(), "placeOrder");
        OrderResponse response = orderService.placeOrder(waterDistributionDto);
        log.printEnd("placeOrder");
        return response;
    }

    @GetMapping(value = Constants.VERSION + "/cancelOrder/{distributionId}")
    public OrderResponse cancelOrder(@PathVariable("distributionId") Integer distributionId) {
        log.printStart("cancelOrder");
        OrderResponse response = orderService.cancelOrder(distributionId);
        log.printEnd("cancelOrder");
        return response;
    }

    @PostMapping(path = Constants.VERSION + "/payment", consumes = "application/json", produces = "application/json")
    public OrderResponse payment(@RequestBody PaymentPayload paymentPayload) {
        log.printStart(paymentPayload.toString(), "payment");
        OrderResponse response = orderService.payment(paymentPayload);
        log.printEnd("payment");
        return response;
    }
}
