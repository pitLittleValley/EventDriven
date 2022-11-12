package com.eventdriven.axon.demo.ordersservice.saga;

import com.eventdriven.axon.demo.core.commands.ReserveProductCommand;
import com.eventdriven.axon.demo.ordersservice.event.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

@Saga
public class OrderSaga {

    private final transient CommandGateway commandGateway;

    public OrderSaga(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        ReserveProductCommand reserveProductCommand =
                ReserveProductCommand.builder()
                        .productId(orderCreatedEvent.getProductId())
                        .orderId(orderCreatedEvent.getOrderId())
                        .quantity(orderCreatedEvent.getQuantity())
                        .userId(orderCreatedEvent.getUserId())
                        .build();

        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            if(commandResultMessage.isExceptional()){
                // Start a compensating transaction
            }
        });
    }

}
