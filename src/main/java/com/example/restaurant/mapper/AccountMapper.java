package com.example.restaurant.mapper;

import com.example.restaurant.controller.vm.AuthResponseVm;
import com.example.restaurant.model.security.Account;

import com.example.restaurant.service.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface AccountMapper {
AccountMapper ACCOUNT_MAPPER = Mappers.getMapper( AccountMapper.class );



    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto accountDto);

    List<Account> toAccountList(List<AccountDto> accountDtos);

    List<AccountDto> toAccountDtos(List<Account> accounts);

    AuthResponseVm toAuthResponseVm(AccountDto accountDto);
}
