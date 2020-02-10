package com.ashraya.customer.model;

import java.sql.Timestamp;

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

import com.ashraya.customer.constants.DistributionStatus;
import com.ashraya.customer.constants.PaymentMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "water_distribution")
public class WaterDistribution {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "recipient_id", referencedColumnName = "rec_id")
	private WaterRecipient waterRecipient;
	
	@Column(name = "distribution_status")
	@Enumerated(EnumType.STRING)
	private DistributionStatus distributionStatus;
	
	@Column(name = "date_time")
	private Timestamp dateTime;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
	private WaterSupplier waterSupplier;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "delivery_geo_location_id", referencedColumnName = "id")
	private DeliveryGeoLocation deliveryGeoLocation;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "water_tanker_category_id", referencedColumnName = "id")
	private WaterTanker WaterTanker;
	
	@Column(name = "discount")
	private Float discount;
	
	@Column(name = "amount")
	private Float amount;
	
	@Column(name = "payment_mode")
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
}
