package com.ashraya.customer.domain;

import java.sql.Timestamp;

import com.ashraya.customer.constants.DistributionStatus;
import com.ashraya.customer.model.DeliveryGeoLocation;
import com.ashraya.customer.model.WaterDistribution;
import com.ashraya.customer.model.WaterRecipient;
import com.ashraya.customer.model.WaterSupplier;
import com.ashraya.customer.model.WaterTanker;

public class OrderMapper {

    public static DeliveryGeoLocation mapToDeliveryGeoLocation(DeliveryGeoLocationPayload deliveryGeoLocationDto, WaterRecipient waterRecpient, Timestamp timeStamp) {
        return DeliveryGeoLocation.builder()
        		.latitute(deliveryGeoLocationDto.getLatitute())
        		.longitute(deliveryGeoLocationDto.getLongitute())
                .address(deliveryGeoLocationDto.getAddress())
                .bearing(deliveryGeoLocationDto.getBearing())
                .dateTime(timeStamp).speed(deliveryGeoLocationDto.getSpeed())
                .accuracy(deliveryGeoLocationDto.getAccuracy())
                .provider(deliveryGeoLocationDto.getProvider())
                .waterRecipient(waterRecpient)
                .build();
    }

    public static WaterDistribution mapToWaterDistribution(WaterRecipient waterRecpient, WaterTanker waterTanker, WaterSupplier waterSupplier, DeliveryGeoLocation geoLocation,
                    Timestamp timeStamp) {
        return WaterDistribution.builder()
        		.distributionStatus(DistributionStatus.scheduled)
        		.waterRecipient(waterRecpient)
        		.waterSupplier(waterSupplier)
        		.WaterTanker(waterTanker)
                .dateTime(timeStamp).deliveryGeoLocation(geoLocation)
                .build();
    }

}
