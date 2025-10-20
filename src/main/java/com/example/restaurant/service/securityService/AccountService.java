package com.example.restaurant.service.securityService;

import com.example.restaurant.service.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountByUsername(String username);

    AccountDto changePassword( String oldPassword, String newPassword);

    void deleteAccount(Long accountId);

    List<AccountDto> getAllAccounts();


}
