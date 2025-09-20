package com.example.restaurant.mapper;

import com.example.restaurant.model.security.Account;

import com.example.restaurant.service.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring" , uses = {RoleMapper.class})
public interface AccountMapper {




    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto accountDto);

    List<Account> toAccountList(List<AccountDto> accountDtos);

    List<AccountDto> toAccountDtos(List<Account> accounts);
}
