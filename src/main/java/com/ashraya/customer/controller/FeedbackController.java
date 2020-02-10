package com.ashraya.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashraya.customer.LoggerService;
import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.domain.FeedbackPayload;
import com.ashraya.customer.domain.OrderResponse;
import com.ashraya.customer.model.Questions;
import com.ashraya.customer.service.FeedbackService;

@RestController
@RequestMapping(Constants.CUSTOMER)
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    private LoggerService log = LoggerService.createLogger(FeedbackController.class.getName());

    @GetMapping(value = Constants.VERSION + "/getQuestion")
    public List<Questions> getAllQuestion() {
        log.printStart("getAllQuestion");
        return feedbackService.getAllQuestion();
    }

    @PostMapping(value = Constants.VERSION + "/feedback")
    public OrderResponse feedback(@RequestBody FeedbackPayload feedbackPayload) {
        log.printStart(feedbackPayload.toString(), "feedback");
        return feedbackService.feedback(feedbackPayload);
    }
}
