package com.brokeragefirmchallenge.bfchallenge.persistence.repository;

import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    // Find an asset by customer ID and asset name
    Optional<Asset> findByCustomerIdAndAssetName(Long customerId, String assetName);

    // Find all assets by customer ID
    List<Asset> findAllByCustomerId(Long customerId);
}


