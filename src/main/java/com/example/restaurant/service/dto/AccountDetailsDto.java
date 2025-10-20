package com.example.restaurant.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Account Details Dto" , description = "Account Details Dto for Account Details Table")
public class AccountDetailsDto {


    private Long id;
    @Schema(name = "Age", description = "User age" , example = "25")
    @NotNull(message = "age.notnull")
    @Min(value = 16 , message = ("age.size"))
    private Integer age;
    @Schema(name = "Phone Number", description = "user phone number" , example = "01234567812")
    @Size(max = 15, message = "phoneNumber.size")
    private String phoneNumber;
    @Schema(name = "Address", description = "user address" , example = "Alexandria")
    @Size(max = 255, message = "address.size")
    private String address;

    private AccountDto account;
}
