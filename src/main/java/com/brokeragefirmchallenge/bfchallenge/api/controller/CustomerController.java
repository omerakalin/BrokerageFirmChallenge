package com.brokeragefirmchallenge.bfchallenge.api.controller;

import com.brokeragefirmchallenge.bfchallenge.api.dto.DepositRequestDTO;
import com.brokeragefirmchallenge.bfchallenge.api.dto.WithdrawalRequestDTO;
import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Asset;
import com.brokeragefirmchallenge.bfchallenge.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/{customerId}/deposit")
    public ResponseEntity<Void> depositMoney(
            @PathVariable Long customerId,
            @RequestBody DepositRequestDTO depositRequest) {
        customerService.depositMoney(customerId, depositRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{customerId}/withdraw")
    public ResponseEntity<Void> withdrawMoney(
            @PathVariable Long customerId,
            @RequestBody WithdrawalRequestDTO withdrawRequest) {
        customerService.withdrawMoney(customerId, withdrawRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{customerId}/assets")
    public ResponseEntity<List<Asset>> listAssets(@PathVariable Long customerId) {
        List<Asset> assets = customerService.listAssets(customerId);
        return ResponseEntity.ok(assets);
    }
}
