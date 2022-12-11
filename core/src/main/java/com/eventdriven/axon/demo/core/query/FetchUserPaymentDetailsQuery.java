package com.eventdriven.axon.demo.core.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Data
public class FetchUserPaymentDetailsQuery {

    private String userId;
}
