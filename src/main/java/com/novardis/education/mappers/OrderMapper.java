package com.novardis.education.mappers;

import com.novardis.education.dto.OrderDto;
import com.novardis.education.models.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public OrderDto orderToDto (Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setUpdatedAt(order.getUpdatedAt());
        return orderDto;
    }

    public Order orderDtoToModel (OrderDto orderDto){
        Order order = new Order();
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setCreatedAt(orderDto.getCreatedAt());
        order.setUpdatedAt(orderDto.getUpdatedAt());
        return order;
    }
}
