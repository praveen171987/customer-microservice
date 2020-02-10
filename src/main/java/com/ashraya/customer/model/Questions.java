package com.ashraya.customer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ashraya.customer.constants.Mandatory;
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
@Table(name = "questions")
public class Questions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	private Integer questionId;

	@Column(name = "hint")
	private String hint;

	@Column(name = "question")
	private String question;
	
	@Column(name = "mandatory")
	@Enumerated(EnumType.STRING)
	private Mandatory mandatory;
	
	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private State state;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "data_type_id", referencedColumnName = "data_type_id")
	private DataTypeMaster dataTypeMaster;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "field_type_id", referencedColumnName = "field_type_id")
	private FieldTypeMaster fieldTypeMaster;

}
