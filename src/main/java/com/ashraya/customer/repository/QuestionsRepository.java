package com.ashraya.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashraya.customer.constants.State;
import com.ashraya.customer.model.Questions;;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Integer>{

	public List<Questions> findQuestionsByState(Enum<State> state);
}
