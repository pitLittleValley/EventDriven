package com.eventdriven.axon.demo.ordersservice.data;

import com.eventdriven.axon.demo.ordersservice.command.OrderStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="orders")
@Data
public class OrderEntity {
    @Id
    @Column(unique = true)
    public String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
