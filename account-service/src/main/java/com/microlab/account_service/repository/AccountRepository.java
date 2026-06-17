package com.microlab.account_service.repository;

import com.microlab.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByAccountNumber(Integer accountNumber);

    Optional<Account> findByCustomerMobileNumber(String mobileNumber);


}
