package com.example.restaurant.service.impl.securityimpl;

import com.example.restaurant.controller.vm.AuthRequestVm;
import com.example.restaurant.controller.vm.AuthResponseVm;
import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.securityService.AccountService;
import com.example.restaurant.service.securityService.AuthService;
import com.example.restaurant.service.token.TokenHandler;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    private AccountService  accountService;
    private TokenHandler tokenHandler;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AccountService accountService, TokenHandler tokenHandler, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.tokenHandler = tokenHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponseVm login(AuthRequestVm authRequestVm) {
       AccountDto accountDto = accountService.getAccountByUsername(authRequestVm.getUsername());
       if(! passwordEncoder.matches(authRequestVm.getPassword(), accountDto.getPassword())) {
           throw new RuntimeException("invalid.password");
       }
       return new AuthResponseVm(tokenHandler.createToken(accountDto));
    }

    @Override
    public AuthResponseVm signup(AccountDto accountDto) throws SystemException {
     AccountDto savedAccountDto = accountService.createAccount(accountDto);
     if(Objects.isNull(savedAccountDto)) {
       throw new SystemException("account.failed");
     }
     return new AuthResponseVm(tokenHandler.createToken(savedAccountDto));
    }
}
