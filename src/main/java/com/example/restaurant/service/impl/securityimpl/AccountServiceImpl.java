package com.example.restaurant.service.impl.securityimpl;

import com.example.restaurant.mapper.AccountMapper;
import com.example.restaurant.model.RoleCode;
import com.example.restaurant.model.security.Account;
import com.example.restaurant.model.security.Role;
import com.example.restaurant.repo.AccountRepo;
import com.example.restaurant.repo.RoleRepo;

import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.dto.RoleDto;
import com.example.restaurant.service.securityService.AccountService;
import com.example.restaurant.service.securityService.AuthService;
import com.example.restaurant.service.token.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private  AuthService authService;
    private final TokenHandler tokenHandler;
    private AccountRepo  accountRepo;
    private AccountMapper accountMapper;
    private PasswordEncoder passwordEncoder;
    private RoleRepo  roleRepo;


    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo, AccountMapper accountMapper , @Lazy PasswordEncoder passwordEncoder , RoleRepo roleRepo, TokenHandler tokenHandler) {

        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;

        this.tokenHandler = tokenHandler;
    }
    @Autowired
    @Lazy
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @CacheEvict(value = "accounts" , allEntries = true)
    public AccountDto createAccount(AccountDto accountDto) {

        if (Objects.nonNull(accountDto.getId())) {
            throw new RuntimeException("id.null");
        }

        if (accountRepo.getAccountByUsername(accountDto.getUsername()).isPresent()) {

            throw new RuntimeException("account.exist");

        }
        Account account = accountMapper.toAccount(accountDto);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        List<Role> rolesToAssign = new ArrayList<>();

        if (accountDto.getRoles() != null && !accountDto.getRoles().isEmpty()) {
            for (RoleDto roleDto : accountDto.getRoles()) {
                Role foundRole = roleRepo.findByCode(roleDto.getCode());
                if (foundRole != null) {
                    rolesToAssign.add(foundRole);
                } else {
                    throw new IllegalArgumentException("roles.not.exist");
                }



            }
        }
        account.setRoles(rolesToAssign);

        accountRepo.save(account);
        return accountMapper.toAccountDto(account);
    }


    @Override
    @Cacheable(value = "accounts" , key = "#username")
    public AccountDto getAccountByUsername(String username) {
       Optional<Account> account = accountRepo.getAccountByUsername(username);
       if (!account.isPresent()) {
           throw new IllegalArgumentException("account.not.found");
       }
        if (account.get().isDeleted()) {
            throw new RuntimeException("account.deactivated");
        }

       return accountMapper.toAccountDto(account.get());

    }

    @Override
    @CacheEvict(value = "accounts" , allEntries = true)
    public AccountDto changePassword( String oldPassword, String newPassword) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        AccountDto accountDto = (AccountDto) currentAuth.getPrincipal();
        Long currentAccountId = accountDto.getId();
        Optional<Account> accountOptional = accountRepo.findById(currentAccountId);
        if (!accountOptional.isPresent()) {
            throw new IllegalArgumentException("account.not.found");
        }
        Account account = accountOptional.get();

        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            throw new IllegalArgumentException("old.password.not.match");
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);

        Account saved = accountRepo.save(account);

       AccountDto updatedAccountDto = accountMapper.toAccountDto(saved);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(updatedAccountDto, null , currentAuth.getAuthorities() );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return updatedAccountDto;
    }

    @Override
    @CacheEvict(value = "accounts" , allEntries = true)
    public void deleteAccount(Long accountId) {
        Optional<Account> accountOptional = accountRepo.findById(accountId);
        if (accountOptional.isPresent()) {
           Account account = accountOptional.get();
           account.setDeleted(true);
           accountRepo.save(account);


        }

    }
    @Cacheable(value = "accounts" , key = "'allAccounts'")
    public List<AccountDto> getAllAccounts() {

        List<Account> accounts = accountRepo.findAllByIsDeletedFalse();
        if (accounts.isEmpty()) {
            return new  ArrayList<>();
        }
        return accounts.stream().map(accountMapper::toAccountDto).collect(Collectors.toList());
    }
}
