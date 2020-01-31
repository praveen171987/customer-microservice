package com.ashraya.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaterDistributionPayload {

    private Integer userId;

    private Integer supplierId;

    private String dateTime;

    private Integer tankerCategoryId;

    private DeliveryGeoLocationPayload deliveryGeoLocation;
}
