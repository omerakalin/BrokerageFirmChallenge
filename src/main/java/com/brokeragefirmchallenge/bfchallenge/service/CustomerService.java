package com.brokeragefirmchallenge.bfchallenge.service;

import com.brokeragefirmchallenge.bfchallenge.api.dto.DepositRequestDTO;
import com.brokeragefirmchallenge.bfchallenge.api.dto.WithdrawalRequestDTO;
import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Asset;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final AssetRepository assetRepository;

    @Autowired
    public CustomerService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public void depositMoney(Long customerId, DepositRequestDTO depositRequest) {
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")
                .orElseThrow(() -> new RuntimeException("Asset not found"));
        tryAsset.setUsableSize(tryAsset.getUsableSize() + depositRequest.getAmount());
        assetRepository.save(tryAsset);
    }

    public void withdrawMoney(Long customerId, WithdrawalRequestDTO withdrawalRequest) {
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")
                .orElseThrow(() -> new RuntimeException("Asset not found"));
        if (tryAsset.getUsableSize() < withdrawalRequest.getAmount()) {
            throw new RuntimeException("Insufficient funds");
        }
        tryAsset.setUsableSize(tryAsset.getUsableSize() - withdrawalRequest.getAmount());
        assetRepository.save(tryAsset);
    }

    public List<Asset> listAssets(Long customerId) {
        return assetRepository.findAllByCustomerId(customerId);
    }
}
