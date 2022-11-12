package com.eventdriven.axon.demo.ordersservice.rest;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateOrderRestModel {
    private String productId;
    private Integer quantity;
    private String addressId;
}
