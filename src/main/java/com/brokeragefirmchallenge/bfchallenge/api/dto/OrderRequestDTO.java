package com.brokeragefirmchallenge.bfchallenge.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderRequestDTO {
    @NotNull
    private Long customerId;

    @NotBlank
    private String assetName;

    @NotBlank
    private String side;

    @Min(1)
    private int size;

    @Min(0)
    private double price;
}
