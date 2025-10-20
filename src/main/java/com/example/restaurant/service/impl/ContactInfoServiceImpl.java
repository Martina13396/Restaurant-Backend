package com.example.restaurant.service.impl;

import com.example.restaurant.mapper.AccountMapper;
import com.example.restaurant.mapper.ContactInfoMapper;
import com.example.restaurant.model.ContactInfo;
import com.example.restaurant.repo.ContactInfoRepo;
import com.example.restaurant.service.ContactInfoService;
import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.dto.ContactInfoDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

    private final AccountMapper accountMapper;

    private ContactInfoRepo contactInfoRepo;

    private ContactInfoMapper  contactInfoMapper;

    public ContactInfoServiceImpl(ContactInfoRepo contactInfoRepo, ContactInfoMapper contactInfoMapper, AccountMapper accountMapper) {
        this.contactInfoRepo = contactInfoRepo;
        this.contactInfoMapper = contactInfoMapper;
        this.accountMapper = accountMapper;

    }

    @Override
    public ContactInfoDto save(ContactInfoDto contactInfoDto) {


        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ContactInfo contactInfo = contactInfoMapper.toContactInfo(contactInfoDto);
        contactInfo.setAccount(accountMapper.toAccount(accountDto));
        contactInfo.setAccountName(accountDto.getUsername());

        contactInfo.setRead(false);

        contactInfo = contactInfoRepo.save(contactInfo);
      return contactInfoMapper.toContactInfoDto(contactInfo);


    }

    @Override
    public List<ContactInfoDto> findAllByAccountId(Long accountId) {

        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long currentAccountId = accountDto.getId();

        List<ContactInfo> contactInfos = contactInfoRepo.findAllByAccountIdOrderByIdAsc(currentAccountId);
        return contactInfoMapper.toContactInfoDtos(contactInfos);
    }

    @Override
    public List<ContactInfoDto> findAllOrderByAccountId() {

      List<ContactInfo> contactInfos = contactInfoRepo.findAllOrderByAccountId();
      return contactInfoMapper.toContactInfoDtos(contactInfos);
    }

    @Override
    public void markAsRead(Long id) {


        Optional<ContactInfo> contactInfoOpt = contactInfoRepo.findById(id);

        if (contactInfoOpt.isEmpty()) {

            throw new RuntimeException("Contact message not found with ID: " + id);
        }
        ContactInfo message = contactInfoOpt.get();

        if (!message.isRead()) {

            message.setRead(true);
            contactInfoRepo.save(message);


        }

    }

    @Override
    public Long getUnreadCount() {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentAccountId = accountDto.getId();

        return contactInfoRepo.countByAccountIdAndIsReadFalse(currentAccountId);
    }

    @Override
    public Long getGlobalUnreadCount() {
        return contactInfoRepo.countByIsReadFalse();
    }

    @Override
    public ContactInfoDto saveReplyMessage(Long id ,String replyMessage) {

      Optional<ContactInfo> contactInfoOptional  =  contactInfoRepo.findById(id);
      if (contactInfoOptional.isEmpty()) {
          throw new RuntimeException("messages.notFound.id");

      }

       contactInfoOptional.get().setReplyMessage(replyMessage);
        contactInfoOptional.get().setReplyDate(new Date());
        contactInfoOptional.get().setReadReply(false);
       ContactInfo saved = contactInfoRepo.save(contactInfoOptional.get());
        return contactInfoMapper.toContactInfoDto(saved);
    }

    @Override
    public List<ContactInfoDto> getReplyMessagesByAccountId() {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentAccountId = accountDto.getId();
        List<ContactInfo> currentContactInfo = contactInfoRepo.findAllByAccountIdOrderByIdAsc(currentAccountId);
        return contactInfoMapper.toContactInfoDtos(currentContactInfo);

    }

    @Override
    public Long getUnreadRepliesCount() {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentAccountId = accountDto.getId();
       return contactInfoRepo.countByAccountIdAndIsReadReplyFalse(currentAccountId);
    }

    @Override
    public void markAsReadReply(Long id) {

        Optional<ContactInfo> contactInfoOpt = contactInfoRepo.findById(id);

        if (contactInfoOpt.isEmpty()) {

            throw new RuntimeException("Contact message not found with ID: " + id);
        }
        ContactInfo message = contactInfoOpt.get();

        if (!message.isReadReply()) {

            message.setReadReply(true);
            contactInfoRepo.save(message);
        }

    }

    }



