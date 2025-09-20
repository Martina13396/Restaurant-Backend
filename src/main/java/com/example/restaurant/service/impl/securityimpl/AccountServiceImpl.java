package com.example.restaurant.service.impl.securityimpl;

import com.example.restaurant.mapper.AccountMapper;
import com.example.restaurant.model.RoleCode;
import com.example.restaurant.model.security.Account;
import com.example.restaurant.model.security.Role;
import com.example.restaurant.repo.AccountRepo;
import com.example.restaurant.repo.RoleRepo;

import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.securityService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepo  accountRepo;
    private AccountMapper accountMapper;
    private PasswordEncoder passwordEncoder;
    private RoleRepo  roleRepo;


    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo,AccountMapper accountMapper ,@Lazy PasswordEncoder passwordEncoder ,RoleRepo roleRepo ) {

        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;

    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {

        if (Objects.nonNull(accountDto.getId())) {
            throw new RuntimeException("id.null");
        }

        if (accountRepo.getAccountByUsername(accountDto.getUsername()).isPresent()) {

            throw new RuntimeException("account.exist");

        }
        Account account = accountMapper.toAccount(accountDto);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        Role accountRole = new Role();
        accountRole.setCode(RoleCode.USER);
        roleRepo.save(accountRole);
        accountRole.setAccounts(Collections.singletonList(account));
        account.setRoles(Collections.singletonList(accountRole));
        accountRepo.save(account);
        return accountMapper.toAccountDto(account);



    }



    @Override
    public AccountDto getAccountByUsername(String username) {
       Optional<Account> account = accountRepo.getAccountByUsername(username);
       if (!account.isPresent()) {
           throw new IllegalArgumentException("account.not.found");
       }
       return accountMapper.toAccountDto(account.get());

    }
}
