package com.example.restaurant.repo;

import com.example.restaurant.model.security.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDetailsRepo extends JpaRepository<AccountDetails, Long> {
    AccountDetails getAccountDetailsByAccountId(Long accountId);
}
