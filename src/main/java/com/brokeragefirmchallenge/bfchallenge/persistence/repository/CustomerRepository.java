package com.brokeragefirmchallenge.bfchallenge.persistence.repository;

import com.brokeragefirmchallenge.bfchallenge.persistence.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {

    Optional<Customers> findByEmail(String email);
    Optional<Customers> findByCustomerId(Long customerId);
}
