package com.example.restaurant.service.impl;

import com.example.restaurant.mapper.ContactInfoMapper;
import com.example.restaurant.model.ContactInfo;
import com.example.restaurant.repo.ContactInfoRepo;
import com.example.restaurant.service.ContactInfoService;
import com.example.restaurant.service.dto.ContactInfoDto;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

    private ContactInfoRepo contactInfoRepo;

    private ContactInfoMapper  contactInfoMapper;

    public ContactInfoServiceImpl(ContactInfoRepo contactInfoRepo, ContactInfoMapper contactInfoMapper) {
        this.contactInfoRepo = contactInfoRepo;
        this.contactInfoMapper = contactInfoMapper;
    }

    @Override
    public ContactInfoDto save(ContactInfoDto contactInfoDto) {


        ContactInfo contactInfo = contactInfoMapper.toContactInfo(contactInfoDto);

        contactInfo = contactInfoRepo.save(contactInfo);

        return contactInfoMapper.toContactInfoDto(contactInfo);

    }
}
