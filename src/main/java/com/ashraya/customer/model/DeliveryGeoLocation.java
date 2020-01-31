package com.ashraya.customer.model;

import java.sql.Timestamp;

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
@Table(name = "delivery_geo_location")
public class DeliveryGeoLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "latitude")
    private Double latitute;

    @Column(name = "longitude")
    private Double longitute;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_user_id", referencedColumnName = "rec_id")
    private WaterRecipient waterRecipient;

    @Column(name = "date_time")
    private Timestamp dateTime;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "bearing")
    private Double bearing;

    @Column(name = "accuracy")
    private Double accuracy;

    @Column(name = "address")
    private String address;

    @Column(name = "provider")
    private String provider;

}
