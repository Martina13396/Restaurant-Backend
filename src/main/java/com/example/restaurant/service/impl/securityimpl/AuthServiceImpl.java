package com.example.restaurant.service.impl.securityimpl;

import com.example.restaurant.controller.vm.AuthRequestVm;
import com.example.restaurant.controller.vm.AuthResponseVm;
import com.example.restaurant.mapper.AccountMapper;
import com.example.restaurant.model.RoleCode;
import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.dto.RoleDto;
import com.example.restaurant.service.securityService.AccountService;
import com.example.restaurant.service.securityService.AuthService;
import com.example.restaurant.service.token.TokenHandler;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private AccountService  accountService;
    private TokenHandler tokenHandler;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public AuthServiceImpl(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;

        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    @Lazy
    public void setTokenHandler(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override

    public AuthResponseVm login(AuthRequestVm authRequestVm) {
       AccountDto accountDto = accountService.getAccountByUsername(authRequestVm.getUsername());
       if(! passwordEncoder.matches(authRequestVm.getPassword(), accountDto.getPassword())) {
           throw new RuntimeException("invalid.password");
       }
      AuthResponseVm authResponseVm = AccountMapper.ACCOUNT_MAPPER.toAuthResponseVm(accountDto);
       authResponseVm.setToken(tokenHandler.createToken(accountDto));
       authResponseVm.setUserRoles(getAccountRoles(accountDto));
       authResponseVm.setAccountId(accountDto.getId());
       return authResponseVm;
    }

    @Override

    public AuthResponseVm signup(AccountDto accountDto) throws SystemException {
     AccountDto savedAccountDto = accountService.createAccount(accountDto);
     if(Objects.isNull(savedAccountDto)) {
       throw new SystemException("account.failed");
     }
    AuthResponseVm authResponseVm = AccountMapper.ACCOUNT_MAPPER.toAuthResponseVm(savedAccountDto);
     authResponseVm.setToken(tokenHandler.createToken(savedAccountDto));
     authResponseVm.setUserRoles(getAccountRoles(savedAccountDto));
     return authResponseVm;
    }

    private List<RoleCode> getAccountRoles(AccountDto accountDto) {
        return accountDto.getRoles().stream().map(RoleDto::getCode).collect(Collectors.toList());
    }
}
