package com.example.restaurant.service;

import com.example.restaurant.service.dto.ContactInfoDto;

import java.util.List;

public interface ContactInfoService {

    ContactInfoDto save(ContactInfoDto contactInfoDto);

    List<ContactInfoDto> findAllByAccountId(Long id);
    List<ContactInfoDto> findAllOrderByAccountId();

    void markAsRead(Long contactInfoId);

    Long getUnreadCount();

    Long getGlobalUnreadCount();

    ContactInfoDto saveReplyMessage(Long id , String replyMessage);

    List<ContactInfoDto> getReplyMessagesByAccountId();

    Long getUnreadRepliesCount();

    void markAsReadReply(Long id);
}
