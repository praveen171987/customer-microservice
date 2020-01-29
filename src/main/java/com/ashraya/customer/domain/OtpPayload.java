package com.ashraya.customer.domain;

import lombok.Data;

@Data
public class OtpPayload {
    private Integer userId;
    private String otpNumber;
}
