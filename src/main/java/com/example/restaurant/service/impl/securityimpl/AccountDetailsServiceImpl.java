package com.example.restaurant.service.impl.securityimpl;

import com.example.restaurant.mapper.AccountDetailsMapper;
import com.example.restaurant.model.security.Account;
import com.example.restaurant.model.security.AccountDetails;
import com.example.restaurant.repo.AccountDetailsRepo;
import com.example.restaurant.repo.AccountRepo;
import com.example.restaurant.service.dto.AccountDetailsDto;
import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.securityService.AccountDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {
    private AccountDetailsRepo accountDetailsRepo;
    private AccountDetailsMapper accountDetailsMapper;
    private AccountRepo  accountRepo;

    public AccountDetailsServiceImpl(AccountDetailsRepo accountDetailsRepo, AccountDetailsMapper accountDetailsMapper , AccountRepo  accountRepo) {
        this.accountDetailsRepo = accountDetailsRepo;
        this.accountDetailsMapper = accountDetailsMapper;
        this.accountRepo = accountRepo;
    }

    @Override
    public AccountDetailsDto saveAccountDetails(AccountDetailsDto accountDetailsDto) {
      AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Long currentAccountId = accountDto.getId();
      Account linkedAccount = accountRepo.getAccountsById(currentAccountId);
      if (linkedAccount == null) {
          throw new RuntimeException("account.not.found");
      }

      AccountDetails accountDetailsToSave = accountDetailsMapper.toAccountDetails(accountDetailsDto);
      accountDetailsToSave.setAccount(linkedAccount);
      AccountDetails savedDetails = accountDetailsRepo.save(accountDetailsToSave);
      return accountDetailsMapper.toAccountDetailsDto(savedDetails);




    }

    @Override
    public AccountDetailsDto getAccountDetails() {
      AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Long currentAccountId = accountDto.getId();
      AccountDetails accountDetails = accountDetailsRepo.getAccountDetailsByAccountId(currentAccountId);
      if (accountDetails == null) {
         return null;
      }
      return accountDetailsMapper.toAccountDetailsDto(accountDetails);

    }
}
