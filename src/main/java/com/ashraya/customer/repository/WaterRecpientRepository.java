package com.ashraya.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashraya.customer.model.WaterRecipient;

@Repository
public interface WaterRecpientRepository extends JpaRepository<WaterRecipient, Integer> {

    public WaterRecipient findByMobileNumber(String mobileNumber);

    public WaterRecipient findByFacebookAccountInfoId(Integer id);

    public WaterRecipient findByGoogleAccountInfoId(Integer id);
    
}
