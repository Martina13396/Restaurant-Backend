package com.example.restaurant.mapper;

import com.example.restaurant.model.security.Role;
import com.example.restaurant.service.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toRoleDto(Role role);

    Role toRole(RoleDto roleDto);

    List<Role> toRoleList(List<RoleDto> roleDtos);

    List<RoleDto> toRoleDtos(List<Role> roles);
}
