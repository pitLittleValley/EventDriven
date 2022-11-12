package com.eventdriven.axon.demo.ordersservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
    private final String userId;
    private final String productId;
    private final Integer quantity;
    private final String addressId;
    private final OrderStatus orderStatus;
}
