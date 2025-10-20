package com.example.restaurant.repo;

import com.example.restaurant.model.security.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

   Optional<Account> getAccountByUsername(String username);

    Account getAccountsById(Long id);

    List<Account> findAllByIsDeletedFalse();
}
