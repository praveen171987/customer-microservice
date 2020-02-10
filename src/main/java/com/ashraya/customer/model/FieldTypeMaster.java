package com.ashraya.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ashraya.customer.constants.State;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "field_type_master")
public class FieldTypeMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "field_type_id")
	private Integer fieldTypeId;

	@Column(name = "field_type_name")
	private String fieldTypeName;

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private State state;
}
