package com.brokeragefirmchallenge.bfchallenge.api.dto;

import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderResponseDTO {
    private Long orderId;
    private Long customerId;
    private String assetName;
    private Order.OrderSide side;
    private int size;
    private BigDecimal price;
    private Order.OrderStatus status;
    private LocalDateTime createDate;

    public OrderResponseDTO(Order order) {
        this.orderId = order.getOrderId();
        this.customerId = order.getCustomerId();
        this.assetName = order.getAssetName();
        this.side = order.getOrderSide();
        this.size = order.getSize();
        this.price = order.getPrice();
        this.status = order.getStatus();
        this.createDate = order.getCreateDate();
    }
}
