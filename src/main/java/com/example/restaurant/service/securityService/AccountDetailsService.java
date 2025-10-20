package com.example.restaurant.service.securityService;

import com.example.restaurant.service.dto.AccountDetailsDto;

public interface AccountDetailsService {
    AccountDetailsDto saveAccountDetails(AccountDetailsDto accountDetailsDto);
    AccountDetailsDto getAccountDetails();
}
