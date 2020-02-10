package com.ashraya.customer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipient_feedback")
public class RecipientFeedback {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "value")
	private String value;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private WaterDistribution waterDistribution;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "recipient_id", referencedColumnName = "rec_id")
	private WaterRecipient waterRecipient;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "question_id", referencedColumnName = "question_id")
	private Questions questions;
}
