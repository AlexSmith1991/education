package com.novardis.education.routes;

import com.novardis.education.configuration.TimeProperties;
import com.novardis.education.dto.OrderDto;
import com.novardis.education.mappers.OrderMapper;
import com.novardis.education.models.Order;
import com.novardis.education.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.apache.camel.Body;
import org.apache.camel.Exchange;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

@AllArgsConstructor
public class RouteService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final TimeProperties timeProperties;

    public void addToDatabase(@Body OrderDto orderDto, Exchange exchange){
        boolean isOrderExist = orderRepository.existsByOrderNumber(orderDto.getOrderNumber());
        Order order = null;
        if (isOrderExist){
            order = orderRepository.findByOrderNumber(orderDto.getOrderNumber()).get();
        } else {
            order = orderMapper.orderDtoToModel(orderDto);
        }
        order.setPreviousAttempt(LocalDateTime.now());
        order.setNextAttempt(LocalDateTime.now().plusSeconds(timeProperties.getAttemptRetryPeriod()));
        order = orderRepository.saveAndFlush(order);
        exchange.setProperty("order", order);
    }

    public void orderDelivered(Exchange exchange){
        Order order = exchange.getProperty("order", Order.class);
        boolean isOrderExist = orderRepository.existsByOrderNumber(order.getOrderNumber());
        if (isOrderExist){
            orderRepository.deleteAllByOrderNumber(order.getOrderNumber());
        }
    }

    public Object addCurrentTime(@Body String body){
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        return body + timestamp + "'";
    }

    public Object getOrderDto(@Body LinkedHashMap<String, Object> payload){
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNumber((Long) payload.get("order_number"));
        orderDto.setCreatedAt(((Timestamp) payload.get("created_at")).toLocalDateTime());
        orderDto.setUpdatedAt(((Timestamp) payload.get("updated_at")).toLocalDateTime());
        return orderDto;
    }
}
