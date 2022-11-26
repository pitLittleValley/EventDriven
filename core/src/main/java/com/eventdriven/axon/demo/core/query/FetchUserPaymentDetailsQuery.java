package com.eventdriven.axon.demo.core.query;

import lombok.Data;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotter;
import org.springframework.stereotype.Component;

@Component
@Data
public class FetchUserPaymentDetailsQuery {

    private String userId;
}
