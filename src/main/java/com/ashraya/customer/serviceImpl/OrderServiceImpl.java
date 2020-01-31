package com.ashraya.customer.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.constants.DistributionStatus;
import com.ashraya.customer.domain.DeliveryGeoLocationMapper;
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

    @Override
    public List<WaterTanker> getAllWaterTankerCategory() {
        return waterTankerRepository.findAll();
    }

    @Override
    public OrderResponse placeOrder(WaterDistributionPayload waterDistributionDto) {
        DeliveryGeoLocationPayload deliveryGeoLocationDto = OrderValidationUtil.validateGeoLocationParams(waterDistributionDto.getDeliveryGeoLocation());
        List<GeoLocation> locations = geoLocationRepository.findByGeoLocation(deliveryGeoLocationDto.getLatitute(), deliveryGeoLocationDto.getLongitute(),
                        deliveryGeoLocationDto.getRadius());
        return (locations.isEmpty()) ? buildOrderResponseFailure() : createOrder(deliveryGeoLocationDto, waterDistributionDto);
    }

    private OrderResponse createOrder(DeliveryGeoLocationPayload deliveryGeoLocationDto, WaterDistributionPayload waterDistributionDto) {
        OrderResponse orderResponse = null;
        WaterRecipient waterRecpient = waterRecpientRepository.findOne(waterDistributionDto.getUserId());
        WaterTanker waterTanker = waterTankerRepository.findOne(waterDistributionDto.getTankerCategoryId());
        WaterSupplier waterSupplier = waterSupplierRepository.findBySupplierId(waterDistributionDto.getSupplierId());

        Timestamp timestamp = CommonUtil.convertStringToTimestamp(waterDistributionDto.getDateTime());
        if (waterRecpient != null && waterTanker != null) {
            DeliveryGeoLocation geoLocation = deliveryGeoLocationRepository
                            .save(DeliveryGeoLocationMapper.mapToDeliveryGeoLocation(deliveryGeoLocationDto, waterRecpient, timestamp));
            WaterDistribution waterDistribution = waterDistributionRepository
                            .save(DeliveryGeoLocationMapper.mapToWaterDistribution(waterRecpient, waterTanker, waterSupplier, geoLocation, timestamp));
            orderResponse = buildOrderResponseSuccess(geoLocation, waterDistribution);
        }
        return orderResponse;
    }

    private OrderResponse buildOrderResponseSuccess(DeliveryGeoLocation geoLocation, WaterDistribution waterDistribution) {
        OrderResponse orderResponse = new OrderResponse();
        OrderGeoLocationResponse orderGeoLocation = new OrderGeoLocationResponse();
        orderGeoLocation.setDatetime(geoLocation.getDateTime().toString());
        orderGeoLocation.setLatitude(geoLocation.getLatitute());
        orderGeoLocation.setLongitute(geoLocation.getLongitute());
        orderResponse.setAmount(null);
        orderResponse.setCurrency(Constants.ORDER_CURRENCY);
        orderResponse.setDisbutionId(waterDistribution.getId());
        orderResponse.setOrderGeoLocation(orderGeoLocation);
        orderResponse.setOrderStatus(Constants.ORDER_STATUS);
        orderResponse.setMessage(Constants.ORDER_MESSAGE_SUCCESS);
        orderResponse.setSupplier(null);
        return orderResponse;
    }

    private OrderResponse buildOrderResponseFailure() {
        return OrderResponse.builder().orderStatus(Constants.ORDER_STATUS_FAIL).message(Constants.ORDER_MESSAGE_FAILURE).build();
    }

    @Override
    public OrderResponse cancelOrder(Integer distributionId, Integer userId) {
        WaterDistribution waterDistribution = waterDistributionRepository.findByIdAndWaterRecipientId(distributionId, userId);
        if (null != waterDistribution) {
            if (waterDistribution.getDistributionStatus().equals(DistributionStatus.cancel)) {
                return OrderResponse.builder().orderStatus(Constants.ORDER_STATUS_FAIL).message(Constants.CANCEL_ORDER_ALREADY).build();
            }
            waterDistribution.setDistributionStatus(DistributionStatus.cancel);
            waterDistribution.setDateTime(new Timestamp(new Date().getTime()));
            waterDistributionRepository.save(waterDistribution);
            return OrderResponse.builder().orderStatus(Constants.ORDER_STATUS).message(Constants.CANCEL_ORDER_SUCCESS).build();
        } else {
            return OrderResponse.builder().orderStatus(Constants.ORDER_STATUS_FAIL).message(Constants.CANCEL_ORDER_FAILED).build();
        }
    }
}
