package com.ashraya.customer.controller;

import java.util.ArrayList;
import java.util.List;

import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.constants.Mandatory;
import com.ashraya.customer.constants.State;
import com.ashraya.customer.domain.DeliveryGeoLocationPayload;
import com.ashraya.customer.domain.FeedbackPayload;
import com.ashraya.customer.domain.OrderResponse;
import com.ashraya.customer.domain.PaymentPayload;
import com.ashraya.customer.domain.QuestionFeedbackPayload;
import com.ashraya.customer.domain.WaterDistributionPayload;
import com.ashraya.customer.model.DataTypeMaster;
import com.ashraya.customer.model.FieldTypeMaster;
import com.ashraya.customer.model.Questions;

public class OrderControllerTestData {

    public static WaterDistributionPayload get() {
        WaterDistributionPayload payload = new WaterDistributionPayload();
        payload.setUserId(1);
        payload.setSupplierId(1);
        payload.setTankerCategoryId(1);
        payload.setDateTime("29/01/2020 12:21:33");
        payload.setDeliveryGeoLocation(getData());
        return payload;
    }

    private static DeliveryGeoLocationPayload getData() {
        DeliveryGeoLocationPayload payload = new DeliveryGeoLocationPayload();
        payload.setAddress("Indore");
        payload.setLatitute(40.036027);
        payload.setLongitute(-76.316528);
        payload.setRadius(5);
        payload.setProvider("Johne Doe");
        payload.setSpeed(80.0);
        return payload;
    }

    public static WaterDistributionPayload getInvalidData() {
        WaterDistributionPayload payload = get();
        payload.getDeliveryGeoLocation().setRadius(0);
        return payload;
    }

    public static OrderResponse getOrderResponse() {
        return OrderResponse.builder().amount(1.0).currency("rupee").disbutionId(1).build();
    }

    public static OrderResponse getCancelOrderOrderResponse() {
        return OrderResponse.builder().message(Constants.CANCEL_ORDER_FAILED).orderStatus(Constants.ORDER_STATUS_FAIL).build();
    }

    public static PaymentPayload getPaymentPayload() {
        return PaymentPayload.builder().amount(23.12f).discount(2.3f).paymentMode("gpay").orderId(22).build();
    }

    public static List<Questions> getAllQuetions() {
        List<Questions> questions = new ArrayList<Questions>();
        questions.add(Questions.builder().dataTypeMaster(new DataTypeMaster()).fieldTypeMaster(new FieldTypeMaster()).hint("km").mandatory(Mandatory.YES).question("Person Contact")
                        .state(State.active).questionId(7).build());
        return questions;
    }

    public static FeedbackPayload feedback() {
        List<QuestionFeedbackPayload> questionFeedbackPayload = new ArrayList<QuestionFeedbackPayload>();
        questionFeedbackPayload.add(QuestionFeedbackPayload.builder().id(7).value("YES").build());
        return FeedbackPayload.builder().orderId(12).userId(22).questionFeedbackPayload(questionFeedbackPayload).build();
    }
}
