package com.eventdriven.axon.demo.query;

import com.eventdriven.axon.demo.core.data.ProductEntity;
import com.eventdriven.axon.demo.core.data.ProductRepository;
import com.eventdriven.axon.demo.core.events.ProductCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
// Also called ProductProjection
public class ProductEventHandler {

    public ProductEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private final ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);

        productRepository.save(productEntity);
    }
}
