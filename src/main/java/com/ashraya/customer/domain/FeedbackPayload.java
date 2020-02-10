package com.ashraya.customer.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackPayload {

	private Integer orderId;

	private Integer userId;

	private List<QuestionFeedbackPayload> questionFeedbackPayload;
}
