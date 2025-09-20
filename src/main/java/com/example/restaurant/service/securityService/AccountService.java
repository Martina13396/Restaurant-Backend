package com.example.restaurant.service.securityService;

import com.example.restaurant.service.dto.AccountDto;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountByUsername(String username);
}
