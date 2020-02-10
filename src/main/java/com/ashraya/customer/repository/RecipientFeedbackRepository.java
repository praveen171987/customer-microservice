package com.ashraya.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashraya.customer.model.RecipientFeedback;

@Repository
public interface RecipientFeedbackRepository extends JpaRepository<RecipientFeedback, Integer> {

    public List<RecipientFeedback> findByWaterDistributionIdAndWaterRecipientId(Integer orderId, Integer userId);
}
