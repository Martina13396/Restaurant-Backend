package com.example.restaurant.model;

import com.example.restaurant.model.security.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "orders")
public class Order extends BaseEntity {

    private String code;

    @Column(name = "TOTAL_PRICE", nullable = false)
    private Double totalPrice;

    private Integer totalNumber;


    private String accountName;





    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL , fetch = FetchType.EAGER )
   private List<OrderProduct> orderProducts = new ArrayList<>();

    @ManyToOne
    private Account account;
}
