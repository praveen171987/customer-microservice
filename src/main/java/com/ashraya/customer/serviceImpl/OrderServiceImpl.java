package com.ashraya.customer.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashraya.customer.LoggerService;
import static com.ashraya.customer.constants.Constants.*;
import com.ashraya.customer.constants.DistributionStatus;
import com.ashraya.customer.domain.OrderMapper;
import com.ashraya.customer.domain.DeliveryGeoLocationPayload;
import com.ashraya.customer.domain.OrderGeoLocationResponse;
import com.ashraya.customer.domain.OrderResponse;
import com.ashraya.customer.domain.WaterDistributionPayload;
import com.ashraya.customer.model.DeliveryGeoLocation;
import com.ashraya.customer.model.GeoLocation;
import com.ashraya.customer.model.WaterDistribution;
import com.ashraya.customer.model.WaterRecipient;
import com.ashraya.customer.model.WaterSupplier;
import com.ashraya.customer.model.WaterTanker;
import com.ashraya.customer.repository.DeliveryGeoLocationRepository;
import com.ashraya.customer.repository.GeoLocationRepository;
import com.ashraya.customer.repository.WaterDistributionRepository;
import com.ashraya.customer.repository.WaterRecpientRepository;
import com.ashraya.customer.repository.WaterSupplierRepository;
import com.ashraya.customer.repository.WaterTankerRepository;
import com.ashraya.customer.service.OrderService;
import com.ashraya.customer.util.CommonUtil;
import com.ashraya.customer.util.OrderValidationUtil;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private WaterTankerRepository waterTankerRepository;

	@Autowired
	private WaterDistributionRepository waterDistributionRepository;

	@Autowired
	private WaterRecpientRepository waterRecpientRepository;

	@Autowired
	private DeliveryGeoLocationRepository deliveryGeoLocationRepository;

	@Autowired
	private GeoLocationRepository geoLocationRepository;

	@Autowired
	private WaterSupplierRepository waterSupplierRepository;

	@Autowired
	private LoggerService log;

	private static final String className = OrderServiceImpl.class.getName();

	@Override
	public List<WaterTanker> getAllWaterTankerCategory() {
		log.printStart(className, "getAllWaterTankerCategory");
		List<WaterTanker> categories = waterTankerRepository.findAll();
		log.printEnd(className, "getAllWaterTankerCategory");
		return categories;
	}

	@Override
	public OrderResponse placeOrder(WaterDistributionPayload waterDistributionDto) throws ParseException {
		log.printStart(className, "placeOrder");

		DeliveryGeoLocationPayload deliveryGeoLocationDto = OrderValidationUtil
				.validateGeoLocationParams(waterDistributionDto.getDeliveryGeoLocation());

		List<GeoLocation> locations = geoLocationRepository.findByGeoLocation(deliveryGeoLocationDto.getLatitute(),
				deliveryGeoLocationDto.getLongitute(), deliveryGeoLocationDto.getRadius());

		log.printEnd(className, "placeOrder");

		return (locations.isEmpty()) ? buildOrderResponseFailure()
				: createOrder(deliveryGeoLocationDto, waterDistributionDto);
	}

	private OrderResponse createOrder(DeliveryGeoLocationPayload deliveryGeoLocationDto,
			WaterDistributionPayload waterDistributionDto) throws ParseException {
		log.printStart(className, "createOrder");

		OrderResponse orderResponse = null;
		WaterRecipient waterRecpient = waterRecpientRepository.findOne(waterDistributionDto.getUserId());
		WaterTanker waterTanker = waterTankerRepository.findOne(waterDistributionDto.getTankerCategoryId());
		WaterSupplier waterSupplier = waterSupplierRepository.findBySupplierId(waterDistributionDto.getSupplierId());

		Timestamp timestamp = CommonUtil.convertStringToTimestamp(waterDistributionDto.getDateTime());

		if (waterRecpient != null && waterTanker != null) {
			DeliveryGeoLocation geoLocation = deliveryGeoLocationRepository
					.save(OrderMapper.mapToDeliveryGeoLocation(deliveryGeoLocationDto, waterRecpient, timestamp));

			WaterDistribution waterDistribution = waterDistributionRepository.save(OrderMapper
					.mapToWaterDistribution(waterRecpient, waterTanker, waterSupplier, geoLocation, timestamp));

			orderResponse = buildOrderResponseSuccess(geoLocation, waterDistribution);
		}
		log.printEnd(className, "createOrder");
		return orderResponse;
	}

	private OrderResponse buildOrderResponseSuccess(DeliveryGeoLocation geoLocation,
			WaterDistribution waterDistribution) {
		log.printStart(className, "buildOrderResponseSuccess");

		OrderResponse orderResponse = new OrderResponse();
		OrderGeoLocationResponse orderGeoLocation = new OrderGeoLocationResponse();
		orderGeoLocation.setDatetime(geoLocation.getDateTime().toString());
		orderGeoLocation.setLatitude(geoLocation.getLatitute());
		orderGeoLocation.setLongitute(geoLocation.getLongitute());
		orderResponse.setAmount(null);
		orderResponse.setCurrency(ORDER_CURRENCY);
		orderResponse.setDisbutionId(waterDistribution.getId());
		orderResponse.setOrderGeoLocation(orderGeoLocation);
		orderResponse.setOrderStatus(ORDER_STATUS);
		orderResponse.setMessage(ORDER_MESSAGE_SUCCESS);
		orderResponse.setSupplier(null);

		log.printEnd(className, "buildOrderResponseSuccess");
		return orderResponse;
	}

	private OrderResponse buildOrderResponseFailure() {
		return OrderResponse.builder().orderStatus(ORDER_STATUS_FAIL).message(ORDER_MESSAGE_FAILURE).build();
	}

	@Override
	public OrderResponse cancelOrder(Integer distributionId) {
		log.printStart(className, "cancelOrder");

		WaterDistribution waterDistribution = waterDistributionRepository.findById(distributionId);
		if (waterDistribution == null) {
			log.printEnd(className, "cancelOrder");
			return buildOrderResponse(ORDER_STATUS_FAIL, CANCEL_ORDER_FAILED);
		}
		if (waterDistribution.getDistributionStatus().equals(DistributionStatus.cancel)) {
			log.printEnd(className, "cancelOrder");
			return buildOrderResponse(ORDER_STATUS_FAIL, CANCEL_ORDER_ALREADY);
		}

		waterDistribution.setDistributionStatus(DistributionStatus.cancel);
		waterDistribution.setDateTime(new Timestamp(new Date().getTime()));
		waterDistributionRepository.save(waterDistribution);
		log.printEnd(className, "cancelOrder");
		return buildOrderResponse(ORDER_STATUS, CANCEL_ORDER_SUCCESS);

	}

	private OrderResponse buildOrderResponse(String status, String message) {
		return OrderResponse.builder().orderStatus(status).message(message).build();
	}
}
