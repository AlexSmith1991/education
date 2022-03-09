package com.novardis.education.services.implementations;

import com.novardis.education.dto.OrderDto;
import com.novardis.education.exceptions.InternalException;
import com.novardis.education.services.interfaces.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public void createOrder(OrderDto orderDto) {
        int percent = (int) (Math.random() * 100) + 1;
        if (percent > 80){
            throw new InternalException("Error during order creating");
        }
    }
}
