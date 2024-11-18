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
public class WithdrawalRequestDTO {
    @NotNull
    private Long customerId;

    @Min(1)
    private double amount;

    @NotBlank
    private String iban;
}
