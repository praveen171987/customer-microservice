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

import com.ashraya.customer.constants.AppUsage;
import com.ashraya.customer.constants.LoginType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "water_supplier")
public class WaterSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "app_usage")
    @Enumerated(EnumType.STRING)
    private AppUsage appUsage;

    @Column(name = "login_account")
    @Enumerated(EnumType.STRING)
    private LoginType loginAccount;

    @Column(name = "gst_num")
    private String gstNum;

    @Column(name = "vechicle_number")
    private String vechicleNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "google_ac_id", referencedColumnName = "id")
    private GoogleAccountInfo googleAccountInfo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fb_ac_id", referencedColumnName = "id")
    private FacebookAccountInfo facebookAccountInfo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "cat_id")
    private Category category;

    @Column(name = "device_id")
    private String deviceId;
}
