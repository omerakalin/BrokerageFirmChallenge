package com.brokeragefirmchallenge.bfchallenge.service;

import com.brokeragefirmchallenge.bfchallenge.api.dto.OrderRequestDTO;
import com.brokeragefirmchallenge.bfchallenge.api.dto.OrderResponseDTO;
import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Asset;
import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Order;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.OrderRepository;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AssetRepository assetRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, AssetRepository assetRepository) {
        this.orderRepository = orderRepository;
        this.assetRepository = assetRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {

        Asset asset = assetRepository.findByCustomerIdAndAssetName(orderRequest.getCustomerId(), orderRequest.getAssetName())
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        if (!"TRY".equals(asset.getCurrency())) {
            throw new RuntimeException("Only assets with currency TRY are accepted.");
        }

        if (orderRequest.getSide().equals("SELL") && asset.getUsableSize() < orderRequest.getSize()) {
            throw new RuntimeException("Insufficient usable size for sale.");
        }

        Order order = new Order(
                orderRequest.getCustomerId(),
                orderRequest.getAssetName(),
                Order.OrderSide.valueOf(orderRequest.getSide()),
                orderRequest.getSize(),
                BigDecimal.valueOf(orderRequest.getPrice()),
                Order.OrderStatus.PENDING,
                LocalDateTime.now()
        );

        Order savedOrder = orderRepository.save(order);

        // If the order is a SELL order, update usable size
        if (orderRequest.getSide().equals("SELL")) {
            asset.setUsableSize(asset.getUsableSize() - orderRequest.getSize());
            assetRepository.save(asset);
        }

        return new OrderResponseDTO(savedOrder);
    }

    public List<OrderResponseDTO> listOrders(Long customerId, String startDate, String endDate) {
        List<Order> orders = orderRepository.findByCustomerIdAndDateRange(customerId, startDate, endDate);
        return orders.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (Order.OrderStatus.PENDING.equals(order.getStatus())) {
            order.setStatus(Order.OrderStatus.CANCELED);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Only pending orders can be canceled");
        }
    }
}