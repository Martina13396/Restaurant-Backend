package com.example.restaurant.mapper;

import com.example.restaurant.model.security.AccountDetails;
import com.example.restaurant.service.dto.AccountDetailsDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {

    AccountDetailsDto  toAccountDetailsDto(AccountDetails accountDetails);

    AccountDetails toAccountDetails(AccountDetailsDto accountDetailsDto);

    List<AccountDetailsDto> toAccountDetailsDtos(List<AccountDetails> accountDetails);

    List<AccountDetails> toAccountDetailsList(List<AccountDetailsDto> accountDetailsDtos);
}
