package com.ashraya.customer.controller;


import com.ashraya.customer.domain.DeliveryGeoLocationPayload;
import com.ashraya.customer.domain.WaterDistributionPayload;;

public class OrderControllerTestData {

	 public static WaterDistributionPayload get() {
		 WaterDistributionPayload payload = new WaterDistributionPayload();
		 payload.setUserId(1);
		 payload.setSupplierId(1);
		 payload.setTankerCategoryId(1);
		 payload.setDateTime("29/01/2020");
		 payload.setDeliveryGeoLocation(getData());
		 return payload;
	 }
	 
	 private static  DeliveryGeoLocationPayload getData() {
		 DeliveryGeoLocationPayload payload = new DeliveryGeoLocationPayload();
		 payload.setAddress("Indore");
		 payload.setLatitute(40.036027);
		 payload.setLongitute(-76.316528);
		 payload.setRadius(5);
		 payload.setProvider("Johne Doe");
		 payload.setSpeed(80.0);
		 
		 return payload;
	 }
	 

	public static WaterDistributionPayload getInvalidData() {
		 WaterDistributionPayload payload = get();
		 payload.getDeliveryGeoLocation().setRadius(0);
		 
		return payload;
	}
	 
}
