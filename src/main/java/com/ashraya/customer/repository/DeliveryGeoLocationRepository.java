package com.ashraya.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashraya.customer.model.DeliveryGeoLocation;

public interface DeliveryGeoLocationRepository extends JpaRepository<DeliveryGeoLocation, Integer> {

}
