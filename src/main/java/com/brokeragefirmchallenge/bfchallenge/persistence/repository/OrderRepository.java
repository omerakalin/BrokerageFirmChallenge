package com.brokeragefirmchallenge.bfchallenge.persistence.repository;

import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByCustomerIdAndCreateDateBetween(Long customerId, LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findByStatus(Order.OrderStatus status);

    // Custom query to handle date range in String format
    default List<Order> findByCustomerIdAndDateRange(Long customerId, String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate); // Assuming the string is in ISO format
        LocalDateTime end = LocalDateTime.parse(endDate);     // Adjust if the format is different
        return findByCustomerIdAndCreateDateBetween(customerId, start, end);
    }
}
