package com.brokeragefirmchallenge.bfchallenge.service;

import com.brokeragefirmchallenge.bfchallenge.exception.ResourceNotFoundException;
import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Asset;
import com.brokeragefirmchallenge.bfchallenge.persistence.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    public Asset findAsset(Long customerId, String assetName) {
        return assetRepository.findByCustomerIdAndAssetName(customerId, assetName)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));
    }
}
