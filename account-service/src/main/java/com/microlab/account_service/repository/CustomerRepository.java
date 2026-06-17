package com.microlab.account_service.repository;

import com.microlab.account_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByMobileNumber(String mobileNumber);

    Optional<Customer> findByEmail(String mobileNumber);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByEmailAndCustomerIdNot(String email, Integer customerId);

    boolean existsByMobileNumberAndCustomerIdNot(String mobileNumber, Integer customerId);

}
