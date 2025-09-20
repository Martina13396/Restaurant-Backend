package com.example.restaurant.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Account Dto" , description = "Account Dto for Account table")
public class AccountDto {
    @Schema(name = "Account ID", description = "id for account" , example = "1")
    private Long id;
    @Schema(name = "Username", description = "user name" , example = "Ahmed Ali")
    @NotBlank(message = "username.notblank")
    @Size(min = 7 , message = "username.size")
    private String username;
    @Schema(name = "Password", description = "Account Password" , example = "tStrong@123")
    @NotBlank(message = "password.notblank")
    @Size(min = 7, message = "password.size")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "password.pattern"
    )
    private String password;

    private List<RoleDto> roles;


}
