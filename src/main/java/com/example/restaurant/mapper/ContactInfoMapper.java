package com.example.restaurant.mapper;

import com.example.restaurant.model.ContactInfo;
import com.example.restaurant.service.dto.ContactInfoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactInfoMapper {

    ContactInfoDto toContactInfoDto(ContactInfo contactInfo);

    ContactInfo toContactInfo(ContactInfoDto contactInfoDto);

    List<ContactInfoDto> toContactInfoDtos(List<ContactInfo> contactInfos);

    List<ContactInfo> toContactInfos(List<ContactInfoDto> contactInfoDtos);
}
