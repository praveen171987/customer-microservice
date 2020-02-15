package com.ashraya.customer.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.domain.OrderResponse;
import com.ashraya.customer.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
public class OrderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderController orderController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(orderService.cancelOrder(Mockito.anyInt())).thenReturn(OrderControllerTestData.getOrderResponse());

    }

    @Ignore
    @Test
    public void whenValidInputForPlaceOrder_thenReturns200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post(Constants.CUSTOMER_BASE_PATH + "placeOrder").contentType("application/json").content(objectMapper.writeValueAsString(OrderControllerTestData.get())))
                        .andExpect(status().isOk());
    }

    @Test
    public void whenInValidInput_thenReturns400() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post(Constants.CUSTOMER_BASE_PATH + "cancelOrder/1").contentType("application/json")
                        .content(objectMapper.writeValueAsString(OrderControllerTestData.getInvalidData()))).andExpect(status().is4xxClientError());
    }

    @Test
    public void whenValidInputForCancelOrder_thenReturns200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get(Constants.CUSTOMER_BASE_PATH + "cancelOrder/1").content(objectMapper.writeValueAsString(OrderControllerTestData.get()))).andExpect(status().isOk());
    }

    @Test
    public void whenOrderFailed_thenReturnOrderFailRespoonse() {
        when(orderService.cancelOrder(anyInt())).thenReturn(OrderControllerTestData.getCancelOrderOrderResponse());
        OrderResponse response = orderController.cancelOrder(1);
        assertThat(response.getMessage()).isEqualTo(Constants.CANCEL_ORDER_FAILED);
    }
    
    @Test
    public void whenValidInputForPayment_thenReturns200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post(Constants.CUSTOMER_BASE_PATH + "payment").contentType("application/json").content(objectMapper.writeValueAsString(OrderControllerTestData.getPaymentPayload()))).andExpect(status().isOk());
    }
    
    @Test
   public void getAllWaterTankerCategoryTest() throws Exception {
    	 ObjectMapper objectMapper = new ObjectMapper();
         mockMvc.perform(get(Constants.CUSTOMER_BASE_PATH + "getAllWaterTankerCategory").content(objectMapper.writeValueAsString(OrderControllerTestData.getWaterTenkers()))).andExpect(status().isOk());
     }
}
