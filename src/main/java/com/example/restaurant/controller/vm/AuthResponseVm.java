package com.example.restaurant.controller.vm;

import com.example.restaurant.model.BaseEntity;
import com.example.restaurant.model.RoleCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseVm  {

    private String token;
    private List<RoleCode> userRoles;

    private Long accountId;

}
