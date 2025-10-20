package com.example.restaurant.model;

import com.example.restaurant.model.security.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo extends BaseEntity {



    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String message;

    private String accountName;

    private boolean isRead;
    private String subject;

    private Date replyDate;
    private String replyMessage;
    private boolean isReadReply;
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;




}
