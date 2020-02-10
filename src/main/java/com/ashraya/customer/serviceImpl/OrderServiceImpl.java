package com.ashraya.customer.serviceImpl;

import static com.ashraya.customer.constants.Constants.CANCEL_ORDER_ALREADY;
import static com.ashraya.customer.constants.Constants.CANCEL_ORDER_FAILED;
import static com.ashraya.customer.constants.Constants.CANCEL_ORDER_SUCCESS;
import static com.ashraya.customer.constants.Constants.ORDERID_NOT_FOUND;
import static com.ashraya.customer.constants.Constants.ORDER_CURRENCY;
import static com.ashraya.customer.constants.Constants.ORDER_MESSAGE_FAILURE;
import static com.ashraya.customer.constants.Constants.ORDER_MESSAGE_SUCCESS;
import static com.ashraya.customer.constants.Constants.ORDER_STATUS;
import static com.ashraya.customer.constants.Constants.ORDER_STATUS_FAIL;
import static com.ashraya.customer.constants.Constants.PAYMENT_MODE_ADDED;
import static com.ashraya.customer.constants.Constants.PAYMENT_MODE_NOT_ALLOWED;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashraya.customer.LoggerService;
import com.ashraya.customer.constants.DistributionStatus;
import com.ashraya.customer.constants.PaymentMode;
import com.ashraya.customer.domain.DeliveryGeoLocationPayload;
import com.ashraya.customer.domain.OrderGeoLocationResponse;
import com.ashraya.customer.domain.OrderMapper;
import com.ashraya.customer.domain.OrderResponse;
import com.ashraya.customer.domain.PaymentPayload;
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
    private OrderValidationUtil orderValidationUtil;

    private LoggerService log = LoggerService.createLogger(OrderServiceImpl.class.getName());

    @Override
    public List<WaterTanker> getAllWaterTankerCategory() {
        log.printStart("getAllWaterTankerCategory");
        List<WaterTanker> categories = waterTankerRepository.findAll();
        log.printEnd("getAllWaterTankerCategory");
        return categories;
    }

    @Override
    public OrderResponse placeOrder(WaterDistributionPayload waterDistributionDto) throws ParseException {
        log.printStart("placeOrder");
        DeliveryGeoLocationPayload deliveryGeoLocationDto = OrderValidationUtil.validateGeoLocationParams(waterDistributionDto.getDeliveryGeoLocation());
        List<GeoLocation> locations = geoLocationRepository.findByGeoLocation(deliveryGeoLocationDto.getLatitute(), deliveryGeoLocationDto.getLongitute(),
                        deliveryGeoLocationDto.getRadius());
        log.printEnd("placeOrder");
        return (locations.isEmpty()) ? buildOrderResponseFailure() : createOrder(deliveryGeoLocationDto, waterDistributionDto);
    }

    private OrderResponse createOrder(DeliveryGeoLocationPayload deliveryGeoLocationDto, WaterDistributionPayload waterDistributionDto) throws ParseException {
        log.printStart("createOrder");
        OrderResponse orderResponse = null;
        WaterRecipient waterRecpient = waterRecpientRepository.findOne(waterDistributionDto.getUserId());
        WaterTanker waterTanker = waterTankerRepository.findOne(waterDistributionDto.getTankerCategoryId());
        WaterSupplier waterSupplier = waterSupplierRepository.findBySupplierId(waterDistributionDto.getSupplierId());

        Timestamp timestamp = CommonUtil.convertStringToTimestamp(waterDistributionDto.getDateTime());

        if (waterRecpient != null && waterTanker != null) {
            DeliveryGeoLocation geoLocation = deliveryGeoLocationRepository.save(OrderMapper.mapToDeliveryGeoLocation(deliveryGeoLocationDto, waterRecpient, timestamp));
            WaterDistribution waterDistribution = waterDistributionRepository
                            .save(OrderMapper.mapToWaterDistribution(waterRecpient, waterTanker, waterSupplier, geoLocation, timestamp));

            orderResponse = buildOrderResponseSuccess(geoLocation, waterDistribution);
        }
        log.printEnd("createOrder");
        return orderResponse;
    }

    private OrderResponse buildOrderResponseSuccess(DeliveryGeoLocation geoLocation, WaterDistribution waterDistribution) {
        log.printStart("buildOrderResponseSuccess");

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

        log.printEnd("buildOrderResponseSuccess");
        return orderResponse;
    }

    private OrderResponse buildOrderResponseFailure() {
        return OrderResponse.builder().orderStatus(ORDER_STATUS_FAIL).message(ORDER_MESSAGE_FAILURE).build();
    }

    @Override
    public OrderResponse cancelOrder(Integer distributionId) {
        log.printStart("cancelOrder");
        WaterDistribution waterDistribution = waterDistributionRepository.findById(distributionId);
        if (waterDistribution == null) {
            log.printEnd("cancelOrder");
            return buildOrderResponse(ORDER_STATUS_FAIL, CANCEL_ORDER_FAILED);
        }
        if (waterDistribution.getDistributionStatus().equals(DistributionStatus.cancel)) {
            log.printEnd("cancelOrder");
            return buildOrderResponse(ORDER_STATUS_FAIL, CANCEL_ORDER_ALREADY);
        }
        waterDistribution.setDistributionStatus(DistributionStatus.cancel);
        waterDistribution.setDateTime(new Timestamp(new Date().getTime()));
        waterDistributionRepository.save(waterDistribution);
        log.printEnd("cancelOrder");
        return buildOrderResponse(ORDER_STATUS, CANCEL_ORDER_SUCCESS);

    }

    private OrderResponse buildOrderResponse(String status, String message) {
        return OrderResponse.builder().orderStatus(status).message(message).build();
    }

    @Override
    public OrderResponse payment(PaymentPayload paymentPayload) {
        log.printStart("payment");
        orderValidationUtil.validatePayment(paymentPayload);
        WaterDistribution waterDistribution = waterDistributionRepository.findById(paymentPayload.getOrderId());
        if (waterDistribution == null) {
            log.printEnd("updatePaymentmode");
            return buildOrderResponse(ORDER_STATUS_FAIL, ORDERID_NOT_FOUND);
        }
        waterDistribution.setAmount(paymentPayload.getAmount());
        waterDistribution.setDiscount(paymentPayload.getDiscount());
        if (paymentPayload.getPaymentMode().equals(PaymentMode.gpay.name())) {
            waterDistribution.setPaymentMode(PaymentMode.gpay);
        } else if (paymentPayload.getPaymentMode().equals(PaymentMode.cash.name())) {
            waterDistribution.setPaymentMode(PaymentMode.cash);
        } else {
            log.printEnd("payment");
            return buildOrderResponse(ORDER_STATUS_FAIL, PAYMENT_MODE_NOT_ALLOWED + paymentPayload.getPaymentMode());
        }
        waterDistributionRepository.save(waterDistribution);
        log.printEnd("payment");
        return buildOrderResponse(ORDER_STATUS, PAYMENT_MODE_ADDED);
    }
}
