package com.ashraya.customer.serviceImpl;

import static com.ashraya.customer.constants.Constants.ORDER_STATUS;
import static com.ashraya.customer.constants.Constants.ORDER_STATUS_FAIL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashraya.customer.LoggerService;
import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.constants.State;
import com.ashraya.customer.domain.FeedbackPayload;
import com.ashraya.customer.domain.OrderResponse;
import com.ashraya.customer.domain.QuestionFeedbackPayload;
import com.ashraya.customer.model.Questions;
import com.ashraya.customer.model.RecipientFeedback;
import com.ashraya.customer.model.WaterDistribution;
import com.ashraya.customer.model.WaterRecipient;
import com.ashraya.customer.repository.QuestionsRepository;
import com.ashraya.customer.repository.RecipientFeedbackRepository;
import com.ashraya.customer.repository.WaterDistributionRepository;
import com.ashraya.customer.repository.WaterRecpientRepository;
import com.ashraya.customer.service.FeedbackService;
import com.ashraya.customer.util.OrderValidationUtil;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private WaterDistributionRepository waterDistributionRepository;

    @Autowired
    private WaterRecpientRepository waterRecpientRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private RecipientFeedbackRepository recipientFeedbackRepository;

    @Autowired
    private OrderValidationUtil orderValidationUtil;

    private LoggerService log = LoggerService.createLogger(OrderServiceImpl.class.getName());

    private OrderResponse buildOrderResponse(String status, String message) {
        return OrderResponse.builder().orderStatus(status).message(message).build();
    }

    @Override
    public List<Questions> getAllQuestion() {
        log.printStart("getAllQuestion");
        return questionsRepository.findQuestionsByState(State.active);
    }

    @Override
    public OrderResponse feedback(FeedbackPayload feedbackPayload) {
        log.printStart("feedback");
        List<RecipientFeedback> recipientFeedbacks = recipientFeedbackRepository.findByWaterDistributionIdAndWaterRecipientId(feedbackPayload.getOrderId(),
                        feedbackPayload.getUserId());
        if (null != recipientFeedbacks && !recipientFeedbacks.isEmpty()) {
            log.printEnd("feedback");
            return buildOrderResponse(ORDER_STATUS_FAIL, Constants.RECIPIENT_FEEDBACK_ALREADY);
        }
        orderValidationUtil.validateFeedback(feedbackPayload);
        WaterDistribution waterDistribution = waterDistributionRepository.findById(feedbackPayload.getOrderId());
        if (null == waterDistribution) {
            log.printEnd("feedback");
            return buildOrderResponse(ORDER_STATUS_FAIL, Constants.ORDERID_NOT_FOUND);
        }
        WaterRecipient waterRecipient = waterRecpientRepository.findOne(feedbackPayload.getUserId());
        if (null == waterRecipient) {
            log.printEnd("feedback");
            return buildOrderResponse(ORDER_STATUS_FAIL, Constants.INVALID_USERID);
        }
        for (QuestionFeedbackPayload questionFeedbackPayload : feedbackPayload.getQuestionFeedbackPayload()) {
            if (null == questionFeedbackPayload.getId()) {
                log.printEnd("feedback");
                return buildOrderResponse(ORDER_STATUS_FAIL, "id " + Constants.NULL_MESSAGE);
            }
            Questions question = questionsRepository.findOne(questionFeedbackPayload.getId());
            if (null == question) {
                log.printEnd("feedback");
                return buildOrderResponse(ORDER_STATUS_FAIL, Constants.INVALID_QUETIONSID);
            }
            recipientFeedbackRepository.save(RecipientFeedback.builder().waterRecipient(waterRecipient).waterDistribution(waterDistribution)
                            .value(questionFeedbackPayload.getValue()).questions(question).build());
        }
        log.printEnd("feedback");
        return buildOrderResponse(ORDER_STATUS, Constants.RECIPIENT_FEEDBACK_SUCCESS);
    }
}
