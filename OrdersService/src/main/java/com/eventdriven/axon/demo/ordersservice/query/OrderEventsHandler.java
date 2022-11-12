package com.eventdriven.axon.demo.ordersservice.query;

import com.eventdriven.axon.demo.ordersservice.data.OrderEntity;
import com.eventdriven.axon.demo.ordersservice.data.OrdersRepository;
import com.eventdriven.axon.demo.ordersservice.event.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderEventsHandler {
    private final OrdersRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent event){
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);

        orderRepository.save(orderEntity);
    }
}
