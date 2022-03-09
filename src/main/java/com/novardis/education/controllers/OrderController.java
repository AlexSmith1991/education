package com.novardis.education.controllers;

import com.novardis.education.dto.OrderDto;
import com.novardis.education.exceptions.InternalException;
import com.novardis.education.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Retryable(maxAttempts = 3, value = InternalException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    @RequestMapping(value = "orders/create", method = RequestMethod.POST)
    public ResponseEntity<Void> createOrder(@RequestBody OrderDto orderDto){
        orderService.createOrder(orderDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
