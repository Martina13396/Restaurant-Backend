package com.example.restaurant.repo;

import com.example.restaurant.model.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInfoRepo extends JpaRepository<ContactInfo, Long> {
   @Query("SELECT c FROM ContactInfo c order by c.account.id asc ")
    List<ContactInfo> findAllOrderByAccountId();

    List<ContactInfo> findAllByAccountIdOrderByIdAsc(long accountId);


    Long countByAccountIdAndIsReadFalse(Long id);

    Long countByIsReadFalse();

    Long countByAccountIdAndIsReadReplyFalse(Long id);
}
