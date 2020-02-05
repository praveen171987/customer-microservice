package com.ashraya.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashraya.customer.model.WaterTanker;

@Repository
public interface WaterTankerRepository extends JpaRepository<WaterTanker, Integer> {

}
