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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ashraya.customer.constants.AppUsage;
import com.ashraya.customer.constants.LoginType;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "water_recipient")
public class WaterRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rec_id")
    private Integer id;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "app_usage")
    @Enumerated(EnumType.STRING)
    private AppUsage appUsage;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "google_ac_id", referencedColumnName = "id")
    private GoogleAccountInfo googleAccountInfo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fb_ac_id", referencedColumnName = "id")
    private FacebookAccountInfo facebookAccountInfo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "cat_id")
    private Category category;

    @Column(name = "gst_num")
    private String gstNumber;

    @Column(name = "login_account")
    @Enumerated(EnumType.STRING)
    private LoginType loginAccount;
}
