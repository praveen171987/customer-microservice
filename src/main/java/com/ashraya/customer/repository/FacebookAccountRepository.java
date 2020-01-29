package com.ashraya.customer.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ashraya.customer.model.FacebookAccountInfo;

public interface FacebookAccountRepository extends CrudRepository<FacebookAccountInfo, Integer> {

    @Query("SELECT id FROM FacebookAccountInfo c WHERE c.emailId = :emailId")
    Integer findByEmailId(@Param("emailId") String emailId);
}
