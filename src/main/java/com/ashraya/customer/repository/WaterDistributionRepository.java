package com.ashraya.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashraya.customer.model.WaterDistribution;

@Repository
public interface WaterDistributionRepository extends JpaRepository<WaterDistribution, Integer> {

    public WaterDistribution findById(Integer distributionId);
}
