package com.ashraya.customer.service;

import java.util.List;

import com.ashraya.customer.domain.FeedbackPayload;
import com.ashraya.customer.domain.OrderResponse;
import com.ashraya.customer.model.Questions;

public interface FeedbackService {

    public List<Questions> getAllQuestion();

    public OrderResponse feedback(FeedbackPayload feedbackPayload);

}
