package com.bookstorevn.repositories;

import com.bookstorevn.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    List<ShoppingCart> findByApplicationUserId(String applicationUserId);
    Optional<ShoppingCart> findByApplicationUserIdAndProductId(String applicationUserId, Integer productId);
    Long countByApplicationUserId(String applicationUserId);
}
