package com.ashraya.customer.util;

import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.domain.DeliveryGeoLocationPayload;
import com.ashraya.customer.exception.ValidationException;

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

}
