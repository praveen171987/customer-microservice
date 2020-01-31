package com.ashraya.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashraya.customer.model.WaterSupplier;

@Repository
public interface WaterSupplierRepository extends JpaRepository<WaterSupplier, Integer> {

    public WaterSupplier findBySupplierId(Integer id);
}
