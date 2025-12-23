package com.bookstorevn.repositories;

import com.bookstorevn.models.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    List<ProductReview> findByProductId(Integer productId);
}
