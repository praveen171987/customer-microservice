package com.ashraya.customer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ashraya.customer.constants.Constants;
import com.ashraya.customer.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    FeedbackService feedbackService;

    @InjectMocks
    FeedbackController feedbackController;

    @Test
    public void whenValidInputForGetQuetions_thenReturns200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get(Constants.CUSTOMER_BASE_PATH + "getQuestion").content(objectMapper.writeValueAsString(OrderControllerTestData.getAllQuetions())))
                        .andExpect(status().isOk());
    }

    @Test
    public void whenValidInputForFeedback_thenReturns200() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post(Constants.CUSTOMER_BASE_PATH + "feedback").contentType("application/json")
                        .content(objectMapper.writeValueAsString(OrderControllerTestData.feedback()))).andExpect(status().isOk());
    }
}
