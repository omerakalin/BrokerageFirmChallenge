package com.brokeragefirmchallenge.bfchallenge.model;

public enum OrderStatus {
    PENDING,    // Order is created but not yet matched
    MATCHED,    // Order has been matched and completed
    CANCELED    // Order has been canceled by the user
}
