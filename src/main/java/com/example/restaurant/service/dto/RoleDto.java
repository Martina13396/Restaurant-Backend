package com.example.restaurant.service.dto;

import com.example.restaurant.model.RoleCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Role Dto" , description = "Role Dto for Role Table")
public class RoleDto {
    @Schema(name = "Role ID", description = "id for role" , example = "1")
    private Long id;
    @Schema(name = "code", description = "code for role" , example = "USER")
    @NotNull(message = "role.code.notnull")
    private RoleCode code;

    List<AccountDto> users;
}
