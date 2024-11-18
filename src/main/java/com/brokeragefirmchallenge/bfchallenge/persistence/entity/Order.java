package com.brokeragefirmchallenge.bfchallenge.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long customerId;

    private String assetName;

    @Enumerated(EnumType.STRING)
    private OrderSide orderSide; // BUY or SELL

    private int size; // Number of shares

    private BigDecimal price; // Price per share

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PENDING, MATCHED, CANCELED

    private LocalDateTime createDate;

    public enum OrderSide {
        BUY, SELL
    }

    public enum OrderStatus {
        PENDING, MATCHED, CANCELED
    }

    // Constructor to match the parameters used in OrderService
    public Order(Long customerId, String assetName, OrderSide orderSide, int size, BigDecimal price, OrderStatus status, LocalDateTime createDate) {
        this.customerId = customerId;
        this.assetName = assetName;
        this.orderSide = orderSide;
        this.size = size;
        this.price = price;
        this.status = status;
        this.createDate = createDate;
    }

}
