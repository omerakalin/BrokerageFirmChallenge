package com.brokeragefirmchallenge.bfchallenge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.brokeragefirmchallenge.bfchallenge.persistence.entity.CompanyUsers;

@Repository
public interface UserRepository extends JpaRepository<CompanyUsers, Long> {

    // Get the role by userId from the companyusers table
    @Query("SELECT u.role FROM CompanyUsers u WHERE u.id = :userId ORDER BY u.id DESC")
    String findRoleByUserId(Long userId);  // Get role by userId

    // Find user by username in the companyusers table
    @Query("SELECT u FROM CompanyUsers u WHERE u.username = :username ORDER BY u.id DESC")
    CompanyUsers findByUsername(String username);

    @Query("SELECT c.customerId FROM Customers c WHERE c.name = :username")
    Long findUserIdByCustomerUsername(@Param("username") String username);

    @Query("SELECT u.id FROM CompanyUsers u WHERE u.username = :username")
    Long findUserIdByCompanyUserUsername(@Param("username") String username);
}
