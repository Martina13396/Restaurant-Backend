package com.example.restaurant.service.securityService;

import com.example.restaurant.controller.vm.AuthRequestVm;
import com.example.restaurant.controller.vm.AuthResponseVm;
import com.example.restaurant.service.dto.AccountDto;
import jakarta.transaction.SystemException;

public interface AuthService {


    AuthResponseVm login (AuthRequestVm authRequestVm);

    AuthResponseVm signup(AccountDto accountDto) throws SystemException;
}
