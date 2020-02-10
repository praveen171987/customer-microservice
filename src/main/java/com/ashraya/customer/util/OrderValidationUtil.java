package com.ashraya.customer.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.domain.DeliveryGeoLocationPayload;
import com.ashraya.customer.domain.FeedbackPayload;
import com.ashraya.customer.domain.PaymentPayload;
import com.ashraya.customer.exception.ValidationException;

@Component
public class OrderValidationUtil {

    public static DeliveryGeoLocationPayload validateGeoLocationParams(DeliveryGeoLocationPayload deliveryGeoLocationDto) {
        if (deliveryGeoLocationDto == null) {
            throw new ValidationException("GeoLocation" + Constants.NULL_MESSAGE);
        }
        if (deliveryGeoLocationDto.getLatitute() == null) {
            throw new ValidationException("Latitute" + Constants.NULL_MESSAGE);
        }
        if (deliveryGeoLocationDto.getLongitute() == null) {
            throw new ValidationException("Longitute" + Constants.NULL_MESSAGE);
        }
        if (deliveryGeoLocationDto.getRadius() == 0) {
            throw new ValidationException("Radius" + Constants.NULL_MESSAGE);
        }
        return deliveryGeoLocationDto;

    }

    public void validatePayment(PaymentPayload paymentPayload) {
        if (paymentPayload == null) {
            throw new ValidationException("PaymentPayload" + Constants.NULL_MESSAGE);
        }

        if (paymentPayload.getOrderId() == null) {
            throw new ValidationException("OrderId" + Constants.NULL_MESSAGE);
        }

        if (paymentPayload.getAmount() == null || paymentPayload.getAmount() == 0) {
            throw new ValidationException("Amount" + Constants.NULL_MESSAGE);
        }

        if (StringUtils.isEmpty(paymentPayload.getPaymentMode())) {
            throw new ValidationException("PaymentMode" + Constants.NULL_MESSAGE);
        }
    }

    public void validateFeedback(FeedbackPayload feedbackPayload) {
        if (feedbackPayload == null) {
            throw new ValidationException("feedbackPayload" + Constants.NULL_MESSAGE);
        }

        if (feedbackPayload.getOrderId() == null) {
            throw new ValidationException("OrderId" + Constants.NULL_MESSAGE);
        }

        if (feedbackPayload.getUserId() == null) {
            throw new ValidationException("UserId" + Constants.NULL_MESSAGE);
        }

        if (null == feedbackPayload.getQuestionFeedbackPayload() || feedbackPayload.getQuestionFeedbackPayload().isEmpty()) {
            throw new ValidationException("QuestionFeedbackPayload" + Constants.NULL_MESSAGE);
        }
    }
}
