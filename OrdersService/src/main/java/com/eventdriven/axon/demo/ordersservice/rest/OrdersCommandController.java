package com.eventdriven.axon.demo.ordersservice.rest;

import com.eventdriven.axon.demo.ordersservice.command.CreateOrderCommand;
import com.eventdriven.axon.demo.ordersservice.command.OrderStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("orders")
public class OrdersCommandController {

    private final CommandGateway commandGateway;
    private final Environment env;
    private String returnValue;

    public OrdersCommandController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createOrder(@RequestBody CreateOrderRestModel createOrder) {

        CreateOrderCommand command = CreateOrderCommand
                .builder()
                .orderId(UUID.randomUUID().toString())
                .userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
                .productId(createOrder.getProductId())
                .quantity(createOrder.getQuantity())
                .addressId(createOrder.getAddressId())
                .orderStatus(OrderStatus.CREATED)
                .build();

        try {
            returnValue = commandGateway.sendAndWait(command);
        } catch (Exception e) {
            returnValue = e.getLocalizedMessage();
        }

        return returnValue;
    }
}
