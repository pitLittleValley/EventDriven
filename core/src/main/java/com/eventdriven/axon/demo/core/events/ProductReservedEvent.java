package com.eventdriven.axon.demo.core.events;

import lombok.Data;

@Data

public class ProductReservedEvent {
    private  String productId;
    private  String orderId;
    private  Integer quantity;
    private  String userId;
 }
