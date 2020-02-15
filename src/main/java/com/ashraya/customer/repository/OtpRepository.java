package com.ashraya.customer.repository;

import org.springframework.data.repository.CrudRepository;

import com.ashraya.customer.model.Otp;

public interface OtpRepository extends CrudRepository<Otp, Integer> {

    public Otp findByWaterRecipientIdAndOtpNumber(Integer userId, String otpNumber);

}


  
