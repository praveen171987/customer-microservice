package com.ashraya.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderGeoLocationResponse {

    private Double latitude;

    private Double longitute;

    private String datetime;
}
