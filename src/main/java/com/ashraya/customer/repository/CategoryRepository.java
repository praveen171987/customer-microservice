package com.ashraya.customer.repository;

import org.springframework.data.repository.CrudRepository;

import com.ashraya.customer.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
