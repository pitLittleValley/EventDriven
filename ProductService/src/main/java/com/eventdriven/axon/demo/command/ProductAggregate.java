package com.eventdriven.axon.demo.command;

import com.eventdriven.axon.demo.core.commands.ReserveProductCommand;
import com.eventdriven.axon.demo.core.events.ProductCreatedEvent;
import com.eventdriven.axon.demo.core.events.ProductReservedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) throws IllegalArgumentException {
        // Validate Create Product Command
        if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be less or equals to Zero.");
        }

        if (createProductCommand.getTitle() == null
                || createProductCommand.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        // apply dispatch de event to all event handlers defined inside this aggregate
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand){
        // Here quantity has de last quantity because AXON build the Aggregate from the events history
        if(quantity < reserveProductCommand.getQuantity()){
            throw new IllegalArgumentException("Insufficient number of item in stock");
        }

        ProductReservedEvent productReservedEvent = new ProductReservedEvent();
        BeanUtils.copyProperties(reserveProductCommand, productReservedEvent);

        AggregateLifecycle.apply(productReservedEvent);

    }


    /*  OJO NO debe añadirse lógica en este método. Solo usar para actualizar el estado del Agregado.
    This method will be used to initilize de current state of the aggregate with the latest information
    y para hacerlo hay que agregar las properties relacionadas con el producto en el agregado, con la anotación @AggretateIdentifier
    y en este metodo reasignarlas con del ProductCreatedEvent, que son las del command (BeanUtils.copyProperties)
    Las anotaciones @TargetAggregateIdentifier y @AggregateIdentifier son usadas por Axon para relacionar
    el  Command (CreateProductCommand) con el Agregado correcto
     */
    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.title = productCreatedEvent.getTitle();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent){
        this.productId = productReservedEvent.getProductId();
        this.quantity -= productReservedEvent.getQuantity();
    }
}
