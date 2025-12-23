package com.bookstorevn.repositories;

import com.bookstorevn.models.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Integer> {
    List<OrderHeader> findByApplicationUserId(String applicationUserId);
    List<OrderHeader> findByOrderStatusNotAndOrderDateBetween(String orderStatus, java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);
    List<OrderHeader> findByOrderStatusIn(java.util.Collection<String> statuses);
}
