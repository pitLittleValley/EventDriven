package com.eventdriven.axon.demo.ordersservice.event;

import com.eventdriven.axon.demo.ordersservice.command.OrderStatus;
import lombok.Data;

@Data
public class OrderCreatedEvent {

    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;
}
