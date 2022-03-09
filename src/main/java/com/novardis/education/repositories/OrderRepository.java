package com.novardis.education.repositories;

import com.novardis.education.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByOrderNumber(Long orderNumber);

    Optional<Order> findByOrderNumber(Long orderNumber);

    void deleteAllByOrderNumber(Long orderNumber);
}
